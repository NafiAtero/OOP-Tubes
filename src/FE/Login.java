package FE;

import BE.JDBC;
import FE.Kitchen.Kitchen;
import FE.Manager.Manager;
import FE.POS.POS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
// todo password verification, manager constructor
public class Login extends JFrame {
    private JLabel Title;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel mainPanel;
    private JLabel errorLabel;

    public Login() {
        setTitle("Login Resto Vision");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDBC.connect();
                JDBC.query("SELECT * FROM users WHERE email='" + emailTextField.getText() + "';");
                ResultSet rs = JDBC.rs;
                try {
                    rs.next();
                    if (rs.getString("password").equals(passwordField.getPassword())) {
                        passwordField.setText("");
                        errorLabel.setText("Wrong password");
                    } else {
                        int userId = rs.getInt("id");
                        int companyId = rs.getInt("company_id");
                        String role = rs.getString("role");
                        if (role.equals("manager")) {
                            new Manager();
                        } else {
                            JDBC.query("SELECT * FROM pos_kitchen_outlet WHERE user_id=" + userId);
                            rs = JDBC.rs;
                            int outletId = rs.getInt("outlet_id");
                            if (role.equals("kitchen")) {
                                new Kitchen(userId,companyId,outletId);
                            } else if (role.equals("pos")) {
                                new POS(userId, companyId, outletId);
                            }
                        }
                    }
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                    errorLabel.setText("User not found");
                }
                JDBC.disconnect();
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
