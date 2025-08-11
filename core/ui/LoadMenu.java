package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import java.awt.List;
import java.io.File;
import java.io.IOException;

import game_of_loife.GofGui;
import game_of_loife.BoardIO;

public class LoadMenu extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private boolean isSelected = false;
    private List savesList = new List();
    private String selected;

    public LoadMenu(GofGui parent) {
        super(parent, true);
        setTitle("Load board");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        SpringLayout sl_contentPanel = new SpringLayout();
        contentPanel.setLayout(sl_contentPanel);

        savesList = new List();
        sl_contentPanel.putConstraint(SpringLayout.NORTH, savesList, 10, SpringLayout.NORTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.WEST, savesList, 10, SpringLayout.WEST, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.SOUTH, savesList, -10, SpringLayout.SOUTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.EAST, savesList, -10, SpringLayout.EAST, contentPanel);
        contentPanel.add(savesList);

        updateSavesList();
        savesList.addItemListener(e -> {
            selected = savesList.getSelectedItem();
            if (selected != null) {
                isSelected = true;
            }
        });

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnLoad = new JButton("Load");
        btnLoad.addActionListener(e -> {
            if (isSelected) {
                try {
                    int[][] loadedBoard = BoardIO.loadBoard(selected);
                    parent.setBoard(loadedBoard);
                    parent.getPnlGame().setBoard(loadedBoard);
                    setVisible(false);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(LoadMenu.this,
                            "Error loading board: " + ex.getMessage(),
                            "Load Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPane.add(btnLoad);
        getRootPane().setDefaultButton(btnLoad);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            parent.setRunning(parent.wasRunning());
            setVisible(false);
        });
        buttonPane.add(btnCancel);
    }

    private void updateSavesList() {
        savesList.removeAll();
        File savesDir = new File(BoardIO.getSaveDir());
        if (savesDir.exists() && savesDir.isDirectory()) {
            File[] files = savesDir.listFiles((dir, name) -> name.endsWith(".gol"));
            if (files != null) {
                for (File file : files) {
                    System.out.println("File");
                    String f = file.getName();
                    System.out.println(f);
                    savesList.add(file.getName().replace(".gol", ""));
                }
            }
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getSelected() {
        return selected;
    }
}
