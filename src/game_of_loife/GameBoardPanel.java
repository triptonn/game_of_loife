package game_of_loife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoardPanel extends JPanel {
    private int[][] _board;
    private boolean painting;
    private int _zoomFactor;
    private static final Color GRID_COLOR = Color.DARK_GRAY;
    private static final Color ALIVE_COLOR = Color.LIGHT_GRAY;
    private static final Color DEAD_COLOR = Color.BLACK;

    public GameBoardPanel(int[][] board, int zoomFactor) {
        _board = board;
        _zoomFactor = Math.max(1, zoomFactor);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                painting = true;
                updateBoard(e);
            }

            public void mouseReleased(MouseEvent e) {
                painting = false;
            }

            public void mouseDragged(MouseEvent e) {
                if (painting) {
                    updateBoard(e);
                }
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);

        addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                _zoomFactor = Math.min(50, _zoomFactor + 1);
            } else if (e.getWheelRotation() > 0) {
                _zoomFactor = Math.max(1, _zoomFactor - 1);
            }
            repaint();
        });

        setPreferredSize(new Dimension(
                _board[0].length * _zoomFactor,
                _board.length * _zoomFactor));
    }

    public void setBoard(int[][] board) {
        this._board = board;
        repaint();
    }

    public void setZoomFactor(int zoomFactor) {
        this._zoomFactor = Math.max(1, zoomFactor);
        setPreferredSize(new Dimension(
                _board[0].length * _zoomFactor,
                _board.length * _zoomFactor));
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            ((Graphics2D) g).setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        for (int i = 0; i < _board.length; i++) {
            for (int j = 0; j < _board[0].length; j++) {
                int x = j * _zoomFactor;
                int y = i * _zoomFactor;

                g.setColor(_board[i][j] == 1 ? ALIVE_COLOR : DEAD_COLOR);
                g.fillRect(x, y, _zoomFactor, _zoomFactor);

                g.setColor(GRID_COLOR);
                g.drawRect(x, y, _zoomFactor, _zoomFactor);
            }
        }
    }

    public void updateBoard(MouseEvent e) {
        int col = e.getX() / _zoomFactor;
        int row = e.getY() / _zoomFactor;

        if (row >= 0 && row < _board.length && col >= 0 && col < _board[0].length) {
            _board[row][col] = 1;
            repaint();
        }
    }
}
