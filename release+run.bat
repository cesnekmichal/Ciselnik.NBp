chcp 1252

REM echo off
REM curpath=%cd%
set curpath=%~dp0

::Does string have a trailing slash? if so remove it 
IF %curpath:~-1%==\ SET curpath=%curpath:~0,-1%

REM echo %curpath%

rmdir "%curpath%\run\dist\lib" /s /q
del "%curpath%\run\dist\CiselnikDlouhehoDrivi.jar" /s /q
mkdir "%curpath%\run\dist\lib\"
XCOPY "%curpath%\dist\lib" "%curpath%\run\dist\lib"
COPY "%curpath%\dist\Ciselnik.NBp.jar" "%curpath%\run\dist\CiselnikDlouhehoDrivi.jar"

start "" "%curpath%\run\Ciselnik.exe"

REM pause