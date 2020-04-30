package softeng2.teamhortons.myxa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GeneralRegistrationActivity extends AppCompatActivity {
    Button customerPortal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_select_registration_type);

        customerPortal = findViewById(R.id.customer_portal_button);
        customerPortal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerRegistration();
            }
        });

    }

    public void customerRegistration(){
        Intent intent = new Intent(GeneralRegistrationActivity.this, CustomerRegistrationActivity.class);
        startActivity(intent);
    }
}
