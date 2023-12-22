package BE;

/**
 * connects Product objects in Company to Outlet
 */
public class OutletProduct extends Product {
    private final int outletProductId, outletId;
    private boolean available;
    public OutletProduct(int outletProductId, int outletId, int productId, int companyId, String name, int price) {
        super(productId, companyId, name, price);
        this.outletProductId = outletProductId;
        this.outletId = outletId;
        available = true;
    }

//region Setter Getter
    public int getOutletProductId() {
        return outletProductId;
    }
    public int getOutletId() {
        return outletId;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
//endregion

//region Setter Getter
//endregion

//region Setter Getter
//endregion
}
