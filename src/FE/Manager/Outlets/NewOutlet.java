package FE.Manager.Outlets;

import FE.Manager.Manager;

import javax.swing.*;
import java.awt.event.*;

public class NewOutlet extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField outletNameTextField;
    private JCheckBox addAllProductsCheckBox;
    private Manager parent;

    public NewOutlet(Manager parent) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("New outlet");
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);
        this.parent = parent;
        addAllProductsCheckBox.setSelected(true);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        parent.getUser().addOutlet(outletNameTextField.getText(), addAllProductsCheckBox.isSelected(), true);
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        NewOutlet dialog = new NewOutlet();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
