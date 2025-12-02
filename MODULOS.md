# Guia de Módulos .setexpr

## Organização do Projeto

O projeto SetExpr agora está organizado em **módulos independentes** com extensão `.setexpr`. Cada módulo demonstra um aspecto específico da linguagem.

## 📁 Estrutura de Módulos

### 1. **ExpConjunto.setexpr**
**Tema:** Operações de Conjunto  
**Conteúdo:**
- Funções auxiliares para manipulação de conjuntos
- Implementações de união, interseção, diferença
- Funções de pertencimento e subconjunto
- 5 exemplos práticos

**Uso típico:**
```setexpr
let a = {1, 2, 3} in
let b = {3, 4, 5} in
a union b
```

---

### 2. **ExpFuncao.setexpr**
**Tema:** Funções Lambda e Programação Funcional  
**Conteúdo:**
- Funções de alta ordem (compose, apply, identity)
- Funções numéricas (double, inc, square, add, mul)
- Funções lógicas (negar, e, ou)
- Exemplos de composição e currying

**Uso típico:**
```setexpr
let compose = fn f => fn g => fn x => f(g(x)) in
let double = fn x => x * 2 in
let inc = fn x => x + 1 in
compose(double)(inc)(5)
```

---

### 3. **ExpLet.setexpr**
**Tema:** Let Bindings e Escopo Léxico  
**Conteúdo:**
- Let bindings simples e aninhados
- Shadowing de variáveis
- Closures e captura de ambiente
- Combinação de let com funções e conjuntos
- 15 exemplos progressivos

**Uso típico:**
```setexpr
let x = 10 in
  let y = 20 in
    x + y
```

---

### 4. **ExpAplicacao.setexpr**
**Tema:** Aplicação de Funções  
**Conteúdo:**
- Aplicação simples e curried
- IIFE (Immediately Invoked Function Expression)
- Funções de alta ordem
- Aplicação com diferentes tipos
- 25 exemplos variados

**Uso típico:**
```setexpr
(fn x => x * 2)(5)

let add = fn x => fn y => x + y in
add(5)(10)
```

---

### 5. **Valores.setexpr**
**Tema:** Valores Primitivos  
**Conteúdo:**
- Literais inteiros (positivos, negativos, zero)
- Literais booleanos (true, false)
- Conjuntos (vazios, simples, aninhados, mistos)
- Combinações e contextos
- 25 exemplos de valores

**Uso típico:**
```setexpr
42
true
{1, 2, 3}
{{1, 2}, {3, 4}}
```

---

### 6. **Exemplos.setexpr**
**Tema:** Exemplos Integrados  
**Conteúdo:**
- 25 exemplos que combinam todas as funcionalidades
- Operações complexas de conjuntos
- Pipelines funcionais
- Closures avançados
- Expressões compostas

**Uso típico:**
```setexpr
let universo = {1, 2, 3, 4, 5} in
let filtrar = fn conj => conj inter {1, 2, 3} in
let adicionar = fn conj => conj union {6, 7} in
let compose = fn f => fn g => fn x => f(g(x)) in
compose(adicionar)(filtrar)(universo)
```

---

## 🚀 Como Usar os Módulos

### Opção 1: Executar um Módulo Completo
```bash
java SetExprParser < ExpConjunto.setexpr
```

### Opção 2: Testar Trechos Específicos
Copie um exemplo específico de um módulo e execute:
```bash
echo "let a = {1, 2, 3} in let b = {3, 4, 5} in a union b" | java SetExprParser
```

### Opção 3: Modo Interativo
```bash
java SetExprParser
# Digite expressões diretamente
```

---

## 📚 Progressão de Aprendizado

Sugerimos estudar os módulos nesta ordem:

1. **Valores.setexpr** - Comece pelos valores básicos
2. **ExpFuncao.setexpr** - Aprenda sobre funções
3. **ExpLet.setexpr** - Entenda escopo e vinculação
4. **ExpAplicacao.setexpr** - Domine aplicação de funções
5. **ExpConjunto.setexpr** - Explore operações de conjunto
6. **Exemplos.setexpr** - Veja tudo integrado

---

## 🔍 Recursos de Cada Módulo

