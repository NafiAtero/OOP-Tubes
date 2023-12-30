package FE.Manager;

import BE.*;
import FE.Kitchen.Kitchen;
import FE.Manager.Outlets.*;
import FE.POS.POS;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private JButton refreshTablesButton;
    private JButton refreshTablesButton1;
    private JButton refreshTablesButton2;
    private JButton refreshTablesButton3;
    private JButton refreshTablesButton4;
    private JButton refreshTablesButton5;
//endregion

    private final BE.Manager user;
    private final Manager parent;

//region SELECTED OBJECTS
    private Outlet selectedOutlet;
    private OutletProduct selectedOutletProduct;
    private OutletItem selectedOutletItem;
    private Product selectedProduct;
    private Ingredient selectedProductIngredient;
    private RawItem selectedRawItem;
    private PerishableItem selectedPerishableItem;
    private Ingredient selectedPerishableItemIngredient;
    private User selectedUser;
//endregion
//region MODELS
    private final OutletsModel outletsModel;
    private final OutletProductsModel outletProductsModel;
    private final OutletItemsModel outletItemsModel;
    private final ProductsModel productsModel;
    private final ProductIngredientsModel productIngredientsModel;
    private final RawItemsModel rawItemsModel;
    private final PerishableItemsModel perishableItemsModel;
    private final PerishableItemIngredientsModel perishableItemIngredientsModel;
    private final UsersModel usersModel;
