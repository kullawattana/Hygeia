package com.eng.chula.se.hygeia.activities.Pharmacy;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.eng.chula.se.hygeia.R;

import com.eng.chula.se.hygeia.activities.LoginMainActivity;
import com.eng.chula.se.hygeia.activities.MainTestActivity;
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

public class ArticleBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextArticleName, editTextWriterId, editTextArticleContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_board);

        editTextArticleName = (EditText) findViewById(R.id.editTextArticleName);
        editTextWriterId = (EditText) findViewById(R.id.editTextWriterId);
        editTextArticleContent = (EditText) findViewById(R.id.editTextArticleContent);

        findViewById(R.id.buttonArticleBoard).setOnClickListener(this);
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

    private void articleBoard() {
        String articleId = UUID.randomUUID().toString();
        String articleName = editTextArticleName.getText().toString().trim();
        String writerId = editTextWriterId.getText().toString().trim();
        String articleContent = editTextArticleContent.getText().toString().trim();


        if (articleName.isEmpty()) {
            editTextArticleName.setError("Article Name is required");
            editTextArticleName.requestFocus();
            return;
        }

        if (writerId.isEmpty()) {
            editTextWriterId.setError("Writer Id is required");
            editTextWriterId.requestFocus();
            return;
        }

        if (articleContent.isEmpty()) {
            editTextArticleContent.setError("Article Content is required");
            editTextArticleContent.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .articleBoard(articleId, articleName, writerId, articleContent);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(ArticleBoardActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                } else if(response.code() == 200){
                    Log.d("ArticleBoardActivity", "Response from service is successfully");
                    Toast.makeText(ArticleBoardActivity.this, "Response from service is successfully", Toast.LENGTH_LONG).show();
                } else if (response.code() == 422) {
                    Toast.makeText(ArticleBoardActivity.this, "Content ID is already exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(ArticleBoardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonArticleBoard:
                openDialog(v);
                //articleBoard();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
        }
    }

    public void openDialog(View view) {
        new DroidDialog.Builder(ArticleBoardActivity.this)
                .icon(R.drawable.ic_action_tick)
                .title("All Well!")
                .content(getString(R.string.short_text))
                .cancelable(true, true)
                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(ArticleBoardActivity.this, "YES", Toast.LENGTH_SHORT).show();
                    }
                })
                .negativeButton("No", new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(ArticleBoardActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                })
                .neutralButton("SKIP", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(ArticleBoardActivity.this, "Skip", Toast.LENGTH_SHORT).show();
                    }
                })
                .typeface("regular.ttf")
                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(ArticleBoardActivity.this, R.color.indigo), ContextCompat.getColor(ArticleBoardActivity.this, R.color.white),
                        ContextCompat.getColor(ArticleBoardActivity.this, R.color.dark_indigo))
                .divider(true, ContextCompat.getColor(ArticleBoardActivity.this, R.color.orange))
                .show();
    }
}
