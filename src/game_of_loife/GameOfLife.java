package game_of_loife;

import java.awt.Dimension;
import javax.swing.Timer;

public class GameOfLife {
    private static Timer gameTimer;
    private static GameLogic gameLogic = new GameLogic();
    private static final int DEFAULT_DELAY = 1000;
    private static int[][] board;

    public static void startGameLoop(GofGui frame) {
        if (gameTimer != null && gameTimer.isRunning()) {
            return;
        }

        gameTimer = new Timer(DEFAULT_DELAY, e -> {
            gameLogic.gameOfLife(frame.getBoard());
            frame.getPnlGame().repaint();
        });

        gameTimer.start();
    }

    public static void stopGameLoop() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    public static void setSpeed(int delayMs) {
        if (gameTimer != null) {
            gameTimer.setDelay(delayMs);
        }
    }

    public static void setBoard(int[][] newBoard) {
        board = newBoard;
    }

    public static void main(String[] args) {
        int min_window_height = 800;
        int min_window_width = 800;

        int default_game_height = 100;
        int default_game_width = 100;

        board = new int[default_game_width][default_game_height];

        GofGui frame = new GofGui(board);

        Dimension dim = new Dimension(min_window_height, min_window_width);
        frame.setMinimumSize(dim);
        frame.setSize(min_window_height, min_window_width);
        frame.setDefaultCloseOperation(GofGui.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
