package com.eng.chula.se.hygeia.activities.DrugRequest;

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

public class DrugRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextTopic, editTextAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_request);

        editTextTopic = (EditText) findViewById(R.id.editTextTopic);
        editTextAttachment = (EditText) findViewById(R.id.editTextAttachment);

        findViewById(R.id.buttonDrugRequest).setOnClickListener(this);
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

    private void drugRequest() {
        String requestId = UUID.randomUUID().toString();
        String topic = editTextTopic.getText().toString().trim();
        String attachment = editTextAttachment.getText().toString().trim();


        if (topic.isEmpty()) {
            editTextTopic.setError("Topic is required");
            editTextTopic.requestFocus();
            return;
        }

        if (attachment.isEmpty()) {
            editTextAttachment.setError("Attachment is required");
            editTextAttachment.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .drugRequest(requestId,topic,attachment);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(DrugRequestActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                } else if (response.code() == 422) {
                    Toast.makeText(DrugRequestActivity.this, "Drug Request is already exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(DrugRequestActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignUp:
                drugRequest();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }
}
