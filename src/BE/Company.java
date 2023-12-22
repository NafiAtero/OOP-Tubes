package BE;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String companyName;
    private final List<Outlet> outlets = new ArrayList<>();
    private final List<CompanyProduct> companyProducts = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public Company(String companyName) {
        this.companyName = companyName;
    }

    //region Getter Setter

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Outlet> getOutlets() {
        return outlets;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<CompanyProduct> getProducts() {
        return companyProducts;
    }

    //endregion





    /**
     * TEMP print all from outlets list
     */
    public void printOutlets() {
        for (Outlet outlet :
                outlets) {
            System.out.println("menu at " + outlet.getOutletName());


            for (CompanyProduct product :
                    outlet.getAvailableProducts()) {
                System.out.println(product.getProductName() + product.getPrice());
            }
        }
    }

    /**
     * TEMP print all from products list
     */
    public void printProducts() {
        System.out.println("Products at " + companyName);
        for (CompanyProduct product :
                companyProducts) {
            System.out.println(product.getProductName());
        }
    }


}

