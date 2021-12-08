mode CON: COLS=124
system("mode CON: COLS=124");

@echo off

reg.exe query "HKLM\Software\JavaSoft\Java Runtime Environment" /v "CurrentVersion" > nul 2> nul
if errorlevel 1 goto NotInstalled

set JvmVersion=
for /F "tokens=3* delims= " %%A IN ('reg.exe query "HKLM\Software\JavaSoft\Java Runtime Environment" /v "CurrentVersion"') do set JvmVersion=%%A
if not "%JvmVersion%" == "1.8" goto InstalledWrongVersion

:Installed
echo.
echo  Booting up TimeEmulator for Windows ..
echo.
java -Dfile.encoding=UTF-8 -Xms1m -jar ./bin/TimeEmulator.jar
exit /b 0

:InstalledWrongVersion
echo Java 1.8 is not installed on this system! TimeEmulator needs at least Java 1.8 installed.
exit /b 1

:NotInstalled
echo Java is not installed on this system after all! Please install Java version 1.8.
exit /b 1