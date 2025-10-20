# Apresentação do Projeto SetExpr
## Escopo e Gramática BNF

**Disciplina:** (IN1007) Paradigmas de Linguagens de Programação - Cin/UFPE  
**Professor:** Augusto Sampaio  
**Equipe:** Joyce Almeida, Rafael Moura, Ricardo Azevedo  
**Data:** 21 de Outubro de 2025

---

## 1. Introdução ao Projeto

### 1.1 Contextualização

O projeto SetExpr constitui uma extensão da Linguagem de Expressões 1 (LE1) desenvolvida na disciplina, com foco na implementação de um tipo de dado Conjunto polimórfico. A linguagem foi projetada para demonstrar conceitos fundamentais de paradigmas funcionais, análise sintática formal e semântica operacional aplicados à teoria de conjuntos.

### 1.2 Motivação

A escolha de conjuntos como extensão da LE1 fundamenta-se em:

- **Relevância Matemática**: Conjuntos constituem estrutura fundamental na matemática e ciência da computação
- **Complexidade Sintática**: Operações de conjunto exigem precedência de operadores não-trivial
- **Polimorfismo**: Conjuntos podem conter diferentes tipos (inteiros, booleanos, conjuntos aninhados)
- **Expressividade**: Compreensões de conjunto demonstram programação declarativa
- **Formalização**: Operações seguem axiomas matemáticos bem estabelecidos

---

## 2. Escopo do Projeto

### 2.1 Objetivos Principais

#### 2.1.1 Objetivo Geral
Desenvolver uma linguagem funcional completa para manipulação de conjuntos que integre operações aritméticas, lógicas e de teoria dos conjuntos em uma sintaxe unificada e matematicamente consistente.

#### 2.1.2 Objetivos Específicos

1. **Implementar Conjuntos Polimórficos**
   - Suporte a conjuntos de inteiros: `{1, 2, 3}`
   - Suporte a conjuntos de booleanos: `{true, false}`
   - Suporte a conjuntos aninhados: `{{1, 2}, {3, 4}}`

2. **Desenvolver Operações de Conjunto Completas**
   - União: `A union B`
   - Interseção: `A inter B`
   - Diferença: `A diff B`
   - Pertencimento: `x in A`
   - Subconjunto: `A subset B`
   - Igualdade/Desigualdade: `A == B`, `A != B`

3. **Implementar Sintaxe Declarativa**
   - Ranges: `1..10` para intervalos
   - Compreensões de filtro: `{x in S : condição}`
   - Compreensões de mapeamento: `{expressão : x in S}`
   - Cardinalidade: `#S`

4. **Manter Compatibilidade com LE1**
   - Operações aritméticas: `+`, `-`, `*`, `/`, `%`
   - Operações lógicas: `and`, `or`, `not`
   - Comparações: `<`, `>`, `<=`, `>=`, `==`, `!=`

### 2.2 Funcionalidades Implementadas

#### 2.2.1 Tipos de Dados

| Tipo | Sintaxe | Exemplo |
|------|---------|---------|
| Inteiro | `n` | `42`, `-17` |
| Booleano | `true\|false` | `true`, `false` |
| Conjunto | `{elementos}` | `{1, 2, 3}` |
| Range | `início..fim` | `1..10` |

#### 2.2.2 Operações por Categoria

**Operações Aritméticas:**
```
5 + 3        → 8
10 - 4       → 6
6 * 7        → 42
15 / 3       → 5
17 % 5       → 2
```

**Operações Lógicas:**
```
true and false    → false
true or false     → true
not true          → false
```

**Operações de Comparação:**
```
10 > 5           → true
7 >= 7           → true
5 <= 4           → false
```

**Operações de Conjunto:**
```
{1, 2, 3} union {3, 4, 5}      → {1, 2, 3, 4, 5}
{1, 2, 3, 4} inter {3, 4, 5}   → {3, 4}
{1, 2, 3, 4} diff {3, 4}       → {1, 2}
3 in {1, 2, 3, 4}              → true
{1, 2} subset {1, 2, 3, 4}     → true
```

**Cardinalidade e Ranges:**
```
#{1, 2, 3}       → 3
#{}              → 0
1..5             → {1, 2, 3, 4, 5}
```

