package BE;

public class CompanyProduct implements Product {
    private String productName;
    private int price;

    public CompanyProduct(String productName, int price) {
        this.productName = productName;
        this.price = price;
    }

    //region Setter Getter
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    //endregion
}


