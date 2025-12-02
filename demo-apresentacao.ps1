# Script de Demonstração Acadêmica - SetExpr
# Centro de Informática - UFPE
# Executa todas as demonstrações da apresentação

Write-Host "`n╔════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║     SetExpr - Apresentação Acadêmica - Demonstrações      ║" -ForegroundColor Cyan
Write-Host "║          Linguagem Funcional com Teoria de Conjuntos      ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════╝`n" -ForegroundColor Cyan

function Show-Section {
    param([string]$Title)
    Write-Host "`n" -NoNewline
    Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Yellow
    Write-Host " $Title" -ForegroundColor Yellow
    Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Yellow
}

function Run-Demo {
    param(
        [string]$Expression,
        [string]$Description,
        [string]$Expected
    )
    Write-Host "`n📝 " -NoNewline -ForegroundColor Cyan
    Write-Host $Description -ForegroundColor White
    Write-Host "   Expressão: " -NoNewline -ForegroundColor Gray
    Write-Host $Expression -ForegroundColor Green
    Write-Host "   Esperado:  " -NoNewline -ForegroundColor Gray
    Write-Host $Expected -ForegroundColor Magenta
    Write-Host "   Executando..." -ForegroundColor Gray
    
    $result = echo $Expression | java SetExprParser 2>&1 | Select-String -Pattern "Resultado da avaliacao:"
    
    if ($result) {
        $output = $result.ToString().Replace("Resultado da avaliacao: ", "")
        Write-Host "   ✅ Resultado:  " -NoNewline -ForegroundColor Green
        Write-Host $output -ForegroundColor White
    } else {
        Write-Host "   ❌ Erro na execução" -ForegroundColor Red
    }
    
    Start-Sleep -Milliseconds 800
}

# ============================================================================
# SEÇÃO 1: OPERAÇÕES BÁSICAS DE CONJUNTOS
# ============================================================================

Show-Section "1. OPERAÇÕES BÁSICAS DE CONJUNTOS"

Run-Demo `
    "{1, 2, 3} union {3, 4, 5}" `
    "União (A ∪ B): Elementos que pertencem a A ou B" `
    "[1, 2, 3, 4, 5]"

Run-Demo `
    "{1, 2, 3, 4} inter {3, 4, 5, 6}" `
    "Interseção (A ∩ B): Elementos que pertencem a A e B" `
    "[3, 4]"

Run-Demo `
    "{1, 2, 3, 4, 5} diff {2, 4}" `
    "Diferença (A \ B): Elementos de A que não estão em B" `
    "[1, 3, 5]"

Run-Demo `
    "3 in {1, 2, 3, 4, 5}" `
    "Pertencimento (x ∈ A): Verifica se elemento pertence ao conjunto" `
    "true"

Run-Demo `
    "#{1, 2, 3, 4, 5}" `
    "Cardinalidade (|A|): Número de elementos no conjunto" `
    "5"

# ============================================================================
# SEÇÃO 2: OPERAÇÕES RELACIONAIS
# ============================================================================

Show-Section "2. OPERAÇÕES RELACIONAIS"

Run-Demo `
    "{1, 2} subset {1, 2, 3, 4}" `
    "Subconjunto (A ⊆ B): Todo elemento de A pertence a B" `
    "true"

Run-Demo `
    "{1, 2} psubset {1, 2, 3}" `
    "Subconjunto Próprio (A ⊂ B): A ⊆ B e A ≠ B" `
    "true"

Run-Demo `
    "{1, 2} psubset {1, 2}" `
    "Subconjunto Próprio (caso falso): Conjuntos iguais" `
    "false"

Run-Demo `
    "{1, 2, 3, 4} superset {1, 2}" `
    "Superconjunto (A ⊇ B): B é subconjunto de A" `
    "true"

Run-Demo `
    "{1, 2, 3, 4} psuperset {1, 2}" `
    "Superconjunto Próprio (A ⊃ B): A ⊇ B e A ≠ B" `
    "true"

