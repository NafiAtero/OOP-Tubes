package BE;

public class OutletItem extends Item {
    private final int outletItemId, outletId;
    private float amount;
    private boolean perishable;

    public OutletItem(int itemId, int companyId, String name, String unit, int outletItemId, int outletId, float amount, boolean perishable) {
        super(itemId, companyId, name, unit);
        this.outletItemId = outletItemId;
        this.outletId = outletId;
        this.amount = amount;
        this.perishable = perishable;
    }

    public int getOutletItemId() {
        return outletItemId;
    }

    public int getOutletId() {
        return outletId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isPerishable() {
        return perishable;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }
}
