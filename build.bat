chcp 1252
set curpath=%~dp0
rmdir "%curpath%\build" /s /q
rmdir "%curpath%\dist" /s /q
ant jar