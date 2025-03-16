package templates;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class WindowAppTemplate {
    private static Timer gameTimer;
    private static int DEFAULT_TIME_STEP = 200;

    static int WINDOW_WIDTH = 1280;
    static int WINDOW_HEIGHT = 720;

    private static JFrame frame;
    private static JPanel panel;

    public static void startGameLoop() {
        if (gameTimer != null && gameTimer.isRunning()) {
            return;
        }

        gameTimer = new Timer(DEFAULT_TIME_STEP, e -> {
        });

        gameTimer.start();
    }

    public static void stopGameLoop() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    };

    public static void main(String[] args) {
        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        startGameLoop();
    }
}
