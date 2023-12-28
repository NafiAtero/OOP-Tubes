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
// todo password verification
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
                JDBC.query("SELECT users.id AS user_id, users.company_id, users.role, users.name AS username, companies.name AS company_name FROM users JOIN companies ON users.company_id=companies.id WHERE email='" + emailTextField.getText() + "';");
                ResultSet rs = JDBC.rs;
                try {
                    rs.next();
                    if (rs.getString("password").equals(passwordField.getPassword())) {
                        passwordField.setText("");
                        errorLabel.setText("Wrong password");
                    } else {
                        int userId = rs.getInt("user_id");
                        int companyId = rs.getInt("company_id");
                        String role = rs.getString("role");
                        String username = rs.getString("username");
                        String companyName = rs.getString("company_name");
                        if (role.equals("manager")) {
                            new Manager(userId, companyId, username, companyName);
                        } else {
                            JDBC.query("SELECT * FROM pos_kitchen_outlet WHERE user_id=" + userId);
                            rs = JDBC.rs;
                            rs.next();
                            int outletId = rs.getInt("outlet_id");
                            JDBC.query("SELECT name FROM outlets WHERE id=" + outletId);
                            rs = JDBC.rs;
                            rs.next();
                            String outletName = rs.getString("name");
                            if (role.equals("kitchen")) {
                                new Kitchen(userId, companyId, outletId, username, companyName, outletName);
                            } else if (role.equals("pos")) {
                                new POS(userId, companyId, outletId, username, companyName, outletName);
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
