package BE;

public class User {
    private final int userId, companyId, outletId;
    private String name, email, role, outletName;

    public User(int userId, int companyId, int outletId, String name, String email, String role, String outletName) {
        this.userId = userId;
        this.companyId = companyId;
        this.outletId = outletId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.outletName = outletName;
    }

    public int getUserId() {
        return userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getOutletId() {
        return outletId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
}
