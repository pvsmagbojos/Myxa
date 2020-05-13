package softeng2.teamhortons.myxa.ui.menu.fragment.showcase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Category;
import softeng2.teamhortons.myxa.data.model.Recipe;
import softeng2.teamhortons.myxa.data.repository.ShowcaseRepository;

class CategoryViewModel extends ViewModel {

    private ShowcaseRepository showcaseRepository;

    private MutableLiveData<QueryResult> queryResult = new MutableLiveData<>();

    CategoryViewModel(ShowcaseRepository showcaseRepository) {
        this.showcaseRepository = showcaseRepository;
    }

    LiveData<QueryResult> getQueryResult() {
        reload();
        return queryResult;
    }

    void reload() {
        if (this.queryResult.getValue() != null) {
            if(queryResult.getValue().getSuccess() != null) {
                queryResult.setValue(this.queryResult.getValue());
            }
        } else {
            showcaseRepository.fetchCategoryListFromRemote()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            final ArrayList<Category> categories = new ArrayList<>();

                            for(DocumentSnapshot document : queryDocumentSnapshots) {
                                final Category category = document.toObject(Category.class);
                                if (category != null) {
                                    showcaseRepository.fetchRecipeListFromRemote(category.getTag())
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    category.setRecipes(new ArrayList<Recipe>());
                                                    for(DocumentSnapshot document : queryDocumentSnapshots) {
                                                        category.getRecipes().add(document.toObject(Recipe.class));
                                                    }

                                                    categories.add(category);
                                                    queryResult.setValue(new QueryResult(categories));
                                                }
                                            });
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            queryResult.setValue(new QueryResult(e));
                        }
                    });
        }
    }
}
