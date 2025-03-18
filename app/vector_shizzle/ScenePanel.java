package vector_shizzle;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ScenePanel extends JPanel {
    private final SceneModel model;
    private final SceneRenderer renderer;

    public ScenePanel(SceneModel model) {
        this.model = model;
        this.renderer = new SceneRenderer();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        renderer.render(g2d, model);
    }
}
