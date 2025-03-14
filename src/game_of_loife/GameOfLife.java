package game_of_loife;

public class GameOfLife {
    public static void main(String[] args) {
        gofGui frame = new gofGui();
        frame.setSize(frame.window_height, frame.window_width);
        frame.setDefaultCloseOperation(gofGui.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
