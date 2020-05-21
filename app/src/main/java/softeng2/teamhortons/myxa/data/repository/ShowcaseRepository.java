package softeng2.teamhortons.myxa.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ShowcaseRepository {

    private static volatile ShowcaseRepository instance;
    private FirebaseFirestore dataSource;

    private final String TAG = "ShowcaseRepository";

    // private constructor : singleton access
    private ShowcaseRepository() {
        this.dataSource = FirebaseFirestore.getInstance();
    }

    public static ShowcaseRepository getInstance() {
        if(instance == null){
            instance = new ShowcaseRepository();
        }
        return instance;
    }

    public Task<QuerySnapshot> fetchCategoryListFromRemote() {
        return dataSource.collection("categories").limit(5).get();
    }

    public Task<QuerySnapshot> fetchRecipeListFromRemote(String tag) {
        return dataSource.collection("recipes")
                .whereArrayContains("tags", tag).get();
    }
}
