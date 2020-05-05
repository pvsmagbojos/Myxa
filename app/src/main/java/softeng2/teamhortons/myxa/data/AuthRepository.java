package softeng2.teamhortons.myxa.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class AuthRepository {

    private static volatile AuthRepository instance;

    private FirebaseAuth dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private FirebaseUser user = null;

    // private constructor : singleton access
    private AuthRepository(FirebaseAuth dataSource) {
        this.dataSource = dataSource;
    }

    public static AuthRepository getInstance(FirebaseAuth dataSource) {
        if(instance == null){
            instance = new AuthRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return this.user != null;
    }

    public void logout() {
        this.user = null;
        dataSource.signOut();
    }

    private void setLoggedInUser(FirebaseUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Task<AuthResult> login(String email, String password) {
        return dataSource.signInWithEmailAndPassword(email, password);
    }
}
