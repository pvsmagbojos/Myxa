package softeng2.teamhortons.myxa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import softeng2.teamhortons.myxa.ui.signup.customer.SignupActivity;

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
                customerRegistration();
            }
        });

        riderPortal = findViewById(R.id.rider_portal_button);
        riderPortal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riderRegistration();
            }
        });

    }

    public void customerRegistration(){
        Intent intent = new Intent(SelectSignupActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    public void riderRegistration(){
        Intent intent = new Intent(SelectSignupActivity.this, SignupActivity.class);//change customerreg to riderreg class
        startActivity(intent);
    }
}
