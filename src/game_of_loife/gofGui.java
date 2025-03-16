package game_of_loife;

import javax.swing.SpringLayout;
import javax.swing.JViewport;
import java.awt.Point;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;

import javax.swing.Box;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GofGui extends javax.swing.JFrame {
    int zoomFactor = 10;
    private JTextField tfHeight;
    private JTextField tfWidth;
    private GameBoardPanel pnlGame;
    private int[][] board;
    private boolean isRunning = false;
    private boolean wasRunning = false;

    public GofGui(int[][] board) {
        this.board = board;
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        JScrollPane scrollPane = new JScrollPane();
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -60, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, getContentPane());
        getContentPane().add(scrollPane);

        JPanel pnlMenu = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, pnlMenu, 10, SpringLayout.SOUTH, scrollPane);
        springLayout.putConstraint(SpringLayout.WEST, pnlMenu, 10, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, pnlMenu, -10, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, pnlMenu, -10, SpringLayout.EAST, getContentPane());

        pnlMenu.setBackground(SystemColor.controlDkShadow);
        getContentPane().add(pnlMenu);

        pnlGame = new GameBoardPanel(board, zoomFactor);
        tfHeight = new JTextField();
        tfHeight.setText(String.valueOf(board.length));
        tfHeight.setToolTipText("Height");
        tfHeight.setColumns(10);

        tfWidth = new JTextField();
        tfWidth.setText(String.valueOf(board[0].length));
        tfWidth.setToolTipText("Width");
        tfWidth.setColumns(10);

        JButton btnPlay = new JButton("Play");
        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    GameOfLife.startGameLoop(GofGui.this);
                    btnPlay.setText("Pause");
                } else {
                    GameOfLife.stopGameLoop();
                    btnPlay.setText("Play");
                }
                isRunning = !isRunning;
            }
        });
        pnlMenu.add(btnPlay);

        Component strut_play_dim = Box.createHorizontalStrut(30);
        pnlMenu.add(strut_play_dim);

        pnlMenu.add(tfHeight);
        pnlMenu.add(tfWidth);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> resetBoard(scrollPane));
        pnlMenu.add(btnReset);

        Component strut_dim_save = Box.createHorizontalStrut(60);
        pnlMenu.add(strut_dim_save);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wasRunning = isRunning;
                if (isRunning) {
                    GameOfLife.stopGameLoop();
                    isRunning = false;
                    btnPlay.setText("Play");
                }

                SaveMenu saveMenu = new SaveMenu(GofGui.this);
                saveMenu.setVisible(true);

                if (saveMenu.isConfirmed()) {
                    String filename = saveMenu.getFileName();
                    try {
                        BoardIO.saveBoard(board, filename);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(GofGui.this,
                                "Error saving board: " + ex.getMessage(),
                                "Save Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (wasRunning) {
                    GameOfLife.startGameLoop(GofGui.this);
                    isRunning = true;
                    btnPlay.setText("Pause");
                    wasRunning = false;
                }
            }
        });
        pnlMenu.add(btnSave);

        JButton btnLoad = new JButton("Load");
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wasRunning = isRunning;
                if (isRunning) {
                    GameOfLife.stopGameLoop();
                    isRunning = false;
                    btnPlay.setText("Play");
                }

                LoadMenu loadMenu = new LoadMenu(GofGui.this);
                loadMenu.setVisible(true);

                if (loadMenu.isSelected()) {
                    String filename = loadMenu.getSelected();
                    try {
                        int[][] loadedBoard = BoardIO.loadBoard(filename);
                        GameOfLife.setBoard(loadedBoard);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(GofGui.this,
                                "Error loading board: " + ex.getMessage(),
                                "Load Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        pnlMenu.add(btnLoad);

        Component strut_load_close = Box.createHorizontalStrut(30);
        pnlMenu.add(strut_load_close);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        pnlMenu.add(btnClose);

        pnlGame.setBackground(SystemColor.controlDkShadow);
        scrollPane.setViewportView(pnlGame);
    }

    private void resetBoard(JScrollPane sp) {
        GameOfLife.stopGameLoop();
        isRunning = false;

        try {
            int newHeight = Integer.parseInt(tfHeight.getText());
            int newWidth = Integer.parseInt(tfWidth.getText());

            if (newHeight <= 0 || newWidth <= 0) {
                throw new NumberFormatException("Dimensions must be positive");
            }

            board = new int[newHeight][newWidth];
            pnlGame.setBoard(board);

            JViewport viewport = sp.getViewport();
            viewport.setViewPosition(new Point(0, 0));

        } catch (NumberFormatException ex) {
            tfHeight.setText(String.valueOf(board.length));
            tfWidth.setText(String.valueOf(board[0].length));
        }
    }

    public void setRunning(boolean runningState) {
        this.isRunning = runningState;
    }

    public boolean wasRunning() {
        return wasRunning;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[][] getBoard() {
        return board;
    }

    public GameBoardPanel getPnlGame() {
        return pnlGame;
    }
}