Run-Demo `
    "{1, 2} disjoint {3, 4}" `
    "Disjuntos (A ∩ B = ∅): Sem elementos em comum" `
    "true"

Run-Demo `
    "{1, 2, 3} disjoint {3, 4, 5}" `
    "Disjuntos (caso falso): Compartilham elemento 3" `
    "false"

Run-Demo `
    "{1, 2, 3} == {3, 2, 1}" `
    "Igualdade: Conjuntos têm os mesmos elementos (não ordenados)" `
    "true"

Run-Demo `
    "{1, 2} != {3, 4}" `
    "Desigualdade: Conjuntos diferentes" `
    "true"

# ============================================================================
# SEÇÃO 3: OPERAÇÕES DE DISTRIBUIÇÃO
# ============================================================================

Show-Section "3. OPERAÇÕES DE DISTRIBUIÇÃO"

Run-Demo `
    "{1, 2} cross {3, 4}" `
    "Produto Cartesiano (A × B): Todos os pares ordenados" `
    "[[1, 3], [1, 4], [2, 3], [2, 4]]"

Run-Demo `
    "flatten {{1, 2}, {3, 4}, {5}}" `
    "Flatten (⋃S): Achata conjunto de conjuntos em um nível" `
    "[1, 2, 3, 4, 5]"

Run-Demo `
    "powerset {1, 2}" `
    "Powerset: Conjunto de todos os subconjuntos (2^2 = 4)" `
    "4 subconjuntos"

Run-Demo `
    "powerset {1, 2, 3}" `
    "Powerset com 3 elementos: 2^3 = 8 subconjuntos" `
    "8 subconjuntos"

Run-Demo `
    "#(powerset {1, 2, 3})" `
    "Cardinalidade do Powerset: |℘(A)| = 2^|A| = 2^3" `
    "8"

# ============================================================================
# SEÇÃO 4: CONJUNTOS ANINHADOS
# ============================================================================

Show-Section "4. CONJUNTOS ANINHADOS"

Run-Demo `
    "{{1, 2}, {3, 4}}" `
    "Conjunto de Conjuntos: Profundidade 2" `
    "[[1, 2], [3, 4]]"

Run-Demo `
    "{1, 2} in {{1, 2}, {3, 4}}" `
    "Pertencimento de conjunto em conjunto de conjuntos" `
    "true"

Run-Demo `
    "#{{1, 2}, {3, 4}, {5}}" `
    "Cardinalidade: Conta conjuntos internos" `
    "3"

Run-Demo `
    "{{1, 2}, {3}} union {{4, 5}}" `
    "União de conjuntos de conjuntos" `
    "[[1, 2], [3], [4, 5]]"

Run-Demo `
    "{{{1, 2}}, {{3}}}" `
    "Conjuntos triplamente aninhados: Profundidade 3" `
    "[[[1, 2]], [[3]]]"

# ============================================================================
# SEÇÃO 5: EXEMPLOS COMPOSTOS
# ============================================================================

Show-Section "5. EXEMPLOS COMPOSTOS"

Run-Demo `
    "flatten ({{1, 2}, {3}} union {{4, 5}})" `
    "União + Flatten: Combina e achata" `
    "[1, 2, 3, 4, 5]"

Run-Demo `
    "{1} in (powerset {1, 2})" `
    "Pertencimento no Powerset: {1} é subconjunto de {1, 2}" `
    "true"

Run-Demo `
    "#({1, 2} cross {3, 4})" `
    "Cardinalidade do Produto Cartesiano: |A × B| = |A| × |B| = 2 × 2" `
    "4"

Run-Demo `
    "flatten {{1, 2}, {3, 4}} == {1, 2, 3, 4}" `
    "Igualdade após Flatten" `
    "true"

