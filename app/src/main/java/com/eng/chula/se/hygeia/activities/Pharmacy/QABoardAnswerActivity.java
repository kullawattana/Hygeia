package com.eng.chula.se.hygeia.activities.Pharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.eng.chula.se.hygeia.activities.LoginMainActivity;
import com.eng.chula.se.hygeia.api.RetrofitClient;
import com.eng.chula.se.hygeia.models.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Article Board Activity
 * Suttipong.k 17/04/2019
 * */

public class QABoardAnswerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAnswerMessage, editTextAskerAccountId, editTextAskerName;

    final String QABoardAnswerActivity = "QABoardAnswerActivity";

    Integer questionIdToInt = 0;
    Integer askerAccountIdToInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa_board_answer);

        editTextAnswerMessage = (EditText) findViewById(R.id.editTextAnswerMessage);
        editTextAskerAccountId = (EditText) findViewById(R.id.editTextAskerAccountId);
        editTextAskerName = (EditText) findViewById(R.id.editTextAskerName);

        findViewById(R.id.buttonQAboardAnswer).setOnClickListener(this);
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

    Integer answerId = 0;
    Integer questionId = 0;
    String textMessage = "";
    String askerAccountId = "";
    String askerName = "";

    Boolean validateFlag = false;

    public void validateAnswerRequest(){
        answerId = 1;
        questionId = 1;
        textMessage = editTextAnswerMessage.getText().toString().trim();
        askerAccountId = editTextAskerAccountId.getText().toString().trim();
        askerName = editTextAskerName.getText().toString().trim();

        if (textMessage.isEmpty()) {
            editTextAnswerMessage.setError("Answer Message is required");
            editTextAnswerMessage.requestFocus();
            validateFlag = false;
            return;
        }

        if (askerAccountId.isEmpty()) {
            editTextAskerAccountId.setError("Asker Account Id Id is required");
            editTextAskerAccountId.requestFocus();
            validateFlag = false;
            return;
        }

        if (askerName.isEmpty()) {
            editTextAskerName.setError("Asker Name is required");
            editTextAskerName.requestFocus();
            validateFlag = false;
            return;
        }

        validateFlag = true;
    }

    private void answerRequest(View v) {
        validateAnswerRequest();

        if(validateFlag == true){

            openDialog(v);

            SharedPreferences.Editor editor = getSharedPreferences(QABoardAnswerActivity, MODE_PRIVATE).edit();

            questionIdToInt = Integer.valueOf(questionId);
            askerAccountIdToInt = Integer.valueOf(askerAccountId);
            editor.putInt("answerId", answerId);
            editor.putInt("questionId", questionIdToInt);
            editor.putString("textMessage", textMessage);
            editor.putInt("askerAccountId", askerAccountIdToInt);
            editor.putString("askerName", askerName);
            editor.apply();

            Call<DefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .answerBoard(answerId,textMessage,questionIdToInt,askerAccountIdToInt, askerName);

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonQAboardAnswer:
                answerRequest(v);
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }

    public void openDialog(View view) {
        new DroidDialog.Builder(QABoardAnswerActivity.this)
                .icon(R.drawable.ic_action_tick)
                .title("All Well!")
                .content("Pharmacy has answer already")
                .cancelable(true, true)
                .positiveButton("OK", droidDialog -> {
                    droidDialog.dismiss();
                    Toast.makeText(QABoardAnswerActivity.this, "YES", Toast.LENGTH_SHORT).show();

                    Intent historyMainActivity = new Intent(getApplicationContext(), FirebaseProfileActivity.class);
                    historyMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(historyMainActivity);
                })
                .negativeButton("No", droidDialog -> {
                    droidDialog.dismiss();
                    Toast.makeText(QABoardAnswerActivity.this, "No", Toast.LENGTH_SHORT).show();
                })
                .neutralButton("SKIP", droidDialog -> {
                    droidDialog.dismiss();
                    Toast.makeText(QABoardAnswerActivity.this, "Skip", Toast.LENGTH_SHORT).show();
                })
                .typeface("regular.ttf")
                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(QABoardAnswerActivity.this, R.color.indigo), ContextCompat.getColor(QABoardAnswerActivity.this, R.color.white),
                        ContextCompat.getColor(QABoardAnswerActivity.this, R.color.dark_indigo))
                .divider(true, ContextCompat.getColor(QABoardAnswerActivity.this, R.color.orange))
                .show();
    }

}
