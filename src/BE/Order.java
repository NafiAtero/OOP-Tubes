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

    public void addItem(OutletProduct outletProduct) {

        orderProducts.add(new OrderProduct());
    }

    public void removeItem(OutletProduct product) {
        int index = searchOrderListIndex(product);
        if (index == -1) {
            System.out.println("Item not found");
        } else {
            orderList.get(index).addQuantity(-1);
            if (orderList.get(index).getQuantity() <= 0) {
                orderList.remove(index);
            }
        }
    }

    /**
     * searches the orderList using the OutletProduct object
     * @param product OutletProduct object
     * @return index of order (if not found, returns -1)
     */
    private int searchOrderListIndex(OutletProduct product) {
        int index;
        for (int i = 0; i < orderList.size() ; i++) {
            if (orderList.get(i).getProduct() == product) {
                return i;
            }
        }
        return -1;
    }
}
