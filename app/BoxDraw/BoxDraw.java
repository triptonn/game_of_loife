package BoxDraw;

import javax.swing.Timer;

import data.Vec;
import objects.Box;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class BoxDraw {

    public Dimension dim = new Dimension(1280, 720);

    private Timer sceneTimer;
    private final SceneModel model;
    private final ScenePanel panel;
    private final JFrame frame;

    public BoxDraw() {
        model = new SceneModel(this.dim);
        panel = new ScenePanel(model);
        frame = new JFrame("BoxDraw");

        panel.setPreferredSize(new Dimension(dim.width, dim.height));
        panel.setBackground(Color.black);

        panel.addMouseMotionListener(new MouseAdapter() {
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
    }

    public void startLoop() {
        if (sceneTimer == null) {
            sceneTimer = new Timer(16, e -> {
                update();
                panel.repaint();
            });
        }
        sceneTimer.start();
        frame.setVisible(true);
    }

    public void stopLoop() {
        if (sceneTimer != null) {
            sceneTimer.stop();
        }
    };

    private void update() {
        // Game logic
    };

    private void setupScene() {
        Vec boxLoc = new Vec(300, 300);

        Box box = new Box("test", boxLoc, 680, 200, dim, Color.blue);
        box.setVisible(true);
        model.addObject(box);
        model.setShowComponents(true);
    }

    public static void main(String[] args) {
        BoxDraw bd = new BoxDraw();
        bd.setupScene();
        bd.startLoop();
    }
}
