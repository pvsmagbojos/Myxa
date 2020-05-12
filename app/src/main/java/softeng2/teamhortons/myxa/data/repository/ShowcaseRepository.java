package softeng2.teamhortons.myxa.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.CategoryItem;

public class ShowcaseRepository {

    private static volatile ShowcaseRepository instance;
    private FirebaseFirestore dataSource;
    private ArrayList<CategoryItem> categoryItems;

    private final String TAG = "ShowcaseRepository";

    // private constructor : singleton access
    private ShowcaseRepository(FirebaseFirestore dataSource) {
        this.dataSource = dataSource;
    }

    public static ShowcaseRepository getInstance(FirebaseFirestore dataSource) {
        if(instance == null){
            instance = new ShowcaseRepository(dataSource);
        }
        return instance;
    }

    public ArrayList<CategoryItem> getCategoryItems() {
        return categoryItems;
    }

    public Task<QuerySnapshot> fetchDataFromRemote() {
        return dataSource.collection("categories").limit(5).get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            ShowcaseRepository.this.categoryItems = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                categoryItems.add(document.toObject(CategoryItem.class));
                            }
                        }
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
    }
}
