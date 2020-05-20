package softeng2.teamhortons.myxa.data.repository;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DeliveryRepository {

    private static volatile DeliveryRepository instance;
    private FirebaseFirestore dataSource;

    private final String TAG = "DeliveryRepository";

    // private constructor : singleton access
    private DeliveryRepository(FirebaseFirestore dataSource) {
        this.dataSource = dataSource;
    }

    public static DeliveryRepository getInstance() {
        if(instance == null){
            instance = new DeliveryRepository(FirebaseFirestore.getInstance());
        }
        return instance;
    }

    public Task<QuerySnapshot> fetchDeliveryListFromRemote() {
        DocumentReference ref = dataSource.collection("users")
                .document(UserRepository.getInstance().getUser().getId());
        Log.d("OrderQueryRef", ref.toString());
        return dataSource.collection("orders").get().addOnSuccessListener(
                queryDocumentSnapshots -> {
            for(DocumentSnapshot doc : queryDocumentSnapshots) {
                Log.d("OrderQuery", doc.getData().toString());
            }
        });
    }
}
