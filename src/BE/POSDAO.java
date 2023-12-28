package BE;

import java.sql.ResultSet;
// todo finish order
public class POSDAO {

//region QUERY
    public static ResultSet getActiveOrdersData(int outletId) {
        String sql = "SELECT * FROM active_orders WHERE outlet_id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        JDBC.disconnect();
        return JDBC.rs;
    }
    public static ResultSet getOutletProductsData(int outletId) {
        String sql = "SELECT * FROM outlet_product WHERE outlet_id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        JDBC.disconnect();
        return JDBC.rs;
    }
    public static ResultSet getOrderProductsData(int orderId) {
        String sql = "SELECT outlet_product.product_id, products.company_id, products.name, products.price, outlet_product.price_override, active_order_product.id, active_order_product.outlet_product_id, active_order_product.quantity FROM ((active_order_product JOIN outlet_product ON active_order_product.outlet_product_id = outlet_product.id) JOIN products ON outlet_product.product_id = products.id) WHERE active_order_id="+orderId;
        JDBC.connect();
        JDBC.query(sql);
        JDBC.disconnect();
        return JDBC.rs;
    }
//endregion

//region UPDATE
    public static void addOrder(int outletId, String tableName) {
        String sql = String.format("INSERT INTO active_orders (outlet_id, table_name) VALUES (%d , '%s')", outletId, tableName);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void updateOrder(int orderId, String tableName) {
        String sql = String.format("UPDATE active_orders SET table_name='%s' WHERE id=%d", tableName, orderId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void finishOrder(int orderId) {}
    public static void deleteOrder(int orderId) {
        String sql = String.format("DELETE FROM active_orders WHERE id=%d", orderId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void addOrderProduct(int orderId, int outletProductId) {
        String sql = String.format("INSERT INTO active_order_product (active_order_id, outlet_product_id , quantity) VALUES (%d, %d, %d)",
                orderId, outletProductId, 0);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void updateOrderProduct(int orderProductId, int quantity) {
        String sql = String.format("UPDATE active_order_product SET quantity=%d WHERE id=%d", quantity, orderProductId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void deleteOrderProduct(int orderProductId) {
//endregion

//region active_order_product
        String sql = String.format("DELETE FROM active_order_product WHERE id=%d", orderProductId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
//endregion
}
