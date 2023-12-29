package BE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// todo remove/unremove ingredients on deliver/undeliver
public class KitchenDAO {
    /*
    active_orders
    active_order_product
    outlet_perishable_item
    outlet_raw_item
     */

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
    public static List<OrderProduct> getOrderProductsData(Order order, int companyId) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        String sql = "SELECT outlet_product.id AS outlet_product_id, outlet_product.product_id, products.price, outlet_product.price_override, active_order_product.id AS order_product_id, products.name, quantity FROM (outlet_product JOIN products ON outlet_product.product_id=products.id) JOIN active_order_product ON active_order_product.outlet_product_id=outlet_product.id WHERE active_order_id="+order.getOrderId();
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
                orderProducts.add(new OrderProduct(productId, companyId, name, price, orderProductId, order.getOrderId(), outletProductId, quantity, false));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return orderProducts;
    }
    public static List<OutletItem> getOutletPerishableItemsData(int outletId) {
        List<OutletItem> perishableItems = new ArrayList<>();
        String sql = "SELECT perishable_items.id AS perishable_item_id, perishable_items.company_id, outlet_perishable_item.id AS outlet_item_id, perishable_items.name, unit, amount FROM outlet_perishable_item JOIN perishable_items ON outlet_perishable_item.perishable_item_id=perishable_items.id WHERE outlet_id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int perishableItemId = rs.getInt("perishable_item_id");
                int companyId = rs.getInt("company_id");
                int outletItemId = rs.getInt("outlet_item_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                float amount = rs.getFloat("amount");
                perishableItems.add(new OutletItem(perishableItemId, companyId, name, unit, outletItemId, outletId, amount, true));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return perishableItems;
    }
    public static List<OutletItem> getOutletRawItemsData(int outletId) {
        List<OutletItem> rawItems = new ArrayList<>();
        String sql = "SELECT raw_items.id AS raw_item_id, raw_items.company_id, outlet_raw_item.id AS outlet_item_id, raw_items.name, unit, amount FROM outlet_raw_item JOIN raw_items ON outlet_raw_item.raw_item_id=raw_items.id WHERE outlet_id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int rawItemId = rs.getInt("raw_item_id");
                int companyId = rs.getInt("company_id");
                int outletItemId = rs.getInt("outlet_item_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                float amount = rs.getFloat("amount");
                rawItems.add(new OutletItem(rawItemId, companyId, name, unit, outletItemId, outletId, amount, false));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return rawItems;
    }

//endregion

//region UPDATE
    public static void deliverProduct(int orderProductId, int quantity) {
        String sql = String.format("UPDATE active_order_product SET active_order_product=%b WHERE id=%d", true, orderProductId);
        JDBC.connect();
        JDBC.update(sql);
        // remove ingredients
        // SELECT amount FROM outlet_raw_item WHERE id IN product_perishable_ingredient - amount(product_perishable_ingredient) * quantity
        JDBC.disconnect();
    }
    public static void undoDeliverProduct(int orderProductId, int quantity) {
        String sql = String.format("UPDATE active_order_product SET active_order_product=%b WHERE id=%d", false, orderProductId);
        JDBC.connect();
        JDBC.update(sql);
        // unremove ingredients
        // SELECT amount FROM outlet_raw_item WHERE id IN product_perishable_ingredient + amount(product_perishable_ingredient) * quantity
        JDBC.disconnect();
    }
    public static void updateItem(int productId, boolean perishable, float amount) {
        String sql, table;
        if (perishable) table = "outlet_perishable_item";
        else table = "outlet_raw_item";
        sql = String.format("UPDATE %s SET amount=%f WHERE id=%d", table, amount, productId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void clearPerishableItems(int outletId) {
        String sql = String.format("UPDATE outlet_perishable_item SET amount=%f WHERE id=%d", 0.0, outletId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void createPerishableItem(int perishableItemId, float createAmount) {
        // + outlet_perishable_item stock
        // SELECT amount FROM outlet_raw_item WHERE id IN perishable_item_ingredient - amount(perishable_item_ingredients) * createAmount
        String sql = "";
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
//endregion
}
