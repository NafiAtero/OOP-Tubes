package FE.Manager.Company;

import BE.Outlet;
import FE.Manager.Manager;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class NewUser extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JComboBox roleComboBox;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JComboBox outletComboBox;
    private final Manager parent;

    public NewUser(Manager parent) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.parent = parent;
        pack();
        setLocationRelativeTo(null);

        roleComboBox.addItem("Manager");
        roleComboBox.addItem("POS");
        roleComboBox.addItem("Kitchen");
        for (Outlet outlet: parent.getUser().getOutlets()) outletComboBox.addItem(outlet);
        outletComboBox.setEnabled(false);

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
        roleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outletComboBox.setEnabled(!roleComboBox.getSelectedItem().equals("Manager"));
            }
        });
    }

    private void onOK() {
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String password = Arrays.toString(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        if (role.equals("Manager")) role = "manager";
        else if (role.equals("POS")) role = "pos";
        else if (role.equals("Kitchen")) role = "kitchen";
        Outlet outlet = (Outlet) outletComboBox.getSelectedItem();
        parent.getUser().addUser(name, email, password, role, outlet);
        parent.updateTables();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
