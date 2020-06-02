package softeng2.teamhortons.myxa.ui.customer_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.menu.MenuActivity;
import softeng2.teamhortons.myxa.ui.rider_login.RiderLoginActivity;
import softeng2.teamhortons.myxa.ui.signup.SelectSignupActivity;

import static softeng2.teamhortons.myxa.generic.RequestCode.REQUEST_SIGNUP;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText emailEditText = findViewById(R.id.editText_email);
        final EditText passwordEditText = findViewById(R.id.editText_password);
        final Button loginButtonCustomer = findViewById(R.id.button_login_customer);

        //final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        findViewById(R.id.textView_register_link).setOnClickListener(v -> startActivityForResult(
                new Intent(getApplicationContext(), SelectSignupActivity.class),
                REQUEST_SIGNUP));

        findViewById(R.id.rider_login).setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), RiderLoginActivity.class)
        ));

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButtonCustomer.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getEmailError() != null) {
                emailEditText.setError(getString(loginFormState.getEmailError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            //loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                setResult(RESULT_OK, new Intent(LoginActivity.this, MenuActivity.class));
                finish();
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
                loginViewModel.loginDataChanged(emailEditText.getText().toString(),passwordEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.loginDataChanged(emailEditText.getText().toString(),passwordEditText.getText().toString());
            }
            return false;
        });

        loginButtonCustomer.setOnClickListener(v -> {
            //loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        this.finish();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SIGNUP && resultCode == RESULT_FIRST_USER) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
