package com.eng.chula.se.hygeia.activities.Homepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.eng.chula.se.hygeia.R;
import com.eng.chula.se.hygeia.activities.DrugRequest.MainStepperActivity;
import com.eng.chula.se.hygeia.activities.DrugRequestActivity;
import com.eng.chula.se.hygeia.activities.History.SendEmailBackgroundActivity;
import com.eng.chula.se.hygeia.activities.History.UpdateNotificationFirebaseDataActivity;;
import com.eng.chula.se.hygeia.activities.ManageUserActivity;
import com.eng.chula.se.hygeia.activities.Pharmacy.PharmacyFormActivity;
import com.eng.chula.se.hygeia.fragments.FirebaseProfileFragment;

public class FirebaseProfileActivity extends AppCompatActivity{

    private FragmentManager fragmentManager;

    private Fragment fragment = null;

    final String MY_STATE = "MY_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Drug Request
        ImageView imgReqDrug = (ImageView) findViewById(R.id.img_drug_request);
        imgReqDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    SharedPreferences.Editor editor = getSharedPreferences(MY_STATE, MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();

                    Intent mainActivityReqDrug = new Intent(getApplicationContext(), MainStepperActivity.class);
                    mainActivityReqDrug.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivityReqDrug);

                } catch (Exception e) {
                    Log.e("FirebaseProfileActivity", e.toString());
                }
            }
        });

        //Chat
        ImageView imgSendEmail = (ImageView) findViewById(R.id.img_chat);
        imgSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("jp.naver.line.android");
                    startActivity(launchIntent);
                } catch (Exception e) {
                    Log.e("FirebaseProfileActivity", e.toString());
                }
            }
        });

        //Pharmacy Checkup
        ImageView imgChatToPharma = (ImageView) findViewById(R.id.img_pharmacy_checkup);
        imgChatToPharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent mainActivityReqDrug = new Intent(getApplicationContext(), PharmacyFormActivity.class);
                    mainActivityReqDrug.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivityReqDrug);
                } catch (Exception e) {
                    Log.e("FirebaseProfileActivity", e.toString());
                }
            }
        });

        //History
        ImageView imgLocation = (ImageView) findViewById(R.id.img_history);
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent mainActivityReqDrug = new Intent(getApplicationContext(), UpdateNotificationFirebaseDataActivity.class);
                    mainActivityReqDrug.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivityReqDrug);
                } catch (Exception e) {
                    Log.e("FirebaseProfileActivity", e.toString());
                }
            }
        });

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new FirebaseProfileFragment();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.commit();

        //Navigation Bar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        disableNavigationViewScrollbars(navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_manage_user) {
                    fragment = new FirebaseProfileFragment();
                } else if (id == R.id.nav_manage_user) {
                    Intent manageUserActivity = new Intent(getApplicationContext(), ManageUserActivity.class);
                    startActivity(manageUserActivity);
                } else if(id == R.id.nav_history){              //ตรวจสอบ History
                    Intent manageUserActivity = new Intent(getApplicationContext(), UpdateNotificationFirebaseDataActivity.class);
                    startActivity(manageUserActivity);
                } else if (id == R.id.nav_send_email) {         //ส่ง E-mail
                    Intent manageUserActivity = new Intent(getApplicationContext(), SendEmailBackgroundActivity.class);
                    startActivity(manageUserActivity);
                } else if (id == R.id.nav_chat) {         //ส่ง E-mail
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("jp.naver.line.android");
                    startActivity(launchIntent);
                } else if (id == R.id.nav_logout) {
                    finish();
                    System.exit(0);
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container_wrapper, fragment);
                transaction.commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                assert drawer != null;
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
}
