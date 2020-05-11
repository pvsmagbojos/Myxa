package softeng2.teamhortons.myxa.ui.menu.fragment.showcase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import softeng2.teamhortons.myxa.data.repository.ShowcaseRepository;

public class ShowcaseViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShowcaseViewModel.class)) {
            return (T) new ShowcaseViewModel(ShowcaseRepository.getInstance(FirebaseFirestore.getInstance()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
