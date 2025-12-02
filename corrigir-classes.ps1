# Script para corrigir todas as novas classes de expressão

$arquivos = @(
    "ExpDivisao.java",
    "ExpModulo.java",
    "ExpMenor.java",
    "ExpMaior.java",
    "ExpMenorIgual.java",
    "ExpMaiorIgual.java",
    "ExpNegacao.java"
)

foreach ($arquivo in $arquivos) {
    Write-Host "Corrigindo $arquivo..."
    $conteudo = Get-Content $arquivo -Raw -Encoding UTF8
    
    # Substituir extends por implements
    $conteudo = $conteudo -replace 'extends Expressao', 'implements Expressao'
    
    # Remover @Override
    $conteudo = $conteudo -replace '@Override\s*\r?\n\s*', ''
    
    # Substituir throws Exception por nada
    $conteudo = $conteudo -replace ' throws Exception', ''
    
    # Substituir .valor() por .getValor()
    $conteudo = $conteudo -replace '\.valor\(\)', '.getValor()'
    
    # Substituir new TipoInteiro() por TipoInteiro.getInstancia()
    $conteudo = $conteudo -replace 'new TipoInteiro\(\)', 'TipoInteiro.getInstancia()'
    
    # Substituir new TipoBooleano() por TipoBooleano.getInstancia()
    $conteudo = $conteudo -replace 'new TipoBooleano\(\)', 'TipoBooleano.getInstancia()'
    
    # Substituir throw new Exception por return null
    $conteudo = $conteudo -replace 'throw new Exception\([^)]+\);', 'return null;'
    
    # Adicionar checaTipo antes de getTipo
    if ($conteudo -notmatch 'public boolean checaTipo') {
        # Pattern para encontrar getTipo e adicionar checaTipo antes
        if ($arquivo -eq "ExpNegacao.java") {
            $pattern = '(  public Tipo getTipo)'
            $replacement = '  public boolean checaTipo(AmbienteCompilacao amb) {' + "`r`n" +
                          '    return exp.checaTipo(amb) && exp.getTipo(amb) instanceof TipoInteiro;' + "`r`n" +
                          '  }' + "`r`n`r`n" + '$1'
        } else {
            $pattern = '(  public Tipo getTipo)'
            $replacement = '  public boolean checaTipo(AmbienteCompilacao amb) {' + "`r`n" +
                          '    return exp1.checaTipo(amb) && exp2.checaTipo(amb) &&' + "`r`n" +
                          '           exp1.getTipo(amb) instanceof TipoInteiro &&' + "`r`n" +
                          '           exp2.getTipo(amb) instanceof TipoInteiro;' + "`r`n" +
                          '  }' + "`r`n`r`n" + '$1'
        }
        $conteudo = $conteudo -replace $pattern, $replacement
    }
    
    Set-Content -Path $arquivo -Value $conteudo -Encoding UTF8 -NoNewline
}

Write-Host "Correcoes concluidas!"
