package FE.Manager.Company;

import BE.User;
import FE.Manager.Manager;

import javax.swing.*;
import java.awt.event.*;

public class DeleteUser extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel label;
    private final Manager parent;
    private final User user;

    public DeleteUser(Manager parent, User user) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Delete User");
        this.parent = parent;
        this.user = user;
        label.setText("Delete "+user.getName()+"?");

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
        parent.getUser().deleteUser(user);
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
