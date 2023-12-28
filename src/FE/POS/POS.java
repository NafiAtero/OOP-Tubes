package FE.POS;

import BE.JDBC;
import BE.Order;
import BE.OrderProduct;
import BE.OutletProduct;
import FE.Kitchen.Kitchen;
import FE.Manager.Manager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
    //private TableModel menuModel, activeOrdersModel, orderProductsModel;
    public POS(int userId, int companyId, int outletId, String username, String companyName, String outletName) {
        setTitle(String.format("Resto Vision - %s (POS) @ %s - %s", username, outletName, companyName));
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setVisible(true);

        user = new BE.POS(userId, companyId, outletId);
        updateTables();

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
                CancelOrder dialog = new CancelOrder();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        deleteOrderProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOrderProduct dialog = new DeleteOrderProduct();
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrder dialog = new AddOrder();
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
                updateTables();
            }
        });
//endregion

    }

//region TABLES
    private static class MenuModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Price"};
        private static List<OutletProduct> list;
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
        public static void setList(List<OutletProduct> list) {
            MenuModel.list = list;
        }
    }
    private static class ActiveOrdersModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Table Name", "Order Time", "Total"};
        private static List<Order> list;
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
        public static void setList(List<Order> list) {
            ActiveOrdersModel.list = list;
        }
    }
    private static class OrderProductsModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Name", "Price", "Quantity"};
        private static List<OrderProduct> list;
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
        public static void setList(List<OrderProduct> list) {
            OrderProductsModel.list = list;
        }
    }
//endregion


    private void updateTables() {
        user.getActiveOrdersData();
        user.getOutletProductsData();

        MenuModel.setList(user.getOutletProducts());
        MenuModel menuModel = new MenuModel();
        availableProductsTable.setModel(menuModel);

        ActiveOrdersModel.setList(user.getActiveOrders());
        ActiveOrdersModel activeOrdersModel = new ActiveOrdersModel();
        activeOrdersTable.setModel(activeOrdersModel);

        OrderProductsModel.setList(user.getOrderProducts());
        OrderProductsModel orderProductsModel = new OrderProductsModel();
        orderProductsTable.setModel(orderProductsModel);
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
