package massive_balls;

import javax.swing.Timer;
import javax.swing.JFrame;

public class MassiveBalls {
	public static int WINDOW_WIDTH = 1320;
	public static int WINDOW_HEIGHT = 760;

    private Timer sceneTimer;
    private final SceneModel model;
    private final ScenePanel panel;
    private final JFrame frame;

    public MassiveBalls() {
        model = new SceneModel();
        panel = new ScenePanel(model);
        frame = new JFrame("Scene");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void startLoop() {
        if (sceneTimer == null) {
            sceneTimer = new Timer(16, e -> {
                updateSceneState();
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

    private void updateSceneState() {
        // Game logic
    };

    private void setupScene() {
        // Scene setup
    }

    public static void main(String[] args) {
        MassiveBalls st = new MassiveBalls();
        st.setupScene();
        st.startLoop();
    }

}
