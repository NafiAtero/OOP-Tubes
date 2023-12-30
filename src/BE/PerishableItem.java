package BE;

import java.util.ArrayList;
import java.util.List;

public class PerishableItem extends Item {
    private final List<Ingredient> ingredientList;

    public PerishableItem(int itemId, int companyId, String name, String unit) {
        super(itemId, companyId, name, unit);
        ingredientList = new ArrayList<>();
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }
}
