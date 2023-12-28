package BE;

import java.util.ArrayList;
import java.util.List;



public class Order {
    private int orderId, outletId;
    private String tableName;
    // datetime
    private final List<OrderProduct> orderProducts = new ArrayList<>();
    //todo constructor

//region Setter Getter
//endregion

    public void addItem(int productId, int companyId, String name, int price, int orderProductId, int outletProductId, int quantity) {
        orderProducts.add(new OrderProduct(productId, companyId, name, price, orderProductId, orderId, outletProductId, quantity, false));
    }
}
