New-Item -ItemType Directory -Force bin | Out-Null
Get-ChildItem -Path app, core -Recurse -File -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding ascii sources.txt
javac -d bin '@sources.txt'