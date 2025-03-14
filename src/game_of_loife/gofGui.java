package game_of_loife;

import javax.swing.SpringLayout;
import javax.swing.JViewport;
import java.awt.Point;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Component;

import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GofGui extends javax.swing.JFrame {
    int zoomFactor = 10;
    private JTextField tfHeight;
    private JTextField tfWidth;
    private GameBoardPanel pnlGame;
    private int[][] board;
    private boolean isRunning = false;

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

        Component strut_play_dim = Box.createHorizontalStrut(40);
        pnlMenu.add(strut_play_dim);

        pnlMenu.add(tfHeight);
        pnlMenu.add(tfWidth);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> resetBoard(scrollPane));
        pnlMenu.add(btnReset);

        Component strut_dim_save = Box.createHorizontalStrut(80);
        pnlMenu.add(strut_dim_save);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        pnlMenu.add(btnSave);

        JButton btnLoad = new JButton("Load");
        pnlMenu.add(btnLoad);

        Component strut_load_close = Box.createHorizontalStrut(40);
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

    public int[][] getBoard() {
        return board;
    }

    public GameBoardPanel getPnlGame() {
        return pnlGame;
    }
}
