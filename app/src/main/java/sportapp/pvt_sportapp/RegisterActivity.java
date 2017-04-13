package sportapp.pvt_sportapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void signUpButtonClick(View v) {
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
            message.setText("");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", username.getText().toString());
                jsonObject.put("firstname", firstName.getText().toString());
                jsonObject.put("lastname", lastName.getText().toString());
                jsonObject.put("mobilnummer", phoneNumber.getText().toString());
                jsonObject.put("user_mail", email.getText().toString());
                jsonObject.put("password_user", password.getText().toString());
                Log.d("JsonObject", jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /* Here the asynctask is triggered */
            new RegisterRequest(this).execute(jsonObject.toString());
        }
    }

    private class RegisterRequest extends AsyncTask<String, RegisterActivity, Void> {
        private ProgressDialog dialog;
        private RegisterActivity ra;
        protected int responseCode;
        protected String responseBody;
        public RegisterRequest(RegisterActivity registerActivity) {
            ra =registerActivity;
            dialog = new ProgressDialog(registerActivity);
        }
        @Override
        protected void onPreExecute(){
            dialog.setMessage("Creating account, please wait");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("https://pvt15app.herokuapp.com/api/testsignup");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(String.format(params[0]));
                osw.flush();
                osw.close();

                BufferedReader br;
                if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
                    br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                } else {
                    br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
                }

                StringBuilder out = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    out.append(line);
                }
                Log.d("JSON?", out.toString());
                JSONObject jsonObject = new JSONObject(out.toString());
                responseBody = jsonObject.getString("body");
                responseCode = connection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(connection != null){
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(dialog.isShowing()){
                dialog.dismiss();
            }

            /* Responsecode 201 maps to successfull account creation */
            if(responseCode == 201){
                Toast.makeText(ra, "Account created!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(ra, responseBody, Toast.LENGTH_LONG).show();
            }
        }
    }
}

