package softeng2.teamhortons.myxa.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import softeng2.teamhortons.myxa.R;
import softeng2.teamhortons.myxa.ui.customer_login.LoginActivity;
import softeng2.teamhortons.myxa.ui.menu.MenuActivity;

import static softeng2.teamhortons.myxa.generic.RequestCode.REQUEST_LOGIN;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this, new MainViewModelFactory())
                .get(MainViewModel.class);

        if(mainViewModel.isLoggedIn()) {
            mainViewModel.getResult().observe(this, result -> {
                if(result.getSuccess() != null) {
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    finish();
                } else {
                    Log.e(TAG, "Error occurred while loading user data", result.getError());
                    Toast.makeText(this, "Attempt to get user data failed, retrying...", Toast.LENGTH_SHORT).show();

                    new CountDownTimer(3000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            //ignore
                        }

                        @Override
                        public void onFinish() {
                            mainViewModel.reload();
                        }
                    }.start();
                }
            });
        } else {
            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //ignore
                }

                @Override
                public void onFinish() {
                    startActivityForResult(
                            new Intent(getApplicationContext(), LoginActivity.class),
                            REQUEST_LOGIN);
                }
            }.start();

        }
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
