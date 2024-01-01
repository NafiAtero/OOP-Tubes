package BE;

/**
 * connects Product objects in Company to Outlet
 */
public class OutletProduct extends Product {
    private final int outletProductId, outletId;
    private boolean available;
    private int priceOverride;
    public OutletProduct(int outletProductId, int outletId, int productId, int companyId, String name, int price, int priceOverride) {
        super(productId, companyId, name, price);
        this.outletProductId = outletProductId;
        this.outletId = outletId;
        available = true;
        this.priceOverride = priceOverride;
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
    @Override
    public int getPrice() {
        if (priceOverride <= 0) {
            return super.getPrice();
        }
        return priceOverride;
    }
    public int getBasePrice() { return super.getPrice(); }
    public int getPriceOverride() { return priceOverride; }
//endregion

}
