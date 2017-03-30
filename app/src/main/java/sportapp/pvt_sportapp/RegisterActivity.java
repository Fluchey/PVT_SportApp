package sportapp.pvt_sportapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button signUpButton = (Button) findViewById(R.id.registerButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.etRegisterUsername);
                EditText password = (EditText) findViewById(R.id.etRegisterPassword);
                EditText firstName = (EditText) findViewById(R.id.etRegisterFirstName);
                EditText lastName = (EditText) findViewById(R.id.etRegisterLastName);
                EditText phoneNumber = (EditText) findViewById(R.id.etRegisterPhoneNumber);
                EditText email = (EditText) findViewById(R.id.etRegisterMail);
                TextView message = (TextView) findViewById(R.id.tvSignUpMessage);

                if (TextUtils.isEmpty(username.getText().toString())) {
                    message.setText("Invalid Username, try again");
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    message.setText("Invalid Email, try again");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    message.setText("Invalid Password, try again");
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("user_id",username.getText().toString());
                        jsonObject.put("firstname", firstName.getText().toString());
                        jsonObject.put("lastname", lastName.getText().toString());
                        jsonObject.put("mobilnummer", phoneNumber.getText().toString());
                        jsonObject.put("user_mail", email.getText().toString());
                        jsonObject.put("password_user", password.getText().toString());
                        Log.d("JsonObject", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

