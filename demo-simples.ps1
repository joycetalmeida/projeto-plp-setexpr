# Script de Demonstração - SetExpr
# Versão clean: apenas expressão e resultado

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "  SetExpr - Demonstracoes" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

# SECAO 1: OPERACOES BASICAS
Write-Host "=== 1. OPERACOES BASICAS ===`n" -ForegroundColor Yellow

Write-Host "{1, 2, 3} union {3, 4, 5}"
echo "{1, 2, 3} union {3, 4, 5}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n{1, 2, 3, 4} inter {3, 4, 5, 6}"
echo "{1, 2, 3, 4} inter {3, 4, 5, 6}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n{1, 2, 3, 4, 5} diff {2, 4}"
echo "{1, 2, 3, 4, 5} diff {2, 4}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n3 in {1, 2, 3, 4, 5}"
echo "3 in {1, 2, 3, 4, 5}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n#{1, 2, 3, 4, 5}"
echo "#{1, 2, 3, 4, 5}" | java SetExprParser | Select-String "Resultado"

# SECAO 2: OPERACOES RELACIONAIS
Write-Host "`n`n=== 2. OPERACOES RELACIONAIS ===`n" -ForegroundColor Yellow

Write-Host "{1, 2} subset {1, 2, 3, 4}"
echo "{1, 2} subset {1, 2, 3, 4}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n{1, 2} psubset {1, 2, 3}"
echo "{1, 2} psubset {1, 2, 3}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n{1, 2, 3, 4} superset {1, 2}"
echo "{1, 2, 3, 4} superset {1, 2}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n{1, 2} disjoint {3, 4}"
echo "{1, 2} disjoint {3, 4}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n{1, 2, 3} == {3, 2, 1}"
echo "{1, 2, 3} == {3, 2, 1}" | java SetExprParser | Select-String "Resultado"

# SECAO 3: OPERACOES DE DISTRIBUICAO
Write-Host "`n`n=== 3. OPERACOES DE DISTRIBUICAO ===`n" -ForegroundColor Yellow

Write-Host "{1, 2} cross {3, 4}"
echo "{1, 2} cross {3, 4}" | java SetExprParser | Select-String "Resultado"

Write-Host "`nflatten {{1, 2}, {3, 4}, {5}}"
echo "flatten {{1, 2}, {3, 4}, {5}}" | java SetExprParser | Select-String "Resultado"

Write-Host "`npowerset {1, 2, 3}"
echo "powerset {1, 2, 3}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n#(powerset {1, 2, 3})"
echo "#(powerset {1, 2, 3})" | java SetExprParser | Select-String "Resultado"

# SECAO 4: OPERACOES COM RANGE
Write-Host "`n`n=== 4. OPERACOES COM RANGE ===`n" -ForegroundColor Yellow

Write-Host "1...5"
echo "1...5" | java SetExprParser | Select-String "Resultado"

Write-Host "`n1...10"
echo "1...10" | java SetExprParser | Select-String "Resultado"

Write-Host "`n#(1...100)"
echo "#(1...100)" | java SetExprParser | Select-String "Resultado"

Write-Host "`n3 in (1...10)"
echo "3 in (1...10)" | java SetExprParser | Select-String "Resultado"

Write-Host "`n(1...5) union (6...10)"
echo "(1...5) union (6...10)" | java SetExprParser | Select-String "Resultado"

Write-Host "`n(1...10) inter (5...15)"
echo "(1...10) inter (5...15)" | java SetExprParser | Select-String "Resultado"

Write-Host "`n(1...10) diff (5...7)"
echo "(1...10) diff (5...7)" | java SetExprParser | Select-String "Resultado"

Write-Host "`npowerset (1...3)"
echo "powerset (1...3)" | java SetExprParser | Select-String "Resultado"

# SECAO 5: CONJUNTOS ANINHADOS
Write-Host "`n`n=== 5. CONJUNTOS ANINHADOS ===`n" -ForegroundColor Yellow

Write-Host "{{1, 2}, {3, 4}}"
echo "{{1, 2}, {3, 4}}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n{1, 2} in {{1, 2}, {3, 4}}"
echo "{1, 2} in {{1, 2}, {3, 4}}" | java SetExprParser | Select-String "Resultado"

Write-Host "`n#{{1, 2}, {3, 4}, {5}}"
echo "#{{1, 2}, {3, 4}, {5}}" | java SetExprParser | Select-String "Resultado"

# SECAO 6: EXEMPLOS COMPOSTOS
Write-Host "`n`n=== 6. EXEMPLOS COMPOSTOS ===`n" -ForegroundColor Yellow

Write-Host "flatten ({{1, 2}, {3}} union {{4, 5}})"
echo "flatten ({{1, 2}, {3}} union {{4, 5}})" | java SetExprParser | Select-String "Resultado"

Write-Host "`n{1} in (powerset {1, 2})"
echo "{1} in (powerset {1, 2})" | java SetExprParser | Select-String "Resultado"

Write-Host "`n#({1, 2} cross {3, 4})"
echo "#({1, 2} cross {3, 4})" | java SetExprParser | Select-String "Resultado"

# SECAO 7: PROGRAMACAO FUNCIONAL
Write-Host "`n`n=== 7. PROGRAMACAO FUNCIONAL ===`n" -ForegroundColor Yellow

Write-Host "(fn x => x union {10})({1, 2, 3})"
echo "(fn x => x union {10})({1, 2, 3})" | java SetExprParser | Select-String "Resultado"

Write-Host "`n(fn x => x inter {2, 3, 4})({1, 2, 3, 4, 5})"
echo "(fn x => x inter {2, 3, 4})({1, 2, 3, 4, 5})" | java SetExprParser | Select-String "Resultado"

Write-Host "`n(fn x => x union {100})(1...5)"
echo "(fn x => x union {100})(1...5)" | java SetExprParser | Select-String "Resultado"

# RESUMO
Write-Host "`n`n========================================" -ForegroundColor Cyan
Write-Host "  FIM - SetExpr" -ForegroundColor Cyan
Write-Host "  16 operacoes implementadas" -ForegroundColor Green
Write-Host "========================================`n" -ForegroundColor Cyan
