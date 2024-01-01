package BE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
// TODO UPDATE ITEMS
public class ManagerDAO {

//region QUERY
//region REPORT
    public static List<CompletedOrder> getCompletedOrderData(int companyId) {
        List<CompletedOrder> orders = new ArrayList<>();
        String sql = "SELECT ord.id AS order_id, ord.outlet_id, ord.table_name, ord.order_completed_time, ord.company_id, otl.name FROM completed_orders ord JOIN outlets otl ON ord.outlet_id=otl.id WHERE ord.company_id="+companyId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int outletId = rs.getInt("outlet_id");
                String tableName = rs.getString("table_name");
                Timestamp orderTime = rs.getTimestamp("order_completed_time");
                //int companyId = rs.getInt("company_id");
                String outletName = rs.getString("name");
                CompletedOrder order = new CompletedOrder(orderId, outletId, tableName, orderTime, companyId, outletName);
                orders.add(order);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return orders;
    }
    public static List<CompletedOrderProduct> getCompletedOrderProductData(int completedOrderId) {
        List<CompletedOrderProduct> orders = new ArrayList<>();
        String sql = "SELECT * FROM completed_order_product WHERE completed_order_id="+completedOrderId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int completedOrderProductId = rs.getInt("id");
                //int completedOrderId = rs.getInt("");
                String productName = rs.getString("product_name");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                CompletedOrderProduct order = new CompletedOrderProduct(completedOrderProductId, completedOrderId, productName, price, quantity);
                orders.add(order);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return orders;
    }
//endregion
//region OUTLETS
    public static List<Outlet> getOutletData(int companyId) {
        List<Outlet> outlets = new ArrayList<>();
        String sql = "SELECT * FROM outlets WHERE company_id="+companyId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int outletId = rs.getInt("id");
                //int companyId = rs.getInt("company_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                Outlet outlet = new Outlet(outletId, companyId, name, address);
                outlets.add(outlet);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return outlets;
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
                int priceOverride = rs.getInt("price_override");
                String name = rs.getString("name");
                OutletProduct outletProduct = new OutletProduct(outletProductId, outletId, productId, companyId, name, price, priceOverride);
                outletProducts.add(outletProduct);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return outletProducts;
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
//region PRODUCTS
    public static List<Product> getProductData(int companyId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int productId = rs.getInt("id");
                //companyId
                String name = rs.getString("name");
                int price = rs.getInt("price");
                products.add(new Product(productId, companyId, name, price));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return products;
    }
    public static List<Ingredient> getProductRawIngredientData(int productId) {
        List<Ingredient> rawItems = new ArrayList<>();
        String sql = "SELECT it.id AS item_id, it.company_id, it.name, it.unit, ing.id AS ingredient_id, ing.product_id, ing.amount FROM product_raw_ingredient ing JOIN raw_items it ON ing.raw_item_id=it.id WHERE product_id="+productId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int companyId = rs.getInt("company_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                int ingredientId = rs.getInt("ingredient_id");
                //int productId = rs.getInt("product_id");
                float amount = rs.getFloat("amount");
                rawItems.add(new Ingredient(itemId, companyId, name, unit, ingredientId, productId, amount, false));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return rawItems;
    }
    public static List<Ingredient> getProductPerishableIngredientData(int productId) {
        List<Ingredient> rawItems = new ArrayList<>();
        String sql = "SELECT it.id AS item_id, it.company_id, it.name, it.unit, ing.id AS ingredient_id, ing.product_id, ing.amount FROM product_perishable_ingredient ing JOIN perishable_items it ON ing.perishable_item_id=it.id WHERE product_id="+productId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int companyId = rs.getInt("company_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                int ingredientId = rs.getInt("ingredient_id");
                //int productId = rs.getInt("product_id");
                float amount = rs.getFloat("amount");
                rawItems.add(new Ingredient(itemId, companyId, name, unit, ingredientId, productId, amount, true));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return rawItems;
    }
//endregion
//region ITEMS
    public static List<RawItem> getRawItemData(int companyId) {
        List<RawItem> rawItems = new ArrayList<>();
        String sql = "SELECT * FROM raw_items";
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int itemId = rs.getInt("id");
                //companyId
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                rawItems.add(new RawItem(itemId, companyId, name, unit));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return rawItems;
    }
    public static List<PerishableItem> getPerishableItemData(int companyId) {
        List<PerishableItem> perishableItems = new ArrayList<>();
        String sql = "SELECT * FROM perishable_items";
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int itemId = rs.getInt("id");
                //companyId
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                perishableItems.add(new PerishableItem(itemId, companyId, name, unit));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return perishableItems;
    }
    public static List<Ingredient> getPerishableItemIngredientData(int perishableItemId) {
        List<Ingredient> perishableItemIngredients = new ArrayList<>();
        String sql = "SELECT it.id AS item_id, it.company_id, it.name, it.unit, ing.id AS ingredient_id, ing.perishable_item_id, ing.amount FROM perishable_item_ingredient ing JOIN raw_items it ON ing.raw_item_id=it.id WHERE perishable_item_id="+perishableItemId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int companyId = rs.getInt("company_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                int ingredientId = rs.getInt("ingredient_id");
                //int perishableItemId = rs.getInt("perishable_item_id");
                float amount = rs.getFloat("amount");
                perishableItemIngredients.add(new Ingredient(itemId, companyId, name, unit, ingredientId, perishableItemId, amount, true));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return perishableItemIngredients;
    }
//endregion
//region COMPANY
    public static List<User> getUserData(int companyId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT users.id AS user_id, users.company_id, pos_kitchen_outlet.outlet_id, users.name AS username, email, role, outlets.name AS outlet_name FROM outlets RIGHT JOIN (users LEFT JOIN pos_kitchen_outlet ON users.id=pos_kitchen_outlet.user_id) ON outlets.id=pos_kitchen_outlet.outlet_id WHERE users.company_id="+companyId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                //int companyId = rs.getInt("company_id");
                int outletId = rs.getInt("outlet_id");
                String name = rs.getString("username");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String outletName = rs.getString("outlet_name");
                User user = new User(userId, companyId, outletId, name, email, role, outletName);
                users.add(user);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        }
        JDBC.disconnect();
        return users;
    }
//endregion
//endregion

//region UPDATE
//region OUTLETS
    public static void addOutlet(int companyId, String name, boolean addAllProducts) {
        String sql = String.format("INSERT INTO outlets (company_id, name, address) VALUES (%d, '%s', '')", companyId, name);
        JDBC.connect();
        JDBC.update(sql);
        if (addAllProducts) {
            JDBC.query("SELECT LAST_INSERT_ID()");
            ResultSet rs = JDBC.rs;
            try {
                rs.next();
                int outletId = rs.getInt("LAST_INSERT_ID()");

                sql = String.format("SELECT * FROM products WHERE company_id=%d", companyId);
                JDBC.query(sql);
                rs = JDBC.rs;

                while (rs.next()) {
                    int productId = rs.getInt("id");
                    sql = String.format("INSERT INTO outlet_product (outlet_id, product_id, price_override, available) VALUES (%d, %d, 0, 1)", outletId, productId);
                    JDBC.update(sql);
                }
            } catch (SQLException err) {
                System.out.println(err.getMessage());
                throw new RuntimeException(err);
            }
        }
        JDBC.disconnect();
    }
    public static void updateOutlet(int outletId, String newOutletName) {
        String sql = String.format("UPDATE outlets SET name='%s' WHERE id=%d", newOutletName, outletId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void deleteOutlet(int outletId) {
        String sql = String.format("DELETE FROM outlets WHERE id=%d", outletId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void addOutletProduct(int outletId, int productId, int priceOverride, boolean isAvailable) {
        String sql = String.format("INSERT INTO outlet_product (outlet_id, product_id, price_override, available) VALUES (%d, %d, %d, %b)", outletId, productId, priceOverride, isAvailable);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void updateOutletProduct(int outletProductId, int newPriceOverride, boolean newAvailable) {
        String sql = String.format("UPDATE outlet_product SET price_override=%d, available=%b WHERE id=%d", newPriceOverride, newAvailable, outletProductId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void deleteOutletProduct(int outletProductId) {
        String sql = String.format("DELETE FROM outlet_product WHERE id=%d", outletProductId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void addOutletItem(int outletId, int itemId, boolean isPerishable) {
        String sql = String.format("INSERT INTO outlet_raw_item (outlet_id, raw_item_id, amount) VALUES (%d, %d, 0)", outletId, itemId);
        if (isPerishable)
            sql = String.format("INSERT INTO outlet_perishable_item (outlet_id, perishable_item_id, amount) VALUES (%d, %d, 0)", outletId, itemId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void updateOutletItem(int outletItemId, boolean isPerishable, float newLowLimit, float newCriticalLimit) {
        String sql = String.format("UPDATE outlet_raw_item SET WHERE id=%d", outletItemId);
        if (isPerishable)
            sql = String.format("UPDATE outlet_perishable_item SET WHERE id=%d", outletItemId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void deleteOutletItem(int outletItemId, boolean isPerishable) {
        String sql = String.format("DELETE FROM outlet_raw_item WHERE id=%d", outletItemId);
        if (isPerishable)
            sql = String.format("DELETE FROM outlet_perishable_item WHERE id=%d", outletItemId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
//endregion
//region PRODUCTS
    public static void addProduct(int companyId, String name, int price) {
        String sql = String.format("INSERT INTO products (company_id, name, price) VALUES (%d, '%s', %d)", companyId, name, price);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void updateProduct(int productId, String newProductName, int newBasePrice) {
        String sql = String.format("UPDATE products SET name='%s', price=%d WHERE id=%d", newProductName, newBasePrice, productId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void deleteProduct(int productId) {
        String sql = String.format("DELETE FROM products WHERE id=%d", productId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void addProductIngredient(int productId, int itemId, float amount, boolean isPerishable) {
        String sql = String.format("INSERT INTO product_raw_ingredient (product_id, raw_item_id, amount) VALUES (%d, %d, %f)", productId, itemId, amount);
        if (isPerishable) sql = String.format("INSERT INTO product_perishable_ingredient (product_id, perishable_item_id, amount) VALUES (%d, %d, %f)", productId, itemId, amount);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void updateProductIngredient(int ingredientId, boolean isPerishable, float newAmount) {
        String sql = String.format("UPDATE product_raw_ingredient SET amount=%f WHERE id=%d", newAmount, ingredientId);
        if (isPerishable) sql = String.format("UPDATE product_perishable_ingredient SET amount=%f WHERE id=%d", newAmount, ingredientId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void deleteProductIngredient(int ingredientId, boolean isPerishable) {
        String sql = String.format("DELETE FROM product_raw_ingredient WHERE id=%d", ingredientId);
        if (isPerishable) sql = String.format("DELETE FROM product_perishable_ingredient WHERE id=%d", ingredientId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
//endregion
//region ITEMS
    public static void addItem(int companyId, String name, String unit, boolean isPerishable) {
        String sql = String.format("INSERT INTO raw_items (company_id, name, unit) VALUES (%d, '%s', '%s')", companyId, name, unit);
        if (isPerishable)
            sql = String.format("INSERT INTO perishable_items (company_id, name, unit) VALUES (%d, '%s', '%s')", companyId, name, unit);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void updateItem(int itemId, boolean isPerishable, String newItemName, String newItemUnit) {
        String sql = String.format("UPDATE raw_items SET name='%s', unit='%s' WHERE id=%d", newItemName, newItemUnit, itemId);
        if (isPerishable)
            sql = String.format("UPDATE perishable_items SET name='%s', unit='%s' WHERE id=%d", newItemName, newItemUnit, itemId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void deleteItem(int itemId, boolean isPerishable) {
        String sql = String.format("DELETE FROM raw_items WHERE id=%d", itemId);
        if (isPerishable)
            sql = String.format("DELETE FROM perishable_items WHERE id=%d", itemId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void addPerishableItemIngredient(int perishableItemId, int rawItemId, float amount) {
        String sql = String.format("INSERT INTO perishable_item_ingredient (perishable_item_id, raw_item_id, amount) VALUES (%d, %d, %f)", perishableItemId, rawItemId, amount);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void updatePerishableItemIngredient(int ingredientId, float newAmount) {
        String sql = String.format("UPDATE perishable_item_ingredient SET amount=%f WHERE id=%d", newAmount, ingredientId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void deletePerishableItemIngredient(int ingredientId) {
        String sql = String.format("DELETE FROM perishable_item_ingredient WHERE id=%d", ingredientId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
//endregion
//region COMPANY
    public static void updateCompany(int companyId, String newCompanyName) {
        String sql = String.format("UPDATE companies SET name='%s' WHERE id=%d", newCompanyName, companyId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
    public static void addUser(String name, int companyId, String email, String password, String role, int outletId) {
        String sql = String.format("INSERT INTO users (name, company_id, name, email, password, role) VALUES ('%s', %d, '%s', '%s', '%s')", name, companyId, email, password, role);
        JDBC.connect();
        JDBC.update(sql);
        if (role.equals("kitchen") || role.equals("pos")) {
            sql = String.format("INSERT INTO pos_kitchen_outlet (user_id, outlet_id) VALUES (LAST_INSERT_ID(), %d)", outletId);
            JDBC.update(sql);
        }
        JDBC.disconnect();
    }
    public static void updateUser(int userId,String newName, String newRole, int newOutletId) {
        String sql = String.format("UPDATE users SET name='%s', role='%s WHERE id=%d", newName, newRole, userId);
        JDBC.connect();
        JDBC.update(sql);
        if (newRole.equals("kitchen") || newRole.equals("pos")) {
            sql = String.format("UPDATE pos_kitchen_outlet SET outlet_id=%d WHERE user_id=%d", newOutletId, userId);
            JDBC.update(sql);
        }
        JDBC.disconnect();
    }
    public static void deleteUser(int userId) {
        String sql = String.format("DELETE FROM users WHERE id=%d", userId);
        JDBC.connect();
        JDBC.update(sql);
        JDBC.disconnect();
    }
//endregion
//endregion

}
