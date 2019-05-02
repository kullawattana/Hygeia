package com.eng.chula.se.hygeia.activities;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressBar;

import com.eng.chula.se.hygeia.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

import com.eng.chula.se.hygeia.models.FirebaseQueryProfile;

/**
 * Created by Suttipong.k
 * 19/04/2019.
 */

public class AddFirebaseDataActivity extends AppCompatActivity implements View.OnClickListener{

      static final String TAG = "AddFirebaseData";
      static Firebase myFirebaseRef;

      Button save;
      EditText nameEditText, surnameEditText;
      EditText addressEditText;
      EditText telEditText;
      EditText emailEditText;
      EditText elderlyEditText;
      EditText timeEditText;

      //ProgressBar progressBar;
      ArrayAdapter<String> valuesAdapter;
      ArrayList<String> displayArray;
      ArrayList<String> keysArray;

      DatabaseReference database = FirebaseDatabase.getInstance().getReference();

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_profile_fall_detection);

            save = (Button)findViewById(R.id.savedata);

            //SAVE DATA
            nameEditText = (EditText)findViewById(R.id.nameprofile);
            surnameEditText = (EditText)findViewById(R.id.surname);
            addressEditText = (EditText)findViewById(R.id.address);
            telEditText = (EditText)findViewById(R.id.tel);
            emailEditText = (EditText)findViewById(R.id.email);
            //elderlyEditText = (EditText)findViewById(R.id.elderly);
            timeEditText = (EditText)findViewById(R.id.time);

            displayArray  = new ArrayList<>();
            keysArray = new ArrayList<>();
            valuesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,displayArray);

            Firebase.setAndroidContext(this);
            myFirebaseRef = new Firebase("https://carekhun-37740.firebaseio.com");
            myFirebaseRef.addChildEventListener(childEventListener);

            save.setOnClickListener(this);

      }

      //***************************************Progress Bar*****************************************
      @Override
      public void onClick(View v) {
        switch (v.getId()){
          case R.id.savedata:

            //SET DATA TO MODEL CLASS
            FirebaseQueryProfile s = new FirebaseQueryProfile();
                    s.setName(database.child(nameEditText.getText().toString()).push().getKey());       //Key Database
                    s.setMyname(nameEditText.getText().toString());
                    s.setSurname(surnameEditText.getText().toString());
                    s.setAddress(addressEditText.getText().toString());
                    s.setTel(telEditText.getText().toString());
                    s.setEmailAddress(emailEditText.getText().toString());
                    s.setTakeCareType(elderlyEditText.getText().toString());
                    s.setTimeFallDetection(timeEditText.getText().toString());
            database.child(nameEditText.getText().toString()).child(s.getName()).setValue(s);
            finish();
        }
      }

      //***************************************Option Menu*****************************************
      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
      }

      //****************************************Query*****************************************
      ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String keyAndValue = "Key: " +dataSnapshot.getKey().toString() + "\t Value: " +  dataSnapshot.getValue().toString();
                displayArray.add(keyAndValue);
                keysArray.add(dataSnapshot.getKey().toString());
                updateListView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String changedKey = dataSnapshot.getKey();
                int changedIndex = keysArray.indexOf(changedKey);
                String keyAndValue = "Key: " +dataSnapshot.getKey().toString() + "\t Value: " +  dataSnapshot.getValue().toString();
                displayArray.set(changedIndex,keyAndValue);
                updateListView();
            }

            //********************************************Remove Data*************************************************
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String deletedKey = dataSnapshot.getKey();
                int removedIndex = keysArray.indexOf(deletedKey);
                keysArray.remove(removedIndex);
                displayArray.remove(removedIndex);
                updateListView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}

      };

      //********************************************Update Data*************************************************
      private void updateListView(){
        valuesAdapter.notifyDataSetChanged();
      }

      //********************************************Adapter List*************************************************
      AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          String clickedKey = keysArray.get(position);
          myFirebaseRef.child(clickedKey).removeValue();
        }
      };

      //********************************************Option*************************************************
      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
      }

}
