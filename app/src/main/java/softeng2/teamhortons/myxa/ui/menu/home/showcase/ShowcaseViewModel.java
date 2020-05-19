package softeng2.teamhortons.myxa.ui.menu.home.showcase;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import softeng2.teamhortons.myxa.data.model.Category;
import softeng2.teamhortons.myxa.data.model.Recipe;
import softeng2.teamhortons.myxa.data.repository.ShowcaseRepository;

class ShowcaseViewModel extends ViewModel {

    private ShowcaseRepository showcaseRepository;

    private MutableLiveData<QueryResult> queryResult = new MutableLiveData<>();

    ShowcaseViewModel(ShowcaseRepository showcaseRepository) {
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
                .addOnSuccessListener(categoryDocumentSnapshots -> {
                    final ArrayList<Category> categories = new ArrayList<>();

                    for(DocumentSnapshot document : categoryDocumentSnapshots) {
                        final Category category = document.toObject(Category.class);
                        if (category != null) {
                            showcaseRepository.fetchRecipeListFromRemote(category.getTag())
                                .addOnSuccessListener(recipeDocumentSnapshots -> {
                                    category.setRecipes(new ArrayList<>());
                                    for(DocumentSnapshot document1 : recipeDocumentSnapshots) {
                                        category.getRecipes().add(document1.toObject(Recipe.class));
                                        Log.d("tag", category.getTag());
                                    }

                                    categories.add(category);
                                    queryResult.setValue(new QueryResult(categories));

                                });
                        }
                    }
                })
                    .addOnFailureListener(e -> queryResult.setValue(new QueryResult(e)));
        }
    }
}
