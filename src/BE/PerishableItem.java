package BE;

import java.util.ArrayList;
import java.util.List;

public class PerishableItem extends Item {
    private List<Ingredient> ingredientList;

    public PerishableItem(int itemId, int companyId, String name, String unit) {
        super(itemId, companyId, name, unit);
        ingredientList = new ArrayList<>();
    }

    public void addIngredient(int id, int companyId, String name, String unit, int perishableItemId, float amount) {
        ingredientList.add(new Ingredient(id, companyId, name, unit, perishableItemId, amount));
    }
}