//endregion

    public Manager(int userId, int companyId, String username, String companyName) {
        setTitle(String.format("Resto Vision - %s (Manager) - %s", username, companyName));
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setVisible(true);
        user = new BE.Manager(userId, companyId);
        parent = this;

        user.getUserData();
        user.getOutletData();
        user.getProductData();
        user.getItemData();

        outletsModel = new OutletsModel(user.getOutlets());
        outletsTable.setModel(outletsModel);
        outletProductsModel = new OutletProductsModel(new ArrayList<>());
        outletProductsTable.setModel(outletProductsModel);
        outletItemsModel = new OutletItemsModel(new ArrayList<>());
        outletInventoryTable.setModel(outletItemsModel);
        productsModel = new ProductsModel(user.getProducts());
        productsTable.setModel(productsModel);
        productIngredientsModel = new ProductIngredientsModel(new ArrayList<>());
        productIngredientsTable.setModel(productIngredientsModel);
        rawItemsModel = new RawItemsModel(user.getRawItems());
        rawItemsTable.setModel(rawItemsModel);
        perishableItemsModel = new PerishableItemsModel(user.getPerishableItems());
        perishableItemsTable.setModel(perishableItemsModel);
        perishableItemIngredientsModel = new PerishableItemIngredientsModel(new ArrayList<>());
        perishableItemIngredientsTable.setModel(perishableItemIngredientsModel);
        usersModel = new UsersModel(user.getUsers());
        usersTable.setModel(usersModel);

//region TABLE SELECTION
        // Outlets
        outletsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!outletsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = outletsTable.convertRowIndexToModel(outletsTable.getSelectedRow());
                selectedOutlet = user.getOutlets().get(selectedIndex);
                if (selectedOutlet != null) {
                    outletNameTextField.setText(selectedOutlet.getName());
                    outletProductsModel.setList(selectedOutlet.getOutletProducts());
                    selectedOutletProduct = null;
                    outletProductsModel.fireTableDataChanged();
                    outletItemsModel.setList(selectedOutlet.getOutletItems());
                    selectedOutletItem = null;
                    outletItemsModel.fireTableDataChanged();
                }
            }
        });
        // Outlet Products
        outletProductsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!outletProductsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = outletProductsTable.convertRowIndexToModel(outletProductsTable.getSelectedRow());
                selectedOutletProduct = selectedOutlet.getOutletProducts().get(selectedIndex);
                if (selectedOutletProduct != null) {
                    priceOverrideSpinner.setValue(selectedOutletProduct.getPrice());
                    outletProductAvailableCheckBox.setSelected(selectedOutletProduct.isAvailable());
                } else {
                    priceOverrideSpinner.setValue(0);
                    outletProductAvailableCheckBox.setSelected(false);
                }
            }
        });
        // Outlet Items
        outletInventoryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!outletInventoryTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = outletInventoryTable.convertRowIndexToModel(outletInventoryTable.getSelectedRow());
                selectedOutletItem = selectedOutlet.getOutletItems().get(selectedIndex);
                //if (selectedOutletItem != null) {

                //} else {

                //}
            }
        });
        // Products
        productsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!productsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = productsTable.convertRowIndexToModel(productsTable.getSelectedRow());
                selectedProduct = user.getProducts().get(selectedIndex);
                if (selectedProduct != null) {
                    productNameTextField.setText(selectedProduct.getName());
                    productBasePriceSpinner.setValue(selectedProduct.getPrice());
                    productIngredientsModel.setList(selectedProduct.getIngredients());
                    selectedProductIngredient = null;
                    productIngredientsModel.fireTableDataChanged();
                } else {
                    productNameTextField.setText("");
                    productBasePriceSpinner.setValue(0);
                    productIngredientsModel.setList(new ArrayList<>());
                    selectedProductIngredient = null;
                    productIngredientsModel.fireTableDataChanged();
                }
            }
        });
        // Product Ingredients
        productIngredientsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!productIngredientsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = productIngredientsTable.convertRowIndexToModel(productIngredientsTable.getSelectedRow());
                selectedProductIngredient = selectedProduct.getIngredients().get(selectedIndex);
                if (selectedProductIngredient != null) {
                    ingredientAmountSpinner.setValue(selectedProductIngredient.getAmount());
                } else {
                    ingredientAmountSpinner.setValue(0);
                }
            }
        });
        // Raw Items
        rawItemsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!rawItemsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = rawItemsTable.convertRowIndexToModel(rawItemsTable.getSelectedRow());
                selectedRawItem = user.getRawItems().get(selectedIndex);
                if (selectedRawItem != null) {
                    rawItemNameTextField.setText(selectedRawItem.getName());
                    rawItemUnitTextField.setText(selectedRawItem.getUnit());
                } else {
                    rawItemNameTextField.setText("");
                    rawItemUnitTextField.setText("");
                }
            }
        });
        // Perishable Items
        perishableItemsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!perishableItemsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = perishableItemsTable.convertRowIndexToModel(perishableItemsTable.getSelectedRow());
                selectedPerishableItem = user.getPerishableItems().get(selectedIndex);
                if (selectedPerishableItem != null) {
                    perishableItemNameTextField.setText(selectedPerishableItem.getName());
                    perishableItemUnitTextField.setText(selectedPerishableItem.getUnit());
                    perishableItemIngredientsModel.setList(selectedPerishableItem.getIngredients());
                    selectedOutletItem = null;
                    perishableItemIngredientsModel.fireTableDataChanged();
                } else {
                    perishableItemNameTextField.setText("");
                    perishableItemUnitTextField.setText("");
                    perishableItemIngredientsModel.setList(new ArrayList<>());
                    selectedOutletItem = null;
                    perishableItemIngredientsModel.fireTableDataChanged();
                }
            }
        });
        // Perishable Item Ingredients
        perishableItemIngredientsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!perishableItemIngredientsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = perishableItemIngredientsTable.convertRowIndexToModel(perishableItemIngredientsTable.getSelectedRow());
                selectedPerishableItemIngredient = selectedPerishableItem.getIngredients().get(selectedIndex);
                if (selectedPerishableItemIngredient != null) {
                    perishableItemIngredientAmountSpinner.setValue(selectedPerishableItemIngredient.getAmount());
                } else {
                    perishableItemIngredientAmountSpinner.setValue(0);
                }
            }
        });
        // Users
        usersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!usersTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = usersTable.convertRowIndexToModel(usersTable.getSelectedRow());
                selectedUser = user.getUsers().get(selectedIndex);
                if (selectedUser != null) {
                    usernameTextField.setText(selectedUser.getName());
                } else {
                    usernameTextField.setText("");
                }
            }
        });


//endregion

//region BUTTONS
        addOutletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewOutlet dialog = new NewOutlet(parent);
                dialog.setVisible(true);
            }
        });
        addOutletProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOutletProduct dialog = new AddOutletProduct(parent, selectedOutlet);
                dialog.setVisible(true);
            }
        });
        deleteOutletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedOutlet != null) {
                    DeleteOutlet dialog = new DeleteOutlet(parent, selectedOutlet);
                    dialog.setVisible(true);
                }
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
        refreshTablesButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTables();
            }
        });
        refreshTablesButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTables();
            }
        });
        refreshTablesButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTables();
            }
        });
        refreshTablesButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTables();
            }
        });
        refreshTablesButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTables();
            }
        });
        saveEditOutletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.updateOutlet(selectedOutlet, outletNameTextField.getText());
                updateTables();
            }
        });