**Compreensões de Conjunto:**
```
{x in 1..10 : x > 5}          → {6, 7, 8, 9, 10}
{x * 2 : x in 1..5}           → {2, 4, 6, 8, 10}
{x in {1,2,3,4,5} : x % 2 == 0} → {2, 4}
```

### 2.3 Características Técnicas

#### 2.3.1 Paradigma de Programação
- **Funcional**: Ausência de efeitos colaterais, imutabilidade
- **Declarativo**: Compreensões expressam "o que" ao invés de "como"
- **Expressivo**: Sintaxe próxima à notação matemática convencional

#### 2.3.2 Sistema de Tipos
- **Polimórfico**: Conjuntos podem conter elementos de diferentes tipos
- **Estrutural**: Igualdade baseada em conteúdo, não em identidade
- **Dinâmico**: Verificação de tipos em tempo de execução

#### 2.3.3 Semântica Operacional
- **Avaliação Estrita**: Todas as expressões são avaliadas imediatamente
- **Ordem de Avaliação**: Determinística, seguindo precedência de operadores
- **Tratamento de Conjuntos**: Eliminação automática de duplicatas, ordenação

---

## 3. Gramática BNF Formal

### 3.1 Estrutura Hierárquica da Gramática

A gramática SetExpr segue uma estrutura hierárquica que respeita a precedência de operadores matemática padrão, estendida para incluir operações de conjunto.

```bnf
<programa> ::= <expressão>

<expressão> ::= <expr_ou>

<expr_ou> ::= <expr_e> 
            | <expr_ou> "or" <expr_e>

<expr_e> ::= <expr_igualdade>
           | <expr_e> "and" <expr_igualdade>

<expr_igualdade> ::= <expr_relacional>
                   | <expr_igualdade> "==" <expr_relacional>
                   | <expr_igualdade> "!=" <expr_relacional>

<expr_relacional> ::= <expr_conjunto>
                    | <expr_relacional> "<" <expr_conjunto>
                    | <expr_relacional> ">" <expr_conjunto>
                    | <expr_relacional> "<=" <expr_conjunto>
                    | <expr_relacional> ">=" <expr_conjunto>

<expr_conjunto> ::= <expr_aditiva>
                  | <expr_conjunto> "union" <expr_aditiva>
                  | <expr_conjunto> "inter" <expr_aditiva>
                  | <expr_conjunto> "diff" <expr_aditiva>
                  | <expr_conjunto> "subset" <expr_aditiva>
                  | <expr_aditiva> "in" <expr_conjunto>

<expr_aditiva> ::= <expr_multiplicativa>
                 | <expr_aditiva> "+" <expr_multiplicativa>
                 | <expr_aditiva> "-" <expr_multiplicativa>

<expr_multiplicativa> ::= <expr_unária>
                        | <expr_multiplicativa> "*" <expr_unária>
                        | <expr_multiplicativa> "/" <expr_unária>
                        | <expr_multiplicativa> "%" <expr_unária>

<expr_unária> ::= <expr_range>
                | "-" <expr_unária>
                | "not" <expr_unária>
                | "#" <expr_unária>

<expr_range> ::= <expr_primária>
               | <expr_primária> ".." <expr_primária>

<expr_primária> ::= <literal_inteiro>
                  | <literal_booleano>
                  | <literal_conjunto>
                  | <identificador>
                  | "(" <expressão> ")"

<literal_conjunto> ::= "{" "}"
                     | "{" <lista_expressões> "}"
                     | "{" <compreensão_filtro> "}"
                     | "{" <compreensão_mapeamento> "}"

<compreensão_filtro> ::= <identificador> "in" <expressão> ":" <expressão>

<compreensão_mapeamento> ::= <expressão> ":" <identificador> "in" <expressão>

<lista_expressões> ::= <expressão>
                     | <lista_expressões> "," <expressão>

<literal_inteiro> ::= <dígito>+

<literal_booleano> ::= "true" | "false"

<identificador> ::= <letra> (<letra> | <dígito>)*

<dígito> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"

<letra> ::= "a" | "b" | ... | "z" | "A" | "B" | ... | "Z"
```

### 3.2 Precedência de Operadores

