package BE;

/**
 * connects Product objects in Outlet to Order
 */
public class OrderProduct extends Product {

    private final int orderProductId, orderId, outletProductId;
    private int quantity;
    private boolean delivered;

    public OrderProduct(int productId, int companyId, String name, int price, int orderProductId, int orderId, int outletProductId, int quantity, boolean delivered) {
        super(productId, companyId, name, price);
        this.orderProductId = orderProductId;
        this.orderId = orderId;
        this.outletProductId = outletProductId;
        this.quantity = quantity;
        this.delivered = delivered;
    }
//region Setter Getter
    public int getOrderProductId() {
        return orderProductId;
    }
    public int getOrderId() {
        return orderId;
    }
    public int getOutletProductId() {
        return outletProductId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public boolean isDelivered() {
        return delivered;
    }
    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
//endregion
}
