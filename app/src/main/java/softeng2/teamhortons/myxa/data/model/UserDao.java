package softeng2.teamhortons.myxa.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class UserDao {

    private String userId;
    private String displayName;

    public UserDao (String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
