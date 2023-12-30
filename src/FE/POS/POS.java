package FE.POS;

import BE.*;
import FE.Kitchen.Kitchen;
import FE.Manager.Manager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// todo move dialog pack setLocationRelativeTo
public class POS extends JFrame {

//region SWING COMPONENTS
    private JPanel mainPanel;
    private JButton finishOrderButton;
    private JButton saveEditOrderButton;
    private JButton addProductToOrderButton;
    private JTable activeOrdersTable;
    private JButton addOrderButton;
    private JTable orderProductsTable;
    private JTable availableProductsTable;
    private JButton deleteOrderButton;
    private JTextField orderTableNameTextField;
    private JButton deleteOrderProductButton;
    private JButton saveEditOrderProductButton;
    private JSpinner orderProductQuantitySpinner;
    private JButton refreshListButton;
//endregion

    private final BE.POS user;
    private final POS parent = this;
    private OutletProduct selectedProduct;
    private Order selectedOrder;
    private OrderProduct selectedOrderProduct;
    private final MenuModel menuModel;
    private final ActiveOrdersModel activeOrdersModel;
    private final OrderProductsModel orderProductsModel;

    public POS(int userId, int companyId, int outletId, String username, String companyName, String outletName) {
        setTitle(String.format("Resto Vision - %s (POS) @ %s - %s", username, outletName, companyName));
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setVisible(true);

        user = new BE.POS(userId, companyId, outletId);
        user.getActiveOrdersData();
        user.getOutletProductsData();

        menuModel = new MenuModel(user.getOutletProducts());
        availableProductsTable.setModel(menuModel);

        activeOrdersModel = new ActiveOrdersModel(user.getActiveOrders());
        activeOrdersTable.setModel(activeOrdersModel);

        orderProductsModel = new OrderProductsModel(new ArrayList<>());
        orderProductsTable.setModel(orderProductsModel);

        updateTables();


//region BUTTONS
        finishOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmFinishOrder dialog = new ConfirmFinishOrder(parent, selectedOrder);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CancelOrder dialog = new CancelOrder(parent, selectedOrder);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        deleteOrderProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOrderProduct dialog = new DeleteOrderProduct(parent, selectedOrderProduct);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrder dialog = new AddOrder(parent);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        refreshListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTables();
            }
        });
        addProductToOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedOrder != null && selectedProduct != null) {
                    user.addOrderProduct(selectedOrder, selectedProduct);
                    updateTables();
                }
            }
        });
        saveEditOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedOrder != null) {
                    user.updateOrder(selectedOrder, orderTableNameTextField.getText());
                    updateTables();
                }
            }
        });
        saveEditOrderProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedOrder != null) {
                    user.updateOrderProduct(selectedOrderProduct, (Integer) orderProductQuantitySpinner.getValue());
                    updateTables();
                }
            }
        });
//endregion

//region TABLE SELECTION
        // Available Products
        availableProductsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!activeOrdersTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = availableProductsTable.convertRowIndexToModel(availableProductsTable.getSelectedRow());
                if (selectedIndex != -1) {
                    selectedProduct = user.getOutletProducts().get(selectedIndex);
                }
                if (selectedProduct != null) {
                    addProductToOrderButton.setEnabled(true);
                    addProductToOrderButton.setText("Add "+selectedProduct.getName()+" To Order");
                    if (selectedOrder != null) addProductToOrderButton.setEnabled(true);
                } else {
                    addProductToOrderButton.setEnabled(false);
                    addProductToOrderButton.setText("Add Product To Order");
                }
            }
        });
        // Active Orders
        activeOrdersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!activeOrdersTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = activeOrdersTable.convertRowIndexToModel(activeOrdersTable.getSelectedRow());
                selectedOrder = user.getActiveOrders().get(selectedIndex);
                if (selectedOrder != null) {
                    orderTableNameTextField.setText(selectedOrder.getTableName());
                    orderProductsModel.setList(selectedOrder.getOrderProducts());
                    saveEditOrderButton.setEnabled(true);
                    finishOrderButton.setEnabled(true);
                    deleteOrderButton.setEnabled(true);
                    if (addProductToOrderButton.getText().equals("Add Product To Order")) addProductToOrderButton.setEnabled(true);
                } else {
                    orderTableNameTextField.setText("");
                    saveEditOrderButton.setEnabled(false);
                    finishOrderButton.setEnabled(false);
                    deleteOrderButton.setEnabled(false);
                }
                orderProductQuantitySpinner.setValue(0);
                saveEditOrderProductButton.setEnabled(false);
                deleteOrderProductButton.setEnabled(false);
                selectedOrderProduct = null;
                orderProductsModel.fireTableDataChanged();
            }
        });
        // Order Products
        orderProductsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!orderProductsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = orderProductsTable.convertRowIndexToModel(orderProductsTable.getSelectedRow());
                selectedOrderProduct = selectedOrder.getOrderProducts().get(selectedIndex);
                if (selectedOrderProduct != null) {
                    orderProductQuantitySpinner.setValue(selectedOrderProduct.getQuantity());
                    saveEditOrderProductButton.setEnabled(true);
                    deleteOrderProductButton.setEnabled(true);
                } else {
                    orderProductQuantitySpinner.setValue(0);
                    saveEditOrderProductButton.setEnabled(false);
                    deleteOrderProductButton.setEnabled(false);
                }
            }
        });
//endregion

    }

//region TABLES
    private static class MenuModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Price"};
        private List<OutletProduct> list;
        public MenuModel(List<OutletProduct> list) {
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
    private static class ActiveOrdersModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Table Name", "Order Time", "Total"};
        private List<Order> list;
        public ActiveOrdersModel(List<Order> list) {
            this.list = list;
        }
        public void setList(List<Order> list) {
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
            if (columnIndex == 0) return list.get(rowIndex).getTableName();
            else if (columnIndex == 1) return list.get(rowIndex).getOrderTime();
            else if (columnIndex == 2) return list.get(rowIndex).getTotal();
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
    private static class OrderProductsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Price", "Quantity", "Delivered"};
        private List<OrderProduct> list;
        public OrderProductsModel(List<OrderProduct> list) {
            this.list = list;
        }
        public void setList(List<OrderProduct> list) {
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
            else if (columnIndex == 2) return list.get(rowIndex).getQuantity();
            else if (columnIndex == 3) return list.get(rowIndex).isDelivered();
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
        user.getActiveOrdersData();
        user.getOutletProductsData();
        selectedProduct = null;
        selectedOrder = null;
        selectedOrderProduct = null;

        menuModel.fireTableDataChanged();
        activeOrdersModel.fireTableDataChanged();
        orderProductsModel.setList(new ArrayList<>());
        orderProductsModel.fireTableDataChanged();

        addProductToOrderButton.setEnabled(false);

        orderTableNameTextField.setText("");
        saveEditOrderButton.setEnabled(false);
        finishOrderButton.setEnabled(false);
        deleteOrderButton.setEnabled(false);

        orderProductQuantitySpinner.setValue(0);
        saveEditOrderProductButton.setEnabled(false);
        deleteOrderProductButton.setEnabled(false);
    }
    public BE.POS getUser() { return user; }

    public static void main(String[] args) {
        String defaultEmail = "djopos@gmail.com";

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
