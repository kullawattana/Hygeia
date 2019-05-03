package com.eng.chula.se.hygeia.activities.Pharmacy;

import android.app.Dialog;
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
import com.eng.chula.se.hygeia.storage.SharedPrefManager;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Article Board Activity
 * Suttipong.k 17/04/2019
 * */

public class QABoardQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextQuestionMessage, editTextAskerAccountId, editTextAskerName;

    final String QABoardQuestionActivity = "QABoardQuestionActivity";

    Integer askerAccountIdToInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa_board_question);

        editTextQuestionMessage = (EditText) findViewById(R.id.editTextQuestionMessage);
        editTextAskerAccountId = (EditText) findViewById(R.id.editTextAskerAccountId);
        editTextAskerName = (EditText) findViewById(R.id.editTextAskerName);

        findViewById(R.id.buttonQAboardQuestion).setOnClickListener(this);
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

    Integer questionId = 0;
    String textMessage = "";
    String askerAccountId = "";
    String askerName = "";

    Boolean validateFlag = false;

    public void validateQuestionForm(){
        questionId = 1;
        textMessage = editTextQuestionMessage.getText().toString().trim();
        askerAccountId = editTextAskerAccountId.getText().toString().trim();
        askerName = editTextAskerName.getText().toString().trim();

        if (textMessage.isEmpty()) {
            editTextQuestionMessage.setError("Question Message is required");
            editTextQuestionMessage.requestFocus();
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

    private void questionRequest(View v) {

        validateQuestionForm();

        if(validateFlag == true){

            openDialog(v);

            SharedPreferences.Editor editor = getSharedPreferences(QABoardQuestionActivity, MODE_PRIVATE).edit();
            askerAccountIdToInt = Integer.valueOf(askerAccountId);
            editor.putInt("questionId", questionId);
            editor.putString("textMessage", textMessage);
            editor.putInt("askerAccountId", askerAccountIdToInt);
            editor.putString("askerName", askerName);
            editor.apply();

            Call<DefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .questionBoard(questionId,textMessage,askerAccountIdToInt, askerName);

            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (response.code() == 201) {

                        DefaultResponse dr = response.body();
                        Toast.makeText(QABoardQuestionActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                    } else if (response.code() == 422) {
                        Toast.makeText(QABoardQuestionActivity.this, "QA Board Question is already exist", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    Toast.makeText(QABoardQuestionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonQAboardQuestion:
                questionRequest(v);
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }

    public void openDialog(View view) {
        new DroidDialog.Builder(QABoardQuestionActivity.this)
                .icon(R.drawable.ic_action_tick)
                .title("All Well!")
                .content("You has update question already to pharmacy and they have answer to you")
                .cancelable(true, true)
                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(QABoardQuestionActivity.this, "YES", Toast.LENGTH_SHORT).show();

                        Intent historyMainActivity = new Intent(getApplicationContext(), FirebaseProfileActivity.class);
                        historyMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        getApplicationContext().startActivity(historyMainActivity);

                    }
                })
                .negativeButton("No", new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(QABoardQuestionActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                })
                .neutralButton("SKIP", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(QABoardQuestionActivity.this, "Skip", Toast.LENGTH_SHORT).show();
                    }
                })
                .typeface("regular.ttf")
                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(QABoardQuestionActivity.this, R.color.indigo), ContextCompat.getColor(QABoardQuestionActivity.this, R.color.white),
                        ContextCompat.getColor(QABoardQuestionActivity.this, R.color.dark_indigo))
                .divider(true, ContextCompat.getColor(QABoardQuestionActivity.this, R.color.orange))
                .show();
    }

}
