package softeng2.teamhortons.myxa.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.util.Patterns;
import android.widget.EditText;

import softeng2.teamhortons.myxa.data.AuthRepository;
import softeng2.teamhortons.myxa.data.Result;
import softeng2.teamhortons.myxa.data.model.LoggedInUser;
import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.homepage.HomePageActivity;

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
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = authRepository.login(email, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            // TODO: Add more failure information
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }
    //not workingz
    boolean loginDataChanged(String email, String password) {
        //TODO: Add more input filters
        //if email ay walang laman - loginFormState.setValue(new LoginFormState("FIELD MUST NOT BE EMPTY", null));

//        if (!isEmailValid(email)) {
//            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
//        } else if (!isPasswordValid(password)) {
//            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
//        } else {
//            loginFormState.setValue(new LoginFormState(true));
//        }
        boolean validEmail = false;
        boolean validPass = false;

        if(!email.isEmpty()){
            if(isEmailValid(email)){
                validEmail=true;
            }else{
                loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
            }
        }else{
            loginFormState.setValue(new LoginFormState(R.string.empty_field,null));
        }

        if(!password.isEmpty()){
            if(isPasswordValid(password)){
                validPass = true;
            }else{
                loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
            }
        }else{
            loginFormState.setValue(new LoginFormState(null, R.string.empty_field));
        }

        boolean valid = false;
        if(validEmail && validPass){
            valid = true;
        }

        return valid;

//        //loadingProgressBar.setVisibility(View.VISIBLE);
//        //loginViewModel.login(emailEditText.getText().toString(),passwordEditText.getText().toString());

    }

    /*private*/protected boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        else{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
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
