@echo off
setlocal DisableDelayedExpansion
set "BRIDGE_HOME=%~dp0.."
set "BRIDGE_JAVA=java"
if defined JAVA_HOME set "BRIDGE_JAVA=%JAVA_HOME%\bin\java.exe"
if defined JAVA_HOME if not exist "%BRIDGE_JAVA%" goto missingjava
if not defined JAVA_HOME where /q java.exe
if errorlevel 1 goto missingjava
"%BRIDGE_JAVA%" --list-modules 2>nul | findstr /b "jdk.compiler@" >nul
if errorlevel 1 goto missingjava
"%BRIDGE_JAVA%" --list-modules 2>nul | findstr /b "java.net.http@" >nul
if errorlevel 1 goto missingjava
"%BRIDGE_JAVA%" -cp "%BRIDGE_HOME%\tools\*" PreviewMain %*
exit /b %errorlevel%
:missingjava
echo wrap-robots requires a full JDK 11 or newer. Install a JDK and set JAVA_HOME or PATH. 1>&2
exit /b 1
