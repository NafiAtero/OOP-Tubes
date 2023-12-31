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
        String sql = "SELECT outlet_product.id AS outlet_product_id, outlet_product.product_id, products.price, outlet_product.price_override, active_order_product.id AS order_product_id, products.name, quantity, delivered FROM (outlet_product JOIN products ON outlet_product.product_id=products.id) JOIN active_order_product ON active_order_product.outlet_product_id=outlet_product.id WHERE active_order_id="+order.getOrderId();
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
                boolean delivered = rs.getBoolean("delivered");
                //order.addItem(productId, companyId, name, price, orderProductId, outletProductId, quantity);
                orderProducts.add(new OrderProduct(productId, companyId, name, price, orderProductId, order.getOrderId(), outletProductId, quantity, delivered));
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
    public static void deliverProduct(OrderProduct orderProduct) {
        String sql = String.format("UPDATE active_order_product SET delivered=%b WHERE id=%d", true, orderProduct.getOrderProductId());
        JDBC.connect();
        JDBC.update(sql);
        JDBC.update("UPDATE outlet_raw_item JOIN product_raw_ingredient ON outlet_raw_item.raw_item_id=product_raw_ingredient.raw_item_id SET outlet_raw_item.amount=outlet_raw_item.amount-(product_raw_ingredient.amount*"+orderProduct.getQuantity()+") WHERE product_raw_ingredient.product_id="+orderProduct.getProductId());
        JDBC.update("UPDATE outlet_perishable_item JOIN product_perishable_ingredient ON outlet_perishable_item.perishable_item_id=product_perishable_ingredient.perishable_item_id SET outlet_perishable_item.amount=outlet_perishable_item.amount-(product_perishable_ingredient.amount*"+orderProduct.getQuantity()+") WHERE product_perishable_ingredient.product_id="+orderProduct.getProductId());
        JDBC.disconnect();
    }
    public static int addCompletedOrder(int companyId, int outletId, String tableName) {
        String sql = String.format("INSERT INTO completed_orders (company_id , outlet_id , table_name, order_completed_time) VALUES (%d , %d ,'%s', CURRENT_TIMESTAMP)", companyId, outletId, tableName);
        int completedOrderId;
        JDBC.connect();
        JDBC.update(sql);
        JDBC.query("SELECT LAST_INSERT_ID()");
        ResultSet rs = JDBC.rs;
        try {
            rs.next();
            completedOrderId = rs.getInt("LAST_INSERT_ID()");
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return completedOrderId;
    }
    public static void addCompletedOrderProduct(int completedOrderId, String name, int price, int quantity) {
        String sql = String.format("INSERT INTO completed_order_product (completed_order_id  , product_name , price, quantity) VALUES (%d , '%s' ,%d, %d)", completedOrderId, name, price, quantity);
        JDBC.connect();
        JDBC.update(sql);
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
        String sql = String.format("UPDATE outlet_perishable_item SET amount=%f WHERE outlet_id=%d", 0.0, outletId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void createPerishableItem(int perishableItemId, float createAmount) {
        String sql = String.format("UPDATE outlet_perishable_item JOIN (outlet_raw_item JOIN perishable_item_ingredient ON outlet_raw_item.raw_item_id=perishable_item_ingredient.raw_item_id ) ON perishable_item_ingredient.perishable_item_id=outlet_perishable_item.perishable_item_id SET outlet_raw_item.amount=outlet_raw_item.amount-(perishable_item_ingredient.amount*%f), outlet_perishable_item.amount=outlet_perishable_item.amount+(perishable_item_ingredient.amount*%f) WHERE perishable_item_ingredient.perishable_item_id=%d",createAmount,createAmount,perishableItemId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
//endregion
}
