@echo off
setlocal

if not exist bin mkdir bin >nul 2>&1
(
  for /r "app" %%F in (*.java) do @echo %%~fF
  for /r "core" %%F in (*.java) do @echo %%~fF
) > sources.txt
javac -d bin @sources.txt

endlocal

