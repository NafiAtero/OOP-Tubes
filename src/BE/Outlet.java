package BE;

public class Outlet {
    private String name;
    private final Inventory inventory = new Inventory(this);


    public Outlet(String name) {
        this.name = name;
    }
}
