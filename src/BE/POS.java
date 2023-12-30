package BE;

import java.util.ArrayList;
import java.util.List;

/*
    todo
    Order class datetime + constructor
    get outlet product join table query
 */

public class POS extends UserController {
    private final int outletId;
    private final List<Order> activeOrders = new ArrayList<>();
    private final List<OutletProduct> outletProducts = new ArrayList<>();
    public POS(int userId, int companyId, int outletId) {
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
    public List<OutletProduct> getOutletProducts() {
        return outletProducts;
    }
//endregion

//region READ
    public void getActiveOrdersData() {
        activeOrders.clear();
        activeOrders.addAll(POSDAO.getActiveOrdersData(outletId));
        for (Order order : activeOrders) {
            order.getOrderProducts().clear();
            order.getOrderProducts().addAll(POSDAO.getOrderProductsData(order.getOrderId(), companyId));
        }
    }
    public void getOutletProductsData() {
        outletProducts.clear();
        outletProducts.addAll(POSDAO.getOutletProductsData(outletId, companyId));
    }
//endregion

//region CREATE
    public void addOrder(String tableName) {
        POSDAO.addOrder(outletId, tableName);
        getActiveOrdersData();
    }
    public void addOrderProduct(Order order, OutletProduct outletProduct) {
        POSDAO.addOrderProduct(order.getOrderId(), outletProduct.getOutletProductId());
        getActiveOrdersData();
    }
//endregion

//region UPDATE
    public void updateOrder(Order order, String newTableName) {
        POSDAO.updateOrder(order.getOrderId(), newTableName);
        getActiveOrdersData();
    }
     public void updateOrderProduct(OrderProduct orderProduct, int quantity) {
        POSDAO.updateOrderProduct(orderProduct.getOrderProductId(), quantity);
        getActiveOrdersData();
    }
    public void finishOrder(Order order) {
        POSDAO.deleteOrder(order.getOrderId());
        int completedOrderId = POSDAO.addCompletedOrder(companyId, outletId, order.getTableName());
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            POSDAO.addCompletedOrderProduct(completedOrderId, orderProduct.getName(), orderProduct.getPrice(), orderProduct.getQuantity());
        }
        getActiveOrdersData();
    }
//endregion

//region DELETE
     public void deleteOrder(Order order) {
        POSDAO.deleteOrder(order.getOrderId());
        getActiveOrdersData();
    }

    public void deleteOrderProduct(OrderProduct orderProduct) {
        POSDAO.deleteOrderProduct(orderProduct.getOrderProductId());
        getActiveOrdersData();
        //getOrderProductsData();
    }
//endregion
}
