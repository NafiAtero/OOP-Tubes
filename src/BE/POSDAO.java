package BE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// todo finish order
public class POSDAO {

//region QUERY
    public static List<Order> getActiveOrdersData(int outletId) {
        List<Order> activeOrders = new ArrayList<>();
        String sql = "SELECT * FROM active_orders WHERE outlet_id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int orderId = rs.getInt("id");
                String tableName = rs.getString("table_name");
                Timestamp orderTime = rs.getTimestamp("order_time");
                Order order = new Order(orderId, outletId, tableName, orderTime);
                activeOrders.add(order);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return activeOrders;
    }
    public static List<OutletProduct> getOutletProductsData(int outletId, int companyId) {
        List<OutletProduct> outletProducts = new ArrayList<>();
        String sql = "SELECT outlet_product.id AS outlet_product_id, outlet_product.product_id, products.price, outlet_product.price_override, products.name FROM outlet_product JOIN products ON outlet_product.product_id=products.id WHERE outlet_id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int outletProductId = rs.getInt("outlet_product_id");
                int productId = rs.getInt("product_id");
                int price = rs.getInt("price");
                if (rs.getInt("price_override") > 0) price = rs.getInt("price_override");
                String name = rs.getString("name");
                OutletProduct outletProduct = new OutletProduct(outletProductId, outletId, productId, companyId, name, price);
                outletProducts.add(outletProduct);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return outletProducts;
    }
    public static List<OrderProduct> getOrderProductsData(Order order, int companyId) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        String sql = "SELECT outlet_product.product_id, products.company_id, products.name, products.price, outlet_product.price_override, active_order_product.id AS order_product_id, active_order_product.outlet_product_id, active_order_product.quantity FROM ((active_order_product JOIN outlet_product ON active_order_product.outlet_product_id = outlet_product.id) JOIN products ON outlet_product.product_id = products.id) WHERE active_order_id="+order.getOrderId();
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                if (rs.getInt("price_override") > 0) price = rs.getInt("price_override");
                int orderProductId = rs.getInt("order_product_id");
                int outletProductId = rs.getInt("outlet_product_id");
                int quantity = rs.getInt("quantity");
                order.addItem(productId, companyId, name, price, orderProductId, outletProductId, quantity);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return orderProducts;
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
