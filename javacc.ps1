# Script para executar JavaCC
param(
    [Parameter(Mandatory=$true)]
    [string]$InputFile
)

$javaccJar = Join-Path $PSScriptRoot "javacc-7.0.13.jar"

if (!(Test-Path $javaccJar)) {
    Write-Host "Erro: javacc-7.0.13.jar não encontrado no diretório atual" -ForegroundColor Red
    exit 1
}

Write-Host "Executando JavaCC em $InputFile..." -ForegroundColor Green
java -cp $javaccJar javacc $InputFile

if ($LASTEXITCODE -eq 0) {
    Write-Host "JavaCC executado com sucesso!" -ForegroundColor Green
} else {
    Write-Host "Erro ao executar JavaCC" -ForegroundColor Red
}
