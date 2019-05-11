package com.eng.chula.se.hygeia.activities.Chat.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.eng.chula.se.hygeia.activities.Chat.Util;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable{

    private String name;
    private String profilePicture;
    private String uid;

    public User (){}

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public User setProfilePicture(ImageView profilePicture) {

        if(profilePicture.getDrawable() instanceof VectorDrawable){
            this.profilePicture = null;
            return this;
        }

        Bitmap bitmap = Util.getBitmapFromAImageView(profilePicture);
        byte [] bytes = Util.compressBitmapToByteArray(bitmap, Bitmap.CompressFormat.PNG, 100);
        this.profilePicture =  Util.encodeByteToStringBase64(bytes);
        return this;
    }

    public String getUid() {
        return uid;
    }

    public User generateUid() {
        this.uid =  String.valueOf(new Random().nextLong());
        Log.d("TEST", uid);
        return this;
    }
}
