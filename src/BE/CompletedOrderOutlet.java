package BE;

import java.util.ArrayList;
import java.util.List;

public class CompletedOrderOutlet {
    private String ouletName;
    private int orderCount;
    private final List<CompletedOrderOutletProduct> products = new ArrayList<>();

    public CompletedOrderOutlet(String ouletName) {
        this.ouletName = ouletName;
    }

    public String getOuletName() {
        return ouletName;
    }
    public List<CompletedOrderOutletProduct> getCompletedOrderOutletProducts() {
        return products;
    }
    public int getOrderCount() {
        return orderCount;
    }

    public int getTotalItems() {
        int total = 0;
        for (CompletedOrderOutletProduct product : products) {
            total += product.getQuantity();
        }
        return total;
    }
    public int getTotalIncome() {
        int total = 0;
        for (CompletedOrderOutletProduct product : products) {
            total += product.getTotalPrice();
        }
        return total;
    }

    public void addOrder() { orderCount++; }
    public void addProduct(String productName, int price, int quantity) {
        //System.out.println("adding "+productName+" "+quantity+"x "+price);
        CompletedOrderOutletProduct selectedProduct = new CompletedOrderOutletProduct(productName, price, quantity);
        for (CompletedOrderOutletProduct product : products) {
            if (product.getName().equals(productName)) {
                product.add(price, quantity);
                //System.out.println("addding to existing");
                return;
            }
        }
        //System.out.println("adding new");
        products.add(selectedProduct);
        //System.out.println("size:  " + products.size());
        //System.out.println("count: " + getTotalItems());
    }


}
