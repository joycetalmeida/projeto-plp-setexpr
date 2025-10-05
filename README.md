# Linguagem SetExpr

## Disciplina
(IN1007) Paradigmas de Linguagens de Programação - Cin/UFPE

## Professor
Augusto Sampaio

## Equipe
* Joyce Almeida | jta@cin.ufpe.br
* Ricardo Azevedo | rams@cin.ufpe.br
* Rafael Moura | rnm4@cin.ufpe.br

---

## Contextualização
Este projeto estende a Linguagem de Expressões 2 (LE2), desenvolvida na disciplina, para incluir um tipo de dado `Conjunto` (Set). A implementação visa não apenas adicionar as operações básicas, mas também funcionalidades complexas como a união distribuída e, principalmente, a compreensão de conjuntos, permitindo a criação de conjuntos de forma declarativa e expressiva.

## Escopo do Projeto
O objetivo é adicionar um tipo `Conjunto` polimórfico à linguagem, permitindo a manipulação de conjuntos de inteiros, booleanos e até mesmo conjuntos de conjuntos. A funcionalidade central é a compreensão de conjuntos, que permite gerar e filtrar conjuntos de forma declarativa.

### Exemplo de Sintaxe
```
// Criação de um conjunto de números pares de 1 a 10
let var evens = {x in 1..10 : x % 2 == 0} in evens

// Criação de um conjunto com o quadrado dos números de 1 a 5
{x * x : x in 1..5}

// Operação de união
{1, 2} union {2, 3}
```


## Gramática BNF
A gramática a seguir representa a Linguagem de Expressões 2 estendida com os construtores de conjunto.

```
<program> ::= <expr>

<expr> ::= <expr_set> | <expr_let> | <expr_binaria> | <expr_unaria> | <valor> | <id> | "(" <expr> ")"

// Regras de Conjunto (Novas)
<expr_set> ::= "{" [ <list_expr> ] "}"                                // Literal: {1, 2, 3}
             | <expr> ".." <expr>                                      // Range: 1..10
             | "{" <id> "in" <expr_set> ":" <expr_boolean> "}"         // Compreensão (Filtro)
             | "{" <expr> ":" <id> "in" <expr_set> "}"                 // Compreensão (Mapeamento)

<expr_unaria> ::= ... | "#" <expr_set> | "UNION" <expr_set>

<expr_binaria> ::= ... | <expr_set> "union" <expr_set>
                       | <expr_set> "inter" <expr_set>
                       | <expr_set> "diff" <expr_set>
                       | <expr> "in" <expr_set>
                       | <expr_set> "subset" <expr_set>

// Regras da LE2 (Base)
<expr_let> ::= "let" <list_decl> "in" <expr>
<list_decl> ::= <decl> | <decl> "," <list_decl>
<decl> ::= "var" <id> "=" <expr>

// ... outras regras para expressões aritméticas, booleanas, etc.
```


## Estrutura da Implementação
O projeto foi implementado estendendo a base da LE2 com as seguintes classes principais:

* Novas Classes de Expressão (AST):
  * ExpConjunto: Representa um literal de conjunto ({1,2,3}).
  * ExpUniao, ExpIntersecao, ExpDiferenca: Representam as operações binárias.
  * ExpCardinalidade, ExpUniaoDistribuida: Representam as operações unárias.
  * ExpCompreensaoFiltro, ExpCompreensaoMap: Representam as duas formas de compreensão de conjunto.
* Novas Classes de Valor e Tipo:
  * ValorConjunto: Armazena o resultado de uma avaliação de conjunto (usando java.util.HashSet internamente).
  * TipoConjunto: Representa o tipo Set no sistema de tipos.

## Como Executar
1. Gerar o Parser:
   ```
   java -cp "caminho/para/javacc.jar" org.javacc.parser.Main SetExprParser.jj
   ```
2. Compilar o Código Java:
   ```
   javac *.java
   ```
3. Executar o Interpretador:
   ```
   java -cp . SetExprParser
   ```
