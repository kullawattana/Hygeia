package com.eng.chula.se.hygeia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.eng.chula.se.hygeia.R;
import java.util.ArrayList;
import java.util.List;

import com.eng.chula.se.hygeia.LineLogin.MainLoginLineActivity;
import com.eng.chula.se.hygeia.activities.Login.FirebaseLoginProfileMainActivity;
import com.eng.chula.se.hygeia.activities.Login.LoginFacebookActivity;
import com.eng.chula.se.hygeia.activities.Login.LoginGoogleActivity;
import com.eng.chula.se.hygeia.adapters.CustomListViewAdapter;
import com.eng.chula.se.hygeia.models.RowItem;

/**
 * Created by Suttipong.k
 * on 9/11/2017.
 */

public class LoginMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

	private static final Class[] CLASSES = new Class[]{
			FirebaseLoginProfileMainActivity.class,			//Login จากการ Setup ใน Firebase
			LoginGoogleActivity.class,						//Login จาก Service ของ Google ใน Firebase
			LoginFacebookActivity.class,					//Login จาก Service ใน Facebook
			MainLoginLineActivity.class						//Login จาก Service ของ Line
	};

	public static final String[] titles = new String[] {
			"Email and Password",
			"Google",
			"Facebook",
			"Line"
	};

	public static final String[] descriptions = new String[] {
			"Login with Email and Password",
			"Login with Google",
			"Login with Facebook",
			"Login with Line"
	};

	public static final Integer[] images = {
			R.drawable.usernameandpasswordicon,
			R.drawable.googleicon,
			R.drawable.facebookicon,
			R.drawable.line_login
	};

	List<RowItem> rowItems;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_login);

		rowItems = new ArrayList<RowItem>();
		for (int i = 0; i < titles.length; i++) {
			RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
			rowItems.add(item);
		}

		listView = (ListView) findViewById(R.id.list);
		CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.login_list_item, rowItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Class clicked = CLASSES[position];								//index class name
		startActivity(new Intent(this, clicked));			//Set Start Activity
	}

	//Main Speech Recognition
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}