package softeng2.teamhortons.myxa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import softeng2.teamhortons.myxa.ui.home.HomeActivity;
import softeng2.teamhortons.myxa.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class),1203);
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1203 && resultCode == RESULT_OK) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }
}
