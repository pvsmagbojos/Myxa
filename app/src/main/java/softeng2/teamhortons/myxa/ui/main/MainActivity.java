package softeng2.teamhortons.myxa.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.login.LoginActivity;
import softeng2.teamhortons.myxa.ui.menu.MenuActivity;

import static softeng2.teamhortons.myxa.generic.RequestCode.REQUEST_LOGIN;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this, new MainViewModelFactory())
                .get(MainViewModel.class);

        new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(mainViewModel.isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    finish();
                } else {
                    startActivityForResult(
                            new Intent(getApplicationContext(), LoginActivity.class),
                            REQUEST_LOGIN);
                }
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            startActivity(data);
        }

        if(resultCode == RESULT_CANCELED) {
            finish();
        }
    }
}
