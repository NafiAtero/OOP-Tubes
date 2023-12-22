package BE;

import java.sql.ResultSet;
// todo remove/unremove ingredients on deliver/undeliver
public class KitchenDAO {
    /*
    active_orders
    active_order_product
    outlet_perishable_item
    outlet_raw_item
     */

//region QUERY
    public static ResultSet getActiveOrdersData(int outletId) {
        String sql = "SELECT * FROM active_orders WHERE outlet_id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        JDBC.disconnect();
        return JDBC.rs;
    }
    public static ResultSet getOrderProductsData(int outletId) {
        String sql = "SELECT * FROM outlet_product WHERE id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        JDBC.disconnect();
        return JDBC.rs;
    }
    public static ResultSet getOutletPerishableItemsData(int outletId) {
        String sql = "SELECT * FROM outlet_perishable_item WHERE outlet_id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        JDBC.disconnect();
        return JDBC.rs;
    }
    public static ResultSet getOutletRawItemsData(int outletId) {
        String sql = "SELECT * FROM outlet_raw_item WHERE outlet_id="+outletId;
        JDBC.connect();
        JDBC.query(sql);
        JDBC.disconnect();
        return JDBC.rs;
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
