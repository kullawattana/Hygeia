package com.eng.chula.se.hygeia.activities.Homepage;

import android.content.Intent;
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
import com.eng.chula.se.hygeia.activities.DrugRequest.DrugRequestActivity;
import com.eng.chula.se.hygeia.activities.ManageUserActivity;
import com.eng.chula.se.hygeia.activities.History.SendEmailBackgroundActivity;
import com.eng.chula.se.hygeia.fragments.FirebaseProfileFragment;

public class FirebaseProfileActivity extends AppCompatActivity{

    private FragmentManager fragmentManager;

    private Fragment fragment = null;

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

        //สั่งยา
        ImageView imgReqDrug = (ImageView) findViewById(R.id.img_add_profile);
        imgReqDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent mainActivityReqDrug = new Intent(getApplicationContext(), DrugRequestActivity.class);
                    mainActivityReqDrug.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivityReqDrug);
                } catch (Exception e) {
                    Log.e("FirebaseProfileActivity", e.toString());
                }
            }
        });

        //ส่ง Email
        ImageView imgSendEmail = (ImageView) findViewById(R.id.img_wearable);
        imgSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent sensorDeviceScanActivity = new Intent(getApplicationContext(), SendEmailBackgroundActivity.class);
                    sensorDeviceScanActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(sensorDeviceScanActivity);
                } catch (Exception e) {
                    Log.e("FirebaseProfileActivity", e.toString());
                }
            }
        });

        //Chat
        ImageView imgChatToPharma = (ImageView) findViewById(R.id.img_fall_detection);
        imgChatToPharma.setOnClickListener(new View.OnClickListener() {
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

        //ตรวจสอบ Location
        ImageView imgLocation = (ImageView) findViewById(R.id.img_chat);
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

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

                if (id == R.id.nav_activity) {
                    fragment = new FirebaseProfileFragment();
                } else if (id == R.id.nav_edit_data) {          //Profile
                    //Intent mainActivityQRCodeScanner = new Intent(getApplicationContext(), MainActivityQRCodeScanner.class);
                    //mainActivityQRCodeScanner.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    //getApplicationContext().startActivity(mainActivityQRCodeScanner);
                } else if(id == R.id.nav_history){              //ตรวจสอบ History

                } else if (id == R.id.nav_send_email) {         //ส่ง E-mail

                } else if (id == R.id.nav_chat) {               //Chat

                } else if (id == R.id.nav_settings) {
                    Intent manageUserActivity = new Intent(getApplicationContext(), ManageUserActivity.class);
                    startActivity(manageUserActivity);
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
