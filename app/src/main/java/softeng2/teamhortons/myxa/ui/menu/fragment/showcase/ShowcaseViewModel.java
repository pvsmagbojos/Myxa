package softeng2.teamhortons.myxa.ui.menu.fragment.showcase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import softeng2.teamhortons.myxa.data.repository.ShowcaseRepository;

class ShowcaseViewModel extends ViewModel {

    private ShowcaseRepository showcaseRepository;

    private MutableLiveData<QueryResult> queryResult = new MutableLiveData<>();

    ShowcaseViewModel(ShowcaseRepository showcaseRepository) {
        this.showcaseRepository = showcaseRepository;
    }

    LiveData<QueryResult> getQueryResult() {
        if(showcaseRepository.getCategoryItems() == null) {
            loadData();
        }
        return queryResult;
    }

    private void loadData() {
        if(showcaseRepository.getCategoryItems() != null) {
            queryResult.setValue(new QueryResult(showcaseRepository.getCategoryItems()));
        }
        showcaseRepository.fetchDataFromRemote()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                queryResult.setValue(new QueryResult(showcaseRepository.getCategoryItems()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                queryResult.setValue(new QueryResult(e));
            }
        });
    }
}
