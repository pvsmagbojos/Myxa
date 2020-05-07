package softeng2.teamhortons.myxa.ui.signup.customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
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
import softeng2.teamhortons.myxa.ui.menu.MenuActivity;

public class SignupActivity extends AppCompatActivity {

    public static int REQUEST_CODE = 2;
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
                if (signupFormState.getGenderError() != null) {
                    ((TextView)genderSpinner.getSelectedView()).setError(getString(signupFormState.getGenderError()));
                }
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
                    setResult(RESULT_FIRST_USER, new Intent(SignupActivity.this, MenuActivity.class));
                    finish();
                }
            }
        });

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                signupViewModel.signUpDataChanged(
                        fNameEditText.getText().toString(),
                        lNameEditText.getText().toString(),
                        genderSpinner.getSelectedItem().toString(),
                        ageEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        confirmPasswordEditText.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //ignore
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
                signupViewModel.signUpDataChanged(
                        fNameEditText.getText().toString(),
                        lNameEditText.getText().toString(),
                        genderSpinner.getSelectedItem().toString(),
                        ageEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        confirmPasswordEditText.getText().toString());
            }
        };

        fNameEditText.addTextChangedListener(afterTextChangedListener);
        lNameEditText.addTextChangedListener(afterTextChangedListener);
        ageEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);

        confirmPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signupViewModel.signUp(fNameEditText.getText().toString(),
                            lNameEditText.getText().toString(),
                            genderSpinner.getSelectedItem().toString().equals("Male"),
                            Integer.parseInt(ageEditText.getText().toString()),
                            emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadingProgressBar.setVisibility(View.VISIBLE);
                signupViewModel.signUp(fNameEditText.getText().toString(),
                        lNameEditText.getText().toString(),
                        genderSpinner.getSelectedItem().toString().equals("Male"),
                        Integer.parseInt(ageEditText.getText().toString()),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }
}
