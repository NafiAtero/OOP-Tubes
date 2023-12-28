package BE;

import java.util.List;

public class Manager extends User {
    private List<Outlet> outlets;
    private List<OutletProduct> outletProducts;
    private List<OutletItem> outletItems;
    private List<Product> products;
    private List<Item> items;
    private List<User> users;
    public Manager(int userId, int companyId) {
        super(userId, companyId);
    }

//region READ
    /*
    outlet
    outlet product
    outlet item
    product
    item
     */
//endregion

//region CREATE
//endregion

//region UPDATE
//endregion

//region DELETE
//endregion


}
