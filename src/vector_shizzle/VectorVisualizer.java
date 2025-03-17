package vector_shizzle;

import javax.swing.Timer;

import javax.swing.JFrame;

public class VectorVisualizer {
    private static Timer sceneTimer;
    private final SceneModel model;
    private final ScenePanel panel;
    private static final JFrame frame;

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
    }

    public static void start() {
        if (sceneTimer != null) {
            sceneTimer = new Timer(16, e -> {
                updateSceneState();
                panel.repaint();
            });
        }
        sceneTimer.start();
        frame.setVisible(true);
    }

    public void stopGameLoop() {
        if (sceneTimer != null) {
            sceneTimer.stop();
        }

        System.out.println("Run finished!");
    };

    private static void updateSceneState() {
        // Game logic
    };

    public static void main(String[] args) {
        start();
    }
}
