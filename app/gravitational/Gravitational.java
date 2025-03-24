package gravitational;

import javax.swing.Timer;

import data.Vec;
import interfaces.Attractor;
import interfaces.Moveable;
import objects.Ball;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Gravitational {

    public Dimension dim = new Dimension(1280, 720);

    private Timer sceneTimer;
    private final SceneModel model;
    private final ScenePanel panel;
    private final JFrame frame;
    private static boolean isRunning = true;

    public Gravitational() {
        model = new SceneModel(this.dim);
        model.setShowComponents(true);

        panel = new ScenePanel(model);
        frame = new JFrame("Gravitational");
        panel.setPreferredSize(dim);
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
        ArrayList<Attractor> attractors = model.getAttractors();
        ArrayList<Moveable> movers = model.getMovers();
        for (Attractor a : attractors) {
            for (Moveable m : movers) {
                Vec force = a.attract(m);
                if (!m.isAttractor()) {
                    m.applyForce(force);
                }
            }
        }

        model.update();
    };

    private void setupScene() {
        int radiusAttractor = 50;
        double massAttractor = 5;
        int radiusMover = 5;
        double massMover = 0.1;
        Vec locAttractor = new Vec(640, 360);
        Vec locMover = new Vec(500, 360);
        Vec initialVelMover = new Vec(0.0, 1);

        Ball attractor = new Ball("attractor", radiusAttractor, massAttractor, locAttractor, this.dim, Color.blue);
        attractor.setAttractor(true);
        attractor.setVisible(true);
        Ball mover = new Ball("mover", radiusMover, massMover, locMover, this.dim, Color.green);
        mover.setVisible(true);
        mover.setFrictionCoefficient(0);
        mover.setDragCoefficient(0);
        mover.setVelocity(initialVelMover);

        model.addObject(attractor);
        model.addObject(mover);
    }

    public static void main(String[] args) {
        Gravitational gr = new Gravitational();
        gr.setupScene();
        gr.startLoop();
    }

    public static void setRunning(boolean running) {
        isRunning = running;
    }
}
