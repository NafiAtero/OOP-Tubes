package FE.Kitchen;

import BE.*;
import FE.Manager.Manager;
import FE.POS.POS;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Kitchen extends JFrame {
//region SWING COMPONENTS
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JButton finishOrderButton;
    private JTable orderProductsTable;
    private JButton deliverOrderProductButton;
    private JButton undoDeliverOrderProductButton;
    private JTable inventoryTable;
    private JButton addRawItemStockButton;
    private JButton addPerishableItemStockButton;
    private JButton clearPerishableItemStockButton;
    private JSpinner itemStockSpinner;
    private JButton saveEditItemButton;
    private JButton refreshListButton;
    private JButton refreshListButton1;
    private JTable activeOrdersTable;
    //endregion
    private final BE.Kitchen user;
    private final Kitchen parent = this;
    private Order selectedOrder;
    private OrderProduct selectedOrderProduct;
    private OutletItem selectedOutletItem;
    private int selectedOrderIndex, selectedOrderProductIndex, selectedOutletItemIndex;
    private ActiveOrdersModel activeOrdersModel;
    private OrderProductsModel orderProductsModel;
    private OutletItemsModel outletItemsModel;

    public Kitchen(int userId, int companyId, int outletId, String username, String companyName, String outletName) {
        setTitle(String.format("Resto Vision - %s (Kitchen) @ %s - %s", username, outletName, companyName));
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setVisible(true);
        user = new BE.Kitchen(userId, companyId, outletId);
        user.getActiveOrdersData();
        user.getOutletItemsData();

        activeOrdersModel = new ActiveOrdersModel(user.getActiveOrders());
        activeOrdersTable.setModel(activeOrdersModel);
        orderProductsModel = new OrderProductsModel(user.getActiveOrders().get(0).getOrderProducts());
        orderProductsTable.setModel(orderProductsModel);
        outletItemsModel = new OutletItemsModel(user.getOutletItems());
        inventoryTable.setModel(outletItemsModel);

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
//endregion

//region TABLE SELECTION

        activeOrdersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!activeOrdersTable.getSelectionModel().isSelectionEmpty()) {
                selectedOrderIndex = activeOrdersTable.convertRowIndexToModel(activeOrdersTable.getSelectedRow());
                selectedOrder = user.getActiveOrders().get(selectedOrderIndex);
                if (selectedOrder != null) {
                    orderProductsModel.setList(selectedOrder.getOrderProducts());
                    orderProductsModel.fireTableDataChanged();
                }
            }
        });
        orderProductsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!orderProductsTable.getSelectionModel().isSelectionEmpty()) {
                selectedOrderProductIndex = orderProductsTable.convertRowIndexToModel(orderProductsTable.getSelectedRow());
                selectedOrderProduct = selectedOrder.getOrderProducts().get(selectedOrderProductIndex);
                if (selectedOrderProduct != null) {
                    //orderProductQuantitySpinner.setValue(selectedOrderProduct.getQuantity());
                }
            }
        });


//endregion
    }

//region TABLE

    private static class ActiveOrdersModel extends AbstractTableModel {
        private final String[] COLUMNS = {"Table Name", "Order Time"};
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
        private final String[] COLUMNS = {"Name", "Quantity", "Delivered"};
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
            else if (columnIndex == 1) return list.get(rowIndex).getQuantity();
            else if (columnIndex == 2) return list.get(rowIndex).isDelivered();
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
        private final String[] COLUMNS = {"Name", "Stock", "Unit", "Perishable"};
        private List<OutletItem> list;
        public OutletItemsModel(List<OutletItem> list) {
            this.list = list;
        }
        public void setList(List<OutletItem> list) {
            this.list = list;
        }
        @Override
        public int getRowCount() {
            if (list == null)  return 0;
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
            else if (columnIndex == 3) return list.get(rowIndex).isPerishable();
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


    public void updateTables() {
        user.getActiveOrdersData();
        activeOrdersModel.setList(user.getActiveOrders());
        activeOrdersModel.fireTableDataChanged();

        user.getOutletItemsData();
        outletItemsModel.setList(user.getOutletItems());
        outletItemsModel.fireTableDataChanged();

    }
//endregion
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
