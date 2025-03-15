package game_of_loife;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.List;
import java.io.File;

public class SaveMenu extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;

    private boolean confirmed = false;
    private List savesList;

    public SaveMenu(GofGui parent) {
        super(parent, true);
        setTitle("Save Board");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        SpringLayout sl_contentPanel = new SpringLayout();
        contentPanel.setLayout(sl_contentPanel);
        textField = new JTextField();

        sl_contentPanel.putConstraint(SpringLayout.NORTH, textField, 10, SpringLayout.NORTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.EAST, textField, -10, SpringLayout.EAST, contentPanel);
        textField.setToolTipText("Enter the name of your board...");
        contentPanel.add(textField);
        textField.setColumns(10);

        savesList = new List();
        sl_contentPanel.putConstraint(SpringLayout.NORTH, savesList, 10, SpringLayout.SOUTH, textField);
        sl_contentPanel.putConstraint(SpringLayout.WEST, savesList, 10, SpringLayout.WEST, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.SOUTH, savesList, -10, SpringLayout.SOUTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.EAST, savesList, -10, SpringLayout.EAST, contentPanel);
        contentPanel.add(savesList);

        updateSavesList();

        savesList.addItemListener(e -> textField.setText(savesList.getSelectedItem()));

        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> {
            if (!textField.getText().trim().isEmpty()) {
                confirmed = true;
                setVisible(false);
            }
        });
        pnlButtons.add(btnSave);
        getRootPane().setDefaultButton(btnSave);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            confirmed = false;
            setVisible(false);
        });
        pnlButtons.add(btnCancel);

        setLocationRelativeTo(parent);
    }

    private void updateSavesList() {
        savesList.removeAll();
        File savesDir = new File(BoardIO.getSaveDir());
        if (savesDir.exists() && savesDir.isDirectory()) {
            File[] files = savesDir.listFiles((dir, name) -> name.endsWith(".gol"));
            if (files != null) {
                for (File file : files) {
                    savesList.add(file.getName().replace(".gol", ""));
                }
            }
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getFileName() {
        return textField.getText().trim();
    }
}
