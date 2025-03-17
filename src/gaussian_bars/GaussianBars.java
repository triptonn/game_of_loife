package gaussian_bars;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.Timer;

public class GaussianBars {
    private static Timer gameTimer;
    private static int DEFAULT_TIME_STEP = 10;

    static int WINDOW_WIDTH = 1280;
    static int WINDOW_HEIGHT = 720;

    private static JFrame frame;
    private static DrawingPanel panel;

    private static int[] counterList = new int[10];

    public static void startGameLoop() {
        if (gameTimer != null && gameTimer.isRunning()) {
            return;
        }

        for (int i = 0; i < 10; i++) {
            counterList[i] = 0;
        }

        gameTimer = new Timer(DEFAULT_TIME_STEP, e -> {
            int val = GaussGenerator.getGaussInt();
            if (val < 0 || val > 99) {
            } else {
                counterList[(val / 10)]++;
                panel.setBars(counterList);
                panel.repaint();
            }
        });

        gameTimer.start();
    }

    public static void stopGameLoop() {
        if (gameTimer != null) {
            gameTimer.stop();
        }

        System.out.println("Run finished!");
    };

    public static void main(String[] args) {
        frame = new JFrame();
        DrawingPanel.setDim(WINDOW_WIDTH, WINDOW_HEIGHT);

        panel = new DrawingPanel();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBackground(Color.black);
        frame.add(panel);

        frame.setVisible(true);
        startGameLoop();
    }
}
