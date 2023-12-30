package BE;

import java.util.ArrayList;
import java.util.List;

public class PerishableItem extends Item {
    private final List<Ingredient> ingredients;

    public PerishableItem(int itemId, int companyId, String name, String unit) {
        super(itemId, companyId, name, unit);
        ingredients = new ArrayList<>();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
