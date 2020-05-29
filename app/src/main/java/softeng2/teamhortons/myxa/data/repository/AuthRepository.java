package softeng2.teamhortons.myxa.data.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class that requests authentication and user information from Firebase authentication servers and
 * maintains an in-memory cache of login status and user information.
 */
public class AuthRepository {

    private static volatile AuthRepository instance;
    private FirebaseAuth dataSource;

    // private constructor : singleton access
    private AuthRepository() {
        this.dataSource = FirebaseAuth.getInstance();
    }

    public static AuthRepository getInstance() {
        if(instance == null){
            instance = new AuthRepository();
        }
        return instance;
    }

    public String getLoggedInUserId() {
        return dataSource.getCurrentUser().getUid();
    }

    public boolean isLoggedIn() {
        return dataSource.getCurrentUser() != null;
    }

    public Task<AuthResult> login(@NonNull String email, @NonNull String password) {
        return dataSource.signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> signUp(@NonNull String email, @NonNull String password) {
        return dataSource.createUserWithEmailAndPassword(email, password);
    }

//    public void logout() {
//        dataSource.signOut();
//    }
}
