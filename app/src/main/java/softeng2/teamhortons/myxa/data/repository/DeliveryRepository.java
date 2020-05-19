package softeng2.teamhortons.myxa.data.repository;

import com.google.android.gms.tasks.Task;
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
        return dataSource.collection("orders").whereEqualTo("userRef",
                UserRepository.getInstance().getUser().getId()).get();
    }
}
