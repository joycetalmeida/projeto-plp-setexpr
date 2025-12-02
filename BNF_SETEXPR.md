# BNF da Linguagem SetExpr
## Extensão da Linguagem Funcional 1 com Teoria de Conjuntos

---

## Estrutura Principal

```bnf
Programa ::= Expressao

Valor ::= ValorConcreto

Expressao ::= ExpBinaria
           | ExpUnaria
           | ExpDeclaracao
           | Id
           | Aplicacao
           | IfThenElse
           | ValorConcreto

ValorConcreto ::= ValorInteiro
               | ValorBooleano
               | ValorConjunto
               | ValorFuncao
```

---

## Valores Concretos

```bnf
ValorInteiro ::= Digito+

ValorBooleano ::= "true" | "false"

ValorConjunto ::= "{" "}"
               | "{" ListExp "}"

ValorFuncao ::= "fn" Id "=>" Expressao
```

**Nota sobre Conjuntos Aninhados:**
- SetExpr suporta conjuntos de conjuntos naturalmente
- Exemplos: `{{1, 2}, {3, 4}}`, `{{}, {1}, {1, 2}}`
- A profundidade de aninhamento é ilimitada
- Comparações funcionam recursivamente

---

## Expressões Unárias

```bnf
ExpUnaria ::= "-" Expressao
           | "not" Expressao
           | "#" Expressao
           | "flatten" Expressao
           | "powerset" Expressao
```

**Operadores Unários:**
- `-` : Negação aritmética
- `not` : Negação lógica
- `#` : Cardinalidade de conjunto
- `flatten` : Achata conjunto de conjuntos em um único nível
- `powerset` : Gera o conjunto das partes (todos os subconjuntos)

---

## Expressões Binárias

### Operações Aritméticas
```bnf
ExpAritmetica ::= Expressao "+" Expressao
               | Expressao "-" Expressao
               | Expressao "*" Expressao
               | Expressao "/" Expressao
               | Expressao "%" Expressao
```

### Operações Lógicas
```bnf
ExpLogica ::= Expressao "and" Expressao
           | Expressao "or" Expressao
```

### Operações Relacionais
```bnf
ExpRelacional ::= Expressao "==" Expressao
               | Expressao "!=" Expressao
               | Expressao "<" Expressao
               | Expressao ">" Expressao
               | Expressao "<=" Expressao
               | Expressao ">=" Expressao
```

### Operações de Conjunto
```bnf
ExpConjunto ::= Expressao "union" Expressao
             | Expressao "inter" Expressao
             | Expressao "diff" Expressao
             | Expressao "in" Expressao
             | Expressao "subset" Expressao
             | Expressao "superset" Expressao
             | Expressao "psubset" Expressao
             | Expressao "psuperset" Expressao
             | Expressao "disjoint" Expressao
             | Expressao "cross" Expressao
             | "flatten" Expressao
             | "powerset" Expressao
             | Expressao "..." Expressao

ExpRange ::= Expressao "..." Expressao
```

**Operadores de Conjunto:**
- `union` : União (A ∪ B)
- `inter` : Interseção (A ∩ B)
- `diff` : Diferença (A \ B)
- `in` : Pertencimento (x ∈ A)
- `subset` : Subconjunto (A ⊆ B)
- `superset` : Superconjunto (A ⊇ B)
- `psubset` : Subconjunto próprio (A ⊂ B)
- `psuperset` : Superconjunto próprio (A ⊃ B)
- `disjoint` : Conjuntos disjuntos (A ∩ B = ∅)
- `cross` : Produto cartesiano (A × B)
- `flatten` : Achata conjunto de conjuntos (⋃ A)
- `powerset` : Conjunto das partes (℘(A))
- `...` : Range (gera sequência de inteiros)

### Agrupamento de Operações Binárias
```bnf
ExpBinaria ::= ExpAritmetica
            | ExpLogica
            | ExpRelacional
            | ExpConjunto
```

---

## Declarações

```bnf
ExpDeclaracao ::= "let" DeclaracaoFuncional "in" Expressao

DeclaracaoFuncional ::= DecVariavel
                     | DecFuncao
                     | DecComposta

DecVariavel ::= "var" Id "=" Expressao

DecFuncao ::= "fun" ListId "=" Expressao

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional
```

---

## Aplicação de Funções

```bnf
Aplicacao ::= Id "(" ListExp ")"
           | ExpFuncao "(" Expressao ")"

ExpFuncao ::= "fn" Id "=>" Expressao
```

---

## Estruturas de Controle

