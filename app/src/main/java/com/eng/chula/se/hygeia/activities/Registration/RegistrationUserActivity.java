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
 * Registration User Activity
 * Suttipong.k 17/04/2019
 * */

public class RegistrationUserActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFirstname, editTextLastname;
    private EditText editTextCitizenId,editTextBirthday, editTextHometown;
    private EditText editTextTelephoneNumber, editTextEmail, editTextContact;

    final String RegistrationUserActivity = "RegistrationUserActivity";

    Date birthdayToDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_form);

        editTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        editTextLastname = (EditText) findViewById(R.id.editTextLastname);
        editTextCitizenId = (EditText) findViewById(R.id.editTextCitizenId);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextHometown = (EditText) findViewById(R.id.editTextHometown);
        editTextTelephoneNumber = (EditText) findViewById(R.id.editTextTelephoneNumber);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextContact = (EditText) findViewById(R.id.editTextContact);

        findViewById(R.id.buttonUserRegistrationForm).setOnClickListener(this);
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

    private void registrationForm() {

        Integer accountId = Integer.valueOf(UUID.randomUUID().toString());
        //String password = editTextPassword.getText().toString().trim();
        String password = "1234";
        String firstname = editTextFirstname.getText().toString().trim();
        String lastname = editTextLastname.getText().toString().trim();
        String citizenId = editTextCitizenId.getText().toString().trim();
        String birthday = editTextBirthday.getText().toString().trim();
        String hometown = editTextHometown.getText().toString().trim();
        String telephoneNumber = editTextTelephoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String contact = editTextContact.getText().toString().trim();

        if (firstname.isEmpty()) {
            editTextFirstname.setError("First Name is required");
            editTextFirstname.requestFocus();
            return;
        }

        if (lastname.isEmpty()) {
            editTextLastname.setError("Last Name is required");
            editTextLastname.requestFocus();
            return;
        }

        if (citizenId.isEmpty()) {
            editTextCitizenId.setError("Citizen Id is required");
            editTextCitizenId.requestFocus();
            return;
        }

        if (birthday != null) {
            editTextBirthday.setError("Birthday is required");
            editTextBirthday.requestFocus();
            return;
        }

        if (hometown.isEmpty()) {
            editTextHometown.setError("Hometown is required");
            editTextHometown.requestFocus();
            return;
        }

        if (telephoneNumber.isEmpty()) {
            editTextTelephoneNumber.setError("Telephone Number is required");
            editTextTelephoneNumber.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (contact.isEmpty()) {
            editTextContact.setError("Contact is required");
            editTextContact.requestFocus();
            return;
        }

        if(!password.isEmpty()){
            SharedPreferences.Editor editor = getSharedPreferences(RegistrationUserActivity, MODE_PRIVATE).edit();

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
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .registrationForm(accountId,password,firstname,lastname,citizenId,birthdayToDate,hometown,telephoneNumber,email,contact);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(RegistrationUserActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                } else if (response.code() == 422) {
                    Toast.makeText(RegistrationUserActivity.this, "Registration is already exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(RegistrationUserActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonUserRegistrationForm:
                openDialog(v);

                //Call Service
                //registrationForm();

                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }

    public void openDialog(View view) {
        new DroidDialog.Builder(RegistrationUserActivity.this)
                .icon(R.drawable.ic_action_tick)
                .title("All Well!")
                .content("Registration form system has receive data already")
                .cancelable(true, true)
                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(RegistrationUserActivity.this, "YES", Toast.LENGTH_SHORT).show();

                        Intent historyMainActivity = new Intent(getApplicationContext(), FirebaseProfileActivity.class);
                        historyMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        getApplicationContext().startActivity(historyMainActivity);

                    }
                })
                .negativeButton("No", new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(RegistrationUserActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                })
                .neutralButton("SKIP", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(RegistrationUserActivity.this, "Skip", Toast.LENGTH_SHORT).show();
                    }
                })
                .typeface("regular.ttf")
                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(RegistrationUserActivity.this, R.color.indigo), ContextCompat.getColor(RegistrationUserActivity.this, R.color.white),
                        ContextCompat.getColor(RegistrationUserActivity.this, R.color.dark_indigo))
                .divider(true, ContextCompat.getColor(RegistrationUserActivity.this, R.color.orange))
                .show();
    }
}
