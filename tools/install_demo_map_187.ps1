$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSScriptRoot
$sourceMapId = "1"
$demoMapId = "187"

$mapFolders = @(
    "data\map\tile_map_data",
    "data\map\item_bg_map_data",
    "data\map\eff_map"
)

foreach ($relativeFolder in $mapFolders) {
    $sourceFile = Join-Path $projectRoot "$relativeFolder\$sourceMapId"
    $targetFile = Join-Path $projectRoot "$relativeFolder\$demoMapId"

    if (-not (Test-Path -LiteralPath $sourceFile)) {
        throw "Khong tim thay file map mau: $sourceFile"
    }

    Copy-Item -LiteralPath $sourceFile -Destination $targetFile -Force
}

Write-Host "Da tao du 3 file tai nguyen cho map 187 tu map mau 1."
Write-Host "Buoc con lai: import tools\install_demo_map_187.sql vao database ngocrong."

