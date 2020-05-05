package softeng2.teamhortons.myxa.ui.signup.customer;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import softeng2.teamhortons.myxa.data.AuthRepository;
import softeng2.teamhortons.myxa.data.UserRepository;

/**
 * ViewModel provider factory to instantiate SignupViewModel.
 * Required given SignupViewModel has a non-empty constructor
 */
public class SignupViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignupViewModel.class)) {
            return (T) new SignupViewModel(AuthRepository.getInstance(FirebaseAuth.getInstance()),
                    UserRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
