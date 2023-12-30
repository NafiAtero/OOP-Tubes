package BE;

public class Ingredient extends Item {
    /*
    itemId = id in items tables
    ingredientId = id in ingredients tables
    productOrPerishableItemId = id of product/perishable item to be made
     */
    private final int ingredientId, productOrPerishableItemId;
    private float amount;
    private boolean isPerishable;

    public Ingredient(int itemId, int companyId, String name, String unit, int ingredientId, int productOrPerishableItemId, float amount, boolean isPerishable) {
        super(itemId, companyId, name, unit);
        this.ingredientId = ingredientId;
        this.productOrPerishableItemId = productOrPerishableItemId;
        this.amount = amount;
        this.isPerishable = isPerishable;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getProductOrPerishableItemId() {
        return productOrPerishableItemId;
    }

    public boolean isPerishable() {
        return isPerishable;
    }

    public void setPerishable(boolean perishable) {
        isPerishable = perishable;
    }
}
