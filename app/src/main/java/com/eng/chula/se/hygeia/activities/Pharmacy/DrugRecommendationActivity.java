package com.eng.chula.se.hygeia.activities.Pharmacy;

import android.content.Intent;
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

public class DrugRecommendationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextCreatorId, editTextCreatorName, editTextReceiverId;
    private EditText editTextReceiverName, editTextCreateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_recommendation_form);

        editTextCreatorId = (EditText) findViewById(R.id.editTextCreatorId);
        editTextCreatorName = (EditText) findViewById(R.id.editTextCreatorName);
        editTextReceiverId = (EditText) findViewById(R.id.editTextReceiverId);
        editTextReceiverName = (EditText) findViewById(R.id.editTextReceiverName);
        editTextCreateDate = (EditText) findViewById(R.id.editTextCreateDate);

        findViewById(R.id.buttonDrugRecomendationForm).setOnClickListener(this);
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

    private void drugRecommendation() {
        String recommendationId = UUID.randomUUID().toString();
        String creatorId = editTextCreatorId.getText().toString().trim();
        String creatorName = editTextCreatorName.getText().toString().trim();
        String receiverId = editTextReceiverId.getText().toString().trim();
        String receiverName = editTextReceiverName.getText().toString().trim();
        String createDate = editTextCreateDate.getText().toString().trim();


        if (creatorId.isEmpty()) {
            editTextCreatorId.setError("Creator ID is required");
            editTextCreatorId.requestFocus();
            return;
        }

        if (creatorName.isEmpty()) {
            editTextCreatorName.setError("Creator Name is required");
            editTextCreatorName.requestFocus();
            return;
        }

        if (receiverId.isEmpty()) {
            editTextReceiverId.setError("Receiver ID is required");
            editTextReceiverId.requestFocus();
            return;
        }

        if (receiverName.isEmpty()) {
            editTextReceiverName.setError("Receiver Name is required");
            editTextReceiverName.requestFocus();
            return;
        }

        if (createDate.isEmpty()) {
            editTextCreateDate.setError("Create Date is required");
            editTextCreateDate.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .drugRecommendation(recommendationId,creatorId,creatorName,receiverId,receiverName,createDate);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(DrugRecommendationActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                } else if (response.code() == 422) {
                    Toast.makeText(DrugRecommendationActivity.this, "Drug Recommendation Data is already exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(DrugRecommendationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDrugRecomendationForm:
                drugRecommendation();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }
}
