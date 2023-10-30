package BE;

public class Main {
    public static void main(String[] args) {
        Company companyA = new Company("A");

        companyA.addOutlet("AA");
        companyA.addOutlet("BB");
        companyA.printOutlets();

        companyA.addProduct("Ayam", 5000);
        companyA.printOutlets();
        companyA.addProduct("Nasi", 2000);
        companyA.printOutlets();
        companyA.printProducts();


    }
}