package BE;

import java.util.ArrayList;
import java.util.List;

public class Outlet {
    private String name;
    private final Inventory inventory;
    private final Company parentCompany;
    private List<Product> products;

    // Constructor
    public Outlet(Company parentCompany, String name) {
        this.parentCompany = parentCompany;
        products = new ArrayList<Product>(parentCompany.getProducts());
        this.name = name;
        inventory = new Inventory(this);
    }

    //region Setter Getter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    public void addProduct(Product product) {
        products.add(product);
    }
    public List<Product> getProducts() {
        return products;
    }
}
