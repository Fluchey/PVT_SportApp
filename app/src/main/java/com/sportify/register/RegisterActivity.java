package com.sportify.register;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.sportify.util.Connector;

import sportapp.pvt_sportapp.R;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText email;
    private TextView wrongFormatMessage;

    private ProgressDialog dialog;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenterImpl(this);
        dialog = new ProgressDialog();
    }

    /**
     * Triggered when user clicks on sign up button
     *
     * @param v
     */
    public void signUpButtonClick(View v) {
        username = (EditText) findViewById(R.id.etRegisterUsername);
        password = (EditText) findViewById(R.id.etRegisterPassword);
        firstName = (EditText) findViewById(R.id.etRegisterFirstName);
        lastName = (EditText) findViewById(R.id.etRegisterLastName);
        phoneNumber = (EditText) findViewById(R.id.etRegisterPhoneNumber);
        email = (EditText) findViewById(R.id.etRegisterMail);
        wrongFormatMessage = (TextView) findViewById(R.id.tvSignUpMessage);

        registerPresenter.createAccount(username.getText().toString(),
                password.getText().toString(),
                firstName.getText().toString(),
                lastName.getText().toString(),
                phoneNumber.getText().toString(),
                email.getText().toString());

//        if (TextUtils.isEmpty(username.getText().toString())) {
//            wrongFormatMessage.setText("Invalid Username, try again");
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
//            wrongFormatMessage.setText("Invalid Email, try again");
//        } else if (TextUtils.isEmpty(password.getText().toString())) {
//            wrongFormatMessage.setText("Invalid Password, try again");
//        } else {
//            wrongFormatMessage.setText("");
//            /**
//             *  Convert to JSON object
//             */
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("user_id", username.getText().toString());
//                jsonObject.put("firstname", firstName.getText().toString());
//                jsonObject.put("lastname", lastName.getText().toString());
//                jsonObject.put("mobilnummer", phoneNumber.getText().toString());
//                jsonObject.put("user_mail", email.getText().toString());
//                jsonObject.put("password_user", password.getText().toString());
//                Log.d("JsonObject", jsonObject.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            /**
//             * Creates new asynctask which runs in background and tries to create new user
//             */
//            new RegisterRequest(this).execute(jsonObject.toString());
//        }
    }

    @Override
    public void showUserNameEmptyError() {
        wrongFormatMessage.setText("Invalid Username, try again");
    }

    @Override
    public void showPasswordEmptyError() {
        wrongFormatMessage.setText("Invalid Password, try again");
    }

    @Override
    public void showEmailWrongFormatError() {
        wrongFormatMessage.setText("Invalid Email, try again");
    }

    @Override
    public void showApiRequestMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void emptyErrorMessage() {
        wrongFormatMessage.setText("");
    }

    @Override
    public void showProgressDialog() {
        dialog.setMessage("Creating account, please wait");
        dialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private class RegisterRequest extends AsyncTask<String, RegisterActivity, Void> {
        private ProgressDialog dialog;
        private RegisterActivity ra;
        protected int responseCode;
        protected String responseBody;

        /**
         * Constructor
         *
         * @param registerActivity The activity the request is called from
         */
        public RegisterRequest(RegisterActivity registerActivity) {
            ra = registerActivity;
            dialog = new ProgressDialog(registerActivity);
        }

        /**
         * This happens first when the asynctask is created
         * Shows a dialog popup to the user
         */
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Creating account, please wait");
            dialog.show();
        }

        /**
         * This happens in the background.
         * Connects to API and saves responseBody and responseCode.
         *
         * @param params The JSON object to be sent to the REST API
         * @return
         */
        @Override
        protected Void doInBackground(String... params) {
            String[] resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/testsignup",
                    "PUT", String.format(params[0]));
            responseBody = resultFromApi[0];
            responseCode = Integer.parseInt(resultFromApi[1]);
            return null;
        }

        /**
         * Happens after doInBackground.
         * Dismisses the dialog popup.
         * Tells the user if account was created or if it failed.
         * This is decided by the responsecode.
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            /** Responsecode 201 maps to successfull account creation
             * */
            if (responseCode == 201) {
                Toast.makeText(ra, "Account created!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ra, responseBody, Toast.LENGTH_LONG).show();
            }
        }
    }
}