| Módulo | Linhas | Exemplos | Dificuldade | Tópicos-chave |
|--------|--------|----------|-------------|---------------|
| Valores.setexpr | ~180 | 25 | ⭐ Básico | Literais, tipos primitivos |
| ExpFuncao.setexpr | ~150 | 5 | ⭐⭐ Intermediário | Lambda, composição, currying |
| ExpLet.setexpr | ~180 | 15 | ⭐⭐ Intermediário | Escopo, closures, shadowing |
| ExpAplicacao.setexpr | ~250 | 25 | ⭐⭐⭐ Avançado | IIFE, alta ordem, polimorfismo |
| ExpConjunto.setexpr | ~80 | 5 | ⭐⭐ Intermediário | União, interseção, pertencimento |
| Exemplos.setexpr | ~280 | 25 | ⭐⭐⭐ Avançado | Integração completa |

---

## 💡 Dicas de Uso

### Para Estudantes
- Comece pelos exemplos simples em cada módulo
- Execute linha por linha para entender o resultado
- Modifique os exemplos para experimentar

### Para Professores
- Use os módulos como material didático
- Cada módulo cobre um tópico específico da disciplina
- Os exemplos são progressivos em dificuldade

### Para Desenvolvedores
- Os módulos servem como biblioteca de referência
- Copie e adapte funções para seus programas
- Combine múltiplos módulos em seus projetos

---

## 🎯 Exemplos Rápidos por Módulo

### ExpConjunto.setexpr
```setexpr
{1, 2, 3} union {3, 4, 5}  # {1, 2, 3, 4, 5}
{1, 2, 3} inter {2, 3, 4}  # {2, 3}
3 in {1, 2, 3}             # true
```

### ExpFuncao.setexpr
```setexpr
let double = fn x => x * 2 in double(5)  # 10
let add = fn x => fn y => x + y in add(3)(4)  # 7
```

### ExpLet.setexpr
```setexpr
let x = 5 in let y = 10 in x + y  # 15
let f = fn x => x * 2 in f(7)     # 14
```

### ExpAplicacao.setexpr
```setexpr
(fn x => x * x)(5)                    # 25
let f = fn x => x + 1 in f(10)        # 11
```

---

## 📖 Convenções dos Módulos

### Estrutura Padrão
```setexpr
# Título do Módulo
# Descrição
# Arquivo: Nome.setexpr

# --- SEÇÃO 1 ---
# Exemplo N: Descrição
código
# Resultado: valor esperado
```

### Comentários
- `#` para comentários de linha única
- Sempre indicar o resultado esperado
- Explicar conceitos complexos

### Nomenclatura
- Funções em camelCase: `makeAdder`, `isPositive`
- Variáveis descritivas: `numeros`, `conjunto`, `resultado`
- Constantes em camelCase: `conjuntoVazio`

---

## 🔧 Compilação e Execução

### Gerar o Parser
```bash
javacc SetExprParser.jj
javac *.java
```

### Executar Módulo
```bash
java SetExprParser < ExpConjunto.setexpr
```

### Executar Exemplo Específico
```bash
echo "let x = {1,2,3} in x union {4,5}" | java SetExprParser
```

---

## 📝 Criando Seus Próprios Módulos

### Template Básico
```setexpr
# Meu Módulo Personalizado
# Descrição: O que este módulo faz
# Arquivo: MeuModulo.setexpr

# --- FUNCIONALIDADES ---

let minhaFuncao = fn x => x in

# --- EXEMPLOS ---

# Exemplo 1: Descrição
minhaFuncao(42)
# Resultado: 42
```

### Boas Práticas
1. Documente cada função
2. Forneça exemplos de uso
3. Indique resultados esperados
4. Organize por seções temáticas
5. Teste todos os exemplos

---

## 🎓 Recursos Pedagógicos

### Para IN1007 (PLP)
- **Aula 1-2:** Valores.setexpr
- **Aula 3-4:** ExpFuncao.setexpr + ExpLet.setexpr
- **Aula 5-6:** ExpAplicacao.setexpr
- **Aula 7-8:** ExpConjunto.setexpr
- **Projeto Final:** Exemplos.setexpr

### Exercícios Sugeridos
1. Modificar exemplos existentes
2. Criar novas funções de conjunto
3. Implementar funções de alta ordem
4. Combinar múltiplos módulos
5. Desenvolver biblioteca personalizada

---

**SetExpr - Extensão da LF1 com Teoria de Conjuntos**  
*Módulos organizados para aprendizado progressivo*
