package FE.Kitchen;

import BE.JDBC;
import FE.Manager.Manager;
import FE.POS.POS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Kitchen extends JFrame {
//region SWING COMPONENTS
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JButton finishOrderButton;
    private JList activeOrdersTable;
    private JTable orderProductsTable;
    private JButton deliverOrderProductButton;
    private JButton undoDeliverOrderProductButton;
    private JTable inventoryTable;
    private JButton addRawItemStockButton;
    private JButton addPerishableItemStockButton;
    private JButton clearPerishableItemStockButton;
    private JSpinner itemStockSpinner;
    private JButton saveEditItemButton;
//endregion
    private final BE.Kitchen user;

    public Kitchen(int userId, int companyId, int outletId, String username, String companyName, String outletName) {
        setTitle(String.format("Resto Vision - %s (Kitchen) @ %s - %s", username, outletName, companyName));
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setVisible(true);
        user = new BE.Kitchen(userId, companyId, outletId);
        finishOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmFinishOrder dialog = new ConfirmFinishOrder();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        addPerishableItemStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPerishableItemStock dialog = new AddPerishableItemStock();
                //dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        clearPerishableItemStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClearPerishableItemStock dialog = new ClearPerishableItemStock();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        String defaultEmail = "djokitchen@gmail.com";

        JDBC.connect();
        JDBC.query("SELECT users.id AS user_id, users.company_id, users.role, users.name AS username, companies.name AS company_name FROM users JOIN companies ON users.company_id=companies.id WHERE email='" + defaultEmail + "';");
        ResultSet rs = JDBC.rs;
        try {
            rs.next();
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

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        JDBC.disconnect();
    }

}
