package game_of_loife;

import javax.swing.*;

import ui.ColorScheme;

import java.awt.*;
import java.awt.event.*;

public class GameBoardPanel extends JPanel implements Scrollable {
    private int[][] _board;
    private boolean painting;
    private boolean isPlayerOne;
    private int _zoomFactor;

    public GameBoardPanel(int[][] board, int zoomFactor) {
        _board = board;
        _zoomFactor = Math.max(1, zoomFactor);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    painting = true;
                    isPlayerOne = true;
                    updateBoard(e, 1);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    painting = true;
                    isPlayerOne = false;
                    updateBoard(e, 2);
                }
            }

            public void mouseReleased(MouseEvent e) {
                painting = false;
            }

            public void mouseDragged(MouseEvent e) {
                if (painting && (e.getButton() == MouseEvent.NOBUTTON)) {
                    updateBoard(e, isPlayerOne ? 1 : 2, true);
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
        setMinimumSize(newSize); // Add minimum size to prevent collapse
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
                if (_board[i][j] > 0) {
                    g.setColor(_board[i][j] == 1 ? ColorScheme.GIRLY_MORE_ALIVE_COLOR : ColorScheme.MORE_ALIVE_COLOR);
                } else {
                    g.setColor(ColorScheme.DEAD_COLOR);
                }
                g.fillRect(x, y, _zoomFactor, _zoomFactor);

                g.setColor(ColorScheme.GRID_COLOR);
                g.drawRect(x, y, _zoomFactor, _zoomFactor);
            }
        }
    }

    public int updateBoard(MouseEvent e, int player) {
        return updateBoard(e, player, false);
    }

    public int updateBoard(MouseEvent e, int player, boolean dragging) {
        int col = e.getX() / _zoomFactor;
        int row = e.getY() / _zoomFactor;

        if (row >= 0 && row < _board.length && col >= 0 && col < _board[0].length) {
            if (player == 1 && _board[row][col] == 1 && !dragging) {
                _board[row][col] = 0;
            } else if (player == 1) {
                _board[row][col] = 1;
            } else if (player == 2 && _board[row][col] == 2 && !dragging) {
                _board[row][col] = 0;
            } else {
                _board[row][col] = 2;
            }
            repaint();
            return 0;
        }
        return 1;
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
