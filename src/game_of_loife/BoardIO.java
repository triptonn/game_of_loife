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

            int[][] board = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    board[i][j] = dis.readInt();
                }
            }
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
