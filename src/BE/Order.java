package BE;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;



public class Order {
    private int orderId, outletId;
    private String tableName;
    private Timestamp orderTime;
    private List<OrderProduct> orderProducts = new ArrayList<>();
    public Order(int orderId, int outletId, String tableName, Timestamp orderTime) {
        this.orderId = orderId;
        this.outletId = outletId;
        this.tableName = tableName;
        this.orderTime = orderTime;
    }

//region Setter Getter

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
//endregion

    public void addItem(int productId, int companyId, String name, int price, int orderProductId, int outletProductId, int quantity) {
        orderProducts.add(new OrderProduct(productId, companyId, name, price, orderProductId, orderId, outletProductId, quantity, false));
    }
}
