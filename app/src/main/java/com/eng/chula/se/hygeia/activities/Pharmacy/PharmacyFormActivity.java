package com.eng.chula.se.hygeia.activities.Pharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eng.chula.se.hygeia.R;
import com.eng.chula.se.hygeia.activities.LoginMainActivity;
import com.eng.chula.se.hygeia.api.RetrofitClient;
import com.eng.chula.se.hygeia.models.DefaultResponse;
import com.eng.chula.se.hygeia.storage.SharedPrefManager;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Drug Request Activity
 * Suttipong.k 17/04/2019
 * */

public class PharmacyFormActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPharmacistName, editTextPharmacistSurname, editTextPharmacistLicenseId;

    final String PharmacyFormActivity = "PharmacyFormActivity";

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
        /*if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }*/
    }

    Integer pharmacistid = 0;
    String pharmacistName = "";
    String pharmacistSurname = "";
    String pharmacistLicenseId = "";
    Boolean validateFlag = false;

    public void validatePharmacyForm(){
        pharmacistid = 1;
        pharmacistName = editTextPharmacistName.getText().toString().trim();
        pharmacistSurname = editTextPharmacistSurname.getText().toString().trim();
        pharmacistLicenseId = editTextPharmacistLicenseId.getText().toString().trim();


        if (pharmacistName.isEmpty()) {
            editTextPharmacistName.setError("Pharmacist Name is required");
            editTextPharmacistName.requestFocus();
            validateFlag = false;
            return;
        }

        if (pharmacistSurname.isEmpty()) {
            editTextPharmacistSurname.setError("Pharmacist Surname is required");
            editTextPharmacistSurname.requestFocus();
            validateFlag = false;
            return;
        }

        if (pharmacistLicenseId.isEmpty()) {
            editTextPharmacistLicenseId.setError("Pharmacist LicenseId is required");
            editTextPharmacistLicenseId.requestFocus();
            validateFlag = false;
            return;
        }

        validateFlag = true;
    }

    private void pharmacyForm() {

        validatePharmacyForm();

        if(validateFlag == true){
            SharedPreferences.Editor editor = getSharedPreferences(PharmacyFormActivity, MODE_PRIVATE).edit();
            editor.putInt("pharmacistid", pharmacistid);
            editor.putString("pharmacistName", pharmacistName);
            editor.putString("pharmacistSurname", pharmacistSurname);
            editor.putString("pharmacistLicenseId", pharmacistLicenseId);
            editor.apply();

            Call<DefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .pharmacist(pharmacistid,pharmacistName,pharmacistSurname,pharmacistLicenseId);

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPharmacyForm:
                pharmacyForm();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }
}
