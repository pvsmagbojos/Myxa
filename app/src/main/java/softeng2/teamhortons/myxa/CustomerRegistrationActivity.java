package softeng2.teamhortons.myxa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CustomerRegistrationActivity extends AppCompatActivity {
    Button customerSignUp;
    EditText fName;
    EditText lName;
    EditText gender;
    EditText age;
    EditText email;
    EditText password;
    EditText cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        Spinner genderSpinner = findViewById(R.id.spinnerGender_c_gender);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(CustomerRegistrationActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.gender));
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        fName = findViewById(R.id.editText_c_firstname);
        lName = findViewById(R.id.editText_c_lastname);
        //gender = findViewById(R.id.editText_c_gender);
        age = findViewById(R.id.editText_c_age);
        email = findViewById(R.id.editText_c_email);
        password = findViewById(R.id.editText_c_password);
        cpassword = findViewById(R.id.editText7_c_confirmpassword);
        
        customerSignUp = findViewById(R.id.button_c_signup);
        customerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFunc();
            }
        });


    }

    public void signUpFunc(){

    }
}
