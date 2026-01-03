@echo off
setlocal

echo ========================================
echo Direct Gradle Wrapper (Fallback)
echo ========================================
echo.
echo This is a fallback script that uses the locally downloaded
echo Gradle distribution since gradle-wrapper.jar is missing or broken.
echo.

set "GRADLE_HOME=gradle-temp\gradle-8.10"
set "GRADLE_VERSION=8.10"

if not exist "%GRADLE_HOME%" (
    echo ERROR: Gradle distribution not found at %GRADLE_HOME%
    echo.
    echo Downloading Gradle %GRADLE_VERSION%...
    
    mkdir gradle-temp 2>nul
    powershell -Command "Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip' -OutFile 'gradle-temp\gradle-%GRADLE_VERSION%-bin.zip'"
    
    if exist "gradle-temp\gradle-%GRADLE_VERSION%-bin.zip" (
        echo Extracting Gradle distribution...
        powershell -Command "Expand-Archive -Path 'gradle-temp\gradle-%GRADLE_VERSION%-bin.zip' -DestinationPath 'gradle-temp' -Force"
        echo Gradle distribution extracted.
    ) else (
        echo Failed to download Gradle distribution.
        echo Please download it manually from:
        echo https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip
        pause
        exit /b 1
    )
)

if exist "%GRADLE_HOME%\bin\gradle.bat" (
    echo Using Gradle from: %GRADLE_HOME%
    echo.
    "%GRADLE_HOME%\bin\gradle.bat" %*
) else (
    echo ERROR: Gradle not found at %GRADLE_HOME%\bin\gradle.bat
    echo.
    echo Please fix the Gradle wrapper issue:
    echo 1. Copy gradle-wrapper.jar from a working Android project
    echo 2. Let Android Studio regenerate the wrapper
    echo 3. Install Gradle globally and run: gradle wrapper --gradle-version %GRADLE_VERSION%
    pause
    exit /b 1
)
