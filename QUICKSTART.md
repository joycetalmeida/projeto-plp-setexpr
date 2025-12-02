# Início Rápido - SetExpr

## 🚀 Como Começar

### Passo 1: Compilar o Parser
```bash
javacc SetExprParser.jj
javac *.java
```

### Passo 2: Executar um Módulo
```bash
java SetExprParser < ExpConjunto.setexpr
```

### Passo 3: Modo Interativo
```bash
java SetExprParser
# Digite suas expressões
{1, 2, 3} union {4, 5}
```

---

## 📚 Módulos Disponíveis

### Para Iniciantes
```bash
# 1. Comece com valores básicos
java SetExprParser < Valores.setexpr

# 2. Aprenda sobre funções
java SetExprParser < ExpFuncao.setexpr

# 3. Entenda let bindings
java SetExprParser < ExpLet.setexpr
```

### Para Usuários Avançados
```bash
# 4. Aplicação de funções
java SetExprParser < ExpAplicacao.setexpr

# 5. Operações de conjunto
java SetExprParser < ExpConjunto.setexpr

# 6. Exemplos integrados
java SetExprParser < Exemplos.setexpr
```

---

## 💡 Exemplos Rápidos

### Conjuntos
```bash
echo "{1, 2, 3} union {3, 4, 5}" | java SetExprParser
# Resultado: {1, 2, 3, 4, 5}

echo "{1, 2, 3} inter {2, 3, 4}" | java SetExprParser
# Resultado: {2, 3}

echo "3 in {1, 2, 3}" | java SetExprParser
# Resultado: true
```

### Funções Lambda
```bash
echo "(fn x => x * 2)(5)" | java SetExprParser
# Resultado: 10

echo "let double = fn x => x * 2 in double(7)" | java SetExprParser
# Resultado: 14
```

### Let Bindings
```bash
echo "let x = 10 in let y = 20 in x + y" | java SetExprParser
# Resultado: 30

echo "let nums = {1,2,3} in nums union {4,5}" | java SetExprParser
# Resultado: {1, 2, 3, 4, 5}
```

### Composição
```bash
echo "let compose = fn f => fn g => fn x => f(g(x)) in let inc = fn x => x + 1 in let double = fn x => x * 2 in compose(double)(inc)(5)" | java SetExprParser
# Resultado: 12
```

---

## 📖 Estrutura dos Módulos .setexpr

Cada módulo contém:
- ✅ Comentários explicativos
- ✅ Definições de funções
- ✅ Exemplos práticos
- ✅ Resultados esperados

```setexpr
# Comentário descritivo
let funcao = fn x => x * 2 in

# Exemplo: Duplicar valor
funcao(5)
# Resultado: 10
```

---

## 🎯 Casos de Uso

### Biblioteca de Funções
Copie funções úteis dos módulos para seus programas:
```setexpr
# De ExpFuncao.setexpr
let compose = fn f => fn g => fn x => f(g(x)) in
let double = fn x => x * 2 in
let inc = fn x => x + 1 in

# Seu código
compose(double)(inc)(10)
```

### Exemplos Didáticos
Use os módulos como material de estudo:
```bash
# Estude cada exemplo sequencialmente
cat ExpLet.setexpr | grep "Exemplo" -A 3
```

### Testes
Valide implementações com os exemplos:
```bash
# Execute todos os módulos
for file in *.setexpr; do
  echo "=== $file ==="
  java SetExprParser < $file
done
```

---

## 🔍 Estrutura do Projeto

```
projeto-plp-setexpr/
├── *.java                    # Implementação Java
├── SetExprParser.jj          # Parser JavaCC
├── *.setexpr                 # Módulos de exemplo
│   ├── Valores.setexpr       # Valores primitivos
│   ├── ExpFuncao.setexpr     # Funções lambda
│   ├── ExpLet.setexpr        # Let bindings
│   ├── ExpAplicacao.setexpr  # Aplicação de funções
│   ├── ExpConjunto.setexpr   # Operações de conjunto
│   └── Exemplos.setexpr      # Exemplos integrados
├── README.md                 # Documentação principal
├── MODULOS.md                # Guia dos módulos
├── ALTERACOES_LF1.md         # Changelog LF1
└── QUICKSTART.md             # Este arquivo
```

---

## 📝 Criando Programas .setexpr

### Template Mínimo
```setexpr
# Meu Programa
let resultado = {1, 2, 3} union {4, 5} in
resultado
```

Salve como `meu_programa.setexpr` e execute:
```bash
java SetExprParser < meu_programa.setexpr
```

### Template com Funções
```setexpr
# Biblioteca de Utilitários
let double = fn x => x * 2 in
let makeSet = fn x => {x} in
let process = fn x => makeSet(double(x)) in

# Uso
process(5)
```

---

## 🛠️ Troubleshooting

### Erro: "Class not found"
```bash
# Recompilar tudo
javacc SetExprParser.jj
javac *.java
```

### Erro: "Parse error"
```bash
# Verificar sintaxe do arquivo .setexpr
# Linhas em branco e comentários são permitidos
# Cada expressão deve ser válida
```

### Teste Simples
```bash
echo "42" | java SetExprParser
# Deve retornar: 42
```

---

## 🎓 Próximos Passos

1. ✅ Execute todos os módulos .setexpr
2. ✅ Experimente modificar os exemplos
3. ✅ Crie suas próprias funções
4. ✅ Combine múltiplos conceitos
5. ✅ Desenvolva programas complexos

---

## 📚 Documentação Completa

- **README.md** - Visão geral e gramática
- **MODULOS.md** - Guia detalhado dos módulos
- **ALTERACOES_LF1.md** - Mudanças técnicas
- **QUICKSTART.md** - Este guia

---

## 💬 Comandos Úteis

```bash
# Listar módulos disponíveis
ls *.setexpr

# Contar exemplos em um módulo
grep -c "Exemplo" ExpAplicacao.setexpr

# Ver estrutura de um módulo
grep "# ---" ExpConjunto.setexpr

# Executar expressão inline
echo "let x = 5 in x * 2" | java SetExprParser

# Modo interativo
java SetExprParser
```

---

## 🎯 Exemplos Completos

### Programa 1: Operações Básicas
```setexpr
# operacoes_basicas.setexpr
let a = {1, 2, 3} in
let b = {3, 4, 5} in
let uniao = a union b in
let intersecao = a inter b in
let diferenca = a diff b in
uniao
```

### Programa 2: Pipeline Funcional
```setexpr
# pipeline.setexpr
let compose = fn f => fn g => fn x => f(g(x)) in
let double = fn x => x * 2 in
let inc = fn x => x + 1 in
let makeSet = fn x => {x} in
let pipeline = compose(makeSet)(compose(double)(inc)) in
pipeline(5)
```

### Programa 3: Biblioteca de Conjuntos
```setexpr
# biblioteca_conjuntos.setexpr
let uniao = fn a => fn b => a union b in
let intersecao = fn a => fn b => a inter b in
let diferenca = fn a => fn b => a diff b in
let vazio = {} in

let s1 = {1, 2, 3} in
let s2 = {2, 3, 4} in
intersecao(s1)(s2)
```

---

**SetExpr - Pronto para usar!** 🎉

Execute: `java SetExprParser < Exemplos.setexpr` para ver todos os recursos em ação.
