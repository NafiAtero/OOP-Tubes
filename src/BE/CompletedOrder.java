package BE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CompletedOrder {
    private int completedOrderId, outletId;
    private String tableName;
    private Timestamp orderTime;
    private int companyId;
    private String outletName;
    private final List<CompletedOrderProduct> completedOrderProducts = new ArrayList<>();

    public CompletedOrder(int completedOrderId, int outletId, String tableName, Timestamp orderTime, int companyId, String outletName) {
        this.completedOrderId = completedOrderId;
        this.outletId = outletId;
        this.tableName = tableName;
        this.orderTime = orderTime;
        this.companyId = companyId;
        this.outletName = outletName;
    }

//region SETTER GETTER
    public int getCompletedOrderId() {
        return completedOrderId;
    }

    public void setCompletedOrderId(int completedOrderId) {
        this.completedOrderId = completedOrderId;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public List<CompletedOrderProduct> getCompletedOrderProducts() {
        return completedOrderProducts;
    }
//endregion

    public int getTotal() {
        int total = 0;
        for (CompletedOrderProduct completedOrderProduct : completedOrderProducts) {
            total += completedOrderProduct.getPrice() * completedOrderProduct.getQuantity();
        }
        return total;
    }

}
