package random_walker;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class RandomWalker {
    private static Timer gameTimer;
    private static int DEFAULT_TIME_STEP = 200;
    private static ArrayList<Point> pointHistory = new ArrayList<Point>();

    static int WINDOW_WIDTH = 1280;
    static int WINDOW_HEIGHT = 720;

    private static JFrame frame;
    private static DrawingPanel dP;
    private static Walker w;

    public static void startGameLoop() {
        if (gameTimer != null && gameTimer.isRunning()) {
            return;
        }

        addToHistory(w.getPos());

        gameTimer = new Timer(DEFAULT_TIME_STEP, e -> {
            w.move();
            Point newPos = w.getPos();
            addToHistory(newPos);
            dP.setDrawing_history(pointHistory);
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

    public static void addToHistory(Point p) {
        Point pointCopy = new Point(p.x, p.y);
        boolean isNew = true;
        for (Point pt : pointHistory) {
            if (pointCopy.x == pt.x && pointCopy.y == pt.y) {
                isNew = false;
            }
        }
        if (isNew) {
            pointHistory.add(pointCopy);
        }
    }
}
