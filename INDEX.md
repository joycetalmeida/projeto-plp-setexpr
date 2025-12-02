# 📚 Índice Completo do Projeto SetExpr

## 🎯 Visão Geral

**SetExpr** é uma extensão da Linguagem Funcional 1 (LF1) com suporte completo a teoria de conjuntos, organizada em módulos independentes com extensão `.setexpr`.

---

## 📁 Arquivos do Projeto

### Documentação (4 arquivos)
| Arquivo | Descrição | Conteúdo |
|---------|-----------|----------|
| `README.md` | Documentação principal | Gramática BNF, objetivos, arquitetura |
| `MODULOS.md` | Guia dos módulos | Detalhes de cada módulo .setexpr |
| `QUICKSTART.md` | Início rápido | Como usar e executar |
| `ALTERACOES_LF1.md` | Changelog técnico | Mudanças de LE1 para LF1 |
| `INDEX.md` | Este arquivo | Índice completo |

### Módulos .setexpr (6 arquivos)
| Módulo | Exemplos | Linhas | Dificuldade |
|--------|----------|--------|-------------|
| `Valores.setexpr` | 25 | ~180 | ⭐ Básico |
| `ExpFuncao.setexpr` | 5+ | ~150 | ⭐⭐ Intermediário |
| `ExpLet.setexpr` | 15 | ~180 | ⭐⭐ Intermediário |
| `ExpAplicacao.setexpr` | 25 | ~250 | ⭐⭐⭐ Avançado |
| `ExpConjunto.setexpr` | 5 | ~80 | ⭐⭐ Intermediário |
| `Exemplos.setexpr` | 25 | ~280 | ⭐⭐⭐ Avançado |

### Código-Fonte Java (28 arquivos)

#### Parser
- `SetExprParser.jj` - Definição do parser JavaCC

#### Interfaces Base
- `Expressao.java` - Interface base para expressões
- `Valor.java` - Interface para valores
- `Tipo.java` - Interface para tipos
- `AmbienteExecucao.java` - Interface do ambiente de execução
- `AmbienteCompilacao.java` - Interface do ambiente de compilação

#### Tipos (4 classes)
- `TipoInteiro.java` - Tipo inteiro (Singleton)
- `TipoBooleano.java` - Tipo booleano (Singleton)
- `TipoConjunto.java` - Tipo conjunto (Singleton)
- `TipoFuncao.java` - Tipo função (Singleton)

#### Valores (4 classes)
- `ValorInteiro.java` - Valor inteiro
- `ValorBooleano.java` - Valor booleano
- `ValorConjunto.java` - Valor conjunto
- `ValorFuncao.java` - Valor função (closure)

#### Expressões de Conjunto (5 classes)
- `ExpConjunto.java` - Literal de conjunto
- `ExpUniao.java` - Operador union
- `ExpIntersecao.java` - Operador inter
- `ExpDiferenca.java` - Operador diff
- `ExpPertencimento.java` - Operador in
- `ExpSubconjunto.java` - Operador subset

#### Expressões Funcionais (4 classes)
- `ExpFuncao.java` - Função lambda (fn x => corpo)
- `ExpAplicacao.java` - Aplicação de função f(x)
- `ExpLet.java` - Let binding
- `ExpId.java` - Identificador/variável

#### Ambientes (2 classes)
- `AmbienteExecucaoMap.java` - Implementação com HashMap
- `AmbienteCompilacaoMap.java` - Implementação com HashMap

---

## 🔍 Índice por Funcionalidade

### Operações de Conjunto
- **União:** `ExpUniao.java`, exemplos em `ExpConjunto.setexpr`
- **Interseção:** `ExpIntersecao.java`, exemplos em `ExpConjunto.setexpr`
- **Diferença:** `ExpDiferenca.java`, exemplos em `ExpConjunto.setexpr`
- **Pertencimento:** `ExpPertencimento.java`, exemplos em `ExpConjunto.setexpr`
- **Subconjunto:** `ExpSubconjunto.java`, exemplos em `ExpConjunto.setexpr`

### Funções Lambda
- **Definição:** `ExpFuncao.java`, exemplos em `ExpFuncao.setexpr`
- **Aplicação:** `ExpAplicacao.java`, exemplos em `ExpAplicacao.setexpr`
- **Closures:** `ValorFuncao.java`, exemplos em `ExpLet.setexpr`
- **Alta Ordem:** Exemplos em `ExpFuncao.setexpr` e `Exemplos.setexpr`

### Variáveis e Escopo
- **Let Bindings:** `ExpLet.java`, exemplos em `ExpLet.setexpr`
- **Identificadores:** `ExpId.java`, usado em todos os módulos
- **Escopo Léxico:** `AmbienteExecucaoMap.java`, exemplos em `ExpLet.setexpr`
- **Shadowing:** Exemplos em `ExpLet.setexpr`

### Valores Primitivos
- **Inteiros:** `ValorInteiro.java`, exemplos em `Valores.setexpr`
- **Booleanos:** `ValorBooleano.java`, exemplos em `Valores.setexpr`
- **Conjuntos:** `ValorConjunto.java`, exemplos em `Valores.setexpr`
- **Funções:** `ValorFuncao.java`, exemplos em `ExpFuncao.setexpr`

---

## 📖 Índice por Conceito (PLP)

### Paradigma Funcional
- Funções de primeira classe: `ExpFuncao.java`, `ValorFuncao.java`
- Imutabilidade: Todos os valores são imutáveis
- Ausência de efeitos colaterais: Design completo do sistema
- Funções puras: `ExpFuncao.setexpr`

