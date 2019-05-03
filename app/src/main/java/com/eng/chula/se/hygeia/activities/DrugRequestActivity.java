package com.eng.chula.se.hygeia.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.eng.chula.se.hygeia.R;
import com.eng.chula.se.hygeia.activities.Homepage.FirebaseProfileActivity;
import com.eng.chula.se.hygeia.activities.Registration.RegistrationFormActivity;
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

        /*if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }*/
    }

    String requestId = "";
    String topic = "";
    String attachment = "";

    Boolean validateFlag = false;

    public void validateDrugRequest(){
        requestId = UUID.randomUUID().toString();
        topic = editTextTopic.getText().toString().trim();
        attachment = editTextAttachment.getText().toString().trim();

        if (topic.isEmpty()) {
            editTextTopic.setError("Topic is required");
            editTextTopic.requestFocus();
            validateFlag = false;
            return;
        }

        if (attachment.isEmpty()) {
            editTextAttachment.setError("Attachment is required");
            editTextAttachment.requestFocus();
            validateFlag = false;
            return;
        }

        validateFlag = true;
    }

    private void drugRequest(View v) {

        validateDrugRequest();

        if(validateFlag == true){
            openDialog(v);

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDrugRequest:
                drugRequest(v);
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }

    public void openDialog(View view) {
        new DroidDialog.Builder(DrugRequestActivity.this)
                .icon(R.drawable.ic_action_tick)
                .title("All Well!")
                .content("Drug request by you have already")
                .cancelable(true, true)
                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(DrugRequestActivity.this, "YES", Toast.LENGTH_SHORT).show();

                        Intent historyMainActivity = new Intent(getApplicationContext(), FirebaseProfileActivity.class);
                        historyMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        getApplicationContext().startActivity(historyMainActivity);

                    }
                })
                .negativeButton("No", new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(DrugRequestActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                })
                .neutralButton("SKIP", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(DrugRequestActivity.this, "Skip", Toast.LENGTH_SHORT).show();
                    }
                })
                .typeface("regular.ttf")
                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(DrugRequestActivity.this, R.color.indigo), ContextCompat.getColor(DrugRequestActivity.this, R.color.white),
                        ContextCompat.getColor(DrugRequestActivity.this, R.color.dark_indigo))
                .divider(true, ContextCompat.getColor(DrugRequestActivity.this, R.color.orange))
                .show();
    }
}
