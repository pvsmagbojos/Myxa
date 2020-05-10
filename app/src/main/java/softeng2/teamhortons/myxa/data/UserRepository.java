package softeng2.teamhortons.myxa.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private FirebaseFirestore dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private UserDao user = null;

    // private constructor : singleton access
    private UserRepository(FirebaseFirestore dataSource) {
        this.dataSource = dataSource;
    }

    public static UserRepository getInstance(FirebaseFirestore dataSource) {
        if(instance == null){
            instance = new UserRepository(dataSource);
        }
        return instance;
    }

    public void recordToDatabase(String userId, String fName, String lName, boolean isMale, int age, String email){
        this.user = new UserDao(userId, email, fName, lName, age, isMale);

        // Add a new document with a generated ID
        dataSource.collection("users").document(userId).set(user)
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

    public void setUser(final String userId) {
        dataSource.collection("users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(UserDao.class);
                        user.id(documentSnapshot.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
