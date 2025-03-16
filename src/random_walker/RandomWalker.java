package random_walker;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.Timer;

public class RandomWalker {
    private static Timer gameTimer;
    private static int DEFAULT_TIME_STEP = 200;

    static int WINDOW_WIDTH = 1280;
    static int WINDOW_HEIGHT = 720;

    private static JFrame frame;
    private static DrawingPanel dP;
    private static Walker w;

    public static void startGameLoop() {
        System.out.println("Game loop started");
        if (gameTimer != null && gameTimer.isRunning()) {
            return;
        }

        gameTimer = new Timer(DEFAULT_TIME_STEP, e -> {
            w.move();
            Point newPos = w.getPos();
            System.out.println("newPos: " + newPos);
            dP.setPos(newPos);
            dP.repaint();
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
        w = new Walker();
        dP = new DrawingPanel(w.getPos());
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(dP);
        frame.setVisible(true);
        startGameLoop();
    }
}
