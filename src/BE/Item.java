package BE;

public class Item {
    protected final int itemId, companyId;
    protected String name, unit;

    public Item(int itemId, int companyId, String name, String unit) {
        this.itemId = itemId;
        this.companyId = companyId;
        this.name = name;
        this.unit = unit;
    }

    public int getItemId() {
        return itemId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
