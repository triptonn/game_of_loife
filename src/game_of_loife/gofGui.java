package game_of_loife;

import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gofGui extends javax.swing.JFrame {
    int window_height = 800;
    int window_width = 600;

    private JTextField tfHeight;
    private JTextField tfWidth;

    public gofGui() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        JScrollPane scrollPane = new JScrollPane();
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -50, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, getContentPane());
        getContentPane().add(scrollPane);

        JPanel pnlGame = new JPanel();
        pnlGame.setBackground(SystemColor.controlDkShadow);
        scrollPane.setViewportView(pnlGame);

        JPanel pnlMenu = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, pnlMenu, 10, SpringLayout.SOUTH, scrollPane);
        springLayout.putConstraint(SpringLayout.WEST, pnlMenu, 10, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, pnlMenu, -10, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, pnlMenu, -10, SpringLayout.EAST, getContentPane());
        pnlMenu.setBackground(SystemColor.controlDkShadow);
        getContentPane().add(pnlMenu);

        JButton btnPlay = new JButton("Play");
        pnlMenu.add(btnPlay);

        Component strut_play_dim = Box.createHorizontalStrut(40);
        pnlMenu.add(strut_play_dim);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        pnlMenu.add(btnReset);

        tfHeight = new JTextField();
        tfHeight.setToolTipText("Height");
        pnlMenu.add(tfHeight);
        tfHeight.setColumns(10);

        tfWidth = new JTextField();
        tfWidth.setToolTipText("Width");
        pnlMenu.add(tfWidth);
        tfWidth.setColumns(10);

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
    }
}
