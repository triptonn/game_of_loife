package game_of_loife;

public class GameLogic {
    private int scorePOne = 0;
    private int scorePTwo = 0;
    private String score = "";

    public void gameOfLife(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                encode(board, i, j);
            }
        }
        System.out.println(this.score);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                update(board, i, j);
            }
        }
    }

    void encode(int[][] board, int i, int j) {
        int aliveCountPOne = 0;
        int aliveCountPTwo = 0;
        int aliveTotal = 0;
        // System.out.println("val[" + i + "][" + j + "]: " + board[i][j]);
        for (int row = -1; row < 2; row++) {
            for (int column = -1; column < 2; column++) {
                int neighR = i + row;
                int neighC = j + column;

                if (neighR >= 0 && neighR < board.length
                        && neighC >= 0 && neighC < board[0].length) {

                    int neighVal = board[neighR][neighC];
                    if (row == -1 || (row == 0 && column == -1)) {
                        // System.out.println("neighVal[" + neighR + "][" + neighC + "]: " + neighVal);
                        if (neighVal >= 200) {
                            neighVal = neighVal % 200;
                        } else if (200 > neighVal && neighVal >= 100) {
                            neighVal = neighVal % 100;
                        }
                        neighVal = neighVal / 10;
                        // System.out.println("neighVal[" + neighR + "][" + neighC + "]: " + neighVal +
                        // " / "
                        // + board[neighR][neighC]);
                    }

                    if (neighVal == 1 && !(row == 0 && column == 0)) {
                        aliveCountPOne++;
                    } else if (neighVal == 2 && !(row == 0 && column == 0)) {
                        aliveCountPTwo++;
                    }
                }
            }
        }

        // TODO: Somewhere here i need to count better
        aliveTotal = aliveCountPOne + aliveCountPTwo;
        // System.out.println("alive total: " + aliveTotal
        // + " = aliveCountPOne: " + aliveCountPOne
        // + " + aliveCountPTwo: " + aliveCountPTwo);
        if ((aliveCountPOne == 0 && !(aliveCountPTwo == 0)) || (aliveCountPTwo == 0 && !(aliveCountPOne == 0))) {
            if (aliveCountPOne < aliveCountPTwo) {
                board[i][j] = 200 + board[i][j] * 10 + aliveTotal;
            } else if (aliveCountPOne > aliveCountPTwo) {
                board[i][j] = 100 + board[i][j] * 10 + aliveTotal;
            } else {
                double chance = Math.random();
                if (chance <= 0.5) {
                    board[i][j] = 100 + board[i][j] * 10 + aliveTotal;
                } else {
                    board[i][j] = 200 + board[i][j] * 10 + aliveTotal;
                }
            }
        } else {
            if (aliveCountPOne < aliveCountPTwo) {
                board[i][j] = 200 + board[i][j] * 10 + aliveTotal;
                scorePTwo++;
            } else if (aliveCountPOne > aliveCountPTwo) {
                board[i][j] = 100 + board[i][j] * 10 + aliveTotal;
                scorePOne++;
            } else {
                double chance = Math.random();
                if (chance <= 0.5) {
                    board[i][j] = 100 + board[i][j] * 10 + aliveTotal;
                    scorePOne++;
                } else {
                    board[i][j] = 200 + board[i][j] * 10 + aliveTotal;
                    scorePTwo++;
                }
            }
        }
        this.score = "Score: " + String.valueOf(scorePOne) + ":" + String.valueOf(scorePTwo);
    }

    void update(int[][] board, int i, int j) {
        int player = 0;
        // System.out.println("board[" + i + "][" + j + "]: " + board[i][j]);

        if (board[i][j] >= 200) {
            board[i][j] = board[i][j] % 200;
            player = 2;
        } else if ((board[i][j] < 200) && (board[i][j] >= 100)) {
            board[i][j] = board[i][j] % 100;
            player = 1;
        }

        int neighCount = board[i][j] % 10;
        // System.out.println("board[" + i + "][" + j + "]: " + board[i][j] + ",
        // neighCount: " + neighCount);

        boolean isAlive = (board[i][j] / 10) > 0;

        if ((isAlive && neighCount < 2) || (isAlive && neighCount > 3)) {
            board[i][j] = 0;
        } else if ((isAlive && (neighCount == 2 || neighCount == 3)) || (!isAlive && neighCount == 3)) {
            board[i][j] = 1;
        } else {
            board[i][j] = isAlive ? 1 : 0;
        }

        if (board[i][j] == 1 && player == 2) {
            board[i][j] = 2;
        }
    }
}
