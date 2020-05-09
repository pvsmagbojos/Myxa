package softeng2.teamhortons.myxa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import softeng2.teamhortons.myxa.ui.signup.customer.SignupActivity;
import softeng2.teamhortons.myxa.ui.signup.rider.RiderSignUpActivity;

import static softeng2.teamhortons.myxa.generic.RequestCode.REQUEST_SIGNUP_CUSTOMER;
import static softeng2.teamhortons.myxa.generic.RequestCode.REQUEST_SIGNUP_RIDER;

public class SelectSignupActivity extends AppCompatActivity {
    Button customerPortal;
    Button riderPortal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_signup);

        customerPortal = findViewById(R.id.customer_portal_button);
        customerPortal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(),
                                SignupActivity.class), REQUEST_SIGNUP_CUSTOMER);
            }
        });

        riderPortal = findViewById(R.id.rider_portal_button);
        riderPortal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getApplicationContext(),
                                RiderSignUpActivity.class), REQUEST_SIGNUP_RIDER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_FIRST_USER) {
            setResult(RESULT_FIRST_USER, data);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }
}
