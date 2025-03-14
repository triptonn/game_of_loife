package game_of_loife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoardPanel extends JPanel implements Scrollable {
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
            updateSize();
        });

        // Initialize size with current zoom factor
        updateSize();
    }

    private void updateSize() {
        Dimension newSize = new Dimension(
                _board[0].length * _zoomFactor,
                _board.length * _zoomFactor);
        setPreferredSize(newSize);
        setMinimumSize(newSize);  // Add minimum size to prevent collapse
        revalidate();
        repaint();

        Container parent = getParent();
        if (parent instanceof JViewport) {
            parent = parent.getParent();
            if (parent instanceof JScrollPane) {
                parent.revalidate();
            }
        }
    }

    public void setBoard(int[][] newBoard) {
        this._board = newBoard;
        updateSize();
    }

    public void setZoomFactor(int zoomFactor) {
        this._zoomFactor = Math.max(1, zoomFactor);
        updateSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            ((Graphics2D) g).setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        Rectangle clipBounds = g.getClipBounds();

        int startRow = Math.max(0, clipBounds.y / _zoomFactor);
        int endRow = Math.min(_board.length, (clipBounds.y + clipBounds.height) / _zoomFactor + 1);
        int startCol = Math.max(0, clipBounds.x / _zoomFactor);
        int endCol = Math.min(_board[0].length, (clipBounds.x + clipBounds.width) / _zoomFactor + 1);

        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
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

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return _zoomFactor;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - _zoomFactor;
        } else {
            return visibleRect.height - _zoomFactor;
        }
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
