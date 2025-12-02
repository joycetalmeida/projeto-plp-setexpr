# Testes das novas funcionalidades aritmeticas e condicionais

Write-Host "=== TESTES SETEXPR - Operacoes Aritmeticas e Condicionais ===" -ForegroundColor Cyan

# Teste 1: Soma
Write-Host "`n1. Soma: 2+3" -ForegroundColor Yellow
"2+3" | java SetExprParser

# Teste 2: Subtracao
Write-Host "`n2. Subtracao: 10-3" -ForegroundColor Yellow
"10-3" | java SetExprParser

# Teste 3: Multiplicacao
Write-Host "`n3. Multiplicacao: 4*5" -ForegroundColor Yellow
"4*5" | java SetExprParser

# Teste 4: Divisao
Write-Host "`n4. Divisao: 20/4" -ForegroundColor Yellow
"20/4" | java SetExprParser

# Teste 5: Modulo
Write-Host "`n5. Modulo: 17%5" -ForegroundColor Yellow
"17%5" | java SetExprParser

# Teste 6: Expressao complexa
Write-Host "`n6. Expressao: 2+3*4" -ForegroundColor Yellow
"2+3*4" | java SetExprParser

# Teste 7: Com parenteses
Write-Host "`n7. Com parenteses: (2+3)*4" -ForegroundColor Yellow
"(2+3)*4" | java SetExprParser

# Teste 8: Menor que
Write-Host "`n8. Menor que: 3<5" -ForegroundColor Yellow
"3<5" | java SetExprParser

# Teste 9: Maior que
Write-Host "`n9. Maior que: 10>2" -ForegroundColor Yellow
"10>2" | java SetExprParser

# Teste 10: Menor ou igual
Write-Host "`n10. Menor ou igual: 5<=5" -ForegroundColor Yellow
"5<=5" | java SetExprParser

# Teste 11: Maior ou igual
Write-Host "`n11. Maior ou igual: 7>=3" -ForegroundColor Yellow
"7>=3" | java SetExprParser

# Teste 12: Negacao
Write-Host "`n12. Negacao: -(10-3)" -ForegroundColor Yellow
"-(10-3)" | java SetExprParser

# Teste 13: If-then-else true
Write-Host "`n13. If-then-else (true): if 5>3 then 100 else 200" -ForegroundColor Yellow
"if 5>3 then 100 else 200" | java SetExprParser

# Teste 14: If-then-else false
Write-Host "`n14. If-then-else (false): if 2>10 then 100 else 200" -ForegroundColor Yellow
"if 2>10 then 100 else 200" | java SetExprParser

# Teste 15: Let com aritmetica
Write-Host "`n15. Let com aritmetica: let var x=10 in x*2" -ForegroundColor Yellow
"let var x=10 in x*2" | java SetExprParser

# Teste 16: Let composto
Write-Host "`n16. Let composto: let var x=5, var y=3 in x+y" -ForegroundColor Yellow
"let var x=5, var y=3 in x+y" | java SetExprParser

# Teste 17: Expressao complexa combinando recursos
Write-Host "`n17. Expressao complexa: if #({1,2,3})>2 then 100 else 0" -ForegroundColor Yellow
"if #({1,2,3})>2 then 100 else 0" | java SetExprParser

# Teste 18: Aritmetica com conjuntos
Write-Host "`n18. Cardinalidade com aritmetica: #({1,2,3})+5" -ForegroundColor Yellow
"#({1,2,3})+5" | java SetExprParser

# Teste 19: Range com aritmetica
Write-Host "`n19. Range com aritmetica: (1+1)...(2*3)" -ForegroundColor Yellow
"(1+1)...(2*3)" | java SetExprParser

# Teste 20: Condicional com conjuntos
Write-Host "`n20. Condicional com pertencimento: if 2 in {1,2,3} then 10 else 20" -ForegroundColor Yellow
"if 2 in {1,2,3} then 10 else 20" | java SetExprParser

Write-Host "`n=== TESTES CONCLUIDOS ===" -ForegroundColor Cyan
