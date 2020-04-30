package softeng2.teamhortons.myxa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView register;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.editText_email);
        password = (EditText)findViewById(R.id.editText_password);

        register = (TextView)findViewById(R.id.textView_register_link);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    //start registration
    public void register(){
        Intent intent = new Intent(LoginActivity.this,GeneralRegistrationActivity.class);
        startActivity(intent);
    }

    public void customerLogIn(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //temporary login function
    public void loginFunc(View view){

        if(!email.getText().toString().isEmpty()){
            if(!password.getText().toString().isEmpty()){
                //check email if exists on database
                if(email.getText().toString().equals("admin")) {
                    //check if rider or customer
                    //if(customer){
                        //check password if correct
                        if (password.getText().toString().equals("password123")) {
                            customerLogIn();
                        } else {
                            //wrong password
                            password.setError("Your Password is incorrect!");
                        }
                    //}else{
                    // check password if correct
                    //   if(correct password){
                            //
                    //   }else{
                            //password.setError("Your Password is incorrect!");
                        //}
                    // }

                }else{
                    //email doesn't exist
                    email.setError("Email doesn't exist!");
                }
            }else{
                //password required
                password.setError("Password is required!");
            }
        }else{
            //email required
            email.setError("Email is required!");
        }
    }
}
