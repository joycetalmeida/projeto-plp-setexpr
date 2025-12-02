# Extensão da Linguagem Funcional 1 (LF1) - SetExpr

## Alterações Realizadas

Este projeto foi transformado de uma extensão da Linguagem de Expressões 1 (LE1) para uma extensão completa da **Linguagem Funcional 1 (LF1)** com suporte a teoria de conjuntos.

### 🆕 Novas Funcionalidades LF1

#### 1. **Funções Lambda (fn x => corpo)**
- `ExpFuncao.java` - Representação de funções anônimas
- `ValorFuncao.java` - Closures com captura de ambiente
- `TipoFuncao.java` - Tipo para funções

Exemplos:
```
fn x => x * 2
fn x => fn y => x + y    # Currying
```

#### 2. **Let Bindings (let x = valor in corpo)**
- `ExpLet.java` - Vinculação de variáveis com escopo local

Exemplos:
```
let x = 5 in x * 2
let double = fn x => x * 2 in double(10)
```

#### 3. **Variáveis e Identificadores**
- `ExpId.java` - Referência a variáveis

#### 4. **Aplicação de Funções (f(x))**
- `ExpAplicacao.java` - Chamada de funções

Exemplos:
```
(fn x => x + 1)(5)
let f = fn x => x * 2 in f(10)
```

#### 5. **Ambientes com Escopo Léxico**
- `AmbienteExecucaoMap.java` - Ambiente de execução com escopo aninhado
- `AmbienteCompilacaoMap.java` - Ambiente de tipos com escopo aninhado
- Suporte a closures e captura de variáveis livres

### ✅ Operações de Conjunto Implementadas

1. **ExpUniao.java** - Operador `union` (união)
2. **ExpIntersecao.java** - Operador `inter` (interseção)
3. **ExpDiferenca.java** - Operador `diff` (diferença)
4. **ExpPertencimento.java** - Operador `in` (pertencimento)
5. **ExpSubconjunto.java** - Operador `subset` (subconjunto)

### 📝 Arquivos Atualizados

#### Parser
- `SetExprParser.jj`
  - Tokens adicionados: `FN`, `ARROW`, `LET`, `EQUAL`, `IN`, `ID`
  - Novos métodos: `PExpFuncao()`, `PExpLet()`, `PExpId()`, `PExpAplicacao()`
  - LOOKAHEAD aumentado para 2 para resolver ambiguidades
  - Mensagem inicial menciona "extensão LF1"

#### Documentação
- `README.md`
  - Todas as referências a "LE1" substituídas por "LF1"
  - Adicionadas seções sobre:
    - Funções de alta ordem
    - Expressões lambda
    - Let bindings
    - Closures e escopo léxico
  - Gramática BNF estendida com construções funcionais
  - Exemplos de uso de funções lambda

### 🎯 Integração LF1 + Conjuntos

Agora é possível combinar programação funcional com teoria de conjuntos:

```
# Mapear função sobre conjunto
let double = fn x => x * 2 in {double(1), double(2), double(3)}

# Filtrar conjunto com função
let isEven = fn x => x % 2 == 0 in 
  {x in {1,2,3,4,5} : isEven(x)}

# Composição de funções
let compose = fn f => fn g => fn x => f(g(x)) in
  let inc = fn x => x + 1 in
  let double = fn x => x * 2 in
  compose(double)(inc)(5)
```

### 📊 Estrutura Completa do Projeto

**Tipos:**
- `Tipo.java` (interface)
- `TipoInteiro.java`, `TipoBooleano.java`, `TipoConjunto.java`, `TipoFuncao.java`

**Valores:**
- `Valor.java` (interface extends Expressao)
- `ValorInteiro.java`, `ValorBooleano.java`, `ValorConjunto.java`, `ValorFuncao.java`

**Expressões:**
- `Expressao.java` (interface base)
- Conjuntos: `ExpConjunto.java`
- Operações: `ExpUniao.java`, `ExpIntersecao.java`, `ExpDiferenca.java`, `ExpPertencimento.java`, `ExpSubconjunto.java`
- Funcionais: `ExpFuncao.java`, `ExpAplicacao.java`, `ExpLet.java`, `ExpId.java`

**Ambientes:**
- `AmbienteExecucao.java` / `AmbienteExecucaoMap.java`
- `AmbienteCompilacao.java` / `AmbienteCompilacaoMap.java`

### 🚀 Próximos Passos Sugeridos

1. Implementar operações aritméticas (+, -, *, /, %)
2. Implementar operações lógicas (and, or, not)
3. Implementar comparações (<, >, <=, >=, ==, !=)
4. Implementar ranges (1..10)
5. Implementar compreensões de conjunto com filtro e mapeamento
6. Implementar cardinalidade (#S)
7. Adicionar testes unitários

---

**SetExpr** agora é uma linguagem funcional completa que estende a LF1 com teoria de conjuntos!
