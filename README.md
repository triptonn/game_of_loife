# Game of Loife
Implementation of ["Conway's Game of Life"](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).

"The Game of Life, also known as Conway's Game of Life or simply Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970. It is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input. One interacts with the Game of Life by creating an initial configuration and observing how it evolves. It is Turing complete and can simulate a universal constructor or any other Turing machine." - Source: [Wikipedia](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)


The usable board size can be adjusted, there is zooming and scrolling for the board, two colors can be used and boards can be saved and loaded.

Have fun!


1. [Dependencies](#dependencies)
2. [Build instructions](#building-game-of-loife)
3. [Example GIF's](#example)

## Dependencies
- JavaSE Development Kit 21

## Building Game of Loife
- Clone this repository
  ```
  git clone https://github.com/triptonn/game_of_loife.git
  ```
- Run the build script depending on your environment:
  - cd into the cloned repository
  ```
  cd ./game_of_loife
  ```

- Windows Command Line (cmd)
  ```cmd
  REM Run this from the project root directory
  .\build\build.cmd
  ```
- PowerShell

  ```powershell
  # Run this from the project root directory
  ./build/build.ps1
  ```
- Bash [Linux and OSX (not tested)]

  ```bash
  # Run this from the project root directory
  ./build/build.sh
  ```

- Run the application from the project root directory
  ```bash
  java -cp bin game_of_loife.GameOfLife
  ```


## Example
![game_of_loife_1](https://github.com/user-attachments/assets/95d7d80a-8680-4a44-9d67-d5d6a56f2f0c)

![game_of_loife_2](https://github.com/user-attachments/assets/ac14fec5-419f-459c-9ae1-d8be02eda3a5)
