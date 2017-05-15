package com.sportify.profile.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.sportify.login.activity.LoginActivity;
import com.sportify.profile.presenter.ProfilePresenterImpl;
import com.sportify.userArea.activity.UserAreaActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sportapp.pvt_sportapp.R;

public class ProfileActivity extends AppCompatActivity implements ProfileView {
    private static int RESULT_LOAD_IMG = 1826;
    private static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 9287;
    String imgDecodableString;
    private SharedPreferences sharedPref;
    private ProfilePresenterImpl profilePresenter;
    private EditText firstname;
    private EditText lastname;
    private EditText dateOfBirth;
    private EditText description;
    private CheckBox fotboll, basket, simning, bandy, ridning,
            running, parkour, outdoortraining, skateboarding, badminton;

    private ImageButton checkboxProfileButton;
    private ImageButton profilePictureButton;
    private List<String> interests;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;
    private int userID;
    private boolean customImage;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userID = getIntent().getIntExtra("userID", -1);
        profilePresenter = new ProfilePresenterImpl(this, sharedPref);
        customImage = false;

        firstname = (EditText) findViewById(R.id.etProfileNameHint);
        lastname = (EditText) findViewById(R.id.etLastnameHint);
        dateOfBirth = (EditText) findViewById(R.id.etProfileBirthdayhint);
        description = (EditText) findViewById(R.id.etDescriptionBoxProfileOmMig);
        profilePictureButton = (ImageButton) findViewById(R.id.ibProfilePicturebutton);
        checkboxProfileButton = (ImageButton) findViewById(R.id.ibCheckboxProfileButton);

        fotboll = (CheckBox) findViewById(R.id.cbProfileFotboll);
        basket = (CheckBox) findViewById(R.id.cbProfileBasket);
        simning = (CheckBox) findViewById(R.id.cbProfileSimmning);
        bandy = (CheckBox) findViewById(R.id.cbProfileBandy);
        ridning = (CheckBox) findViewById(R.id.cbProfileHästRidning);
        running = (CheckBox) findViewById(R.id.cbProfileRunning);
        parkour = (CheckBox) findViewById(R.id.cbProfileParkour);
        outdoortraining = (CheckBox) findViewById(R.id.cbProfileoutdoortraining);
        skateboarding = (CheckBox) findViewById(R.id.cbProfileSkateboarding);
        badminton = (CheckBox) findViewById(R.id.cbProfileBadminton);

        calendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                dateOfBirth.setText(sdf.format(calendar.getTime()));
            }
        };

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ProfileActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dialog = new ProgressDialog(this);
    }

    @Override
    public String getProfileFirstName() {
        return firstname.getText().toString();
    }

    @Override
    public String getProfileLastName() {
        return lastname.getText().toString();
    }

    @Override //TODO: See if DatePicker stores this as string.
    public String getDateOfBirth() {
        return dateOfBirth.getText().toString();
    }

    @Override
    public String getUserBio() {
        return description.getText().toString();
    }

    @Override
    public List<String> getInterests() {
        interests = new ArrayList<>();
        if (badminton.isChecked()) interests.add("Badminton");
        if (bandy.isChecked()) interests.add("Bandy");
        if (basket.isChecked()) interests.add("Basket");
        if (fotboll.isChecked()) interests.add("Fotboll");
        if (running.isChecked()) interests.add("Löpning");
        if (parkour.isChecked()) interests.add("Parkour");
        if (ridning.isChecked()) interests.add("Ridning");
        if (simning.isChecked()) interests.add("Simning");
        if (skateboarding.isChecked()) interests.add("Skateboard");
        if (outdoortraining.isChecked()) interests.add("Utegym");

        return interests;
    }

    @Override
    public void showFirstNameEmptyError(int resId) {
        firstname.setError(getString(resId));
    }

    @Override
    public void showLastNameEmptyError(int resId) {
        lastname.setError(getString(resId));
    }

    @Override
    public void showDateOfBirthEmptyError(int resId) {
        dateOfBirth.setError(getString(resId));
    }

    @Override
    public void showDateOfBirthWrongFormatError(int resId) {
        dateOfBirth.setError(getString(resId));
    }

    @Override
    public void showNoInterestCheckedError(int resID) {
        Toast.makeText(this, getString(resID), Toast.LENGTH_LONG).show();
    }

    public void profilePictureButtonClick(View v) {
        //TODO: REMOVE profilePresenter.addProfilePicture();
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageButton profilePic = (ImageButton) findViewById(R.id.ibProfilePicturebutton);

                //Get Height and Width of imageButton
                int image_width = profilePic.getWidth();
                int image_height = profilePic.getHeight();

                // Set the Image in ImageButton after scaling the Bitmap to size of imageButton
                Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                Bitmap image = bitmap.createScaledBitmap(bitmap, image_width, image_height, false); //scale the image
                profilePic.setImageBitmap(image);
                customImage = true;

            } else {
                Toast.makeText(this, "Var god välj en bild",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public Bitmap getProfileImage() {
        ImageButton profilePic = (ImageButton) findViewById(R.id.ibProfilePicturebutton);
        Bitmap image = ((BitmapDrawable) profilePic.getDrawable()).getBitmap();
        return image;
    }

    @Override
    public Boolean userSelectedImage() {
        return customImage;
    }

    public void checkboxProfileButton(View v) {
        profilePresenter.updateBaseProfileInfo(userID);
    }

    @Override
    public void launchLoginActivity() {
        //TODO: Goto Login Screen
    }

    @Override
    public void showProgressDialog() {
        dialog.setMessage("Entering profile information, just a sec");
        dialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void goToLoginActivity() {
        Intent goToLoginActivity = new Intent(ProfileActivity.this, LoginActivity.class);
        ProfileActivity.this.startActivity(goToLoginActivity);
    }
}
