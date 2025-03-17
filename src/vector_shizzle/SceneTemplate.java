package vector_shizzle;

import javax.swing.Timer;

import javax.swing.JFrame;

public class SceneTemplate {
    private Timer sceneTimer;
    private final SceneModel model;
    private final ScenePanel panel;
    private final JFrame frame;

    public SceneTemplate() {
        model = new SceneModel();
        panel = new ScenePanel(model);
        frame = new JFrame("Scene");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void start() {
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

    private void updateSceneState() {
        // Game logic
    };
}
