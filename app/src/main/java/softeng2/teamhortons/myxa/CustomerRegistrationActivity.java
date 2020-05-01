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
    Spinner gender;
    EditText age;
    EditText email;
    EditText password;
    EditText cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        fName = (EditText)findViewById(R.id.editText_c_firstname);
        lName = (EditText)findViewById(R.id.editText_c_lastname);
        age = (EditText)findViewById(R.id.editText_c_age);
        gender = findViewById(R.id.spinnerGender_c_gender);
        email = (EditText)findViewById(R.id.editText_c_email);
        password = (EditText)findViewById(R.id.editText_c_password);
        cpassword = (EditText)findViewById(R.id.editText7_c_confirmpassword);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(CustomerRegistrationActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.gender));
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);



        
        customerSignUp = findViewById(R.id.button_c_signup);
        customerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFunc();
            }
        });

        gender.getSelectedItem().toString();

        signUpFunc();
    }

    public void signUpFunc(){

    }
}
