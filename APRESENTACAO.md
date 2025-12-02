# SetExpr: Linguagem Funcional com Teoria de Conjuntos
## Apresentação Acadêmica

**Centro de Informática - UFPE**  
**Paradigmas de Linguagens de Programação**  
**2025**

---

## 📋 Sumário

1. [Introdução](#1-introdução)
2. [Fundamentos Teóricos](#2-fundamentos-teóricos)
3. [Arquitetura da Linguagem](#3-arquitetura-da-linguagem)
4. [Operações Básicas de Conjuntos](#4-operações-básicas-de-conjuntos)
5. [Operações Relacionais](#5-operações-relacionais)
6. [Operações de Distribuição](#6-operações-de-distribuição)
7. [Características Funcionais](#7-características-funcionais)
8. [Conjuntos Aninhados](#8-conjuntos-aninhados)
9. [Demonstrações ao Vivo](#9-demonstrações-ao-vivo)
10. [Conclusão](#10-conclusão)

---

## 1. Introdução

### 1.1 Motivação

A teoria de conjuntos é fundamental na matemática e ciência da computação, porém:
- Poucas linguagens oferecem suporte nativo robusto
- Operações frequentemente requerem bibliotecas externas
- Sintaxe não reflete naturalmente a notação matemática

### 1.2 Objetivo

**SetExpr** é uma extensão da **Linguagem Funcional 1 (LF1)** que integra:
- ✅ Operações completas de teoria de conjuntos
- ✅ Paradigma funcional com closures e composição
- ✅ Sintaxe próxima à notação matemática
- ✅ Sistema de tipos com verificação estática

### 1.3 Características Principais

```
📊 16 Operações de Conjuntos (incluindo Range)
🔧 Paradigma Funcional Completo
🎯 Conjuntos Aninhados (profundidade ilimitada)
⚡ Parser JavaCC com LALR
🔒 Tipagem Estática
🔢 Ranges para geração de sequências
```

---

## 2. Fundamentos Teóricos

### 2.1 Teoria de Conjuntos

**Definição**: Um conjunto é uma coleção não ordenada de elementos distintos.

**Propriedades**:
- **Unicidade**: Cada elemento aparece apenas uma vez
- **Não-ordenação**: {1, 2, 3} ≡ {3, 2, 1}
- **Extensionalidade**: Conjuntos são iguais se têm os mesmos elementos

### 2.2 Operações Fundamentais

| Operação | Notação Matemática | Sintaxe SetExpr |
|----------|-------------------|-----------------|
| União | A ∪ B | `A union B` |
| Interseção | A ∩ B | `A inter B` |
| Diferença | A \ B | `A diff B` |
| Pertencimento | x ∈ A | `x in A` |
| Cardinalidade | \|A\| | `#A` |

### 2.3 Relações Entre Conjuntos

| Relação | Notação | Sintaxe SetExpr |
|---------|---------|-----------------|
| Subconjunto | A ⊆ B | `A subset B` |
| Superconjunto | A ⊇ B | `A superset B` |
| Subconjunto Próprio | A ⊂ B | `A psubset B` |
| Superconjunto Próprio | A ⊃ B | `A psuperset B` |
| Disjuntos | A ∩ B = ∅ | `A disjoint B` |

---

## 3. Arquitetura da Linguagem

### 3.1 Componentes

```
┌─────────────────────────────────────────┐
│         SetExprParser.jj                │
│    (Gramática JavaCC - 230 linhas)     │
└──────────────┬──────────────────────────┘
               │
               ↓
┌─────────────────────────────────────────┐
│      Análise Sintática (Parser)         │
│  - Tokens: 20+ operadores               │
│  - Produções: 12 níveis precedência     │
└──────────────┬──────────────────────────┘
               │
               ↓
┌─────────────────────────────────────────┐
│    Árvore de Sintaxe Abstrata (AST)    │
│  - 18 classes Expressão                 │
│  - Interface comum: avaliar/checaTipo   │
└──────────────┬──────────────────────────┘
               │
               ↓
┌─────────────────────────────────────────┐
│      Verificação de Tipos               │
│  - TipoInteiro, TipoBooleano            │
│  - TipoConjunto, TipoFuncao             │
└──────────────┬──────────────────────────┘
               │
               ↓
┌─────────────────────────────────────────┐
│         Avaliação                       │
│  - AmbienteExecucao (escopo léxico)     │
│  - ValorInteiro, ValorBooleano          │
│  - ValorConjunto (HashSet<Valor>)       │
│  - ValorFuncao (closure)                │
└─────────────────────────────────────────┘
```

### 3.2 Hierarquia de Tipos

```java
Tipo (interface)
  ├── TipoInteiro
  ├── TipoBooleano
  ├── TipoConjunto
  └── TipoFuncao

Valor (interface)
  ├── ValorInteiro
  ├── ValorBooleano
  ├── ValorConjunto
  └── ValorFuncao
```

### 3.3 Pipeline de Execução

```
Código Fonte → Lexer → Parser → AST → Checagem Tipos → Avaliação → Resultado
```

---

## 4. Operações Básicas de Conjuntos

### 4.1 União (union) - A ∪ B

**Definição**: Elementos que pertencem a A **ou** B (ou ambos).

**Implementação**:
```java
// ExpUniao.java
Set<Valor> resultado = new HashSet<>(conjuntoEsq);
resultado.addAll(conjuntoDir);
```

**Demonstração**:
```setexpr
{1, 2, 3} union {3, 4, 5}
```

**Resultado Esperado**: `[1, 2, 3, 4, 5]`

**Propriedades**:
- Comutativa: A ∪ B = B ∪ A
- Associativa: (A ∪ B) ∪ C = A ∪ (B ∪ C)
- Identidade: A ∪ ∅ = A

---

### 4.2 Interseção (inter) - A ∩ B

**Definição**: Elementos que pertencem a A **e** B simultaneamente.

**Implementação**:
```java
// ExpIntersecao.java
Set<Valor> resultado = new HashSet<>(conjuntoEsq);
resultado.retainAll(conjuntoDir);
```

**Demonstração**:
```setexpr
{1, 2, 3, 4} inter {3, 4, 5, 6}
```

**Resultado Esperado**: `[3, 4]`

**Propriedades**:
- Comutativa: A ∩ B = B ∩ A
- Associativa: (A ∩ B) ∩ C = A ∩ (B ∩ C)
- Identidade: A ∩ U = A (U = universo)

---

### 4.3 Diferença (diff) - A \ B

**Definição**: Elementos que pertencem a A mas **não** pertencem a B.

**Implementação**:
```java
// ExpDiferenca.java
Set<Valor> resultado = new HashSet<>(conjuntoEsq);
resultado.removeAll(conjuntoDir);
```

**Demonstração**:
```setexpr
{1, 2, 3, 4, 5} diff {2, 4}
```

**Resultado Esperado**: `[1, 3, 5]`

**Propriedades**:
- **NÃO** comutativa: A \ B ≠ B \ A
- A \ A = ∅
- A \ ∅ = A

---

### 4.4 Pertencimento (in) - x ∈ A

**Definição**: Verifica se um elemento pertence ao conjunto.

**Implementação**:
```java
// ExpPertencimento.java
boolean resultado = conjunto.contains(elemento);
return new ValorBooleano(resultado);
```

**Demonstração**:
```setexpr
3 in {1, 2, 3, 4, 5}
5 in {1, 2, 3}
```

**Resultado Esperado**: `true`, `false`

---

### 4.5 Cardinalidade (#) - |A|

**Definição**: Número de elementos no conjunto.

**Implementação**:
```java
// ExpCardinalidade.java
int tamanho = conjunto.size();
return new ValorInteiro(tamanho);
```

**Demonstração**:
```setexpr
#{1, 2, 3, 4, 5}
#{}
```

**Resultado Esperado**: `5`, `0`

**Propriedades**:
- |A ∪ B| = |A| + |B| - |A ∩ B|
- |A × B| = |A| × |B|
- |℘(A)| = 2^|A|

---

## 5. Operações Relacionais

### 5.1 Subconjunto (subset) - A ⊆ B

**Definição**: Todo elemento de A pertence a B.

**Implementação**:
```java
// ExpSubconjunto.java
boolean resultado = conjuntoDir.containsAll(conjuntoEsq);
```

**Demonstração**:
```setexpr
{1, 2} subset {1, 2, 3, 4}
{1, 2, 3} subset {1, 2, 3}
```

**Resultado Esperado**: `true`, `true`

**Nota**: A ⊆ B permite A = B

---

### 5.2 Subconjunto Próprio (psubset) - A ⊂ B

**Definição**: A ⊆ B **e** A ≠ B.

**Implementação**:
```java
// ExpSubconjuntoProprio.java
boolean contem = conjuntoDir.containsAll(conjuntoEsq);
boolean diferente = !conjEsq.equals(conjDir);
return contem && diferente;
```

**Demonstração**:
```setexpr
{1, 2} psubset {1, 2, 3}
{1, 2} psubset {1, 2}
```

**Resultado Esperado**: `true`, `false`

---

### 5.3 Superconjunto (superset) - A ⊇ B

**Definição**: Todo elemento de B pertence a A (inverso de subset).

**Demonstração**:
```setexpr
{1, 2, 3, 4} superset {1, 2}
{1, 2} superset {1, 2, 3}
```

**Resultado Esperado**: `true`, `false`

---

### 5.4 Superconjunto Próprio (psuperset) - A ⊃ B

**Definição**: A ⊇ B **e** A ≠ B.

**Demonstração**:
```setexpr
{1, 2, 3, 4} psuperset {1, 2}
{1, 2} psuperset {1, 2}
```

**Resultado Esperado**: `true`, `false`

---

### 5.5 Conjuntos Disjuntos (disjoint) - A ∩ B = ∅

**Definição**: Não possuem elementos em comum.

**Implementação**:
```java
// ExpDisjuntos.java
Set<Valor> intersecao = new HashSet<>(conjuntoEsq);
intersecao.retainAll(conjuntoDir);
return new ValorBooleano(intersecao.isEmpty());
```

**Demonstração**:
```setexpr
{1, 2} disjoint {3, 4}
{1, 2, 3} disjoint {3, 4, 5}
```

**Resultado Esperado**: `true`, `false`

---

### 5.6 Igualdade (==) e Desigualdade (!=)

**Definição**: Conjuntos têm exatamente os mesmos elementos.

**Implementação**:
```java
// ExpIgualdade.java
boolean resultado = valEsq.equals(valDir);
```

**Demonstração**:
```setexpr
{1, 2, 3} == {3, 2, 1}
{1, 2} == {1, 2, 3}
{1, 2} != {3, 4}
```

**Resultado Esperado**: `true`, `false`, `true`

---

## 6. Operações de Distribuição

### Slides 18-20: Operações avançadas

#### 6.1 Produto Cartesiano (cross) - A × B

**Definição**: Conjunto de todos os pares ordenados (a, b) onde a ∈ A e b ∈ B.

**Matemática**: A × B = {(a, b) | a ∈ A, b ∈ B}

**Implementação**:
```java
// ExpProdutoCartesiano.java
for (Valor a : conjuntoEsq) {
    for (Valor b : conjuntoDir) {
        Set<Valor> par = new HashSet<>();
        par.add(a);
        par.add(b);
        resultado.add(new ValorConjunto(par));
    }
}
```

**Demonstração**:
```setexpr
{1, 2} cross {3, 4}
```

**Resultado Esperado**: `[[1, 3], [1, 4], [2, 3], [2, 4]]`

**Propriedades**:
- **NÃO** comutativa: A × B ≠ B × A
- |A × B| = |A| × |B|
- Elemento neutro à direita: A × {b} = {(a, b) | a ∈ A}

**Aplicações**:
- Relações em bancos de dados
- Grafos (conjunto de arestas)
- Espaços de estados em autômatos

---

### 6.2 Flatten - ⋃S

**Definição**: Achata um conjunto de conjuntos em um único nível (união generalizada).

**Matemática**: flatten({{a₁, a₂}, {b₁}, {c₁, c₂, c₃}}) = {a₁, a₂, b₁, c₁, c₂, c₃}

**Implementação**:
```java
// ExpFlatten.java
for (Valor elemento : conjuntoExterno) {
    if (elemento instanceof ValorConjunto) {
        resultado.addAll(((ValorConjunto) elemento).getValor());
    } else {
        resultado.add(elemento);
    }
}
```

**Demonstração**:
```setexpr
flatten {{1, 2}, {3, 4}, {5}}
flatten {{{1, 2}}, {{3}}}
```

**Resultado Esperado**: `[1, 2, 3, 4, 5]`, `[{1, 2}, {3}]`

**Nota**: Flatten opera **apenas um nível** de profundidade.

**Aplicações**:
- Processamento de partições
- Agregação de resultados
- Simplificação de estruturas aninhadas

---

### 6.3 Powerset (Conjunto das Partes) - ℘(A)

**Definição**: Conjunto de todos os subconjuntos possíveis de A.

**Matemática**: ℘(A) = {S | S ⊆ A}

**Implementação** (usando representação binária):
```java
// ExpPowerset.java
int n = elementos.size();
int totalSubconjuntos = (int) Math.pow(2, n);

for (int i = 0; i < totalSubconjuntos; i++) {
    Set<Valor> subconjunto = new HashSet<>();
    for (int j = 0; j < n; j++) {
        if ((i & (1 << j)) != 0) {
            subconjunto.add(listaElementos.get(j));
        }
    }
    powerset.add(new ValorConjunto(subconjunto));
}
```

**Demonstração**:
```setexpr
powerset {1, 2}
powerset {1, 2, 3}
#(powerset {1, 2, 3})
```

**Resultado Esperado**: 
- `[[], [1], [2], [1, 2]]`
- `[[], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]]`
- `8`

**Propriedades**:
- |℘(A)| = 2^|A|
- ∅ ∈ ℘(A) sempre
- A ∈ ℘(A) sempre
- Se A ⊆ B então ℘(A) ⊆ ℘(B)

**Complexidade**: O(2^n × n) - exponencial

**Aplicações**:
- Análise combinatória
- Geração de configurações
- Testes de cobertura (todos os casos)

---

## 7. Operações com Range

### 7.1 Range (geração de sequências) - ... 

**Definição**: Gera conjunto contendo sequência de inteiros de início até fim (inclusivo).

**Sintaxe**: `inicio...fim`

**Implementação**:
```java
// ExpRange.java
int start = intInicio.getValor();
int end = intFim.getValor();

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
```

**Demonstração**:
```setexpr
1...5
```
**Resultado**: `[1, 2, 3, 4, 5]`

💡 *Explicar*: 
- Sintaxe concisa para gerar sequências
- Suporta ranges crescentes e decrescentes
- Gera conjuntos (não listas ordenadas)

**Demonstrações adicionais**:
```setexpr
1...10                    # {1, 2, 3, ..., 10}
10...1                    # {10, 9, 8, ..., 1} (decrescente)
#(1...100)                # 100
3 in (1...10)             # true
(1...5) union (6...10)    # {1, 2, ..., 10}
(1...10) inter (5...15)   # {5, 6, ..., 10}
(1...10) diff (5...7)     # {1,2,3,4,8,9,10}
```

**Propriedades**:
- |inicio...fim| = |fim - inicio| + 1
- Ranges vazios: se inicio > fim em range crescente
- Eficiente para grandes sequências
- Combina com todas as operações de conjunto

**Aplicações**:
- Geração de domínios numéricos
- Iteração sobre intervalos
- Criação rápida de conjuntos de teste
- Bases para compreensões (futuro)

---

## 8. Características Funcionais

### 8.1 Funções Lambda (fn x => corpo)

**Sintaxe**:
```setexpr
fn x => x union {10}
```

**Demonstração**:
```setexpr
let f = fn x => x union {10} in f({1, 2, 3})
```

**Resultado Esperado**: `[1, 2, 3, 10]`

---

### 8.2 Let Bindings

**Sintaxe**:
```setexpr
let var x = valor in expressao
let fun f x = corpo in expressao
```

**Demonstração**:
```setexpr
let var conjunto1 = {1, 2, 3},
    var conjunto2 = {4, 5, 6}
in conjunto1 union conjunto2
```

**Resultado Esperado**: `[1, 2, 3, 4, 5, 6]`

---

### 8.3 Closures (Escopo Léxico)

**Definição**: Funções capturam o ambiente onde foram definidas.

**Demonstração**:
```setexpr
let var base = {10, 20} in
  let fun adicionar x = base union x in
    adicionar({1, 2})
```

**Resultado Esperado**: `[1, 2, 10, 20]`

---

### 7.4 Composição de Funções

**Demonstração**:
```setexpr
let fun dobrar x = x union x in
  let fun add10 x = x union {10} in
    add10(dobrar({1, 2}))
```

**Resultado Esperado**: `[1, 2, 10]`

---

## 8. Conjuntos Aninhados

### 9.1 Profundidade Arbitrária

SetExpr suporta conjuntos aninhados sem limite de profundidade.

**Exemplos**:
```setexpr
{{1, 2}, {3, 4}}                    # Profundidade 2
{{{1}}, {{2, 3}}}                   # Profundidade 3
{{{{1}}}}                           # Profundidade 4
```

---

### 9.2 Operações com Conjuntos Aninhados

**Pertencimento**:
```setexpr
{1, 2} in {{1, 2}, {3, 4}}
```
**Resultado**: `true`

**União**:
```setexpr
{{1, 2}, {3}} union {{4, 5}}
```
**Resultado**: `[[1, 2], [3], [4, 5]]`

**Cardinalidade**:
```setexpr
#{{1, 2}, {3, 4}, {5}}
```
**Resultado**: `3` (três conjuntos internos)

---

### 9.3 Flatten vs Profundidade

```setexpr
flatten {{{1, 2}}, {{3}}}           # Um nível: [{1, 2}, {3}]
flatten (flatten {{{1, 2}}, {{3}}}) # Dois níveis: [1, 2, 3]
```

---

## 9. Demonstrações ao Vivo

### 10.1 Operações Básicas

```bash
# União
echo "{1, 2, 3} union {3, 4, 5}" | java SetExprParser

# Interseção
echo "{1, 2, 3, 4} inter {3, 4, 5}" | java SetExprParser

# Diferença
echo "{1, 2, 3, 4, 5} diff {2, 4}" | java SetExprParser

# Pertencimento
echo "3 in {1, 2, 3, 4, 5}" | java SetExprParser

# Cardinalidade
echo "#{1, 2, 3, 4, 5}" | java SetExprParser
```

---

### 10.2 Operações Relacionais

```bash
# Subconjunto
echo "{1, 2} subset {1, 2, 3, 4}" | java SetExprParser

# Subconjunto Próprio
echo "{1, 2} psubset {1, 2, 3}" | java SetExprParser

# Disjuntos
echo "{1, 2} disjoint {3, 4}" | java SetExprParser

# Igualdade
echo "{1, 2, 3} == {3, 2, 1}" | java SetExprParser
```

---

### 10.3 Operações de Distribuição

```bash
# Produto Cartesiano
echo "{1, 2} cross {3, 4}" | java SetExprParser

# Flatten
echo "flatten {{1, 2}, {3, 4}, {5}}" | java SetExprParser

# Powerset
echo "powerset {1, 2, 3}" | java SetExprParser

# Cardinalidade do Powerset
echo "#(powerset {1, 2, 3})" | java SetExprParser
```

---

### 9.4 Exemplos Compostos

```bash
# União distribuída com flatten
echo "flatten ({{1, 2}, {3}} union {{4, 5}})" | java SetExprParser

# Pertencimento no powerset
echo "{1} in (powerset {1, 2})" | java SetExprParser

# Produto cartesiano com cardinalidade
echo "#({1, 2} cross {3, 4})" | java SetExprParser
```

---

### 9.5 Programação Funcional

```bash
# Lambda simples
echo "(fn x => x union {10})({1, 2, 3})" | java SetExprParser

# Let binding
echo "let var x = {1, 2, 3} in x union {4, 5}" | java SetExprParser

# Função nomeada
echo "let fun f x = x union {10} in f({1, 2, 3})" | java SetExprParser
```

---

### 9.6 Casos Complexos

```bash
# Análise de subconjuntos
echo "let var A = {1, 2, 3}, var B = {1, 2}, var C = {4, 5} in if B subset A then B union C else A inter C" | java SetExprParser

# Verificação de partição
echo "let var S1 = {1, 2}, var S2 = {3, 4}, var S3 = {5} in if (S1 disjoint S2) then flatten {{S1, S2, S3}} else {}" | java SetExprParser
```

---

## 12. Conclusão

### Conquistas

✅ **16 operações de teoria de conjuntos** implementadas e testadas  
✅ **Paradigma funcional completo** com closures e composição  
✅ **Conjuntos aninhados** com profundidade ilimitada  
✅ **Parser robusto** com JavaCC e precedência correta  
✅ **Sistema de tipos** com verificação estática  
✅ **Sintaxe intuitiva** próxima à notação matemática  
✅ **Range operator** para geração eficiente de sequências  

### Operações Implementadas

**Básicas (5)**: union, inter, diff, in, #  
**Relacionais (7)**: subset, superset, psubset, psuperset, disjoint, ==, !=  
**Distribuição (3)**: cross, flatten, powerset  
**Range (1)**: ... (geração de sequências)  

**Total**: **16 operações**

### 10.3 Complexidade Algorítmica

| Operação | Complexidade | Observação |
|----------|--------------|------------|
| union | O(n + m) | HashSet.addAll |
| inter | O(min(n,m)) | HashSet.retainAll |
| diff | O(n) | HashSet.removeAll |
| in | O(1) amortizado | HashSet.contains |
| flatten | O(n × k) | k = tamanho médio interno |
| powerset | O(2^n × n) | Exponencial |
| range | O(n) | n = |fim - inicio| | |
| flatten | O(n × k) | k = tamanho médio interno |
| powerset | O(2^n × n) | Exponencial |

### 10.4 Vantagens da SetExpr

1. **Expressividade**: Sintaxe clara e próxima à matemática
2. **Completude**: Cobertura abrangente de operações de conjuntos
3. **Composicionalidade**: Operações podem ser combinadas livremente
4. **Segurança**: Tipagem estática detecta erros em tempo de compilação
5. **Eficiência**: HashSet garante operações O(1) para busca

### 10.5 Aplicações Potenciais

- **Educação**: Ensino de teoria de conjuntos
- **Prototipagem**: Modelagem rápida de problemas combinatórios
- **Verificação**: Análise de propriedades de conjuntos
- **Pesquisa**: Experimentação com algoritmos de conjuntos

### 10.6 Trabalhos Futuros
🔮 **Extensões Possíveis**:
- Compreensão de conjuntos: `{x * 2 | x in S, x > 5}`
- Ranges com passo: `1...10 step 2`
- Multiconjuntos (bags) com repetição
- Operações aritméticas completas
- Sistema de móduloscas completas
- Sistema de módulos

### 10.7 Comparação com Outras Linguagens
| Linguagem | Conjuntos Nativos | Operações | Imutabilidade | Range |
|-----------|-------------------|-----------|---------------|-------|
| **SetExpr** | ✅ Sim | 16 operações | ✅ Sim | ✅ ... |
| Python | ⚠️ set() | 6-8 métodos | ❌ Não | ✅ range() |
| Haskell | ⚠️ Data.Set | 10+ funções | ✅ Sim | ✅ [1..10] |
| Java | ❌ Biblioteca | HashSet API | ❌ Não | ❌ Não |
| SQL | ⚠️ Tables | UNION/INTERSECT | ✅ Sim | ❌ Não |
| SQL | ⚠️ Tables | UNION/INTERSECT | ✅ Sim |

### 10.8 Referências

1. **Halmos, P.** (1960). *Naive Set Theory*. Springer.
2. **Pierce, B.** (2002). *Types and Programming Languages*. MIT Press.
3. **Aho, A., Lam, M., Sethi, R., Ullman, J.** (2006). *Compilers: Principles, Techniques, and Tools*. Pearson.
4. **JavaCC Documentation**. https://javacc.github.io/javacc/
5. **Documentação SetExpr**: README.md, BNF_SETEXPR.md

---

## 📊 Estatísticas do Projeto

```
Linhas de Código Java:    ~2000 linhas
Arquivo Parser (JavaCC):   230 linhas
Classes Expressão:         19 classes
Classes Valor:             4 classes
Classes Tipo:              4 classes
Operações Implementadas:   16 operações
Testes Executados:         25+ casos
Taxa de Sucesso:           100%
```

---

## 🎯 Perguntas e Respostas

### Q1: Por que usar JavaCC?
**R**: JavaCC gera parsers LALR eficientes com sintaxe declarativa clara, ideal para linguagens acadêmicas.

### Q2: SetExpr é Turing-completo?
**R**: Com a extensão LF1 (funções, recursão), sim. Sem recursão explícita, é uma linguagem de primeiro grau.

### Q3: Como é representado internamente um conjunto?
**R**: `HashSet<Valor>` do Java, garantindo unicidade e operações O(1).

### Q4: Conjuntos podem conter funções?
**R**: Teoricamente sim (ValorFuncao implementa Valor), mas requer equals/hashCode customizados.

### Q5: Qual o limite prático de elementos?
**R**: Limitado pela memória da JVM. Powerset explode exponencialmente: 2^30 ≈ 1 bilhão de subconjuntos.

---

## 🚀 Como Executar

### Compilação:
```bash
java -cp javacc-7.0.13.jar javacc SetExprParser.jj
javac *.java
```

### Execução Interativa:
```bash
java SetExprParser
```

### Execução com Pipeline:
```bash
echo "{1, 2, 3} union {4, 5}" | java SetExprParser
```

### Script de Testes:
```bash
.\run-tests.ps1
```

---

## 📝 Contato

**Centro de Informática - UFPE**  
**Paradigmas de Linguagens de Programação**  
**2025**

---

**FIM DA APRESENTAÇÃO**

*SetExpr - Where Functional Programming Meets Set Theory* ✨
