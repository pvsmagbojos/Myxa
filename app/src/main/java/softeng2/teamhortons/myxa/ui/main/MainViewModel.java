package softeng2.teamhortons.myxa.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import softeng2.teamhortons.myxa.data.model.User;
import softeng2.teamhortons.myxa.data.repository.AuthRepository;
import softeng2.teamhortons.myxa.data.repository.UserRepository;

class MainViewModel extends ViewModel {

    private AuthRepository authRepository;
    private UserRepository userRepository;

    private MutableLiveData<UserResult> result = new MutableLiveData<>();

    MainViewModel(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    boolean isLoggedIn() {
        return authRepository.isLoggedIn();
    }

    LiveData<UserResult> getResult() {
        reload();
        return result;
    }

    void reload() {
        userRepository.refreshUserData(authRepository.getLoggedInUserId())
            .addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                result.setValue(new UserResult(user));
                //TODO: Add withId method if id doesnt automatically get saved
            }).addOnFailureListener(e -> {
                result.setValue(new UserResult(e));
        });
    }
}
