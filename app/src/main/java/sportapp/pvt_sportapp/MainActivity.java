package sportapp.pvt_sportapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity implements LayoutInflater {

    @Override
    protected void onCreate(Bundle savedInstanceState, LayoutInflater inflater, ViewGroup container) {
        super.onCreate(savedInstanceState);
        CallbackManager callbackManager;
        setContentView(R.layout.activity_main);

        TextView tvLoginRegisterHere = (TextView) findViewById(R.id.tvLoginRegisterHere);

        tvLoginRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        View view = inflater.inflate(R.layout.null, container, false); //layout.splash
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button;
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { ... });

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }

    }


}
