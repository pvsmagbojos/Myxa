package softeng2.teamhortons.myxa.ui.signup.customer;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.AuthRepository;
import softeng2.teamhortons.myxa.data.Result;
import softeng2.teamhortons.myxa.data.model.LoggedInUser;

public class SignupViewModel extends ViewModel {

    private MutableLiveData<SignupFormState> signupFormState = new MutableLiveData<>();
    private MutableLiveData<SignupResult> signupResult = new MutableLiveData<>();
    private AuthRepository authRepository;

    SignupViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    LiveData<SignupFormState> getSignupFormState() {
        return signupFormState;
    }

    LiveData<SignupResult> getSignupResult() {
        return signupResult;
    }

    public void login(String email, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = authRepository.login(email, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            signupResult.setValue(new SignupResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            // TODO: Add more failure information
            signupResult.setValue(new SignupResult(R.string.login_failed));
        }
    }

    void loginDataChanged(String email, String password) {
        //TODO: Add more input filters
        if (!isEmailValid(email)) {
            signupFormState.setValue(new SignupFormState(
                    R.string.invalid_email,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null));
        } else if (!isPasswordValid(password)) {
            signupFormState.setValue(new SignupFormState(
                    null,
                    null,
                    null,
                    R.string.invalid_password,
                    null,
                    null,
                    null));
        } else {
            signupFormState.setValue(new SignupFormState(true));
        }
    }

    /*private*/protected boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }else{
            String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            return email.matches(regex);
        }

//        if (email.contains("@")) {
//            ///return Patterns.EMAIL_ADDRESS.matcher(email).matches();
//            return true;
//        } else {
//            //return !email.trim().isEmpty();
//            return false;
//        }
    }

    /*private*/protected boolean isPasswordValid(String password) {
        // return password != null && password.trim().length() > 8;
        if (password != null && password.trim().length() > 8) {
            return true;
        }else{
            return false;
        }
    }
}
