package softeng2.teamhortons.myxa.ui.signup.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import softeng2.teamhortons.myxa.R;

public class RiderSignUpActivity extends AppCompatActivity {

    Button continueSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_signup_screen1);
        continueSignUp = findViewById(R.id.button_continue_rider);
        continueSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueRegistration();
            }
        });
    }

    public void continueRegistration(){
        setContentView(R.layout.activity_rider_signup_screen2);
    }
}
