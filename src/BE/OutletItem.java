package BE;

public class OutletItem extends Item {
    private final int outletItemId, outletId;
    private float amount;

    public OutletItem(int itemId, int companyId, String name, String unit, int outletItemId, int outletId, float amount) {
        super(itemId, companyId, name, unit);
        this.outletItemId = outletItemId;
        this.outletId = outletId;
        this.amount = amount;
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
}
