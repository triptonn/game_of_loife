package massive_balls;

import javax.swing.Timer;

import data.Vec;
import ui.ColorScheme;
import interfaces.Moveable;
import objects.Ball;
import objects.SceneObject;
import objects.SimpleLiquid;

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
    private double airBounceFactor = 0.8;
    private double waterBounceFactor = 0.2;

    private static double[] draggedPos = new double[2];

    private boolean windyLeft = false;
    private boolean windyRight = false;
    private static boolean isRunning = true;

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
                    windyRight = true;
                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    windyLeft = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (windyLeft == true) {
                    windyLeft = false;
                }

                if (windyRight == true) {
                    windyRight = false;
                }

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
                    if (!isRunning) {
                        startLoop();
                        setRunning(true);
                    } else {
                        stopLoop();
                        setRunning(false);
                    }
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

        if (!isRunning) {
            setRunning(true);
        }

        sceneTimer.start();

        frame.setVisible(true);
    }

    public void stopLoop() {
        if (sceneTimer != null) {
            if (isRunning) {
                setRunning(false);
            }
            sceneTimer.stop();
        }
    };

    private void update() {
        Vec gravity = new Vec(0.0, 0.3);
        Vec drag = new Vec(0.0, 0.0);
        Vec wind_right = new Vec(-10.0, 0.0);
        Vec wind_left = new Vec(10.0, 0.0);

        for (Moveable m : model.getMovers()) {
            m.applyForce(gravity.scale(m.getMass()));
            if (windyRight) {
                m.applyForce(wind_right);
            }

            if (windyLeft) {
                m.applyForce(wind_left);
            }

            if (m.isLanded() && m.getHasFriction()) {
                Vec friction = new Vec(m.getVelocity().norm().scale(m.getFrictionCoefficient()));
                m.applyForce(friction);
            }

            if (m.getHasDrag()) {
                double speed = m.getVelocity().mag();
                drag = m.getVelocity().norm().scale(m.getDragCoefficient() * speed * speed);
                m.applyForce(drag);
            }

            for (SceneObject obj : model.getObjects()) {
                if (obj instanceof SimpleLiquid && m.getVelocity().mag() > 0) {
                    SimpleLiquid liquidBody = (SimpleLiquid) obj;
                    if (liquidBody.contains(m)) {
                        m.setBounceFactor(this.waterBounceFactor);
                        liquidBody.drag(m);
                    } else {
                        m.setBounceFactor(this.airBounceFactor);
                    }
                }
            }
            model.update();
        }
    };

    private void setupScene() {
        Random r = new Random(725630);
        for (int i = 0; i < 4; i++) {
            int x = (int) (r.nextGaussian() * 360) + 640;
            int y = (int) (r.nextGaussian() * 120) + 260;

            int radius;
            double mass;
            radius = (r.nextInt(10) + 2) * 2;
            mass = radius * 3;
            Vec spawn = new Vec(x, y);
            Ball b = new Ball("ball" + i, radius, mass, spawn, dim, Color.green);
            b.setBouncy(true);
            b.setFrictionCoefficient(0.05);
            b.setDragCoefficient(0.01);
            b.setVisible(true);
            model.addObject(b);
        }

        SimpleLiquid liquidBody = new SimpleLiquid("water", (int) (this.dim.getWidth() / 2),
                (int) (this.dim.getHeight() / 2), 16,
                new Vec(10, 360), this.dim,
                ColorScheme.WATER_COLOR);

        liquidBody.setVisible(true);
        model.addObject(liquidBody);

        model.setShowComponents(true);
    }

    public static void main(String[] args) {
        MassiveBalls mb = new MassiveBalls();
        mb.setupScene();
        mb.startLoop();
    }

    public static void setRunning(boolean running) {
        isRunning = running;
    }
}
