package softeng2.teamhortons.myxa.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import softeng2.teamhortons.myxa.data.AuthRepository;
import softeng2.teamhortons.myxa.ui.main.MainViewModel;

/**
 * ViewModel provider factory to instantiate MainViewModel.
 * Required given MainViewModel has a non-empty constructor
 */
public class MainViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(AuthRepository.getInstance(FirebaseAuth.getInstance()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
