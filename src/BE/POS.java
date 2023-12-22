package BE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// todo DAO join table query
public class POS extends User {
    private final int outletId;
    private List<Order> activeOrders;
    private List<OutletProduct> outletProducts;
    private List<OrderProduct> orderProducts;
    public POS(int userId, int companyId, int outletId) {
        super(userId, companyId);
        this.outletId = outletId;
    }

//region SETTER GETTER
    public int getOutletId() {
        return outletId;
    }
    public List<Order> getActiveOrders() {
        return activeOrders;
    }
    public void setActiveOrders(List<Order> activeOrders) {
        this.activeOrders = activeOrders;
    }
    public List<OutletProduct> getOutletProducts() {
        return outletProducts;
    }
    public void setOutletProducts(List<OutletProduct> outletProducts) {
        this.outletProducts = outletProducts;
    }
    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }
    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
//endregion

//region READ
    public void getActiveOrdersData() {
        activeOrders.clear();
        ResultSet rs = POSDAO.getActiveOrdersData(outletId);
        try {
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                Order order = new Order();
                activeOrders.add(order);
                //getOrderProductsData(orderId);
                //order.setOrderProducts(orderProducts);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
    }
    public void getOutletProductsData() {
        outletProducts.clear();
        ResultSet rs = POSDAO.getOutletProductsData(outletId);
        try {
            while (rs.next()) {
                int outletProductId = rs.getInt("product_id");
                int productId = rs.getInt("product_id");
                OutletProduct outletProduct = new OutletProduct(outletProductId, outletId, productId, companyId, "name", 10000);
                outletProducts.add(outletProduct);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
    }
    public void getOrderProductsData(int orderId) {
        orderProducts.clear();
        ResultSet rs = POSDAO.getOrderProductsData(orderId);
        try {
            while (rs.next()) {
                int outletProductId = rs.getInt("product_id");
                int productId = rs.getInt("product_id");
                //orderProducts.add(new OrderProduct(productId, companyId, "name", 10000, ));
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
    }
//endregion

//region CREATE
    /**
     * Create on active_orders
     * @param tableName
     */
    public void addOrder(String tableName) {
        POSDAO.addOrder(outletId, tableName);
        getActiveOrdersData();
    }
    /**
     * Create on active_order_product
     * @param orderId
     * @param productId
     */
    public void addOrderProduct(int orderId, int productId) {
        // todo DAO add to active_order_product
        getOrderProductsData();
    }
//endregion

//region UPDATE
    /**
     * Edit on active_orders
     * @param orderId
     * @param tableName new table name
     */
    public void updateOrder(int orderId, String tableName) {
        POSDAO.updateOrder(orderId, tableName);
        getActiveOrdersData();
    }
    /**
     * Update active_order_product on edit
     * @param orderProductId
     * @param quantity new quantity
     */
    public void updateOrderProduct(int orderProductId, int quantity) {
        POSDAO.updateOrderProduct(orderProductId, quantity);
        getOrderProductsData();
    }
    /**
     * Moves data from active_order to finished_order
     * @param orderId
     */
    public void finishOrder(int orderId) {
        POSDAO.finishOrder(orderId);
        getActiveOrdersData();
    }
//endregion

//region DELETE
    /**
     * Deletes from active_orders
     * @param orderId
     */
    public void deleteOrder(int orderId) {
        POSDAO.deleteOrder(orderId);
        getActiveOrdersData();
    }
    /**
     * Deletes from active_order_product
     * @param orderProductId
     */
    public void deleteOrderProduct(int orderProductId) {
        POSDAO.deleteOrderProduct(orderProductId);
        getOrderProductsData();
    }
//endregion
}
