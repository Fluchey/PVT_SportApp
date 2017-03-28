package sportapp.pvt_sportapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button signUpButton = (Button) findViewById(R.id.registerButton);

        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.etRegisterUsername);
                EditText password = (EditText) findViewById(R.id.etRegisterPassword);
                EditText email = (EditText) findViewById(R.id.etRegisterEmail);
                TextView message = (TextView) findViewById(R.id.tvSignUpMessage);

                if (TextUtils.isEmpty(username.getText().toString())) {
                    message.setText("Invalid Username, try again");
                }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    message.setText("Invalid Email, try again");
                }else if (TextUtils.isEmpty(password.getText().toString())) {
                    message.setText("Invalid Password, try again");
                }
                //else{
                // skapa användare, pusha till databas
                // hoppa vidare till någon slags "profil"}
            }
        } );
    }
}

