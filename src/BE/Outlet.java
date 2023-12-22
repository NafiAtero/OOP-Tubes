package BE;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Outlet {
    private String outletName;
    private final Inventory inventory;
    private final List<OutletProduct> outletProducts = new ArrayList<>();;
    private final List<Order> activeOrders = new ArrayList<>();

    /**
     *
     * @param parentCompany use [this]
     * @param outletName name of outlet
     */
    public Outlet(Company parentCompany, String outletName) {
        this.outletName = outletName;
        inventory = new Inventory(this);
        for (CompanyProduct product :
                parentCompany.getProducts()) {
            addProduct(product);
        }
    }

    //region Setter Getter
    public String getOutletName() {
        return outletName;
    }
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
    public  List<OutletProduct> getOutletProducts() {
        return outletProducts;
    }
    public List<Order> getActiveOrders() {
        return activeOrders;
    }

    //endregion

    /**
     * adds a product to the outlet
     * @param product to be added to outlet
     */
    void addProduct(CompanyProduct product) {
        outletProducts.add(new OutletProduct(product));
    }

    /**
     * returns list of available products from the outlet
     */
    public List<CompanyProduct> getAvailableProducts() {
        List<CompanyProduct> products = new ArrayList<>();
        for (OutletProduct product :
                outletProducts) {
            if (product.isAvailable()) {
                products.add(product.getProduct());
            }
        }
        return products;
    }

    /**
     *
     * @param index index of product to be changed
     * @param available to be changed to
     */
    public void changeProductAvailability(int index, boolean available) {
        outletProducts.get(index).setAvailable(available);
    }

    /**
     * searches the orderList using the OutletProduct object
     * @param productName name of product
     * @return index of order (if not found, returns -1)
     */
    private int searchOutletProductIndex(String productName) {
        for (int i = 0; i < outletProducts.size(); i++) {
            if (Objects.equals(outletProducts.get(i).getProductName(), productName)) {
                return i;
            }
        }
        return -1;
    }
}