### Escopo e Vinculação
- Escopo léxico: `AmbienteExecucaoMap.java`
- Escopo estático: Implementado via ambientes aninhados
- Closures: `ValorFuncao.java` captura ambiente
- Let bindings: `ExpLet.java`

### Sistema de Tipos
- Tipos primitivos: `TipoInteiro`, `TipoBooleano`
- Tipos compostos: `TipoConjunto`, `TipoFuncao`
- Polimorfismo: Conjuntos podem conter qualquer tipo
- Verificação de tipos: Interface `AmbienteCompilacao`

### Semântica Operacional
- Avaliação estrita: Todos argumentos avaliados antes da aplicação
- Ordem aplicativa: Avalia argumentos antes de aplicar função
- Redução: Implementado via método `avaliar()`

---

## 🎓 Índice Pedagógico

### Aula 1-2: Introdução
- Leia: `README.md` (seções 1-2)
- Execute: `Valores.setexpr`
- Conceitos: Literais, tipos primitivos

### Aula 3-4: Funções Lambda
- Leia: `README.md` (seção 3), `MODULOS.md`
- Execute: `ExpFuncao.setexpr`
- Conceitos: Lambda, aplicação, currying

### Aula 5-6: Escopo e Vinculação
- Leia: `ALTERACOES_LF1.md`
- Execute: `ExpLet.setexpr`
- Conceitos: Let, escopo léxico, closures

### Aula 7-8: Alta Ordem
- Execute: `ExpAplicacao.setexpr`
- Conceitos: Composição, funções que retornam funções

### Aula 9-10: Conjuntos
- Execute: `ExpConjunto.setexpr`
- Conceitos: Teoria de conjuntos, operações

### Projeto Final
- Execute: `Exemplos.setexpr`
- Desenvolva: Seu próprio módulo .setexpr

---

## 🔎 Busca Rápida

### Como fazer X?

#### Definir uma função
```setexpr
let minhaFuncao = fn x => x * 2 in
```
Ver: `ExpFuncao.setexpr`, linha ~10

#### Criar um conjunto
```setexpr
let meuConjunto = {1, 2, 3} in
```
Ver: `Valores.setexpr`, linha ~50

#### Usar let binding
```setexpr
let x = 5 in x + 10
```
Ver: `ExpLet.setexpr`, linha ~5

#### Aplicar função
```setexpr
(fn x => x + 1)(5)
```
Ver: `ExpAplicacao.setexpr`, linha ~5

#### Fazer união de conjuntos
```setexpr
{1, 2} union {3, 4}
```
Ver: `ExpConjunto.setexpr`, linha ~15

#### Compor funções
```setexpr
let compose = fn f => fn g => fn x => f(g(x)) in
```
Ver: `ExpFuncao.setexpr`, linha ~15

#### Criar closure
```setexpr
let x = 10 in
let f = fn y => x + y in
f(5)
```
Ver: `ExpLet.setexpr`, linha ~70

---

## 📊 Estatísticas do Projeto

### Código Java
- **Total de arquivos:** 28
- **Interfaces:** 5
- **Classes concretas:** 23
- **Linhas de código:** ~1500

### Módulos .setexpr
- **Total de arquivos:** 6
- **Total de exemplos:** 100+
- **Linhas totais:** ~1120
- **Comentários:** ~30%

### Documentação
- **Arquivos markdown:** 5
- **Linhas totais:** ~1500
- **Exemplos documentados:** 150+

---

## 🎯 Referência Rápida

### Sintaxe SetExpr

```setexpr
# Valores
42                          # Inteiro
true, false                 # Booleanos
{1, 2, 3}                   # Conjunto

# Funções
fn x => x * 2               # Lambda
(fn x => x + 1)(5)          # Aplicação
let f = fn x => x in f(5)   # Definição

# Let
let x = 5 in x + 10         # Simples
let x = 5 in let y = 10 in x + y  # Aninhado

# Conjuntos
{1, 2} union {3, 4}         # União
{1, 2, 3} inter {2, 3, 4}   # Interseção
{1, 2, 3} diff {2}          # Diferença
3 in {1, 2, 3}              # Pertencimento
{1, 2} subset {1, 2, 3}     # Subconjunto
```

### Comandos Shell

```bash
# Compilar
javacc SetExprParser.jj
javac *.java

# Executar módulo
java SetExprParser < ExpConjunto.setexpr

# Executar expressão
echo "{1,2,3} union {4,5}" | java SetExprParser

# Modo interativo
java SetExprParser
```

---

## 🔗 Links Internos

- [Documentação Principal](README.md)
- [Guia dos Módulos](MODULOS.md)
- [Início Rápido](QUICKSTART.md)
- [Changelog LF1](ALTERACOES_LF1.md)

---

## 📝 Convenções

### Nomenclatura de Arquivos
- `Exp*.java` - Classes de expressões
- `Valor*.java` - Classes de valores
- `Tipo*.java` - Classes de tipos
- `Ambiente*.java` - Classes de ambientes
- `*.setexpr` - Módulos de exemplo

### Padrões de Código
- Interfaces com métodos mínimos
- Classes concretas implementam interfaces
- Singletons para tipos
- Imutabilidade em valores
- Ambientes com escopo aninhado

---

## 🎉 Conclusão

Este índice cobre **todos os aspectos** do projeto SetExpr:
- ✅ 28 arquivos Java
- ✅ 6 módulos .setexpr
- ✅ 5 documentos markdown
- ✅ 100+ exemplos práticos
- ✅ Suporte completo LF1 + Conjuntos

**Projeto completo e pronto para uso acadêmico e prático!**

---

*Centro de Informática - UFPE - 2025*  
*Paradigmas de Linguagens de Programação (IN1007)*
