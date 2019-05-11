package com.eng.chula.se.hygeia.activities.Chat;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class ApplicationChat extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.getApps(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
