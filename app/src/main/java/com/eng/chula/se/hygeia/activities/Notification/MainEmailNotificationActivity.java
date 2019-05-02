package com.eng.chula.se.hygeia.activities.Notification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.eng.chula.se.hygeia.R;

import java.util.ArrayList;
import java.util.List;

public class MainEmailNotificationActivity extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    List<EmailData> mEmailData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_main);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MainEmailNotificationActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainEmailNotificationActivity.this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        EmailData mEmail = new EmailData("Announcement", "Test",
                "Test",
                "10:42am");
        mEmailData.add(mEmail);

        MailAdapter mMailAdapter = new MailAdapter(MainEmailNotificationActivity.this, mEmailData);
        mRecyclerView.setAdapter(mMailAdapter);

    }
}
