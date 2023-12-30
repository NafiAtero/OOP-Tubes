package BE;

import java.util.ArrayList;
import java.util.List;

public class Kitchen extends UserController {
    private final int outletId;
    private List<Order> activeOrders = new ArrayList<>();
    private List<OrderProduct> orderProducts = new ArrayList<>();
    private List<OutletItem> outletItems = new ArrayList<>();
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
    public void getActiveOrdersData() {
        activeOrders = KitchenDAO.getActiveOrdersData(outletId);
        for (Order order : activeOrders) {
            order.setOrderProducts(KitchenDAO.getOrderProductsData(order, companyId));
        }
    }
    public void getOutletItemsData() {
        if (outletItems != null) {
            outletItems.clear();
            outletItems.addAll(KitchenDAO.getOutletPerishableItemsData(outletId));
            outletItems.addAll(KitchenDAO.getOutletRawItemsData(outletId));
        }
    }
//endregion

//region UPDATE
    public void createPerishableItem(int perishableItemId, float amount) {
        KitchenDAO.createPerishableItem(perishableItemId, amount);
        getOutletItemsData();
    }
    public void updateItem(int productId, boolean perishable, float stock) {
        KitchenDAO.updateItem(productId, perishable, stock);
        getOutletItemsData();
    }
    public void deliverProduct(OrderProduct orderProduct) {
        KitchenDAO.deliverProduct(orderProduct);
        getActiveOrdersData();
    }
    public void clearPerishableItems() {
        KitchenDAO.clearPerishableItems(outletId);
        getOutletItemsData();
    }
//endregion

}
