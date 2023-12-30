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

//region READ
    /*
    outlet
    outlet product
    outlet item
    product
    item
     */
    public void getUserData() {
        users.clear();
        users.addAll(ManagerDAO.getUserData(companyId));
    }
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
            perishableItem.getIngredientList().clear();
            perishableItem.getIngredientList().addAll(ManagerDAO.getPerishableItemIngredientData(perishableItem.getItemId()));
        }
        items.clear();
        items.addAll(rawItems);
        items.addAll(perishableItems);
    }
//endregion

//region CREATE
//endregion

//region UPDATE
//endregion

//region DELETE
//endregion


}
