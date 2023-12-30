package BE;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Outlet {
    private final int outletId, companyId;
    private String name, address;
    private final List<OutletItem> outletItems = new ArrayList<>();
    private final List<OutletProduct> outletProducts = new ArrayList<>();;
    private final List<Order> activeOrders = new ArrayList<>();

    public Outlet(int outletId, int companyId, String name, String address) {
        this.outletId = outletId;
        this.companyId = companyId;
        this.name = name;
        this.address = address;
    }

//region Setter Getter

    public int getOutletId() {
        return outletId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OutletItem> getOutletItems() {
        return outletItems;
    }

    public List<OutletProduct> getOutletProducts() {
        return outletProducts;
    }

    public List<Order> getActiveOrders() {
        return activeOrders;
    }

//endregion

}
