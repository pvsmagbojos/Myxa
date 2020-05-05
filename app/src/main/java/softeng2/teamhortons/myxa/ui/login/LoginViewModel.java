package softeng2.teamhortons.myxa.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.AuthRepository;
import softeng2.teamhortons.myxa.data.Result;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private AuthRepository authRepository;

    LoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        Result<FirebaseUser> result = authRepository.login(email, password);

        if (result instanceof Result.Success) {
            FirebaseUser data = ((Result.Success<FirebaseUser>) result).getData();
            loginResult.setValue(new LoginResult(data));
        } else {
            // TODO: Add more failure information
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    void loginDataChanged(String email, String password) {
        if(email.isEmpty()) {
            loginFormState.setValue(new LoginFormState(R.string.empty_field, null));
        } else if (!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
        }

        if(password.isEmpty()) {
            loginFormState.setValue(new LoginFormState(null, R.string.empty_field));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        }

        if(isEmailValid(email) && isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isEmailValid(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }
}
