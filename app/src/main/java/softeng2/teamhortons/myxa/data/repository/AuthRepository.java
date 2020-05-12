package softeng2.teamhortons.myxa.data.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
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
        this.dataSource = FirebaseAuth.getInstance();
        this.user = this.dataSource.getCurrentUser();
        return this.user !=null;
    }

    private void setLoggedInUser(FirebaseUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Task<AuthResult> login(@NonNull String email, @NonNull String password) {
        return dataSource.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        setLoggedInUser(authResult.getUser());
                    }
                });
    }

    public Task<AuthResult> signUp(@NonNull String email, @NonNull String password) {
        return dataSource.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        setLoggedInUser(authResult.getUser());
                    }
                });
    }

    public void logout() {
        setLoggedInUser(null);
        dataSource.signOut();
    }
}
