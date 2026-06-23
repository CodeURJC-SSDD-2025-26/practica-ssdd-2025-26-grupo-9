# Instrucciones de uso: .\docker\publish_docker_compose.ps1 -Username "yourDockerUsername"
# Debes haber ejecutado 'docker login' en tu terminal antes de lanzar este script para no usar
# la variable yourDockerUsername (si no dara error)

param (
    [string]$Username = $(if ($env:DOCKER_USERNAME) { $env:DOCKER_USERNAME } else { "yourDockerUsername" })
)

if ([string]::IsNullOrWhiteSpace($Username)) {
    Write-Host "Error: Debes indicar el usuario con -Username 'yourDockerUsername'" -ForegroundColor Red
    exit 1
}

$IMAGE_NAME = "ssdd-proyecto"
$TAG = "latest"
$FULL_IMAGE = "${Username}/${IMAGE_NAME}:${TAG}"

Write-Host "--- Publicando Docker Compose como OCI Artifact ---" -ForegroundColor Cyan

# Publicación nativa de OCI Artifact
docker compose -f docker/docker-compose.yml publish $FULL_IMAGE --with-env

if ($LASTEXITCODE -eq 0) {
    Write-Host "Publicacion exitosa" -ForegroundColor Green
    Write-Host "Para ejecutar el artifact completo ejecuta la siguiente linea:" -ForegroundColor Cyan
    Write-Host "docker compose -f oci://docker.io/$FULL_IMAGE up" -ForegroundColor White
} else {
    Write-Host "Error durante la publicacion. Verifica que has hecho 'docker login' correctamente." -ForegroundColor Red
}