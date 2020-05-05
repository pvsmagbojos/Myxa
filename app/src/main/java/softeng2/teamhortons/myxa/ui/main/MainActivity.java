package softeng2.teamhortons.myxa.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.data.AuthRepository;
import softeng2.teamhortons.myxa.ui.home.HomeActivity;
import softeng2.teamhortons.myxa.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    public static int REQUEST_CODE = 0;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this, new MainViewModelFactory())
                .get(MainViewModel.class);

        new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(mainViewModel.isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    startActivityForResult(
                            new Intent(getApplicationContext(), LoginActivity.class),
                            LoginActivity.REQUEST_CODE);
                }

            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LoginActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }
}