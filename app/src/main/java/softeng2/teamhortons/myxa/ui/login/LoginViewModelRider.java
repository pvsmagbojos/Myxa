package softeng2.teamhortons.myxa.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.repository.AuthRepository;

class LoginViewModelRider extends ViewModel {

    private AuthRepository authRepository;

    private MutableLiveData<LoginFormStateRider> loginFormStateRider = new MutableLiveData<>();
    private MutableLiveData<LoginResultRider> loginResultRider = new MutableLiveData<>();

    LoginViewModelRider(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    LiveData<LoginResultRider> getLoginResultRider() {
        return loginResultRider;
    }

    void loginRider(String email, String password) {
        authRepository.login(email, password).addOnCompleteListener(
                task -> task.addOnSuccessListener(
                        authResult -> loginResultRider.setValue(new LoginResultRider(authResult.getUser())))
                .addOnFailureListener(
                        e -> loginResultRider.setValue(new LoginResultRider(R.string.toast_login_failed))));
    }

}
