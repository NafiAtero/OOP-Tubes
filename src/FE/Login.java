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
                JDBC.disconnect();
                try {
                    System.out.println(1);
                    if (!rs.getString("password").equals(passwordField.getPassword())) {
                        System.out.println(2);
                        passwordField.setText("");
                        errorLabel.setText("Wrong password");
                    } else {
                        System.out.println(3);
                        if (rs.getString("role").equals("manager")) {
                            System.out.println(4);
                            new Manager();
                        } else if (rs.getString("role").equals("kitchen")) {
                            new Kitchen(0,0,0);
                        } else if (rs.getString("role").equals("pos")) {
                            new POS(0, 0, 0);
                        }
                    }
                } catch (SQLException err) {
                    errorLabel.setText("User not found");
                }
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
