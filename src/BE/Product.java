package BE;

import java.util.List;

public class Product {
    private final int productId, companyId;
    private String name;
    private int price;
    private List<Item> ingredients;

    public Product(int productId, int companyId, String name, int price) {
        this.productId = productId;
        this.companyId = companyId;
        this.name = name;
        this.price = price;
    }

//region Setter Getter
    public int getProductId() {
        return productId;
    }
    public int getCompanyId() {
        return companyId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
//endregion

    public void addIngredient(int id, int companyId, String name, String unit, int perishableItemId, float amount) {
        ingredients.add(new Ingredient(id, companyId, name, unit, perishableItemId, amount));
    }

//region Setter Getter
//endregion
}
