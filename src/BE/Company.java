package BE;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private List<Outlet> outlets = new ArrayList<Outlet>();
    private List<Product> products = new ArrayList<Product>();

    public Company(String name) {
        this.name = name;
    }

    //region Outlet
    public void addOutlet(String name) {
        outlets.add(new Outlet(this, name));
    }

    public Outlet getOutlet(int index) {
        return outlets.get(index);
    }
    //endregion

    //region Products
    public void addProduct(String name, int price) {
        Product newProduct = new Product(name, price);
        products.add(newProduct);

        for (Outlet outlet :
                outlets) {
            outlet.addProduct(newProduct);
        }
    }

    public Product getProduct(int index) {
        return products.get(index);
    }

    public List<Product> getProducts() {
        return products;
    }
    //endregion



    public void printOutlets() {
        for (Outlet outlet :
                outlets) {
            System.out.println("menu at " + outlet.getName());


            for (Product product :
                    outlet.getProducts()) {
                System.out.println(product.getName() + product.getPrice());
            }
        }
    }

    public void printProducts() {
        for (Product product :
                products) {
            System.out.println(product.getName());
        }
    }


    }
}
