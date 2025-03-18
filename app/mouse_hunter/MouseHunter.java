package mouse_hunter;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.Timer;

import data.Vec;
import objects.Ball;

public class MouseHunter {
    private static Timer sceneTimer;
    private final SceneModel model;
    private static ScenePanel panel;
    private static JFrame frame;

    private static int DEFAULT_TIME_STEP = 20;
    static int WINDOW_WIDTH = 1320;
    static int WINDOW_HEIGHT = 760;

    private static double[] draggedPos = new double[2];

    MouseHunter() {
        model = new SceneModel();
        panel = new ScenePanel(model);
        frame = new JFrame("MouseHunter");

        panel.setBackground(Color.black);
        panel.setFocusable(true);

        panel.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                draggedPos[0] = e.getX();
                draggedPos[1] = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                model.setMousePos(new Vec(e.getX(), e.getY()));
                panel.repaint();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    model.toggleShowComponents();
                    panel.repaint();
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void startLoop() {

        if (sceneTimer != null && sceneTimer.isRunning()) {
            return;
        }

        sceneTimer = new Timer(DEFAULT_TIME_STEP, e -> {
            updateScene();
            panel.repaint();
        });

        sceneTimer.start();
    }

    public void stopLoop() {
        if (sceneTimer != null) {
            sceneTimer.stop();
        }

        System.out.println("Run finished!");
    };

    private void updateScene() {
        model.update(model);
    }

    private void setupScene() {
        Vec ballPos = model.getOrigin();
        Ball ball = new Ball(20, ballPos, Color.orange);
        ball.setVisible(true);
        model.addObject(ball);
        model.setShowComponents(true);
    }

    public static void main(String[] args) {
        MouseHunter mh = new MouseHunter();
        mh.setupScene();
        mh.startLoop();
    }
}
