package vector_shizzle;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.Timer;

public class MouseHunter {
    private static Timer gameTimer;
    private static int DEFAULT_TIME_STEP = 20;

    static int WINDOW_WIDTH = 1280;
    static int WINDOW_HEIGHT = 720;

    private static JFrame frame;
    private static DrawingPanel panel;

    private static double[] draggedPos = new double[2];
    private static double[] movedPos = new double[2];

    private static Vec ballPos;

    public static void startGameLoop() {
        if (gameTimer != null && gameTimer.isRunning()) {
            return;
        }

        gameTimer = new Timer(DEFAULT_TIME_STEP, e -> {
            Vec mousePos = new Vec(movedPos[0], movedPos[1]);
            Vec direction = mousePos.minus(ballPos);

            if (direction.mag() > 0) {
                Vec movement = direction.direction().scale(5);
                ballPos = ballPos.plus(movement);
            }

            panel.updateLoc(ballPos);
            panel.repaint();
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

        panel = new DrawingPanel();
        ballPos = new Vec(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
        panel.updateLoc(ballPos);

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBackground(Color.black);

        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                movedPos[0] = e.getX();
                movedPos[1] = e.getY();
                System.out.println("Got mouse moved to " + movedPos[0] + ", " + movedPos[1]);
            }

            public void mouseDragged(MouseEvent e) {
                draggedPos[0] = e.getX();
                draggedPos[1] = e.getY();
            }
        };
        panel.addMouseMotionListener(mouseMotionListener);

        frame.add(panel);

        frame.setVisible(true);
        startGameLoop();
    }
}
