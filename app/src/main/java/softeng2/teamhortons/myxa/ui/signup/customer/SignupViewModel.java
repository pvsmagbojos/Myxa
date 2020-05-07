package softeng2.teamhortons.myxa.ui.signup.customer;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.AuthRepository;
import softeng2.teamhortons.myxa.data.UserRepository;

class SignupViewModel extends ViewModel {

    private MutableLiveData<SignupFormState> signupFormState = new MutableLiveData<>();
    private MutableLiveData<SignupResult> signupResult = new MutableLiveData<>();
    private AuthRepository authRepository;
    private UserRepository userRepository;

    SignupViewModel(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    LiveData<SignupFormState> getSignupFormState() {
        return signupFormState;
    }

    LiveData<SignupResult> getSignupResult() {
        return signupResult;
    }

    void signUp(final String firstName, final String lastName, final boolean isMale, final int age,
                final String email, String password) {
        authRepository.signUp(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                signupResult.setValue(new SignupResult(authResult.getUser()));
                                userRepository.recordToDatabase(
                                        authResult.getUser().getUid(),
                                        firstName,
                                        lastName,
                                        isMale,
                                        age,
                                        email);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                signupResult.setValue(new SignupResult(R.string.signup_failed));
                            }
                        });
                    }
                });
    }
    void signUpDataChanged(String fName, String lName, String gender, String age,String email,
            String password, String confirmPassword) {

        signupFormState.setValue(new SignupFormState(
                (fName.isEmpty() ? R.string.empty_field : null),
                (lName.isEmpty() ? R.string.empty_field : null),
                (gender.isEmpty() ? R.string.empty_field : null),
                (age.isEmpty() ? R.string.empty_field : null),
                (email.isEmpty() ? R.string.empty_field : null),
                (password.isEmpty() ? R.string.empty_field : null),
                (confirmPassword.isEmpty() ? R.string.empty_field : null)
        ));

        signupFormState.setValue(new SignupFormState(
                null, null, null, null,
                (!email.isEmpty() && (isEmailValid(email)) ? null : R.string.invalid_email),
                ((!password.isEmpty() && confirmPassword.isEmpty() && !isPasswordValid(password))
                        ? R.string.invalid_password : null),
                ((password.isEmpty() && !confirmPassword.isEmpty() && !isPasswordValid(confirmPassword))
                        ? R.string.invalid_password : null)
        ));

        if(!isConfirmPasswordValid(password,confirmPassword)) {
            signupFormState.setValue(new SignupFormState(
                    null, null, null, null, null,
                    R.string.invalid_confirm_password,
                    R.string.invalid_confirm_password));
        }

        if(!isFieldsEmpty(fName, lName, gender, age) && isEmailValid(email) &&
                isPasswordValid(password) && isConfirmPasswordValid(password, confirmPassword)) {
            signupFormState.setValue(new SignupFormState(true));
        }
    }

    private boolean isEmailValid(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 8;
    }

    private boolean isConfirmPasswordValid(String password, String confirmPassword) {
        return confirmPassword.equals(password);
    }

    private boolean isFieldsEmpty(String firstName, String lastName, String gender, String age) {
        return firstName.isEmpty() && lastName.isEmpty() && gender.isEmpty() && age.isEmpty();
    }
}
