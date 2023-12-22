package BE;
public abstract class User {
    protected final int userId, companyId;

    public User(int userId, int companyId) {
        this.userId = userId;
        this.companyId = companyId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCompanyId() {
        return companyId;
    }
}
