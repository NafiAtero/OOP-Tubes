package BE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {

//region QUERY
//region OUTLETS
    public static List<Outlet> getOutletData(int companyId) {
        List<Outlet> outlets = new ArrayList<>();
        String sql = "SELECT * FROM outlets WHERE company_id="+companyId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int outletId = rs.getInt("outlet_id");
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
        String sql = "SELECT users.id AS user_id, company_id, pos_kitchen_outlet.outlet_id, name, email, role FROM users LEFT JOIN pos_kitchen_outlet on users.id=pos_kitchen_outlet.user_id WHERE company_id="+companyId;
        JDBC.connect();
        JDBC.query(sql);
        ResultSet rs = JDBC.rs;
        try {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                //int companyId = rs.getInt("company_id");
                int outletId = rs.getInt("outlet_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String role = rs.getString("role");
                User user = new User(userId, companyId, outletId, name, email, role);
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
    public static void addOutlet() {}
    public static void updateOutlet(int outletId, String newOutletName) {}
    public static void deleteOutlet(int outletId) {}
    public static void addOutletProduct(int outletId, int productId) {}
    public static void updateOutletProduct(int outletProductId, int newPriceOverride, boolean newAvailable) {}
    public static void deleteOutletProduct(int outletProductId) {}
    public static void addOutletItem(int outletId, int itemId, boolean isPerishable) {}
    public static void updateOutletItem(int itemId, boolean isPerishable, float newLowLimit, float newCriticalLimit) {}
    public static void deleteOutletItem(int itemId, boolean isPerishable) {}
//endregion
//region PRODUCTS
    public static void addProduct() {}
    public static void updateProduct(int productId, String newProductName, int newBasePrice) {}
    public static void deleteProduct(int productId) {}
    public static void addProductIngredient(int productId, int itemId, boolean isPerishable) {}
    public static void updateProductIngredient(int productId, int itemId, boolean isPerishable, float newAmount) {}
    public static void deleteProductIngredient(int productId, int itemId, boolean isPerishable) {}
//endregion
//region ITEMS
    public static void addItem() {}
    public static void updateItem(int itemId, boolean isPerishable, String newItemName, String newItemUnit) {}
    public static void deleteItem(int itemId, boolean isPerishable) {}
    public static void addPerishableItemIngredient() {}
    public static void editPerishableItemIngredient(int perishableItemId, float newAmount) {}
    public static void deletePerishableItemIngredient(int perishableItemId) {}
//endregion
//region COMPANY
    public static void updateCompany(int companyId, String newCompanyName) {}
    public static void addUser() {}
    public static void updateUser(int userId,String newUsername, String newRole, String newOutletId) {}
    public static void deleteUser(int userId) {}
//endregion
//endregion

}
