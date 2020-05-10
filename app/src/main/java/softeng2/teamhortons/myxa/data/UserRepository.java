package softeng2.teamhortons.myxa.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import softeng2.teamhortons.myxa.data.model.UserDao;

/**
 * Class that requests authentication and user information from the Firebase database and
 * maintains an in-memory cache of user data.
 */
public class UserRepository {

    private static volatile UserRepository instance;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private UserDao user = null; //TODO: Replace with user model

    // private constructor : singleton access
    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    public void recordToDatabase(final String userId, String fName, String lName, boolean isMale, int age, String email){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        this.user = new UserDao(userId, email, fName, lName, age, isMale);

        // Add a new document with a generated ID
        db.collection("users").document(userId).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("SUCCESS", "Successfully recorded to database");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ERROR", "Database connection Error", e);
            }
        });
    }
}
