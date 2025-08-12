# Game of Loife
Implementation of ['Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

The board size can be adjusted, there is zooming and scrolling for the board, there are two colors available and boards can be saved and loaded.

Have fun!

## Building Game of Loife
- Clone this repository
- then:
```powershell
# Requires JDK 21 (javac/java on PATH)

# 1) Create the output directory
New-Item -ItemType Directory -Force bin | Out-Null

# 2) Generate an argfile listing all sources under `app` and `core`
#    Use ASCII so javac can read it reliably on Windows PowerShell 5.1+
Get-ChildItem -Path app, core -Recurse -File -Filter *.java |
  ForEach-Object { $_.FullName } |
  Set-Content -Encoding ascii sources.txt

# 3) Compile all sources into ./bin (packages like `data`, `objects`, `massive_balls` are rooted at app/core)
#    Quote @sources.txt to avoid PowerShell splatting
javac -d bin '@sources.txt'
# (Alternative for Windows PowerShell): javac --% -d bin @sources.txt

# 4) Run the application
java -cp bin game_of_loife/GameOfLife
```


### Example
![game_of_loife_1](https://github.com/user-attachments/assets/95d7d80a-8680-4a44-9d67-d5d6a56f2f0c)

![game_of_loife_2](https://github.com/user-attachments/assets/ac14fec5-419f-459c-9ae1-d8be02eda3a5)
