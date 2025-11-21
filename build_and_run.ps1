# Crypto Checker - Build and Run Script
# This script sets the correct JDK path and runs Maven commands

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "  Crypto Checker Build & Run Script" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Set JAVA_HOME to JDK (not JRE)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-1.8"
$env:PATH = "C:\Program Files\Java\jdk-1.8\bin;$env:PATH"

Write-Host "Setting JAVA_HOME to: $env:JAVA_HOME" -ForegroundColor Green
Write-Host ""

# Check if user wants to build or just run
$choice = $args[0]

if ($choice -eq "build" -or $choice -eq "") {
    Write-Host "Building the application..." -ForegroundColor Yellow
    Write-Host ""
    mvn clean package -DskipTests
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "=====================================" -ForegroundColor Green
        Write-Host "  BUILD SUCCESSFUL!" -ForegroundColor Green
        Write-Host "=====================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "JAR file location:" -ForegroundColor Cyan
        Write-Host "  target\crypto-checker-1.1-no-dependencies.jar" -ForegroundColor White
        Write-Host ""
        
        # Ask if user wants to run the application
        $runChoice = Read-Host "Do you want to run the application now? (Y/N)"
        if ($runChoice -eq "Y" -or $runChoice -eq "y") {
            Write-Host ""
            Write-Host "Starting Crypto Checker application..." -ForegroundColor Yellow
            java -jar target\crypto-checker-1.1-no-dependencies.jar
        }
    } else {
        Write-Host ""
        Write-Host "=====================================" -ForegroundColor Red
        Write-Host "  BUILD FAILED!" -ForegroundColor Red
        Write-Host "=====================================" -ForegroundColor Red
    }
}
elseif ($choice -eq "run") {
    Write-Host "Starting Crypto Checker application..." -ForegroundColor Yellow
    Write-Host ""
    
    if (Test-Path "target\crypto-checker-1.1-no-dependencies.jar") {
        java -jar target\crypto-checker-1.1-no-dependencies.jar
    } else {
        Write-Host "Error: JAR file not found!" -ForegroundColor Red
        Write-Host "Please run './build_and_run.ps1 build' first" -ForegroundColor Yellow
    }
}
elseif ($choice -eq "test") {
    Write-Host "Running automated tests..." -ForegroundColor Yellow
    Write-Host ""
    mvn test
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "=====================================" -ForegroundColor Green
        Write-Host "  ALL TESTS PASSED!" -ForegroundColor Green
        Write-Host "=====================================" -ForegroundColor Green
    } else {
        Write-Host ""
        Write-Host "=====================================" -ForegroundColor Red
        Write-Host "  TESTS FAILED!" -ForegroundColor Red
        Write-Host "=====================================" -ForegroundColor Red
    }
}
elseif ($choice -eq "help") {
    Write-Host "Usage:" -ForegroundColor Cyan
    Write-Host "  .\build_and_run.ps1         - Build the application and optionally run it" -ForegroundColor White
    Write-Host "  .\build_and_run.ps1 build   - Build the application" -ForegroundColor White
    Write-Host "  .\build_and_run.ps1 run     - Run the application (must build first)" -ForegroundColor White
    Write-Host "  .\build_and_run.ps1 test    - Run automated JUnit tests" -ForegroundColor White
    Write-Host "  .\build_and_run.ps1 help    - Show this help message" -ForegroundColor White
    Write-Host ""
}
else {
    Write-Host "Unknown command: $choice" -ForegroundColor Red
    Write-Host "Run '.\build_and_run.ps1 help' for usage information" -ForegroundColor Yellow
}

