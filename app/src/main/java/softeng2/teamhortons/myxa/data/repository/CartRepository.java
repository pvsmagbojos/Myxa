package softeng2.teamhortons.myxa.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CartRepository {
    private static volatile CartRepository instance;
    private FirebaseFirestore dataSource;

    private final String TAG = "CartRepository";

    private CartRepository(FirebaseFirestore dataSource) {
        this.dataSource = dataSource;
    }

    public static CartRepository getInstance(FirebaseFirestore dataSource) {
        if(instance == null){
            instance = new CartRepository(dataSource);
        }
        return instance;
    }

    public Task<QuerySnapshot> fetchCartListFromRemote(String uid) {
        return dataSource.collection("carts").whereEqualTo("uid", uid).limit(1).get();
    }

    public Task<DocumentSnapshot> fetchRecipeFromRemote(String recipeId) {
        return dataSource.collection("recipes")
                .document(recipeId).get();
    }
}
