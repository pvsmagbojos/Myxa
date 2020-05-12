package softeng2.teamhortons.myxa.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Category;
import softeng2.teamhortons.myxa.data.model.Recipe;

public class ShowcaseRepository {

    private static volatile ShowcaseRepository instance;
    private FirebaseFirestore dataSource;
    private ArrayList<Category> categories;

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

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public Task<QuerySnapshot> fetchCategoryItemsFromRemote() {
        return dataSource.collection("categories").limit(5).get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    ShowcaseRepository.this.categories = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        categories.add(document.toObject(Category.class));
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Error getting documents: ", e);
                }
            });
    }

    private Task<QuerySnapshot> fetchRecipeItemsFromRemote(final Category category) {
        return dataSource.collection("recipes")
                .whereArrayContains("tag", category.getTag()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Recipe> recipes = new ArrayList<>();

                        for(DocumentSnapshot document : queryDocumentSnapshots) {
                            recipes.add(document.toObject(Recipe.class));
                        }

                        category.setRecipes(recipes);
                    }
                });
    }
}
