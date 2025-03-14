package game_of_loife;

public class GameLogic {
    public void gameOfLife(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                encode(board, i, j);
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                update(board, i, j);
            }
        }
    }

    void encode(int[][] board, int i, int j) {
        int aliveCount = 0;
        for (int row = -1; row < 2; row++) {
            for (int column = -1; column < 2; column++) {
                int neighR = i + row;
                int neighC = j + column;

                if (neighR >= 0 && neighR < board.length
                        && neighC >= 0 && neighC < board[0].length) {

                    int neighVal = board[neighR][neighC];
                    if (row == -1 || (row == 0 && column == -1)) {
                        neighVal = neighVal / 10;
                    }

                    if (neighVal == 1 && !(row == 0 && column == 0)) {
                        aliveCount++;
                    }
                }
            }
        }
        board[i][j] = board[i][j] * 10 + aliveCount;
    }

    void update(int[][] board, int i, int j) {
        int neighCount = board[i][j] % 10;
        boolean isAlive = board[i][j] / 10 == 1;

        if ((isAlive && neighCount < 2) || (isAlive && neighCount > 3)) {
            board[i][j] = 0;
        } else if ((isAlive && (neighCount == 2 || neighCount == 3)) || (!isAlive && neighCount == 3)) {
            board[i][j] = 1;
        } else {
            board[i][j] = isAlive ? 1 : 0;
        }
    }
}
