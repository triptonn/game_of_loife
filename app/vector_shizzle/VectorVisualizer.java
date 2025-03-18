package vector_shizzle;

import javax.swing.Timer;

import javax.swing.JFrame;

public class VectorVisualizer {
    private static Timer sceneTimer;
    private final SceneModel model;
    private final ScenePanel panel;
    private static JFrame frame;

    static final int WINDOW_WIDTH = 1320;
    static final int WINDOW_HEIGHT = 760;

    public VectorVisualizer() {
        model = new SceneModel();
        panel = new ScenePanel(model);
        frame = new JFrame("VectorVisualizer");

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

        sceneTimer = new Timer(16, e -> {
            updateSceneState();
            panel.repaint();
        });

        sceneTimer.start();
    }

    public void stopGameLoop() {
        if (sceneTimer != null) {
            sceneTimer.stop();
        }

        System.out.println("Run finished!");
    };

    private void updateSceneState() {
        // Game logic
    };

    private void setupScene() {

    }

    public static void main(String[] args) {
        VectorVisualizer vv = new VectorVisualizer();
        vv.setupScene();
        vv.startLoop();
    }
}
