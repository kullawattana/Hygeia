package com.eng.chula.se.hygeia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eng.chula.se.hygeia.R;
import com.eng.chula.se.hygeia.api.RetrofitClient;
import com.eng.chula.se.hygeia.models.DefaultResponse;
import com.eng.chula.se.hygeia.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Drug Request Activity
 * Suttipong.k 17/04/2019
 * */

public class PharmacyFormActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPharmacistName, editTextPharmacistSurname, editTextPharmacistLicenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacy_form);

        editTextPharmacistName = (EditText) findViewById(R.id.editTextPharmacistName);
        editTextPharmacistSurname = (EditText) findViewById(R.id.editTextPharmacistSurname);
        editTextPharmacistLicenseId = (EditText) findViewById(R.id.editTextPharmacistLicenseId);

        findViewById(R.id.buttonPharmacyForm).setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        //Not Login
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void pharmacyForm() {
        String pharmacistName = editTextPharmacistName.getText().toString().trim();
        String pharmacistSurname = editTextPharmacistSurname.getText().toString().trim();
        String pharmacistLicenseId = editTextPharmacistLicenseId.getText().toString().trim();


        if (pharmacistName.isEmpty()) {
            editTextPharmacistName.setError("Pharmacist Name is required");
            editTextPharmacistName.requestFocus();
            return;
        }

        if (pharmacistSurname.isEmpty()) {
            editTextPharmacistSurname.setError("Pharmacist Surname is required");
            editTextPharmacistSurname.requestFocus();
            return;
        }

        if (pharmacistLicenseId.isEmpty()) {
            editTextPharmacistLicenseId.setError("Pharmacist LicenseId is required");
            editTextPharmacistLicenseId.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .pharmacyForm(pharmacistName,pharmacistSurname,pharmacistLicenseId);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(PharmacyFormActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                } else if (response.code() == 422) {
                    Toast.makeText(PharmacyFormActivity.this, "Pharmacy Data is already exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(PharmacyFormActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignUp:
                pharmacyForm();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}