//endregion

    }

//region TABLE
    private static class OutletsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name"};
        private List<Outlet> list;
        public OutletsModel(List<Outlet> list) {
            this.list = list;
        }
        public void setList(List<Outlet> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
    private static class OutletProductsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Price", "Available"};
        private List<OutletProduct> list;
        public OutletProductsModel(List<OutletProduct> list) {
            this.list = list;
        }
        public void setList(List<OutletProduct> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else if (columnIndex == 1) return list.get(rowIndex).getPrice();
            else if (columnIndex == 2) return list.get(rowIndex).isAvailable();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
    private static class OutletItemsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Unit", "Perishable"};
        private List<OutletItem> list;
        public OutletItemsModel(List<OutletItem> list) {
            this.list = list;
        }
        public void setList(List<OutletItem> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else if (columnIndex == 1) return list.get(rowIndex).getUnit();
            else if (columnIndex == 2) return list.get(rowIndex).isPerishable();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
    private static class ProductsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Price"};
        private List<Product> list;
        public ProductsModel(List<Product> list) {
            this.list = list;
        }
        public void setList(List<Product> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else if (columnIndex == 1) return list.get(rowIndex).getPrice();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
    private static class ProductIngredientsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Amount", "Unit"};
        private List<Ingredient> list;
        public ProductIngredientsModel(List<Ingredient> list) {
            this.list = list;
        }
        public void setList(List<Ingredient> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else if (columnIndex == 1) return list.get(rowIndex).getAmount();
            else if (columnIndex == 2) return list.get(rowIndex).getUnit();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
    private static class RawItemsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Unit"};
        private List<RawItem> list;
        public RawItemsModel(List<RawItem> list) {
            this.list = list;
        }
        public void setList(List<RawItem> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else if (columnIndex == 1) return list.get(rowIndex).getUnit();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
    private static class PerishableItemsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Unit"};
        private List<PerishableItem> list;
        public PerishableItemsModel(List<PerishableItem> list) {
            this.list = list;
        }
        public void setList(List<PerishableItem> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else if (columnIndex == 1) return list.get(rowIndex).getUnit();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
    private static class PerishableItemIngredientsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Amount", "Unit"};
        private List<Ingredient> list;
        public PerishableItemIngredientsModel(List<Ingredient> list) {
            this.list = list;
        }
        public void setList(List<Ingredient> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else if (columnIndex == 1) return list.get(rowIndex).getAmount();
            else if (columnIndex == 2) return list.get(rowIndex).getUnit();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
    private static class UsersModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Role", "Outlet"};
        private List<User> list;
        public UsersModel(List<User> list) {
            this.list = list;
        }
        public void setList(List<User> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            return list.size();
        }
        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) return list.get(rowIndex).getName();
            else if (columnIndex == 1) return list.get(rowIndex).getRole();
            else if (columnIndex == 2) return list.get(rowIndex).getOutletName();
            else return "-";
        }
        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
//endregion

    public void updateTables() {
        user.getOutletData();
        user.getProductData();
        user.getItemData();
        user.getUserData();
        selectedOutlet = null;
        selectedOutletProduct = null;
        selectedOutletItem = null;
        selectedProduct = null;
        selectedProductIngredient = null;
        selectedRawItem = null;
        selectedPerishableItem = null;
        selectedPerishableItemIngredient = null;
        selectedUser = null;
        outletsModel.fireTableDataChanged();
        outletProductsModel.fireTableDataChanged();
        outletItemsModel.fireTableDataChanged();
        productsModel.fireTableDataChanged();
        productIngredientsModel.fireTableDataChanged();
        rawItemsModel.fireTableDataChanged();
        perishableItemsModel.fireTableDataChanged();
        perishableItemIngredientsModel.fireTableDataChanged();
        usersModel.fireTableDataChanged();
    }


    public BE.Manager getUser() { return user; }
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
