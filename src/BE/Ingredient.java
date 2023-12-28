package BE;

public class Ingredient extends Item {
    protected final int perishableItemId;
    protected float amount;

    public Ingredient(int itemId, int companyId, String name, String unit, int perishableItemId, float amount) {
        super(itemId, companyId, name, unit);
        this.perishableItemId = perishableItemId;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getPerishableItemId() {
        return perishableItemId;
    }
}
