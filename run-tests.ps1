# Script de Testes - SetExpr
# Testa as funcionalidades implementadas

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "   TESTES SETEXPR - Extensão LF1" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

$tests = @(
    @{
        Name = "Conjunto vazio"
        Expr = "{}"
        Expected = "[]"
    },
    @{
        Name = "Conjunto simples"
        Expr = "{1, 2, 3}"
        Expected = "[1, 2, 3]"
    },
    @{
        Name = "Conjunto com booleanos"
        Expr = "{true, false}"
        Expected = "[false, true]"
    },
    @{
        Name = "União de conjuntos"
        Expr = "{1, 2, 3} union {3, 4, 5}"
        Expected = "[1, 2, 3, 4, 5]"
    },
    @{
        Name = "Interseção"
        Expr = "{1, 2, 3, 4} inter {3, 4, 5}"
        Expected = "[3, 4]"
    },
    @{
        Name = "Diferença"
        Expr = "{1, 2, 3, 4} diff {2, 4}"
        Expected = "[1, 3]"
    },
    @{
        Name = "Pertencimento (true)"
        Expr = "3 in {1, 2, 3, 4}"
        Expected = "true"
    },
    @{
        Name = "Pertencimento (false)"
        Expr = "5 in {1, 2, 3, 4}"
        Expected = "false"
    },
    @{
        Name = "Subconjunto (true)"
        Expr = "{1, 2} subset {1, 2, 3, 4}"
        Expected = "true"
    },
    @{
        Name = "Subconjunto (false)"
        Expr = "{1, 5} subset {1, 2, 3, 4}"
        Expected = "false"
    },
    @{
        Name = "Operações combinadas"
        Expr = "({1, 2, 3} union {4, 5}) inter {2, 3, 4, 5}"
        Expected = "[2, 3, 4, 5]"
    },
    @{
        Name = "Conjunto de conjuntos"
        Expr = "{{1, 2}, {3, 4}}"
        Expected = "[[1, 2], [3, 4]]"
    },
    @{
        Name = "Valores booleanos"
        Expr = "true"
        Expected = "true"
    },
    @{
        Name = "Valor inteiro"
        Expr = "42"
        Expected = "42"
    }
)

$passed = 0
$failed = 0

foreach ($test in $tests) {
    Write-Host "[$($passed + $failed + 1)/$($tests.Count)] " -NoNewline -ForegroundColor Yellow
    Write-Host "$($test.Name)" -NoNewline
    
    $result = echo $test.Expr | java SetExprParser 2>&1 | Select-String "Resultado da avaliacao:" | ForEach-Object { $_ -replace "Resultado da avaliacao: ", "" }
    
    if ($result -like "*$($test.Expected)*") {
        Write-Host " ✓" -ForegroundColor Green
        $passed++
    } else {
        Write-Host " ✗" -ForegroundColor Red
        Write-Host "  Esperado: $($test.Expected)" -ForegroundColor Gray
        Write-Host "  Obtido: $result" -ForegroundColor Gray
        $failed++
    }
}

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "RESUMO: " -NoNewline -ForegroundColor White
Write-Host "$passed passou" -NoNewline -ForegroundColor Green
Write-Host " | " -NoNewline
Write-Host "$failed falhou" -ForegroundColor Red
Write-Host "========================================`n" -ForegroundColor Cyan

if ($failed -eq 0) {
    Write-Host "🎉 Todos os testes passaram!" -ForegroundColor Green
} else {
    Write-Host "⚠️  Alguns testes falharam" -ForegroundColor Yellow
}
