package FE.Manager;

import BE.JDBC;
import FE.Kitchen.Kitchen;
import FE.Manager.Outlets.*;
import FE.POS.POS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Manager extends JFrame {

//region SWING COMPONENTS
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTextField companyNameTextField;
    private JButton saveButton;
    private JTable usersTable;
    private JButton addNewUserButton;
    private JComboBox compareOutletComboBoxR;
    private JComboBox compareOutletComboBoxL;
    private JTabbedPane tabbedPane3;
    private JTable outletsTable;
    private JButton addOutletButton;
    private JTextField outletNameTextField;
    private JButton saveEditOutletButton;
    private JTable outletProductsTable;
    private JSpinner priceOverrideSpinner;
    private JButton saveEditOutletProductButton;
    private JCheckBox outletProductAvailableCheckBox;
    private JButton deleteOutletProductButton;
    private JButton addOutletProductButton;
    private JTable productsTable;
    private JButton addProductIngredientButton;
    private JTextField productNameTextField;
    private JButton saveEditProductButton;
    private JButton deleteProductButton;
    private JButton deleteOutletButton;
    private JTable outletInventoryTable;
    private JButton addOutletItemButton;
    private JSpinner outletItemLowLimitSpinner;
    private JSpinner outletItemCriticalLimitSpinner;
    private JButton saveEditOutletItemButton;
    private JButton deleteOutletItemButton;
    private JButton addProductButton;
    private JSpinner productBasePriceSpinner;
    private JTable productIngredientsTable;
    private JSpinner ingredientAmountSpinner;
    private JButton saveEditIngredientButton;
    private JButton deleteIngredientButton;
    private JTable rawItemsTable;
    private JButton addRawItemButton;
    private JTextField rawItemNameTextField;
    private JTextField rawItemUnitTextField;
    private JButton saveEditRawItemButton;
    private JButton deleteRawItemButton;
    private JTable perishableItemsTable;
    private JButton addPerishableItemButton;
    private JTextField perishableItemNameTextField;
    private JTextField perishableItemUnitTextField;
    private JButton saveEditPerishableItemButton;
    private JButton deletePerishableItemButton;
    private JTable perishableItemIngredientsTable;
    private JButton addPerishableItemIngredientButton;
    private JSpinner perishableItemIngredientAmountSpinner;
    private JButton saveEditPerishableItemIngredientButton;
    private JButton deletePerishableItemIngredientButton;
    private JButton saveButton1;
    private JButton deleteUserButton;
    private JTextField usernameTextField;
    private JComboBox userOutletComboBox;
    private JComboBox roleComboBox;
    private JButton changePasswordButton;
//endregion

    private final BE.Manager user;
    public Manager(int userId, int companyId, String username, String companyName) {
        setTitle(String.format("Resto Vision - %s (Manager) - %s", username, companyName));
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setVisible(true);
        user = new BE.Manager(userId, companyId);
        addOutletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewOutlet dialog = new NewOutlet();
                dialog.setVisible(true);
            }
        });
        addOutletProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOutletProduct dialog = new AddOutletProduct();
                dialog.setVisible(true);
            }
        });
        deleteOutletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOutlet dialog = new DeleteOutlet();
                dialog.setVisible(true);
            }
        });
        deleteOutletProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOutletProduct dialog = new DeleteOutletProduct();
                dialog.setVisible(true);
            }
        });
        addOutletItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOutletItem dialog = new AddOutletItem();
                dialog.setVisible(true);
            }
        });
        deleteOutletItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOutletItem dialog = new DeleteOutletItem();
                dialog.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        String defaultEmail = "djokallaabadi@gmail.com";

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