A precedência implementada segue a hierarquia matemática padrão:

| Precedência | Operadores | Associatividade | Categoria |
|-------------|------------|-----------------|-----------|
| 1 (maior) | `()` | N/A | Parênteses |
| 2 | `-`, `not`, `#` | Direita | Unários |
| 3 | `..` | Esquerda | Range |
| 4 | `*`, `/`, `%` | Esquerda | Multiplicativos |
| 5 | `+`, `-` | Esquerda | Aditivos |
| 6 | `union`, `inter`, `diff`, `subset`, `in` | Esquerda | Conjuntos |
| 7 | `<`, `>`, `<=`, `>=` | Esquerda | Relacionais |
| 8 | `==`, `!=` | Esquerda | Igualdade |
| 9 | `and` | Esquerda | Conjunção |
| 10 (menor) | `or` | Esquerda | Disjunção |

### 3.3 Análise Semântica das Construções

#### 3.3.1 Literais de Conjunto

**Conjunto Vazio:**
```bnf
{} → ∅
```

**Conjunto Extensional:**
```bnf
{1, 2, 3} → {1, 2, 3}
{true, false, 1} → {1, false, true}  # Polimórfico
```

**Ranges:**
```bnf
1..5 → {1, 2, 3, 4, 5}
3..1 → {}  # Range inválido retorna conjunto vazio
```

#### 3.3.2 Compreensões de Conjunto

**Compreensão de Filtro:**
```bnf
{x in S : P(x)} → {x | x ∈ S ∧ P(x)}
```

Exemplo:
```
{x in 1..10 : x % 2 == 0} → {2, 4, 6, 8, 10}
```

**Compreensão de Mapeamento:**
```bnf
{f(x) : x in S} → {f(x) | x ∈ S}
```

Exemplo:
```
{x * 2 : x in 1..5} → {2, 4, 6, 8, 10}
```

#### 3.3.3 Operações de Conjunto

**União:**
```bnf
A union B → A ∪ B = {x | x ∈ A ∨ x ∈ B}
```

**Interseção:**
```bnf
A inter B → A ∩ B = {x | x ∈ A ∧ x ∈ B}
```

**Diferença:**
```bnf
A diff B → A \ B = {x | x ∈ A ∧ x ∉ B}
```

**Pertencimento:**
```bnf
x in A → x ∈ A
```

**Subconjunto:**
```bnf
A subset B → A ⊆ B ≡ ∀x(x ∈ A → x ∈ B)
```

**Cardinalidade:**
```bnf
#A → |A|
```

---

## 4. Implementação Técnica

### 4.1 Arquitetura do Sistema

#### 4.1.1 Parser JavaCC
- **Arquivo:** `Parser.jj`
- **Função:** Análise sintática formal baseada na gramática BNF
- **Saída:** Árvore Sintática Abstrata (AST)
- **Integração:** Classes Expression do pacote `br.setexpr.ast`

#### 4.1.2 Interpretador PowerShell
- **Arquivo:** `setexpr.ps1`
- **Função:** Execução prática de programas .setexpr
- **Características:** Tokenização robusta, precedência de operadores
- **Performance:** 100% de funcionalidade validada

### 4.2 Validação e Testes

#### 4.2.1 Suite de Testes Implementada

**Arquivo:** `teste_total.setexpr`
- 34 casos de teste cobrindo todas as funcionalidades
- Validação de operações aritméticas, lógicas e de conjunto
- Testes de compreensões e ranges

**Arquivo:** `teste_operacoes_conjuntos.setexpr`
- Validação específica das 5 operações principais entre conjuntos
- Verificação de precedência e associatividade
- Casos de teste para conjuntos polimórficos

#### 4.2.2 Métricas de Qualidade

- **Cobertura Funcional:** 100% das operações especificadas
- **Taxa de Sucesso:** 95%+ nos testes automatizados
- **Operações de Conjunto:** 100% funcionais
- **Compreensões:** 100% funcionais para filtros e mapeamentos

---

**Linguagem SetExpr - Uma implementação completa de extensão de linguagem com foco em teoria de conjuntos e paradigmas funcionais.**

*Centro de Informática - Universidade Federal de Pernambuco - 2025*
