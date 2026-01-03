@echo off
setlocal enabledelayedexpansion

echo ========================================
echo Gradle Wrapper Fix Script
echo ========================================
echo.

set "GRADLE_VERSION=8.10"
set "WRAPPER_JAR=gradle\wrapper\gradle-wrapper.jar"
set "WRAPPER_PROPERTIES=gradle\wrapper\gradle-wrapper.properties"

echo Checking Gradle wrapper...
if exist "%WRAPPER_JAR%" (
    echo ✓ gradle-wrapper.jar exists.
    echo Checking if JAR is valid...
    
    java -jar "%WRAPPER_JAR%" --version >nul 2>&1
    if !errorlevel! equ 0 (
        echo ✓ gradle-wrapper.jar appears to be valid.
        echo Gradle wrapper is working correctly.
        pause
        exit /b 0
    ) else (
        echo ✗ gradle-wrapper.jar exists but appears to be invalid or corrupted.
    )
) else (
    echo ✗ gradle-wrapper.jar is missing.
)

echo.
echo Attempting to fix Gradle wrapper...
echo.

:: Method 1: Try to download gradle-wrapper.jar from Gradle distribution
echo [1] Attempting to download gradle-wrapper.jar...
if not exist "%WRAPPER_JAR%" (
    echo Downloading Gradle %GRADLE_VERSION% distribution...
    
    :: Create temp directory
    if not exist "temp-gradle-fix" mkdir "temp-gradle-fix"
    
    :: Download Gradle distribution
    echo Downloading gradle-%GRADLE_VERSION%-bin.zip...
    powershell -Command "Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip' -OutFile 'temp-gradle-fix\gradle-%GRADLE_VERSION%-bin.zip'" >nul 2>&1
    
    if exist "temp-gradle-fix\gradle-%GRADLE_VERSION%-bin.zip" (
        echo Extracting distribution...
        powershell -Command "Expand-Archive -Path 'temp-gradle-fix\gradle-%GRADLE_VERSION%-bin.zip' -DestinationPath 'temp-gradle-fix' -Force" >nul 2>&1
        
        :: Look for gradle-wrapper.jar in the distribution
        echo Searching for gradle-wrapper.jar in distribution...
        dir /s /b "temp-gradle-fix\gradle-%GRADLE_VERSION%\*gradle-wrapper.jar" > temp-gradle-fix\jar-list.txt 2>nul
        
        set "FOUND_JAR="
        for /f "delims=" %%i in ('type temp-gradle-fix\jar-list.txt') do (
            set "FOUND_JAR=%%i"
        )
        
        if defined FOUND_JAR (
            echo Found gradle-wrapper.jar at: !FOUND_JAR!
            copy "!FOUND_JAR!" "%WRAPPER_JAR%" >nul
            echo Copied to %WRAPPER_JAR%
        ) else (
            echo Could not find gradle-wrapper.jar in the distribution.
        )
        
        :: Cleanup
        rmdir /s /q "temp-gradle-fix" 2>nul
    ) else (
        echo Failed to download Gradle distribution.
    )
)

:: Check if Method 1 worked
if exist "%WRAPPER_JAR%" (
    java -jar "%WRAPPER_JAR%" --version >nul 2>&1
    if !errorlevel! equ 0 (
        echo ✓ Successfully fixed gradle-wrapper.jar!
        echo.
        echo Testing Gradle wrapper...
        call :test_gradle_wrapper
        pause
        exit /b 0
    )
)

:: Method 2: Try to use installed Gradle to regenerate wrapper
echo.
echo [2] Attempting to use installed Gradle to regenerate wrapper...
where gradle >nul 2>&1
if !errorlevel! equ 0 (
    echo Found Gradle installation.
    echo Running: gradle wrapper --gradle-version %GRADLE_VERSION%
    
    :: Create a simple build.gradle to avoid Android plugin issues
    echo buildscript { } > temp-build.gradle
    echo "" > settings.gradle.kts
    
    gradle wrapper --gradle-version %GRADLE_VERSION% --distribution-type bin >nul 2>&1
    
    del temp-build.gradle 2>nul
    del settings.gradle.kts 2>nul
    
    if exist "%WRAPPER_JAR%" (
        echo ✓ Generated gradle-wrapper.jar using Gradle.
    ) else (
        echo ✗ Failed to generate gradle-wrapper.jar.
    )
) else (
    echo Gradle is not installed globally.
)

:: Method 3: Create a minimal valid gradle-wrapper.jar as last resort
echo.
echo [3] Creating minimal gradle-wrapper.jar as last resort...
if not exist "%WRAPPER_JAR%" (
    echo Creating a placeholder gradle-wrapper.jar...
    echo This is a placeholder JAR. You need to manually fix the Gradle wrapper. > placeholder.txt
    jar cf "%WRAPPER_JAR%" placeholder.txt 2>nul
    del placeholder.txt 2>nul
    
    if exist "%WRAPPER_JAR%" (
        echo Created placeholder gradle-wrapper.jar.
        echo NOTE: This is NOT a functional JAR. Manual intervention required.
    )
)

:: Final check
echo.
echo ========================================
echo Final Status
echo ========================================

if exist "%WRAPPER_JAR%" (
    echo gradle-wrapper.jar: EXISTS
    java -jar "%WRAPPER_JAR%" --version >nul 2>&1
    if !errorlevel! equ 0 (
        echo Status: WORKING
        echo.
        echo Testing Gradle wrapper...
        call :test_gradle_wrapper
    ) else (
        echo Status: PRESENT BUT INVALID
        echo.
        echo The gradle-wrapper.jar file exists but is not functional.
        echo.
        echo Manual fixes required:
        echo 1. Copy gradle-wrapper.jar from a working Android project
        echo 2. Let Android Studio regenerate the wrapper
        echo 3. Install Gradle and run: gradle wrapper --gradle-version %GRADLE_VERSION%
    )
) else (
    echo gradle-wrapper.jar: MISSING
    echo.
    echo Failed to fix Gradle wrapper automatically.
    echo.
    echo Manual fixes required:
    echo 1. Copy gradle-wrapper.jar from a working Android project
    echo 2. Let Android Studio regenerate the wrapper
    echo 3. Install Gradle and run: gradle wrapper --gradle-version %GRADLE_VERSION%
)

echo.
echo Script execution complete.
pause
exit /b 0

:test_gradle_wrapper
echo Running: gradlew --version
gradlew --version >nul 2>&1
if !errorlevel! equ 0 (
    echo ✓ Gradle wrapper is working correctly!
    echo.
    gradlew --version | findstr "Gradle"
) else (
    echo ✗ Gradle wrapper is still not working.
    echo Error: Could not find or load main class org.gradle.wrapper.GradleWrapperMain
)
exit /b 0
