package BE;
public abstract class UserController {
    protected final int userId, companyId;

    public UserController(int userId, int companyId) {
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
