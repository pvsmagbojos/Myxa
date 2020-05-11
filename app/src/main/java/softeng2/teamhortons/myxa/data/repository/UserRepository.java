package softeng2.teamhortons.myxa.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import softeng2.teamhortons.myxa.data.model.User;

/**
 * Class that requests authentication and user information from the Firebase database and
 * maintains an in-memory cache of user data.
 */
public class UserRepository {

    private static volatile UserRepository instance;
    private FirebaseFirestore dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;
    private String userId = null;

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
        final User user = new User(email, fName, lName, age, isMale).withId(userId);

        dataSource.collection("users").document(userId).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UserRepository.this.user = user;
                        Log.d("SUCCESS", "Successfully recorded to database");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ERROR", "Database connection Error", e);
            }
        });
    }

    public User getUser() {
        return this.user;
    }

    public Task<DocumentSnapshot> setUser(String userId) {
        this.userId = userId;
        return refreshUserData();
    }

    private Task<DocumentSnapshot> refreshUserData() {
        return dataSource.collection("users").document(this.userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.toObject(User.class) != null) {
                            UserRepository.this.user = documentSnapshot.toObject(User.class);
                            Log.d("SUCCESS", "Successfully retrieved user data");
                        } else {
                            Log.e("ERROR", "Data not found", new Exception());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ERROR", "User data retrieval failed", e);
            }
        });
    }
}
