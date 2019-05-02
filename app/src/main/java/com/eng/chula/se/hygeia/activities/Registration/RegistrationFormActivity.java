package com.eng.chula.se.hygeia.activities.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eng.chula.se.hygeia.R;
import com.eng.chula.se.hygeia.activities.LoginMainActivity;
import com.eng.chula.se.hygeia.api.RetrofitClient;
import com.eng.chula.se.hygeia.models.DefaultResponse;
import com.eng.chula.se.hygeia.storage.SharedPrefManager;

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

    private void registrationForm() {
        String password = editTextPassword.getText().toString().trim();
        String firstname = editTextFirstname.getText().toString().trim();
        String lastname = editTextLastname.getText().toString().trim();
        String citizenId = editTextCitizenId.getText().toString().trim();
        String birthday = editTextBirthday.getText().toString().trim();
        String hometown = editTextHometown.getText().toString().trim();
        String telephoneNumber = editTextTelephoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String contact = editTextContact.getText().toString().trim();

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be at least 6 character long");
            editTextPassword.requestFocus();
            return;
        }

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

        if (birthday.isEmpty()) {
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

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .registrationForm(password,firstname,lastname,citizenId,birthday,hometown,telephoneNumber,email,contact);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegistrationForm:
                registrationForm();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }
}
