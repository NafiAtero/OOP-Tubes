package BE;

import java.util.ArrayList;
import java.util.List;

public class Manager extends UserController {
    private final List<Outlet> outlets = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final List<RawItem> rawItems = new ArrayList<>();
    private final List<PerishableItem> perishableItems = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    public Manager(int userId, int companyId) {
        super(userId, companyId);
    }

    public List<Outlet> getOutlets() {
        return outlets;
    }
    public List<Product> getProducts() {
        return products;
    }
    public List<RawItem> getRawItems() {
        return rawItems;
    }
    public List<PerishableItem> getPerishableItems() {
        return perishableItems;
    }
    public List<Item> getItems() {
        return items;
    }
    public List<User> getUsers() {
        return users;
    }

//region READ
    /*
    outlet
    outlet product
    outlet item
    product
    item
     */
    public void getOutletData() {
        outlets.clear();
        outlets.addAll(ManagerDAO.getOutletData(companyId));
        for (Outlet outlet : outlets) {
            outlet.getOutletProducts().clear();
            outlet.getOutletProducts().addAll(ManagerDAO.getOutletProductsData(outlet.getOutletId(), companyId));
            outlet.getOutletItems().clear();
            outlet.getOutletItems().addAll(ManagerDAO.getOutletPerishableItemsData(outlet.getOutletId()));
            outlet.getOutletItems().addAll(ManagerDAO.getOutletRawItemsData(outlet.getOutletId()));
        }
    }
    public void getProductData() {
        products.clear();
        products.addAll(ManagerDAO.getProductData(companyId));
        for (Product product : products) {
            product.getIngredients().clear();
            product.getIngredients().addAll(ManagerDAO.getProductRawIngredientData(product.getProductId()));
            product.getIngredients().addAll(ManagerDAO.getProductPerishableIngredientData(product.getProductId()));
        }
    }
    public void getItemData() {
        rawItems.clear();
        rawItems.addAll(ManagerDAO.getRawItemData(companyId));
        perishableItems.clear();
        perishableItems.addAll(ManagerDAO.getPerishableItemData(companyId));
        for (PerishableItem perishableItem : perishableItems) {
            perishableItem.getIngredients().clear();
            perishableItem.getIngredients().addAll(ManagerDAO.getPerishableItemIngredientData(perishableItem.getItemId()));
        }
        items.clear();
        items.addAll(rawItems);
        items.addAll(perishableItems);
    }
    public void getUserData() {
        users.clear();
        users.addAll(ManagerDAO.getUserData(companyId));
    }
//endregion

//region CREATE
    public void addOutlet(String name, boolean addAllProducts, boolean addAllItems) {
        ManagerDAO.addOutlet(companyId, name, addAllProducts);
        getOutletData();
    }
    public void addOutletProduct(Outlet outlet, Product product) {
        ManagerDAO.addOutletProduct(outlet.getOutletId(), product.getProductId(), 0, true);
        getOutletData();
    }
    public void addOutletItem(Outlet outlet, Item item) {
        ManagerDAO.addOutletItem(outlet.getOutletId(), item.getItemId(), item instanceof PerishableItem);
        getOutletData();
    }
    public void addProduct(String name, int price) {
        ManagerDAO.addProduct(companyId, name, price);
        getProductData();
    }
    public void addProductIngredient(Product product, Item item, float amount) {
        ManagerDAO.addProductIngredient(product.getProductId(), item.getItemId(), amount, item instanceof PerishableItem);
        getProductData();
    }
    public void addItem(String name, String unit, boolean isPerishable) {
        ManagerDAO.addItem(companyId, name, unit, isPerishable);
        getItemData();
    }
    public void addPerishableItemIngredient(RawItem rawItem, PerishableItem perishableItem, float amount) {
        ManagerDAO.addPerishableItemIngredient(perishableItem.getItemId(), rawItem.getItemId(), amount);
        getItemData();
    }
    public void addUser(String name, String email, String password, String role, Outlet outlet) {
        ManagerDAO.addUser(name, companyId, email, password, role, outlet.getOutletId());
        getUserData();
    }
//endregion

//region UPDATE
    public void updateOutlet(Outlet outlet, String newOutletName) {
        ManagerDAO.updateOutlet(outlet.getOutletId(), newOutletName);
        getOutletData();
    }
    public void updateOutletProduct(OutletProduct outletProduct, int newPriceOverride, boolean newAvailable) {
        ManagerDAO.updateOutletProduct(outletProduct.getOutletProductId(), newPriceOverride, newAvailable);
        getOutletData();
    }
    public void updateOutletItem(OutletItem outletItem, float newLowLimit, float newCriticalLimit) {
        ManagerDAO.updateOutletItem(outletItem.getOutletItemId(), outletItem.isPerishable(), newLowLimit, newCriticalLimit);
        getOutletData();
    }
    public void updateProduct(Product product, String newProductName, int newBasePrice) {
        ManagerDAO.updateProduct(product.getProductId(), newProductName, newBasePrice);
        getProductData();
    }
    public void updateProductIngredient(Ingredient ingredient, boolean isPerishable, float newAmount) {
        ManagerDAO.updateProductIngredient(ingredient.getIngredientId(), isPerishable, newAmount);
        getProductData();
    }
    public void updateItem(Item item, String newItemName, String newItemUnit) {
        ManagerDAO.updateItem(item.getItemId(), item instanceof PerishableItem, newItemName, newItemUnit);
        getItemData();
    }
    public void updatePerishableItemIngredient(Ingredient ingredient, float newAmount) {
        ManagerDAO.updatePerishableItemIngredient(ingredient.getIngredientId(), newAmount);
        getItemData();
    }
    public void updateCompany(String newCompanyName) {
        ManagerDAO.updateCompany(companyId, newCompanyName);
    }
    public void updateUser(User user, String newName, String newRole, int newOutletId) {
        ManagerDAO.updateUser(user.getUserId(), newName, newRole, newOutletId);
        getUserData();
    }
//endregion

//region DELETE
    public void deleteOutlet(Outlet outlet) {
        ManagerDAO.deleteOutlet(outlet.getOutletId());
        getOutletData();
    }
    public void deleteOutletProduct(OutletProduct outletProduct) {
        ManagerDAO.deleteOutletProduct(outletProduct.getOutletProductId());
        getOutletData();
    }
    public void deleteOutletItem(OutletItem outletItem) {
        ManagerDAO.deleteOutletItem(outletItem.getOutletItemId(), outletItem.isPerishable());
        getOutletData();
    }
    public void deleteProduct(Product product) {
        ManagerDAO.deleteProduct(product.getProductId());
        getProductData();
    }
    public void deleteProductIngredient(Ingredient ingredient) {
        ManagerDAO.deleteProductIngredient(ingredient.getIngredientId(), ingredient.isPerishable());
        getProductData();
    }
    public void deleteItem(Item item) {
        ManagerDAO.deleteItem(item.getItemId(), item instanceof PerishableItem);
        getItemData();
    }
    public void deletePerishableItemIngredient(Ingredient ingredient) {
        ManagerDAO.deletePerishableItemIngredient(ingredient.getIngredientId());
        getItemData();
    }
    public void deleteUser(User user) {
        ManagerDAO.deleteUser(user.getUserId());
        getUserData();
    }

//endregion


}
