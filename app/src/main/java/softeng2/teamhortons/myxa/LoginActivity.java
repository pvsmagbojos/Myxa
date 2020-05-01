package softeng2.teamhortons.myxa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView register;
    EditText email;
    EditText password;
    Button login;

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

        login = (Button)findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFunc();
            }
        });

    }

    //start registration
    public void register(){
        Intent intent = new Intent(LoginActivity.this,GeneralRegistrationActivity.class);
        startActivity(intent);
    }

    public void customerLogIn(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);//change main activity to customer home
        startActivity(intent);
    }

    public void riderLogIn(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);//change main activity to rider home
        startActivity(intent);
    }

    //temporary login function
    public void loginFunc(){
        String emailAdd = email.getText().toString();
        String pass = password.getText().toString();
        if(!emailAdd.isEmpty()){
            if(!pass.isEmpty()){
                //check email if exists on database
                if(emailAdd.equals("admin")) {
                    //check if rider or customer
                    //if(customer){
                        //check password if correct
                        if (pass.equals("password123")) {
                            customerLogIn();
                        } else {
                            //wrong password
                            password.setError("Your Password is incorrect!");
                        }
                /*     }else{
                        check password if correct
                        if (password.getText().toString().equals("password123")) {
                            riderLogIn();
                        }else{
                            //wrong password
                            password.setError("Your Password is incorrect!");
                        }
                    }*/
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
