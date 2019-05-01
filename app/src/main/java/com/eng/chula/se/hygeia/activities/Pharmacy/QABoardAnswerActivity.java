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
 * Article Board Activity
 * Suttipong.k 17/04/2019
 * */

public class QABoardAnswerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAnswerMessage, editTextAskerAccountId, editTextAskerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa_board_answer);

        editTextAnswerMessage = (EditText) findViewById(R.id.editTextAnswerMessage);
        editTextAskerAccountId = (EditText) findViewById(R.id.editTextAskerAccountId);
        editTextAskerName = (EditText) findViewById(R.id.editTextAskerName);

        findViewById(R.id.buttonQABoardAnswer).setOnClickListener(this);
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

    private void answerRequest() {
        String questionId = UUID.randomUUID().toString();
        String textMessage = editTextAnswerMessage.getText().toString().trim();
        String askerAccountId = editTextAskerAccountId.getText().toString().trim();
        String askerName = editTextAskerName.getText().toString().trim();


        if (textMessage.isEmpty()) {
            editTextAnswerMessage.setError("Answer Message is required");
            editTextAnswerMessage.requestFocus();
            return;
        }

        if (askerAccountId.isEmpty()) {
            editTextAskerAccountId.setError("Asker Account Id Id is required");
            editTextAskerAccountId.requestFocus();
            return;
        }

        if (askerName.isEmpty()) {
            editTextAskerName.setError("Asker Name is required");
            editTextAskerName.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .answerRequest(questionId,textMessage,askerAccountId, askerName);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(QABoardAnswerActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                } else if (response.code() == 422) {
                    Toast.makeText(QABoardAnswerActivity.this, "QA Board Answer is already exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(QABoardAnswerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignUp:
                answerRequest();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }

}
