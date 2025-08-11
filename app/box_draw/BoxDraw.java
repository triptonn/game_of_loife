package box_draw;

import javax.swing.Timer;

import data.Vec;
import interfaces.Moveable;
import objects.Box;
import objects.MoBox;
import objects.SceneObject;
import objects.VectorArrow;

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

    private Vec angularVel_mobox = new Vec(0.0, 0.0);
    private Vec locAngularVelocity_mobox = new Vec(0.0, 0.0);
    private double __angle;

    public BoxDraw() {
        model = new SceneModel(this.dim);
        model.setShowComponents(true);

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
        for (SceneObject obj : model.getObjects()) {
            if (obj instanceof Moveable) {
                Moveable m = (Moveable) obj;
                Vec momentum_change = new Vec(angularVel_mobox.scale(-1 * 2.0));
                m.applyMomentum(momentum_change);

                if (m instanceof MoBox) {
                    MoBox mobox = (MoBox) m;
                    // this.angularVel_mobox = new Vec(0.0, mobox.getAngularVelocity());
                    this.__angle = mobox.getAngle() + (Math.PI / 4);
                }
            }

            if (obj instanceof VectorArrow) {
                VectorArrow v = (VectorArrow) obj;
                v.update(this.angularVel_mobox.scale(-1000));
            }
        }
        model.update();
    };

    private void setupScene() {
        Vec boxLoc = new Vec(300, 300);
        Vec moboxLoc = new Vec(700, 300);

        Box box = new Box("test", boxLoc, 300, 150, dim, Color.blue);
        box.setVisible(true);
        model.addObject(box);

        MoBox mobox = new MoBox("testi", moboxLoc, 300, 150, 1, dim, Color.red);
        mobox.setVisible(true);
        model.addObject(mobox);

        this.locAngularVelocity_mobox = mobox.getLocation();
        VectorArrow angularVelocity_mobox = new VectorArrow(
                "angularVel",
                this.angularVel_mobox,
                this.locAngularVelocity_mobox.minus(new Vec(0.0, -75)),
                10,
                this.dim,
                Color.yellow);
        angularVelocity_mobox.setVisible(true);
        model.addObject(angularVelocity_mobox);

    }

    public static void main(String[] args) {
        BoxDraw bd = new BoxDraw();
        bd.setupScene();
        bd.startLoop();
    }
}
