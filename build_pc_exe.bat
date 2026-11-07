@echo off
echo ===================================================
echo     NAFI TV 24 - Windows PC Installer Builder (.exe)
echo ===================================================
echo.
echo Checking Java Environment...
java -version
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH! Please install JDK 17 or JDK 21.
    pause
    exit /b
)

echo.
echo Building NAFI TV 24 Windows (.exe) Package...
if exist "gradlew.bat" (
    call gradlew.bat -p desktop packageExe
) else (
    call gradle -p desktop packageExe
)

echo.
echo ===================================================
if %errorlevel% equ 0 (
    echo [SUCCESS] .exe installer generated successfully!
    echo Location: desktop\build\compose\binaries\main\exe\
    echo.
    echo NOTE for older Windows (7/8/early 10):
    echo If you see 'api-ms-win-crt-runtime-l1-1-0.dll' or 'ucrtbase.terminate' error,
    echo please install 'Microsoft Visual C++ Redistributable (x64)' from Microsoft:
    echo https://aka.ms/vs/17/release/vc_redist.x64.exe
) else (
    echo [FAILED] Build failed! Check the logs above.
)
echo ===================================================
pause
