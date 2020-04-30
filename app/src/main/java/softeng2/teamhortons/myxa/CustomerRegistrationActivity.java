package softeng2.teamhortons.myxa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CustomerRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        Spinner genderSpinner = findViewById(R.id.spinnerGender_c_gender);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(CustomerRegistrationActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.gender));
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
    }
}
