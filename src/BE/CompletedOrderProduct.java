package BE;

public class CompletedOrderProduct {
    private int completedOrderProductId, completedOrderId;
    private String productName;
    private int price, quantity;

    public CompletedOrderProduct(int completedOrderProductId, int completedOrderId, String productName, int price, int quantity) {
        this.completedOrderProductId = completedOrderProductId;
        this.completedOrderId = completedOrderId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getCompletedOrderProductId() {
        return completedOrderProductId;
    }

    public void setCompletedOrderProductId(int completedOrderProductId) {
        this.completedOrderProductId = completedOrderProductId;
    }

    public int getCompletedOrderId() {
        return completedOrderId;
    }

    public void setCompletedOrderId(int completedOrderId) {
        this.completedOrderId = completedOrderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
