# SetExpr - Linguagem Funcional para Teoria de Conjuntos

**Disciplina:** (IN1007) Paradigmas de Linguagens de Programação - Cin/UFPE  
**Professor:** Augusto Sampaio  
**Equipe:** Joyce Almeida, Rafael Moura, Ricardo Azevedo  
**Data:** Dezembro de 2025

**[Slides da Apresentação](https://docs.google.com/presentation/d/1vDxkcSzXv5n8ovFE0-ypeOXpedVMhpVY6QS7jWMnf0k/edit?slide=id.p12#slide=id.p12)**

---

## Índice

1. [Introdução](#1-introdução)
2. [Características](#2-características)
3. [BNF da Linguagem](#3-bnf-da-linguagem)
4. [Operações Implementadas](#4-operações-implementadas)
5. [Implementação](#5-implementação)
6. [Compilação e Execução](#6-compilação-e-execução)
7. [Exemplos de Uso](#7-exemplos-de-uso)
8. [Testes](#8-testes)
9. [Arquitetura](#9-arquitetura)
10. [Documentação Completa](#10-documentação-completa)

---

## 1. Introdução

SetExpr é uma extensão da **Linguagem Funcional 1 (LF1)** desenvolvida para demonstrar conceitos de paradigmas funcionais aplicados à teoria de conjuntos. A linguagem oferece suporte nativo para manipulação de conjuntos com sintaxe próxima à notação matemática.

### 1.1 Motivação

A escolha de conjuntos como extensão da LF1 fundamenta-se em:

- **Relevância Matemática**: Conjuntos são estruturas fundamentais na matemática e ciência da computação
- **Expressividade**: Sintaxe declarativa próxima à notação matemática (∪, ∩, ∈)
- **Polimorfismo**: Conjuntos podem conter inteiros, booleanos, funções e conjuntos aninhados
- **Paradigma Funcional**: Integração natural com funções lambda, closures e imutabilidade
- **Complexidade**: Demonstra precedência de operadores, sistema de tipos e avaliação preguiçosa

### 1.2 Objetivos do Projeto

1. **Implementar 16 operações de teoria de conjuntos** (básicas, relacionais, distribuição e range)
2. **Suportar conjuntos aninhados** com profundidade arbitrária
3. **Integrar paradigma funcional** com lambda, let bindings e closures
4. **Garantir correção matemática** seguindo axiomas da teoria de conjuntos
5. **Fornecer sintaxe intuitiva** próxima à notação matemática padrão

---

## 2. Características

### 2.1 Operações Principais

**16 Operações Implementadas:**
- 5 Básicas: `union`, `inter`, `diff`, `in`, `#`
- 7 Relacionais: `subset`, `superset`, `psubset`, `psuperset`, `disjoint`, `==`, `!=`
- 3 Distribuição: `cross`, `flatten`, `powerset`
- 1 Range: `...` (geração de sequências)

### 2.2 Paradigma Funcional

**Características Funcionais:**
- Funções Lambda: `fn x => x union {10}`
- Let Bindings: `let x = {1, 2, 3} in x union {4, 5}`
- Closures: Captura de ambiente léxico
- Imutabilidade: Conjuntos são imutáveis
- Funções de Alta Ordem: Composição de operações

### 2.3 Sistema de Tipos

**Tipos Suportados:**
- `TipoInteiro`: Números inteiros
- `TipoBooleano`: true/false
- `TipoConjunto`: Conjuntos de valores
- `TipoFuncao`: Funções lambda
- **Polimorfismo**: Conjuntos podem conter qualquer tipo

### 2.4 Características Técnicas

- **Parser**: JavaCC 7.0.13 (LALR)
- **Runtime**: Java 17.0.12
- **Estrutura de Dados**: HashSet (garante unicidade e O(1) para pertencimento)
- **Tipagem**: Estática com verificação em tempo de compilação
- **Avaliação**: Eager (ávida) para operações de conjunto

---

## 3. BNF da Linguagem

### 3.1 Gramática Completa

```bnf
Expressao ::= ExpIgualdade

ExpIgualdade ::= ExpUniao ( ("==" | "!=") ExpUniao )*

ExpUniao ::= ExpCross ( ("union" | "inter" | "diff" | "subset" | "superset" 
                       | "psubset" | "psuperset" | "disjoint") ExpCross )*

ExpCross ::= ExpRange ( "cross" ExpRange )*

ExpRange ::= ExpPertencimento ( "..." ExpPertencimento )*

ExpPertencimento ::= ExpUnaria ( "in" ExpUnaria )*

ExpUnaria ::= "#" ExpUnaria
            | "flatten" ExpUnaria
            | "powerset" ExpUnaria
            | ExpPrimaria

ExpPrimaria ::= INTEGER_LITERAL
              | "true" | "false"
              | "{" [ Expressao ("," Expressao)* ] "}"
              | "fn" ID "=>" Expressao
              | "let" ID "=" Expressao "in" Expressao
              | ID
              | "(" Expressao ")"
              | ExpPrimaria "(" Expressao ")"  // Aplicação de função
```

### 3.2 Precedência de Operadores (do maior para o menor)

| Nível | Operadores | Associatividade | Categoria |
|-------|------------|-----------------|-----------|
| 1 | `()` | N/A | Parênteses |
| 2 | `#`, `flatten`, `powerset` | Direita | Unários |
| 3 | `cross` | Esquerda | Produto Cartesiano |
| 4 | `...` | Esquerda | Range |
| 5 | `union`, `inter`, `diff`, `subset`, `superset`, `psubset`, `psuperset`, `disjoint` | Esquerda | Conjuntos |
| 6 | `in` | Esquerda | Pertencimento |
| 7 | `==`, `!=` | Esquerda | Igualdade |
| 8 | `let-in` | Direita | Declaração |

### 3.3 Tokens

```
INTEGER_LITERAL ::= ["0"-"9"]+
ID              ::= ["a"-"z","A"-"Z"] (["a"-"z","A"-"Z","0"-"9","_"])*

Palavras-chave:
  true, false, union, inter, diff, in, subset, superset, psubset, 
  psuperset, disjoint, cross, flatten, powerset, fn, let

Símbolos:
  {, }, (, ), ,, #, ..., ==, !=, =, =>
```

---

## 4. Operações Implementadas

### 4.1 Operações Básicas

| Operação | Sintaxe | Descrição | Exemplo | Resultado |
|----------|---------|-----------|---------|-----------|
| **União** | `A union B` | A ∪ B | `{1,2} union {3,4}` | `{1,2,3,4}` |
| **Interseção** | `A inter B` | A ∩ B | `{1,2,3} inter {2,3,4}` | `{2,3}` |
| **Diferença** | `A diff B` | A \ B | `{1,2,3} diff {2,3}` | `{1}` |
| **Pertencimento** | `x in A` | x ∈ A | `2 in {1,2,3}` | `true` |
| **Cardinalidade** | `#A` | \|A\| | `#{1,2,3}` | `3` |

### 4.2 Operações Relacionais

| Operação | Sintaxe | Descrição | Exemplo | Resultado |
|----------|---------|-----------|---------|-----------|
| **Subconjunto** | `A subset B` | A ⊆ B | `{1,2} subset {1,2,3}` | `true` |
| **Superconjunto** | `A superset B` | A ⊇ B | `{1,2,3} superset {1,2}` | `true` |
| **Subconjunto Próprio** | `A psubset B` | A ⊂ B | `{1,2} psubset {1,2}` | `false` |
| **Superconjunto Próprio** | `A psuperset B` | A ⊃ B | `{1,2,3} psuperset {1,2}` | `true` |
| **Disjuntos** | `A disjoint B` | A ∩ B = ∅ | `{1,2} disjoint {3,4}` | `true` |
| **Igualdade** | `A == B` | A = B | `{1,2} == {2,1}` | `true` |
| **Desigualdade** | `A != B` | A ≠ B | `{1,2} != {1,3}` | `true` |

### 4.3 Operações de Distribuição

| Operação | Sintaxe | Descrição | Exemplo | Resultado |
|----------|---------|-----------|---------|-----------|
| **Produto Cartesiano** | `A cross B` | A × B | `{1,2} cross {3,4}` | `[[1,3],[1,4],[2,3],[2,4]]` |
| **Flatten** | `flatten A` | Achata 1 nível | `flatten {{1,2},{3,4}}` | `{1,2,3,4}` |
| **Powerset** | `powerset A` | ℘(A) | `powerset {1,2}` | `[[],[1],[2],[1,2]]` |

### 4.4 Operação de Range

| Operação | Sintaxe | Descrição | Exemplo | Resultado |
|----------|---------|-----------|---------|-----------|
| **Range** | `inicio...fim` | Gera sequência | `1...5` | `{1,2,3,4,5}` |
| | | | `1...10` | `{1,2,3,...,10}` |
| | | Suporta decrescente | `10...1` | `{1,2,...,10}` |

**Propriedades do Range:**
- Inclusivo nos dois extremos: `1...5` inclui 1 e 5
- Suporta ranges crescentes e decrescentes
- Gera HashSet (não preserva ordem)
- Complexidade: O(n) onde n = |fim - inicio| + 1
- Combina com todas as operações: `#(1...100)` → `100`

---

## 5. Implementação

### 5.1 Estrutura de Arquivos

```
projeto-plp-setexpr/
├── SetExprParser.jj           # Gramática JavaCC
├── ExpConjunto.java            # Literal de conjunto
├── ExpUniao.java               # União
├── ExpIntersecao.java          # Interseção
├── ExpDiferenca.java           # Diferença
├── ExpPertencimento.java       # Pertencimento
├── ExpCardinalidade.java       # Cardinalidade
├── ExpSubconjunto.java         # Subconjunto
├── ExpSuperconjunto.java       # Superconjunto
├── ExpSubconjuntoProprio.java  # Subconjunto próprio
├── ExpSuperconjuntoProprio.java # Superconjunto próprio
├── ExpDisjuntos.java           # Conjuntos disjuntos
├── ExpIgualdade.java           # Igualdade
├── ExpDesigualdade.java        # Desigualdade
├── ExpProdutoCartesiano.java   # Produto cartesiano
├── ExpFlatten.java             # Flatten
├── ExpPowerset.java            # Powerset
├── ExpRange.java               # Range (...)
├── ValorConjunto.java          # Valor: conjunto
├── TipoConjunto.java           # Tipo: conjunto
├── AmbienteExecucao.java       # Interface de ambiente
├── AmbienteExecucaoMap.java    # Implementação com Map
└── Expressao.java              # Interface base
```

### 5.2 Classes Principais

#### 5.2.1 ExpRange.java (Exemplo de Implementação)

```java
public class ExpRange extends Expressao {
    private Expressao inicio;
    private Expressao fim;

    public ExpRange(Expressao inicio, Expressao fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public Valor avaliar(AmbienteExecucao amb) {
        Valor vInicio = inicio.avaliar(amb);
        Valor vFim = fim.avaliar(amb);

        if (!(vInicio instanceof ValorInteiro)) {
            throw new RuntimeException("Range: inicio deve ser inteiro");
        }
        if (!(vFim instanceof ValorInteiro)) {
            throw new RuntimeException("Range: fim deve ser inteiro");
        }

        int start = ((ValorInteiro) vInicio).getValor();
        int end = ((ValorInteiro) vFim).getValor();

        Set<Valor> resultado = new HashSet<>();
        if (start <= end) {
            for (int i = start; i <= end; i++) {
                resultado.add(new ValorInteiro(i));
            }
        } else {
            for (int i = start; i >= end; i--) {
                resultado.add(new ValorInteiro(i));
            }
        }

        return new ValorConjunto(resultado);
    }

    @Override
    public Tipo verificaTipo(AmbienteExecucao amb) {
        Tipo tInicio = inicio.verificaTipo(amb);
        Tipo tFim = fim.verificaTipo(amb);

        if (!(tInicio instanceof TipoInteiro)) {
            throw new RuntimeException("Range: inicio deve ser inteiro");
        }
        if (!(tFim instanceof TipoInteiro)) {
            throw new RuntimeException("Range: fim deve ser inteiro");
        }

        return new TipoConjunto(new TipoInteiro());
    }
}
```

### 5.3 Complexidade das Operações

| Operação | Complexidade | Observações |
|----------|--------------|-------------|
| union | O(n + m) | n = \|A\|, m = \|B\| |
| inter | O(min(n, m)) | HashSet.contains() é O(1) |
| diff | O(n) | Percorre A verificando B |
| in | O(1) | HashSet lookup |
| # | O(1) | HashSet.size() |
| subset | O(n) | Verifica cada elemento de A em B |
| cross | O(n × m) | Gera n×m pares |
| flatten | O(n × k) | k = tamanho médio dos conjuntos internos |
| powerset | O(2^n × n) | Exponencial |
| range | O(n) | n = \|fim - inicio\| |

---

## 6. Compilação e Execução

### 6.1 Pré-requisitos

- **Java JDK 17+**
- **JavaCC 7.0.13** (incluído: `javacc-7.0.13.jar`)

### 6.2 Compilar o Parser

```powershell
# Gerar arquivos Java a partir da gramática
java -cp javacc-7.0.13.jar javacc SetExprParser.jj

# Compilar todos os arquivos Java
javac *.java
```

### 6.3 Executar Expressões

```powershell
# Modo interativo
java SetExprParser

# Modo pipeline (um comando)
echo "{1, 2, 3} union {4, 5}" | java SetExprParser

# Modo pipeline (múltiplos comandos)
echo "1...10" | java SetExprParser
echo "#(1...100)" | java SetExprParser
echo "(1...5) union (6...10)" | java SetExprParser
```

### 6.4 Script de Demonstração

```powershell
# Executar todas as demonstrações
.\demo-simples.ps1
```

---

## 7. Exemplos de Uso

### 7.1 Operações Básicas

```setexpr
{1, 2, 3} union {3, 4, 5}
# Resultado: [1, 2, 3, 4, 5]

{1, 2, 3, 4} inter {3, 4, 5, 6}
# Resultado: [3, 4]

{1, 2, 3, 4, 5} diff {2, 4}
# Resultado: [1, 3, 5]

3 in {1, 2, 3, 4, 5}
# Resultado: true

#{1, 2, 3, 4, 5}
# Resultado: 5
```

### 7.2 Operações Relacionais

```setexpr
{1, 2} subset {1, 2, 3, 4}
# Resultado: true

{1, 2} psubset {1, 2, 3}
# Resultado: true

{1, 2} psubset {1, 2}
# Resultado: false

{1, 2} disjoint {3, 4}
# Resultado: true

{1, 2, 3} == {3, 2, 1}
# Resultado: true (ordem não importa)
```

### 7.3 Operações de Distribuição

```setexpr
{1, 2} cross {3, 4}
# Resultado: [[1, 3], [1, 4], [2, 3], [2, 4]]

flatten {{1, 2}, {3, 4}, {5}}
# Resultado: [1, 2, 3, 4, 5]

powerset {1, 2, 3}
# Resultado: [[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]]

#(powerset {1, 2, 3})
# Resultado: 8 (2^3)
```

### 7.4 Operações com Range

```setexpr
1...5
# Resultado: [1, 2, 3, 4, 5]

1...10
# Resultado: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

#(1...100)
# Resultado: 100

3 in (1...10)
# Resultado: true

(1...5) union (6...10)
# Resultado: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

(1...10) inter (5...15)
# Resultado: [5, 6, 7, 8, 9, 10]

(1...10) diff (5...7)
# Resultado: [1, 2, 3, 4, 8, 9, 10]

powerset (1...3)
# Resultado: [[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]]
```

### 7.5 Conjuntos Aninhados

```setexpr
{{1, 2}, {3, 4}}
# Resultado: [[1, 2], [3, 4]]

{1, 2} in {{1, 2}, {3, 4}}
# Resultado: true

#{{1, 2}, {3, 4}, {5}}
# Resultado: 3

flatten {{{1, 2}}, {{3}}}
# Resultado: [{1, 2}, {3}] (achata apenas 1 nível)
```

### 7.6 Programação Funcional

```setexpr
# Lambda
(fn x => x union {10})({1, 2, 3})
# Resultado: [1, 2, 3, 10]

# Let binding
let x = {1, 2, 3} in x union {4, 5}
# Resultado: [1, 2, 3, 4, 5]

# Composição
(fn x => x inter {2, 3, 4})({1, 2, 3, 4, 5})
# Resultado: [2, 3, 4]

# Range com Lambda
(fn x => x union {100})(1...5)
# Resultado: [1, 2, 3, 4, 5, 100]
```

### 7.7 Exemplos Compostos

```setexpr
# Flatten + União
flatten ({{1, 2}, {3}} union {{4, 5}})
# Resultado: [1, 2, 3, 4, 5]

# Pertencimento no Powerset
{1} in (powerset {1, 2})
# Resultado: true

# Cardinalidade de Produto Cartesiano
#({1, 2} cross {3, 4})
# Resultado: 4 (2×2)

# Propriedade Comutativa
{1, 2, 3} union {4, 5} == {4, 5} union {1, 2, 3}
# Resultado: true

# Lei de De Morgan (simplificada)
{1, 2, 3, 4, 5} diff ({1, 2} union {3, 4})
# Resultado: [5]

# Range com Operações Complexas
(1...10) inter (5...15)
# Resultado: [5, 6, 7, 8, 9, 10]
```

---

## 8. Testes

### 8.1 Script de Testes Automatizados

O arquivo `demo-simples.ps1` executa 40+ demonstrações organizadas em 7 categorias:

```powershell
.\demo-simples.ps1
```

**Saída esperada:**
```
════════════════════════════════════════════════════════════
     SetExpr - Demonstrações
════════════════════════════════════════════════════════════

═══ 1. OPERAÇÕES BÁSICAS ═══
União: [1, 2, 3, 4, 5]
Interseção: [3, 4]
...

═══ 4. OPERAÇÕES COM RANGE ═══
Range Simples (1...5): [1, 2, 3, 4, 5]
Cardinalidade de Range: 100
...

16 operações de teoria de conjuntos
Operações básicas: union, inter, diff, in, #
Operações relacionais: subset, superset, psubset, disjoint, ==
Operações de distribuição: cross, flatten, powerset
Operações com range: ... (geração de sequências)
Paradigma funcional: lambda, let bindings, closures
Conjuntos aninhados com profundidade ilimitada
```

### 8.2 Casos de Teste por Categoria

#### 8.2.1 Operações Básicas (5 testes)
- nião de conjuntos disjuntos
- Interseção de conjuntos com elementos comuns
- Diferença simétrica
- Pertencimento verdadeiro e falso
- Cardinalidade de conjuntos vazios e não-vazios

#### 8.2.2 Operações Relacionais (7 testes)
- Subconjunto próprio e impróprio
- Superconjunto próprio e impróprio
- Conjuntos disjuntos e não-disjuntos
- Igualdade com ordem diferente
- Desigualdade estrutural

#### 8.2.3 Operações de Distribuição (4 testes)
- Produto cartesiano e verificação de cardinalidade
- Flatten de conjuntos aninhados (1 nível)
- Powerset e verificação 2^n
- Pertencimento em powerset

#### 8.2.4 Operações com Range (8 testes)
- Range simples crescente (1...5)
- Range maior (1...10)
- Range decrescente (10...1)
- Cardinalidade de range (#(1...100))
- Pertencimento em range (3 in (1...10))
- União de ranges consecutivos
- Interseção de ranges sobrepostos
- Powerset de range pequeno

#### 8.2.5 Conjuntos Aninhados (4 testes)
- Criação de conjunto de conjuntos
- Pertencimento de conjunto em conjunto de conjuntos
- Cardinalidade de conjunto de conjuntos
- Profundidade 3 ou mais

#### 8.2.6 Exemplos Compostos (4 testes)
- Flatten após união
- Pertencimento no resultado de powerset
- Cardinalidade de produto cartesiano
- Composição de múltiplas operações

#### 8.2.7 Programação Funcional (3 testes)
- Aplicação de lambda com união
- Composição de funções
- Range com lambda

### 8.3 Cobertura de Testes

| Categoria | Testes | Status |
|-----------|--------|--------|
| Operações Básicas | 5 | ✅ 100% |
| Operações Relacionais | 7 | ✅ 100% |
| Operações de Distribuição | 4 | ✅ 100% |
| Operações com Range | 8 | ✅ 100% |
| Conjuntos Aninhados | 4 | ✅ 100% |
| Exemplos Compostos | 4 | ✅ 100% |
| Programação Funcional | 3 | ✅ 100% |
| **TOTAL** | **35+** | **✅ 100%** |

### 8.4 Testes de Propriedades Matemáticas

```setexpr
# Comutatividade da União
{1, 2} union {3, 4} == {3, 4} union {1, 2}  # true

# Associatividade da Interseção
({1, 2} inter {2, 3}) inter {2, 4} == {1, 2} inter ({2, 3} inter {2, 4})  # true

# Elemento Identidade (união com vazio)
{1, 2, 3} union {} == {1, 2, 3}  # true

# Idempotência
{1, 2, 3} union {1, 2, 3} == {1, 2, 3}  # true

# Cardinalidade do Produto Cartesiano
#({1, 2} cross {3, 4}) == 4  # true (2×2 = 4)

# Cardinalidade do Powerset
#(powerset {1, 2, 3}) == 8  # true (2^3 = 8)
```

---

## 9. Arquitetura

### 9.1 Pipeline de Compilação

```
Código SetExpr
      ↓
┌─────────────┐
│   Lexer     │ ← Tokenização (JavaCC)
└──────┬──────┘
       ↓
┌─────────────┐
│   Parser    │ ← Análise Sintática (JavaCC)
└──────┬──────┘
       ↓
┌─────────────┐
│  AST (Exp)  │ ← Árvore Sintática Abstrata
└──────┬──────┘
       ↓
┌─────────────┐
│Verificação  │ ← Checagem de Tipos
│  de Tipos   │
└──────┬──────┘
       ↓
┌─────────────┐
│  Avaliação  │ ← Execução com AmbienteExecucao
└──────┬──────┘
       ↓
   Resultado (Valor)
```

### 9.2 Hierarquia de Classes

```
Expressao (interface)
├── ExpConjunto
├── ExpUniao
├── ExpIntersecao
├── ExpDiferenca
├── ExpPertencimento
├── ExpCardinalidade
├── ExpSubconjunto
├── ExpSuperconjunto
├── ExpSubconjuntoProprio
├── ExpSuperconjuntoProprio
├── ExpDisjuntos
├── ExpIgualdade
├── ExpDesigualdade
├── ExpProdutoCartesiano
├── ExpFlatten
├── ExpPowerset
├── ExpRange              ← NOVO
├── ExpLet
├── ExpFuncao
├── ExpAplicacao
└── ExpId

Valor (interface)
├── ValorInteiro
├── ValorBooleano
├── ValorConjunto         ← Usa HashSet<Valor>
└── ValorFuncao

Tipo (interface)
├── TipoInteiro
├── TipoBooleano
├── TipoConjunto
└── TipoFuncao
```

### 9.3 Ambiente de Execução

```java
interface AmbienteExecucao {
    Valor get(String var);              // Buscar variável
    void add(String var, Valor valor);  // Adicionar variável
    Tipo getTipo(String var);           // Buscar tipo
    void addTipo(String var, Tipo tipo); // Adicionar tipo
}

class AmbienteExecucaoMap implements AmbienteExecucao {
    private Map<String, Valor> ambiente;
    private Map<String, Tipo> ambienteTipos;
    // Implementação com HashMap
}
```

### 9.4 Diagrama de Componentes

```
┌─────────────────────────────────────────┐
│         SetExprParser.jj                │
│  - Tokens (UNION, INTER, ...)           │
│  - Produções (PExpressao, ...)          │
│  - Precedência de Operadores            │
└───────────────┬─────────────────────────┘
                ↓
┌───────────────────────────────────────────┐
│         Árvore de Expressões              │
│  - ExpUniao, ExpIntersecao, ...           │
│  - ExpRange (1...5 → {1,2,3,4,5})         │
└───────────┬───────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│       Verificação de Tipos                │
│  - TipoInteiro, TipoBooleano              │
│  - TipoConjunto, TipoFuncao               │
└───────────┬───────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│         Avaliação                         │
│  - AmbienteExecucao (escopo léxico)       │
│  - ValorInteiro, ValorBooleano            │
│  - ValorConjunto (HashSet<Valor>)         │
│  - ValorFuncao (closure)                  │
└───────────────────────────────────────────┘
```

---

## 10. Documentação Completa

### 10.1 Arquivos de Documentação

| Arquivo | Descrição | Linhas |
|---------|-----------|--------|
| `README.md` | Este arquivo (visão geral completa) | ~1000 |
| `BNF_SETEXPR.md` | BNF completa com exemplos | 467 |
| `APRESENTACAO.md` | Slides detalhados | 945 |
| `demo-simples.ps1` | Script de demonstração automatizada | ~150 |

### 10.2 Links Úteis

- **[Slides da Apresentação](https://docs.google.com/presentation/d/1vDxkcSzXv5n8ovFE0-ypeOXpedVMhpVY6QS7jWMnf0k/edit?slide=id.p12#slide=id.p12)**
- [BNF Completa](./BNF_SETEXPR.md)
- [Apresentação](./APRESENTACAO.md)
- [Script de Demonstração](./demo-simples.ps1)

### 10.3 Estrutura do Repositório

```
projeto-plp-setexpr/
├── README.md                      ← Este arquivo
├── BNF_SETEXPR.md                 ← Gramática formal
├── APRESENTACAO.md                ← Slides acadêmicos
├── GUIA_APRESENTADOR.md           ← Roteiro de apresentação
├── demo-simples.ps1               ← Script de testes
│
├── SetExprParser.jj               ← Gramática JavaCC
├── javacc-7.0.13.jar              ← Compilador JavaCC
│
├── Expressao.java                 ← Interface base
├── ExpConjunto.java               ← 19 classes de expressões
├── ExpUniao.java
├── ... (outras expressões)
├── ExpRange.java                  ← Operador de range
│
├── Valor.java                     ← Interface de valores
├── ValorConjunto.java             ← Valor: conjunto
├── ValorInteiro.java              ← Valor: inteiro
├── ValorBooleano.java             ← Valor: booleano
├── ValorFuncao.java               ← Valor: função
│
├── Tipo.java                      ← Interface de tipos
├── TipoConjunto.java              ← Tipo: conjunto
├── TipoInteiro.java               ← Tipo: inteiro
├── TipoBooleano.java              ← Tipo: booleano
├── TipoFuncao.java                ← Tipo: função
│
└── AmbienteExecucao.java          ← Ambiente de execução
    └── AmbienteExecucaoMap.java   ← Implementação
```

---

## 11. Métricas do Projeto

### 11.1 Estatísticas de Código

| Métrica | Valor |
|---------|-------|
| **Linhas de Código** | ~2.200 |
| **Classes Java** | 27 |
| **Expressões** | 19 |
| **Valores** | 4 |
| **Tipos** | 4 |
| **Operações** | 16 |
| **Tokens** | 25 |
| **Produções** | 12 |
| **Testes** | 35+ |
| **Taxa de Sucesso** | 100% |

### 11.2 Complexidade

| Aspecto | Complexidade |
|---------|--------------|
| Parser | LALR (JavaCC) |
| Tipagem | Estática |
| Avaliação | Eager |
| Espaço (HashSet) | O(n) |
| Union/Inter | O(n + m) |
| Powerset | O(2^n) |
| Range | O(n) |

---

## 12. Comparação com Outras Linguagens

| Linguagem | Conjuntos Nativos | Operações | Imutabilidade | Range | Sintaxe |
|-----------|-------------------|-----------|---------------|-------|---------|
| **SetExpr** | ✅ Sim | 16 | ✅ Sim | ✅ `...` | `A union B` |
| Python | ⚠️ `set()` | 6-8 | ❌ Não | ✅ `range()` | `A.union(B)` |
| Haskell | ⚠️ `Data.Set` | 10+ | ✅ Sim | ✅ `[1..10]` | `union A B` |
| Java | ❌ Biblioteca | API | ❌ Não | ❌ Não | `A.addAll(B)` |
| SQL | ⚠️ Tables | UNION/INTERSECT | ✅ Sim | ❌ Não | `SELECT ... UNION` |
| JavaScript | ⚠️ `Set` | 5 | ❌ Não | ❌ Não | Manual |

**Vantagens do SetExpr:**
- Sintaxe matemática natural (`A union B` vs `A.union(B)`)
- 16 operações completas (maioria tem <10)
- Conjuntos aninhados ilimitados
- Range integrado (`1...5` gera conjunto)
- Paradigma funcional puro

---

## 13. Trabalhos Futuros

### 13.1 Extensões Planejadas

1. **Compreensão de Conjuntos**
   ```setexpr
   {x * 2 | x in {1, 2, 3}, x > 1}
   # Resultado: {4, 6}
   ```

2. **Range com Passo**
   ```setexpr
   1...10 step 2
   # Resultado: {1, 3, 5, 7, 9}
   ```

3. **Operações Aritméticas Completas**
   ```setexpr
   {1, 2, 3} + 10
   # Resultado: {11, 12, 13}
   ```

4. **Multiconjuntos (Bags)**
   ```setexpr
   bag{1, 1, 2, 2, 3}
   # Permite repetições
   ```

5. **Operações Lógicas Avançadas**
   ```setexpr
   forall x in {1, 2, 3} : x > 0
   # Resultado: true
   ```

6. **Pattern Matching**
   ```setexpr
   match S with
   | {} => "vazio"
   | {x} => "unitario"
   | _ => "multiplo"
   ```

### 13.2 Otimizações

- [ ] Lazy evaluation para operações custosas
- [ ] Cache de resultados de powerset
- [ ] Representação compacta para ranges grandes
- [ ] Paralelização de produto cartesiano
- [ ] JIT compilation para expressões frequentes

---

## 14. Conclusão

SetExpr demonstra com sucesso a integração entre **paradigma funcional** e **teoria de conjuntos**, oferecendo:

**16 operações matemáticas** completas e testadas  
**Sintaxe intuitiva** próxima à notação matemática  
**Paradigma funcional puro** com lambda e closures  
**Conjuntos aninhados** com profundidade ilimitada  
**Sistema de tipos robusto** com verificação estática  
**Performance adequada** com HashSet (O(1) para pertencimento)  
**Documentação completa** (>1000 linhas)  
**100% de testes passando** (35+ casos)  

### Contribuições Acadêmicas

1. **Didática**: Demonstra implementação completa de parser com JavaCC
2. **Teórica**: Formaliza operações de conjunto em paradigma funcional
3. **Prática**: Código Java idiomático e bem estruturado
4. **Extensível**: Arquitetura permite fácil adição de novas operações

---

## 15. Licença e Autoria

**Projeto Acadêmico** desenvolvido para a disciplina **IN1007 - Paradigmas de Linguagens de Programação**  
**Centro de Informática - UFPE**

**Equipe:**
- Joyce Almeida
- Rafael Moura  
- Ricardo Azevedo

**Professor:** Augusto Sampaio  
**Período:** 2025.2

---

**SetExpr - Where Functional Programming Meets Set Theory** ✨

*"A linguagem que fala a língua da matemática"* 🎓
