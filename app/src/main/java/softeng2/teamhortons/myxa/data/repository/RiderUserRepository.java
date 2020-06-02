package softeng2.teamhortons.myxa.data.repository;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import softeng2.teamhortons.myxa.data.model.RiderUser;
import softeng2.teamhortons.myxa.data.model.User;

/**
 * Class that requests authentication and user information from the Firebase database and
 * maintains an in-memory cache of user data.
 */
public class RiderUserRepository {

    private static volatile RiderUserRepository instance;
    private FirebaseFirestore dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private RiderUser user = null;

    // private constructor : singleton access
    private RiderUserRepository() {
        this.dataSource = FirebaseFirestore.getInstance();
    }

    public static RiderUserRepository getInstance() {
        if(instance == null){
            instance = new RiderUserRepository();
        }
        return instance;
    }

    public void recordToDatabase(String userId, String fName, String lName, boolean isMale, int age, String plate, String license, String email){
        final RiderUser user = new RiderUser(email, fName, lName, plate, license, age, isMale).withId(userId);

        dataSource.collection("riders").document(userId).set(user)
                .addOnSuccessListener(aVoid -> {
                    RiderUserRepository.this.user = user;
                    Log.d("SUCCESS", "Successfully recorded to database");
                }).addOnFailureListener(
                e -> Log.e("ERROR", "Database connection Error", e));
    }

    RiderUser getUser() {
        return this.user;
    }


    public Task<DocumentSnapshot> refreshUserData(String userId) {
        return dataSource.collection("riders").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.toObject(User.class) != null) {
                        user = documentSnapshot.toObject(RiderUser.class);
                        Log.d("SUCCESS", "Successfully retrieved user data");
                    } else {
                        Log.e("ERROR", "Data not found", new Exception());
                    }
                }).addOnFailureListener(
                        e -> Log.e("ERROR", "User data retrieval failed", e));
    }
}