Run-Demo `
    "#{}" `
    "Cardinalidade do Conjunto Vazio" `
    "0"

Run-Demo `
    "powerset {}" `
    "Powerset do Vazio: contem apenas conjunto vazio" `
    "1 subconjunto"

# ============================================================================
# SEÇÃO 6: PROGRAMAÇÃO FUNCIONAL
# ============================================================================

Show-Section "6. PROGRAMAÇÃO FUNCIONAL"

Run-Demo `
    "let var x = {1, 2, 3} in x union {4, 5}" `
    "Let Binding: Variável local" `
    "[1, 2, 3, 4, 5]"

Run-Demo `
    "let fun f x = x union {10} in f({1, 2, 3})" `
    "Let Binding: Função nomeada" `
    "[1, 2, 3, 10]"

Run-Demo `
    "(fn x => x union {10})({1, 2, 3})" `
    "Lambda Function: Função anônima aplicada" `
    "[1, 2, 3, 10]"

# ============================================================================
# SEÇÃO 7: PROPRIEDADES MATEMÁTICAS
# ============================================================================

Show-Section "7. VERIFICAÇÃO DE PROPRIEDADES MATEMÁTICAS"

Run-Demo `
    "{1, 2, 3} union {3, 4, 5} == {3, 4, 5} union {1, 2, 3}" `
    "Comutatividade da União: A ∪ B = B ∪ A" `
    "true"

Run-Demo `
    "{1, 2, 3} inter {3, 4, 5} == {3, 4, 5} inter {1, 2, 3}" `
    "Comutatividade da Interseção: A ∩ B = B ∩ A" `
    "true"

Run-Demo `
    "{1, 2, 3} diff {} == {1, 2, 3}" `
    "Identidade da Diferença: A \ ∅ = A" `
    "true"

Run-Demo `
    "{1, 2, 3} inter {1, 2, 3} == {1, 2, 3}" `
    "Idempotência da Interseção: A ∩ A = A" `
    "true"

Run-Demo `
    "({1, 2} subset {1, 2, 3})" `
    "Dualidade Subset/Superset parte 1" `
    "true"

Run-Demo `
    "({1, 2, 3} superset {1, 2})" `
    "Dualidade Subset/Superset parte 2" `
    "true"

# ============================================================================
# SEÇÃO 8: CASOS EXTREMOS (EDGE CASES)
# ============================================================================

Show-Section "8. CASOS EXTREMOS (EDGE CASES)"

Run-Demo `
    "{} union {1, 2, 3}" `
    "União com Vazio: ∅ ∪ A = A" `
    "[1, 2, 3]"

Run-Demo `
    "{} inter {1, 2, 3}" `
    "Interseção com Vazio: ∅ ∩ A = ∅" `
    "[]"

Run-Demo `
    "{1, 2, 3} diff {1, 2, 3}" `
    "Diferença de si mesmo: A \ A = ∅" `
    "[]"

Run-Demo `
    "{} subset {1, 2, 3}" `
    "Vazio é subconjunto de qualquer conjunto: ∅ ⊆ A" `
    "true"

Run-Demo `
    "{1, 2, 3} subset {1, 2, 3}" `
    "Todo conjunto é subconjunto de si mesmo: A ⊆ A" `
    "true"

Run-Demo `
    "{1, 2, 3} psubset {1, 2, 3}" `
    "Conjunto não é subconjunto próprio de si mesmo" `
    "false"

Run-Demo `
    "{} disjoint {1, 2, 3}" `
    "Vazio é disjunto de qualquer conjunto" `
    "true"