```bnf
IfThenElse ::= "if" Expressao "then" Expressao "else" Expressao
```

---

## Estruturas Auxiliares

```bnf
ListId ::= Id
        | Id ListId

ListExp ::= Expressao
         | Expressao "," ListExp

Id ::= Letra (Letra | Digito | "_")*

Letra ::= "a".."z" | "A".."Z"

Digito ::= "0".."9"
```

---

## Precedência de Operadores (do maior para o menor)

| Precedência | Operadores | Associatividade | Categoria |
|-------------|------------|-----------------|-----------|
| 1 (maior) | `()` | N/A | Parênteses |
| 2 | `-`, `not`, `#`, `flatten`, `powerset` | Direita | Unários |
| 3 | `*`, `/`, `%` | Esquerda | Multiplicativos |
| 4 | `cross` | Esquerda | Produto Cartesiano |
| 5 | `...` | Esquerda | Range |
| 6 | `+`, `-` | Esquerda | Aditivos |
| 7 | `union`, `inter`, `diff`, `subset`, `superset`, `psubset`, `psuperset`, `disjoint` | Esquerda | Conjuntos |
| 8 | `in` | Esquerda | Pertencimento |
| 9 | `<`, `>`, `<=`, `>=` | Esquerda | Relacionais |
| 10 | `==`, `!=` | Esquerda | Igualdade |
| 11 | `and` | Esquerda | Conjunção |
| 12 | `or` | Esquerda | Disjunção |
| 13 | `if-then-else` | Direita | Condicional |
| 14 (menor) | `let-in` | Direita | Declaração |

---

## Classes Auxiliares (Java)

### Definições
```
DefFuncao - Definição de função com parâmetros e corpo
DefVariavel - Definição de variável com valor
```

### Tipos
```
Tipo - Interface base para tipos
TipoInteiro - Tipo inteiro
TipoBooleano - Tipo booleano
TipoConjunto - Tipo conjunto
TipoFuncao - Tipo função
TipoPolimorfico - Tipo polimórfico para conjuntos heterogêneos
```

### Ambientes de Compilação
```
AmbienteCompilacao - Interface para ambiente de tipos
ContextoCompilacao - Contexto de compilação com escopo
AmbienteCompilacaoMap - Implementação com HashMap
```

### Ambientes de Execução
```
AmbienteFuncional - Interface base para ambiente funcional
AmbienteExecucaoFuncional - Ambiente de execução funcional
ContextoFuncional - Contexto funcional com closures
ContextoExecucaoFuncional - Contexto de execução funcional
AmbienteExecucaoMap - Implementação com HashMap
```

### Valores
```
ValorInteiro - Valor inteiro concreto
ValorBooleano - Valor booleano concreto
ValorConjunto - Valor conjunto concreto (Set<Valor>)
ValorFuncao - Valor função (closure)
```

### Expressões de Conjunto
```
ExpConjunto - Literal de conjunto
ExpUniao - União de conjuntos
ExpIntersecao - Interseção de conjuntos
ExpDiferenca - Diferença de conjuntos
ExpPertencimento - Pertencimento a conjunto
ExpSubconjunto - Relação de subconjunto
ExpSuperconjunto - Relação de superconjunto
ExpSubconjuntoProprio - Subconjunto próprio
ExpSuperconjuntoProprio - Superconjunto próprio
ExpDisjuntos - Conjuntos disjuntos
ExpCardinalidade - Cardinalidade de conjunto
ExpIgualdade - Igualdade entre valores
ExpDesigualdade - Desigualdade entre valores
```

### Expressões Funcionais
```
ExpFuncao - Expressão lambda (fn x => corpo)
ExpAplicacao - Aplicação de função
ExpLet - Let binding
ExpId - Identificador/Variável
```

---

## Exemplos de Programas SetExpr

### Operações de Conjunto
```setexpr
{1, 2, 3} union {3, 4, 5}
{1, 2, 3, 4} inter {3, 4, 5}
{1, 2, 3, 4} diff {2, 4}
3 in {1, 2, 3}
{1, 2} subset {1, 2, 3, 4}
{1, 2, 3} superset {1, 2}
{1, 2} psubset {1, 2, 3}
{1, 2} disjoint {3, 4}
#{1, 2, 3, 4, 5}
{1, 2, 3} == {1, 2, 3}
```

