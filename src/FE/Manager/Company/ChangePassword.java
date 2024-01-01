package FE.Manager.Company;

import BE.User;
import FE.Manager.Manager;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class ChangePassword extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JLabel errorLabel;
    private final Manager parent;
    private final User user;

    public ChangePassword(Manager parent, User user) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Change Password");
        this.parent = parent;
        this.user = user;
        pack();
        setLocationRelativeTo(null);

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
        String oldPassword = Arrays.toString(oldPasswordField.getPassword());
        String newPassword = Arrays.toString(newPasswordField.getPassword());
        if (parent.getUser().validatePassword(user, oldPassword)) {
            parent.getUser().updatePassword(user, newPassword);
            dispose();
        } else {
            errorLabel.setText("Wrong password!");
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
