# Use instructions: .\docker\create_image.ps1 -Username "tu_usuario"
#If you get a execution perms problem just run(on Powershell)
#Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope Process

param (
    [string]$Username = $(if ($env:DOCKER_USERNAME) { $env:DOCKER_USERNAME } else { "yourDockerUsername" })
)

if ([string]::IsNullOrWhiteSpace($Username)) {
    Write-Host "Error: Debes indicar el usuario con -Username 'yourDockerUsername'" -ForegroundColor Red
    exit 1
}

Write-Host "--- Construyendo App Service ---" -ForegroundColor Cyan
docker build -t "$Username/app-service:latest" -f docker/app-service.Dockerfile .

Write-Host "--- Construyendo Utility Service ---" -ForegroundColor Cyan
docker build -t "$Username/utility-service:latest" -f docker/utility-service.Dockerfile .

Write-Host "--- Imagenes construidas correctamente ---" -ForegroundColor Green