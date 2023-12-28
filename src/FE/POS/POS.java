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
    private int selectedProductIndex, selectedOrderIndex, selectedOrderProductIndex;
    private MenuModel menuModel;
    private ActiveOrdersModel activeOrdersModel;
    private OrderProductsModel orderProductsModel;

    public POS(int userId, int companyId, int outletId, String username, String companyName, String outletName) {
        setTitle(String.format("Resto Vision - %s (POS) @ %s - %s", username, outletName, companyName));
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setVisible(true);

        user = new BE.POS(userId, companyId, outletId);
        //updateTables();
        user.getActiveOrdersData();
        user.getOutletProductsData();

        menuModel = new MenuModel(user.getOutletProducts());
        availableProductsTable.setModel(menuModel);

        activeOrdersModel = new ActiveOrdersModel(user.getActiveOrders());
        activeOrdersTable.setModel(activeOrdersModel);

        orderProductsModel = new OrderProductsModel(user.getOrderProducts());
        orderProductsTable.setModel(orderProductsModel);


//region BUTTON
        finishOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmFinishOrder dialog = new ConfirmFinishOrder();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CancelOrder dialog = new CancelOrder(parent);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        deleteOrderProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOrderProduct dialog = new DeleteOrderProduct(parent);
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
        saveEditOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //user.updateOrder(156744, orderTableNameTextField.getText())
            }
        });
        refreshListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTables();
            }
        });
        addProductToOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedOrder != null && selectedProduct != null) {
                    user.addOrderProduct(selectedOrder.getOrderId(), selectedProduct.getOutletProductId());
                    //selectedProductIndex = 0;
                    updateLists();
                }
            }
        });
        saveEditOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.updateOrder(selectedOrder.getOrderId(), orderTableNameTextField.getText());
                updateLists();
            }
        });
        saveEditOrderProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.updateOrderProduct(selectedOrderProduct.getOrderProductId(), (Integer) orderProductQuantitySpinner.getValue());
            }
        });

//endregion

//region TABLE SELECTION
        availableProductsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!activeOrdersTable.getSelectionModel().isSelectionEmpty()) {
                selectedProductIndex = availableProductsTable.convertRowIndexToModel(availableProductsTable.getSelectedRow());
                selectedProduct = user.getOutletProducts().get(selectedProductIndex);
                if (selectedProduct != null) {
                    addProductToOrderButton.setEnabled(true);
                    addProductToOrderButton.setText("Add "+selectedProduct.getName()+" To Order");
                } else {
                    addProductToOrderButton.setEnabled(false);
                    addProductToOrderButton.setText("Add Product To Order");
                    selectedProduct = null;
                }
            }
        });
        activeOrdersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!activeOrdersTable.getSelectionModel().isSelectionEmpty()) {
                selectedOrderIndex = activeOrdersTable.convertRowIndexToModel(activeOrdersTable.getSelectedRow());
                selectedOrder = user.getActiveOrders().get(selectedOrderIndex);
                selectedOrderProductIndex = -1;
                selectedOrderProduct = null;
                if (selectedOrder != null) {
                    orderTableNameTextField.setText(selectedOrder.getTableName());

                    orderProductsModel = new OrderProductsModel(selectedOrder.getOrderProducts());
                    orderProductsTable.setModel(orderProductsModel);
                }
            }
        });
        orderProductsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!orderProductsTable.getSelectionModel().isSelectionEmpty()) {
                selectedOrderProductIndex = orderProductsTable.convertRowIndexToModel(orderProductsTable.getSelectedRow());
                selectedOrderProduct = selectedOrder.getOrderProducts().get(selectedOrderProductIndex);
                if (selectedOrderProduct != null) {
                    orderProductQuantitySpinner.setValue(selectedOrderProduct.getQuantity());
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
        private final String[] COLUMNS = {"Name", "Price", "Quantity"};
        private List<OrderProduct> list;
        public OrderProductsModel(List<OrderProduct> list) {
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
    public void resetProductSelection() {
        selectedProduct = null;
        selectedProductIndex = -1;
    }
    public void resetOrderSelection() {
        selectedOrder = null;
        selectedOrderIndex = -1;
    }
    public void resetOrderProductSelection() {
        selectedOrderProduct = null;
        selectedOrderProductIndex = -1;
    }
//endregion


    private void updateLists() {
        user.getActiveOrdersData();
        user.getOutletProductsData();
        resetProductSelection();
        resetOrderSelection();
        resetOrderProductSelection();
        menuModel.fireTableDataChanged();
        activeOrdersModel.fireTableDataChanged();
        orderProductsModel.fireTableDataChanged();

        System.out.println("prduct: " + selectedProductIndex + "/" + user.getOutletProducts().size());
        System.out.println("aorder: " + selectedOrderIndex + "/" + user.getActiveOrders().size());
        System.out.println("ordpro: " + selectedOrderProductIndex + "/" + user.getOrderProducts().size());
    }
    public void refreshTables() {
        updateLists();

        menuModel = new MenuModel(user.getOutletProducts());
        availableProductsTable.setModel(menuModel);

        activeOrdersModel = new ActiveOrdersModel(user.getActiveOrders());
        activeOrdersTable.setModel(activeOrdersModel);

        orderProductsModel = new OrderProductsModel(selectedOrder.getOrderProducts());
        orderProductsTable.setModel(orderProductsModel);
    }
    private void changeSelectedOrder() {

    }

    public BE.POS getUser() { return user; }
    public Order getSelectedOrder() { return selectedOrder; }

    public OrderProduct getSelectedOrderProduct() { return selectedOrderProduct; }

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
