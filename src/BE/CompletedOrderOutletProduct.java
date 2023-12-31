package BE;

public class CompletedOrderOutletProduct {

    private String name;
    private int totalPrice, quantity;
    public CompletedOrderOutletProduct(String name, int price, int quantity) {
        this.name = name;
        this.totalPrice = price * quantity;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
    public int getQuantity() {
        return quantity;
    }
    public void add(int price, int count) {
        System.out.println("fr adding");
        totalPrice += price * count;
        quantity += count;
    }
}
