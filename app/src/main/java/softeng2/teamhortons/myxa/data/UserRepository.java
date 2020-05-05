package softeng2.teamhortons.myxa.data;

import com.google.firebase.auth.FirebaseUser;

/**
 * Class that requests authentication and user information from the Firebase database and
 * maintains an in-memory cache of user data.
 */
public class UserRepository {

    private static volatile UserRepository instance;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private FirebaseUser user = null; //TODO: Replace with user model

    // private constructor : singleton access
    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }
}
