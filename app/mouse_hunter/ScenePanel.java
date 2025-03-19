package mouse_hunter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ScenePanel extends JPanel {
    static final long serialVersionUID = 1004002001008008002L;

    private final SceneModel model;
    private final SceneRenderer renderer;

    public ScenePanel(SceneModel model) {
        this.model = model;
        this.renderer = new SceneRenderer();

        setPreferredSize(new Dimension(MouseHunter.WINDOW_WIDTH + 40, MouseHunter.WINDOW_HEIGHT + 20));
        setBackground(Color.black);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        renderer.render(g2d, model);
    }
}
