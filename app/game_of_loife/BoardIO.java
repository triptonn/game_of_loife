package game_of_loife;

import java.io.*;

public class BoardIO {
    private static final String SAVE_DIRECTORY = "saves";

    static {
        new File(SAVE_DIRECTORY).mkdirs();
    }

    public static void saveBoard(int[][] board, String filename) throws IOException {
        if (!filename.endsWith(".gol")) {
            filename += ".gol";
        }
        String fullPath = SAVE_DIRECTORY + File.separator + filename;

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fullPath))) {
            System.out.println("Saving board dimensions: " + board.length + "x" + board[0].length);
            System.out
                    .println("Sample values from first row: " + board[0][0] + ", " + board[0][1] + ", " + board[0][2]);

            dos.writeInt(board.length);
            dos.writeInt(board[0].length);
            for (int[] row : board) {
                for (int cell : row) {
                    dos.writeInt(cell);
                }
            }
        }
    }

    public static int[][] loadBoard(String filename) throws IOException {
        if (!filename.endsWith(".gol")) {
            filename += ".gol";
        }

        String fullPath = SAVE_DIRECTORY + File.separator + filename;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(fullPath))) {
            int rows = dis.readInt();
            int cols = dis.readInt();

            System.out.println("Loading board dimensions: " + rows + "x" + cols);

            int[][] board = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    board[i][j] = dis.readInt();
                }
            }
            System.out.println(
                    "Sample value from first row: " + board[0][0] + ", " + board[0][1] + ", " + board[0][2]);
            return board;
        }
    }

    public static boolean saveExists(String filename) {
        if (!filename.endsWith(".gol")) {
            filename += ".gol";
        }
        return new File(SAVE_DIRECTORY + File.separator + filename).exists();
    }

    public static String getSaveDir() {
        return SAVE_DIRECTORY;
    }
}