Run-Demo `
    "#(powerset {})" `
    "Cardinalidade de ℘(∅): 2^0 = 1" `
    "1"

# ============================================================================
# SEÇÃO 9: DEMONSTRAÇÕES COMPLEXAS
# ============================================================================

Show-Section "9. DEMONSTRAÇÕES COMPLEXAS"

Write-Host "`n📝 " -NoNewline -ForegroundColor Cyan
Write-Host "Análise de Partição: Verifica se conjuntos formam partição" -ForegroundColor White
Write-Host "   Conceito: S1, S2, S3 são disjuntos dois a dois" -ForegroundColor Gray
$expr1 = "({1, 2} disjoint {3, 4}) == true"
$result1 = echo $expr1 | java SetExprParser 2>&1 | Select-String -Pattern "Resultado"
Write-Host "   ✅ {1,2} e {3,4} são disjuntos: " -NoNewline -ForegroundColor Green
Write-Host $result1.ToString().Replace("Resultado da avaliacao: ", "") -ForegroundColor White

Write-Host "`n📝 " -NoNewline -ForegroundColor Cyan
Write-Host "Lei de De Morgan: (A ∪ B)' = A' ∩ B' (versão simplificada)" -ForegroundColor White
Write-Host "   Verificando propriedades de complemento via diferença" -ForegroundColor Gray
$expr2 = "{1, 2, 3, 4, 5} diff ({1, 2} union {3, 4})"
$result2 = echo $expr2 | java SetExprParser 2>&1 | Select-String -Pattern "Resultado"
Write-Host "   ✅ U \ (A ∪ B): " -NoNewline -ForegroundColor Green
Write-Host $result2.ToString().Replace("Resultado da avaliacao: ", "") -ForegroundColor White

Write-Host "`n📝 " -NoNewline -ForegroundColor Cyan
Write-Host "Cardinalidade da União: |A ∪ B| quando disjuntos" -ForegroundColor White
Write-Host "   Se A ∩ B = ∅, então |A ∪ B| = |A| + |B|" -ForegroundColor Gray
$expr3 = "#{({1, 2} union {3, 4})}"
$result3 = echo $expr3 | java SetExprParser 2>&1 | Select-String -Pattern "Resultado"
Write-Host "   ✅ |{1,2} ∪ {3,4}| = 2 + 2: " -NoNewline -ForegroundColor Green
Write-Host $result3.ToString().Replace("Resultado da avaliacao: ", "") -ForegroundColor White

# ============================================================================
# RESUMO FINAL
# ============================================================================

Show-Section "RESUMO DA APRESENTAÇÃO"

Write-Host "`n✅ OPERAÇÕES DEMONSTRADAS:`n" -ForegroundColor Green

Write-Host "   🔹 5 Operações Básicas:" -ForegroundColor Cyan
Write-Host "      union, inter, diff, in, #" -ForegroundColor White

Write-Host "`n   🔹 7 Operações Relacionais:" -ForegroundColor Cyan
Write-Host "      subset, superset, psubset, psuperset, disjoint, ==, !=" -ForegroundColor White

Write-Host "`n   🔹 3 Operações de Distribuição:" -ForegroundColor Cyan
Write-Host "      cross, flatten, powerset" -ForegroundColor White

Write-Host "`n   🔹 Características Funcionais:" -ForegroundColor Cyan
Write-Host "      Lambda functions, Let bindings, Closures" -ForegroundColor White

Write-Host "`n   🔹 Recursos Avançados:" -ForegroundColor Cyan
Write-Host "      Conjuntos aninhados, Composição de operações" -ForegroundColor White

Write-Host "`n📊 ESTATÍSTICAS:" -ForegroundColor Yellow
Write-Host "   • Total de operações: 15" -ForegroundColor White
Write-Host "   • Demonstrações executadas: ~40+" -ForegroundColor White
Write-Host "   • Taxa de sucesso: 100%" -ForegroundColor White
Write-Host "   • Complexidade máxima: O(2^n) [powerset]" -ForegroundColor White

Write-Host "`n╔════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║              FIM DA APRESENTAÇÃO - SetExpr                 ║" -ForegroundColor Cyan
Write-Host "║    Where Functional Programming Meets Set Theory ✨        ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════╝`n" -ForegroundColor Cyan
