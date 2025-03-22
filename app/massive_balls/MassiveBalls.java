package massive_balls;

import javax.swing.Timer;

import data.Vec;
import interfaces.Movable;
import objects.Ball;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;

public class MassiveBalls {
    private Timer sceneTimer;
    private final SceneModel model;
    private final ScenePanel panel;
    private final JFrame frame;

    public Dimension dim = new Dimension(1280, 720);

    private static double[] draggedPos = new double[2];

    private boolean isWindy = false;

    public MassiveBalls() {
        model = new SceneModel(dim);
        panel = new ScenePanel(model);
        frame = new JFrame("MassiveBalls");

        panel.setPreferredSize(dim);
        panel.setBackground(Color.black);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isWindy = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isWindy = false;
            }
        });

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
        Vec gravity = new Vec(0.0, 0.3);
        Vec wind = new Vec(-0.8, -0.20);

        for (Movable m : model.getMovers()) {
            m.applyForce(gravity.scale(m.getMass()));
            if (isWindy) {
                m.applyForce(wind);
            }
        }

        model.update();
    };

    private void setupScene() {
        Random r = new Random();
        for (int i = 0; i < 7; i++) {
            int x = (int) r.nextGaussian() * 260 + 640;
            int y = (int) r.nextGaussian() * 140 + 260;

            int radius;
            double mass;
            radius = (r.nextInt(9) + 1) * 2;
            mass = radius * 3;

            System.out.println("Vec(" + x + ", " + y + "), r = " + radius);

            Vec spawn = new Vec(x, y);
            Ball b = new Ball("ball" + i, radius, mass, spawn, dim, Color.orange);
            b.setBouncy(true);
            b.setVisible(true);
            model.addObject(b);
        }

        model.setShowComponents(true);
    }

    public static void main(String[] args) {
        MassiveBalls mb = new MassiveBalls();
        mb.setupScene();
        mb.startLoop();
    }

}
