package softeng2.teamhortons.myxa.ui.signup.customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.home.HomeActivity;
import softeng2.teamhortons.myxa.ui.login.LoginActivity;

public class SignupActivity extends AppCompatActivity {

    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        final EditText fNameEditText = findViewById(R.id.editText_first_name);
        final EditText lNameEditText = findViewById(R.id.editText_last_name);
        final Spinner genderSpinner = findViewById(R.id.spinner_gender);
        final EditText ageEditText = findViewById(R.id.editText_age);
        final EditText emailEditText = findViewById(R.id.editText_email);
        final EditText passwordEditText = findViewById(R.id.editText_password);
        final EditText confirmPasswordEditText = findViewById(R.id.editText_confirm_password);
        final Button signupButton = findViewById(R.id.button_signup);

        signupViewModel = ViewModelProviders.of(this, new SignupViewModelFactory())
                .get(SignupViewModel.class);

        signupViewModel.getSignupFormState().observe(this, new Observer<SignupFormState>() {
            @Override
            public void onChanged(@Nullable SignupFormState signupFormState) {
                if (signupFormState == null) {
                    return;
                }
                signupButton.setEnabled(signupFormState.isDataValid());

                if (signupFormState.getFirstNameError() != null) {
                    fNameEditText.setError(getString(signupFormState.getFirstNameError()));
                }
                if (signupFormState.getLastNameError() != null) {
                    lNameEditText.setError(getString(signupFormState.getLastNameError()));
                }
//                if (signupFormState.getGenderError() != null) {
//                    genderSpinner.setError(getString(signupFormState.getGenderError()));
//                }
                if (signupFormState.getAgeError() != null) {
                    ageEditText.setError(getString(signupFormState.getAgeError()));
                }
                if (signupFormState.getEmailError() != null) {
                    emailEditText.setError(getString(signupFormState.getEmailError()));
                }
                if (signupFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(signupFormState.getPasswordError()));
                }
                if (signupFormState.getConfirmPasswordError() != null) {
                    confirmPasswordEditText.setError(getString(signupFormState.getConfirmPasswordError()));
                }
            }
        });

        signupViewModel.getSignupResult().observe(this, new Observer<SignupResult>() {
            @Override
            public void onChanged(@Nullable SignupResult signupResult) {
                if (signupResult == null) {
                    return;
                }
                //loadingProgressBar.setVisibility(View.GONE);
                if (signupResult.getError() != null) {
                    showLoginFailed(signupResult.getError());
                }
                if (signupResult.getSuccess() != null) {
                    Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                    startActivity(intent);
                    updateUiWithUser(signupResult.getSuccess());
                    finish();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                signupViewModel.loginDataChanged(fNameEditText.getText().toString(), lNameEditText.getText().toString(), /*genderSpinner.getSelectedItem().toString(),*/ ageEditText.getText().toString(), emailEditText.getText().toString(),
                        passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
            }
        };

        fNameEditText.addTextChangedListener(afterTextChangedListener);
        lNameEditText.addTextChangedListener(afterTextChangedListener);
        //genderSpinner.addTextChangedListener(afterTextChangedListener);
        ageEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);

        confirmPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signupViewModel.loginDataChanged(fNameEditText.getText().toString(), lNameEditText.getText().toString(), /*genderSpinner.getSelectedItem().toString(),*/ ageEditText.getText().toString(), emailEditText.getText().toString(),
                            passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
                }
                return false;
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadingProgressBar.setVisibility(View.VISIBLE);
                //TODO: Implement signup
                signupViewModel.login(emailEditText.getText().toString(),passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
