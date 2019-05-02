package com.eng.chula.se.hygeia.activities.History;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.eng.chula.se.hygeia.R;
import com.eng.chula.se.hygeia.activities.Notification.EmailData;
import com.eng.chula.se.hygeia.activities.Notification.MailAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TOPPEE
 * on 02/05/2019
 */

public class UpdateNotificationFirebaseDataActivity extends AppCompatActivity {

    static Firebase myFirebaseRef;
    static final String TAG = "Update Notification";

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    List<EmailData> mEmailData = new ArrayList<>();

    EmailData mEmail;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_notification_main);

          mToolbar = findViewById(R.id.toolbar);
          mToolbar.setTitle(R.string.app_name);
          mRecyclerView = findViewById(R.id.recyclerView);
          LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(UpdateNotificationFirebaseDataActivity.this, LinearLayoutManager.VERTICAL, false);
          mRecyclerView.addItemDecoration(new DividerItemDecoration(UpdateNotificationFirebaseDataActivity.this, DividerItemDecoration.VERTICAL));
          mRecyclerView.setLayoutManager(mLinearLayoutManager);

          //********************************Firebase Database************************************
          Firebase.setAndroidContext(this);
          myFirebaseRef = new Firebase("https://carekhun-37740.firebaseio.com");
          myFirebaseRef.addChildEventListener(childEventListener);
      }

      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.menu_main, menu);
          return true;
      }

      //*************************Firebase Query Database**********************************
      ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, dataSnapshot.getKey() + ":" + dataSnapshot.getValue().toString());
            String keyAndValue = null;

            //Check ChatMessage
            String chkKey = dataSnapshot.getValue().toString().substring(0,4);
            Log.d(TAG, chkKey);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
            String currentDateandTime = sdf.format(new Date());

            keyAndValue = dataSnapshot.getValue().toString().substring(5, dataSnapshot.getValue().toString().length() - 1);
            mEmail = new EmailData("Announcement", "Drug Send", keyAndValue, currentDateandTime);
            mEmailData.add(mEmail);
            MailAdapter mMailAdapter = new MailAdapter(UpdateNotificationFirebaseDataActivity.this, mEmailData);
            mRecyclerView.setAdapter(mMailAdapter);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) { }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

        @Override
          public void onCancelled(FirebaseError firebaseError) {}
        };

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {  return super.onOptionsItemSelected(item); }

}
