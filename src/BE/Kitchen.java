package BE;

import java.util.List;

public class Kitchen extends User {
    private final int outletId;
    private List<Order> activeOrders;
    private List<OrderProduct> orderProducts;
    private List<OutletItem> outletItems;
    public Kitchen(int userId, int companyId, int outletId) {
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
    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }
    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
    public List<OutletItem> getOutletItems() {
        return outletItems;
    }
    public void setOutletItems(List<OutletItem> outletItems) {
        this.outletItems = outletItems;
    }
//endregion

//region READ
    public void getActiveOrdersData() {}
    public void getOrderProductsData() {}
    public void getOutletItemsData() {}
//endregion

//region UPDATE
    public void createPerishableItem(int perishableItemId, float amount) {
        //KitchenDAO.createPerishableItem(perishableItemId, amount);
        getOutletItemsData();
    }
    public void updateItem(int productId, boolean perishable, float stock) {
        KitchenDAO.updateItem(productId, perishable, stock);
        getOutletItemsData();
    }
    public void deliverProduct(int orderProductId, int quantity) {
        KitchenDAO.deliverProduct(orderProductId, quantity);
        getActiveOrdersData();
    }
    public void undoDeliverProduct(int orderProductId, int quantity) {
        KitchenDAO.undoDeliverProduct(orderProductId, quantity);
        getActiveOrdersData();
    }
//endregion

}