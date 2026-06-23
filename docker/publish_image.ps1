# Uso: .\docker\publish_image.ps1 -Username "tu_usuario"
param (
    [string]$Username = $(if ($env:DOCKER_USERNAME) { $env:DOCKER_USERNAME } else { "yourDockerUsername" })
)

$ErrorActionPreference = "Stop"

Write-Host "--- Paso 1: (Re)construyendo imagenes... ---" -ForegroundColor Yellow
.\docker\create_image.ps1 -Username $Username

Write-Host "--- Paso 2: Iniciando sesion en Docker Hub... ---" -ForegroundColor Yellow
docker login

Write-Host "--- Paso 3: Publicando imagenes en Docker Hub... ---" -ForegroundColor Cyan
Write-Host "> Subiendo app-service..."
docker push "$Username/app-service:latest"

Write-Host "> Subiendo utility-service..."
docker push "$Username/utility-service:latest"

Write-Host "--- Proceso de publicacion completado con exito ---" -ForegroundColor Green