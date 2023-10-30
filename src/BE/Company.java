package BE;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private List<Outlet> outlets = new ArrayList<Outlet>();
    private List<Product> products = new ArrayList<Product>();
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Company(String name) {
        this.name = name;
    }

    //region BE.Outlet
    public void addOutlet(String name) {
        outlets.add(new Outlet(name));
    }

    public Outlet getOutlet(int index) {
        return outlets.get(index);
    }
    //endregion

    //region Products
    public void addProduct(String name, int price) {
        products.add(new Product(name, price));
    }

    public Product getProduct(int index) {
        return products.get(index);
    }
    //endregion

    //region Ingredients
    public void addIngredient(String name) {
        ingredients.add(new Ingredient(name));
    }

    public Ingredient getIngredient(int index) {
        return ingredients.get(index);
    }
    //endregion
}
