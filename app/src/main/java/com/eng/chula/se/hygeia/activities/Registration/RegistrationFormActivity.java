package com.eng.chula.se.hygeia.activities.Registration;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.eng.chula.se.hygeia.R;
import com.eng.chula.se.hygeia.activities.Homepage.FirebaseProfileActivity;
import com.eng.chula.se.hygeia.activities.LoginMainActivity;
import com.eng.chula.se.hygeia.activities.Pharmacy.ArticleBoardActivity;
import com.eng.chula.se.hygeia.api.RetrofitClient;
import com.eng.chula.se.hygeia.models.DefaultResponse;
import com.eng.chula.se.hygeia.storage.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Registration From Activity
 * Suttipong.k 17/04/2019
 * */

public class RegistrationFormActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPassword, editTextFirstname, editTextLastname;
    private EditText editTextCitizenId,editTextBirthday, editTextHometown;
    private EditText editTextTelephoneNumber, editTextEmail, editTextContact;

    final String RegistrationFormActivity = "RegistrationFormActivity";

    Date birthdayToDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        editTextLastname = (EditText) findViewById(R.id.editTextLastname);
        editTextCitizenId = (EditText) findViewById(R.id.editTextCitizenId);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextHometown = (EditText) findViewById(R.id.editTextHometown);
        editTextTelephoneNumber = (EditText) findViewById(R.id.editTextTelephoneNumber);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextContact = (EditText) findViewById(R.id.editTextContact);

        findViewById(R.id.buttonRegistrationForm).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    Integer accountId = 0;
    String password = "";
    String firstname = "";
    String lastname = "";
    String citizenId = "";
    String birthday = "";
    String hometown = "";
    String telephoneNumber = "";
    String email = "";
    String contact = "";

    Boolean validateFlag = false;

    public void validateRegistrationForm(){
        Integer accountId = 1;
        password = editTextPassword.getText().toString().trim();
        firstname = editTextFirstname.getText().toString().trim();
        lastname = editTextLastname.getText().toString().trim();
        citizenId = editTextCitizenId.getText().toString().trim();
        birthday = editTextBirthday.getText().toString().trim();
        hometown = editTextHometown.getText().toString().trim();
        telephoneNumber = editTextTelephoneNumber.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        contact = editTextContact.getText().toString().trim();

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            validateFlag = false;
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be at least 6 character long");
            editTextPassword.requestFocus();
            validateFlag = false;
            return;
        }

        if (firstname.isEmpty()) {
            editTextFirstname.setError("First Name is required");
            editTextFirstname.requestFocus();
            validateFlag = false;
            return;
        }

        if (lastname.isEmpty()) {
            editTextLastname.setError("Last Name is required");
            editTextLastname.requestFocus();
            validateFlag = false;
            return;
        }

        if (citizenId.isEmpty()) {
            editTextCitizenId.setError("Citizen Id is required");
            editTextCitizenId.requestFocus();
            validateFlag = false;
            return;
        }

        if (birthday.isEmpty()) {
            editTextBirthday.setError("Birthday is required");
            editTextBirthday.requestFocus();
            validateFlag = false;
            return;
        }

        if (hometown.isEmpty()) {
            editTextHometown.setError("Hometown is required");
            editTextHometown.requestFocus();
            validateFlag = false;
            return;
        }

        if (telephoneNumber.isEmpty()) {
            editTextTelephoneNumber.setError("Telephone Number is required");
            editTextTelephoneNumber.requestFocus();
            validateFlag = false;
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            validateFlag = false;
            return;
        }

        if (contact.isEmpty()) {
            editTextContact.setError("Contact is required");
            editTextContact.requestFocus();
            validateFlag = false;
            return;
        }

        validateFlag = true;
    }

    private void registrationForm(View v) {

        validateRegistrationForm();

        if(validateFlag == true){

            openDialog(v);

            SharedPreferences.Editor editor = getSharedPreferences(RegistrationFormActivity, MODE_PRIVATE).edit();

            String sDate = "14/07/2533";
            try {
                birthdayToDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            editor.putInt("accountId", accountId);
            editor.putString("password", password);
            editor.putString("firstname", firstname);
            editor.putString("lastname", lastname);
            editor.putString("citizenId", citizenId);
            editor.putString("birthday", birthday.toString());
            editor.putString("hometown", hometown);
            editor.putString("telephoneNumber", telephoneNumber);
            editor.putString("email", email);
            editor.putString("contact", contact);
            editor.apply();

            Call<DefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .registrationForm(accountId,password,firstname,lastname,citizenId,birthdayToDate,hometown,telephoneNumber,email,contact);

            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (response.code() == 201) {

                        DefaultResponse dr = response.body();
                        Toast.makeText(RegistrationFormActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                    } else if (response.code() == 422) {
                        Toast.makeText(RegistrationFormActivity.this, "Registration is already exist", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    Toast.makeText(RegistrationFormActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegistrationForm:
                registrationForm(v);
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }

    public void openDialog(View view) {
        new DroidDialog.Builder(RegistrationFormActivity.this)
                .icon(R.drawable.ic_action_tick)
                .title("All Well!")
                .content("Registration form system has receive data already")
                .cancelable(true, true)
                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(RegistrationFormActivity.this, "YES", Toast.LENGTH_SHORT).show();

                        Intent historyMainActivity = new Intent(getApplicationContext(), FirebaseProfileActivity.class);
                        historyMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        getApplicationContext().startActivity(historyMainActivity);

                    }
                })
                .negativeButton("No", new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(RegistrationFormActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                })
                .neutralButton("SKIP", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(RegistrationFormActivity.this, "Skip", Toast.LENGTH_SHORT).show();
                    }
                })
                .typeface("regular.ttf")
                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(RegistrationFormActivity.this, R.color.indigo), ContextCompat.getColor(RegistrationFormActivity.this, R.color.white),
                        ContextCompat.getColor(RegistrationFormActivity.this, R.color.dark_indigo))
                .divider(true, ContextCompat.getColor(RegistrationFormActivity.this, R.color.orange))
                .show();
    }
}
