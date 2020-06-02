package softeng2.teamhortons.myxa.ui.rider_login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.repository.AuthRepository;

class RiderLoginViewModel extends ViewModel {

    private AuthRepository authRepository;

    private MutableLiveData<RiderLoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<RiderLoginResult> loginResult = new MutableLiveData<>();

    RiderLoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    LiveData<RiderLoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<RiderLoginResult> getLoginResult() {
        return loginResult;
    }

    void login(String email, String password) {
        authRepository.login(email, password).addOnCompleteListener(
                task -> task.addOnSuccessListener(
                        authResult -> loginResult.setValue(new RiderLoginResult(authResult.getUser())))
                .addOnFailureListener(
                        e -> loginResult.setValue(new RiderLoginResult(R.string.toast_login_failed))));
    }

    void loginDataChanged(String email, String password) {
        if(email.isEmpty()) {
            loginFormState.setValue(new RiderLoginFormState(R.string.empty_field, null));
        } else if (!isEmailValid(email)) {
            loginFormState.setValue(new RiderLoginFormState(R.string.invalid_email, null));
        }

        if(password.isEmpty()) {
            loginFormState.setValue(new RiderLoginFormState(null, R.string.empty_field));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new RiderLoginFormState(null, R.string.invalid_password));
        }

        if(isEmailValid(email) && isPasswordValid(password)) {
            loginFormState.setValue(new RiderLoginFormState(true));
        }
    }

    private boolean isEmailValid(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }
}
