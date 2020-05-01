package softeng2.teamhortons.myxa.data;

import softeng2.teamhortons.myxa.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class AuthDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            return null;
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
