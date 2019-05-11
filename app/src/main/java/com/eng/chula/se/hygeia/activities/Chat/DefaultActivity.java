package com.eng.chula.se.hygeia.activities.Chat;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

public class DefaultActivity extends AppCompatActivity {

    public static final int GALLERY_PICTURE = 1;

    protected void showToast(String message, int length){
        Toast.makeText(this, message, length).show();
    }

    protected void callGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Choose a profile picture!"), GALLERY_PICTURE);
    }

    protected boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    protected String getStringFromEditText(EditText editText){
         return editText.getText().toString().trim();
    }
}
