package vector_shizzle;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.Timer;

import data.Vec;
import objects.VectorArrow;

public class VectorVisualizer {
    private static Timer sceneTimer;
    private final SceneModel model;
    private final ScenePanel panel;
    private static JFrame frame;

    private static int DEFAULT_TIME_STEP = 16;
    static final int WINDOW_WIDTH = 1280;
    static final int WINDOW_HEIGHT = 720;

    private static double[] draggedPos = new double[2];

    public VectorVisualizer() {
        model = new SceneModel();
        panel = new ScenePanel(model);
        frame = new JFrame("VectorVisualizer");

        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        panel.setBackground(Color.black);

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

        panel.setFocusable(true);
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

    public void stopGameLoop() {
        if (sceneTimer != null) {
            sceneTimer.stop();
        }
    };

    private void updateScene() {
        model.update();
    };

    private void setupScene() {
        Vec A = new Vec(200, 500);
        Vec B = new Vec(800, 500);
        // Vec C = new Vec(800, 200);
        Vec a = new Vec(600, 0);
        Vec b = new Vec(0, -300);
        Vec c = new Vec(600, -300); 
        Dimension dim = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT); 
        VectorArrow aVec = new VectorArrow("a", a, A, 10, dim, Color.red);
        VectorArrow bVec = new VectorArrow("b", b, B, 10, dim, Color.yellow);
        VectorArrow cVec = new VectorArrow("c", c, A, 10, dim, Color.green);
        aVec.setVisible(true);
        bVec.setVisible(true);
        cVec.setVisible(true);
        model.addObject(aVec);
        model.addObject(bVec);
        model.addObject(cVec);
        model.setShowComponents(true);
    }

    public static void main(String[] args) {
        VectorVisualizer vv = new VectorVisualizer();
        vv.setupScene();
        vv.startLoop();
    }
}
