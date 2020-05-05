package softeng2.teamhortons.myxa.ui.signup.customer;

import android.util.Log;
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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.AuthRepository;

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
        authRepository.login(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                signupResult.setValue(new SignupResult(authResult.getUser()));
                            }
                        });

                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                signupResult.setValue(new SignupResult(R.string.login_failed));
                            }
                        });
                    }
                });
    }

    void loginDataChanged(String fName, String lName, /*String gender,*/ String age, String email, String password, String confirmPassword) {
        //TODO: Add more input filters
        int notEmptyCtr = 0;
        if(fName.isEmpty()){
            signupFormState.setValue(new SignupFormState(
                    null,
                    R.string.empty_field,
                    null,
                    null,
                    null,
                    null,
                    null));
        }else{
            notEmptyCtr+=1;
        }

        if(lName.isEmpty()){
            signupFormState.setValue(new SignupFormState(
                    null,
                    null,
                    R.string.empty_field,
                    null,
                    null,
                    null,
                    null));
        }else{
            notEmptyCtr+=1;
        }

//        if(gender.equals(" ")){
//            signupFormState.setValue(new SignupFormState(
//                    null,
//                    null,
//                    null,
//                    null,
//                    null,
//                    R.string.empty_field,
//                    null));
//        }else{
//            notEmptyCtr+=1;
//        }

        if(age.isEmpty()){
            signupFormState.setValue(new SignupFormState(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    R.string.empty_field));
        }else{
            notEmptyCtr+=1;
        }

        if(email.isEmpty()){
            signupFormState.setValue(new SignupFormState(
                    R.string.empty_field,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null));
        } else if (!isEmailValid(email)) {
            signupFormState.setValue(new SignupFormState(
                    R.string.invalid_email,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null));
        }

        if(password.isEmpty()){
            signupFormState.setValue(new SignupFormState(
                    null,
                    null,
                    null,
                    R.string.empty_field,
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
        }

        if(confirmPassword.isEmpty()){
            signupFormState.setValue(new SignupFormState(
                    null,
                    null,
                    null,
                    null,
                    R.string.empty_field,
                    null,
                    null));
        } else if(confirmPassword.equals(password)){
            signupFormState.setValue(new SignupFormState(
                    null,
                    null,
                    null,
                    null,
                    R.string.invalid_confirm_password,
                    null,
                    null));
        }

        if(notEmptyCtr == 4 && isEmailValid(email) && isPasswordValid(password) && isConfirmPasswordValid(password,confirmPassword)){
            recordToDatabase(fName, lName, /*gender,*/ age, email, password, confirmPassword);
        }
    }

    private void recordToDatabase(String fName, String lName, /*String gender,*/ String age, String email, String password, String confirmPassword){
        //add to db, if successful - setvalue
        signupFormState.setValue(new SignupFormState(true));

        //add to db

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", fName);
        user.put("last", lName);
        user.put("age", age);
        //user.put("gender", gender);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("success", "Database connection Success at " + documentReference.getId());
                        //save to auth
                        //call here

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("error", "Database connection Error", e);
                    }
                });

    }

    private boolean isEmailValid(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }

    private boolean isConfirmPasswordValid(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }
}
