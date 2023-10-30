package BE;

public class Ingredient {
    private String name;
    private boolean perishable = false;
    public Ingredient(String name) {
        this.name = name;
    }

    //region Setter Getter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPerishable() {
        return perishable;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }
    //endregion


}