### Conjuntos de Conjuntos (Conjuntos Aninhados)
```setexpr
# Conjunto vazio de conjuntos
{}

# Conjunto contendo conjuntos
{{1, 2}, {3, 4}}

# Conjunto de partes (power set parcial)
{{}, {1}, {2}, {1, 2}}

# União de conjuntos de conjuntos
{{1, 2}, {3}} union {{4, 5}}

# Interseção de conjuntos de conjuntos
{{1, 2}, {3, 4}} inter {{1, 2}, {5, 6}}

# Pertencimento de conjunto em conjunto de conjuntos
{1, 2} in {{1, 2}, {3, 4}}

# Cardinalidade de conjunto de conjuntos
#{{1, 2}, {3, 4}, {5}}
# Conjuntos aninhados em múltiplos níveis
{{{1}}, {{2, 3}}}
```

### Operações de Distribuição
```setexpr
# Produto cartesiano - gera pares ordenados
{1, 2} cross {3, 4}
# Resultado: {{1, 3}, {1, 4}, {2, 3}, {2, 4}}

# Flatten - achata conjunto de conjuntos
flatten {{1, 2}, {3, 4}, {5}}
# Resultado: {1, 2, 3, 4, 5}

# Power set - conjunto das partes
powerset {1, 2, 3}
# Resultado: {{}, {1}, {2}, {3}, {1, 2}, {1, 3}, {2, 3}, {1, 2, 3}}
# Produto cartesiano triplo (aninhado)
{1, 2} cross ({3, 4} cross {5, 6})

# Flatten de conjuntos profundamente aninhados
flatten {{{1, 2}}, {{3}}}
# Resultado: {{1, 2}, {3}}

# Cardinalidade do power set
#(powerset {1, 2, 3})
# Resultado: 8 (2^3)

# União distribuída com flatten
flatten ({{1, 2}, {3}} union {{4, 5}})
# Resultado: {1, 2, 3, 4, 5}
```

### Operações com Range
```setexpr
# Range simples
1...5
# Resultado: {1, 2, 3, 4, 5}

# Range maior
1...10
# Resultado: {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}

# Range decrescente
10...1
# Resultado: {10, 9, 8, 7, 6, 5, 4, 3, 2, 1}

# União de ranges
(1...5) union (6...10)
# Resultado: {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}

# Interseção de ranges
(1...10) inter (5...15)
# Resultado: {5, 6, 7, 8, 9, 10}

# Pertencimento em range
3 in (1...10)
# Resultado: true

# Cardinalidade de range
#(1...100)
# Resultado: 100

# Diferença de ranges
(1...10) diff (5...7)
# Resultado: {1, 2, 3, 4, 8, 9, 10}

# Range como base para outras operações
powerset (1...3)
# Resultado: conjunto das partes de {1, 2, 3}
```

### Funções e Let Bindings
### Funções e Let Bindingsúltiplos níveis
{{{1}}, {{2, 3}}}
```

### Funções e Let Bindings
```setexpr
let var x = {1, 2, 3} in x union {4, 5}

let fun f x = x union {10} in f({1, 2, 3})

let fun double x = {y : y in x, 2 * y} in
  double({1, 2, 3})
```

### Estruturas de Controle
```setexpr
if #{1, 2, 3} > 2 then
  {1, 2, 3} union {4}
else
  {1, 2, 3}
```

### Expressões Compostas
```setexpr
let var conjunto1 = {1, 2, 3, 4, 5},
    var conjunto2 = {4, 5, 6, 7, 8} in
  if conjunto1 disjoint conjunto2 then
    conjunto1 union conjunto2
  else
    conjunto1 inter conjunto2
```

---

## Semântica Operacional

### Regras de Avaliação para Conjuntos

**União:**
```
E1 ⇒ V1    E2 ⇒ V2    V1 = {x1, ..., xn}    V2 = {y1, ..., ym}
─────────────────────────────────────────────────────────────
              E1 union E2 ⇒ {x1, ..., xn, y1, ..., ym}
```

**Interseção:**
```
E1 ⇒ V1    E2 ⇒ V2    V1 = {x1, ..., xn}    V2 = {y1, ..., ym}
─────────────────────────────────────────────────────────────
          E1 inter E2 ⇒ {z | z ∈ V1 ∧ z ∈ V2}
```

**Pertencimento:**
```
E1 ⇒ V1    E2 ⇒ V2    V2 = {x1, ..., xn}
───────────────────────────────────────
      E1 in E2 ⇒ V1 ∈ {x1, ..., xn}
```

**Cardinalidade:**
```
E ⇒ V    V = {x1, ..., xn}
──────────────────────────
        #E ⇒ n
```

---

**SetExpr - BNF Completa**  
*Linguagem Funcional com Teoria de Conjuntos*  
*Centro de Informática - UFPE - 2025*
