package softeng2.teamhortons.myxa.ui.signup.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import softeng2.teamhortons.myxa.MainActivity;
import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.login.LoginActivity;

public class RiderSignUpActivity extends AppCompatActivity {

    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_signup_scroll);

        signUpButton = findViewById(R.id.rider_signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLogin();
            }
        });
    }

    public void backToLogin(){
        Intent intent = new Intent(RiderSignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
