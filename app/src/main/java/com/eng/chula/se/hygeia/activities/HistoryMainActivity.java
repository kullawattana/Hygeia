package com.eng.chula.se.hygeia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eng.chula.se.hygeia.R;
import com.eng.chula.se.hygeia.activities.History.SendEmailBackgroundActivity;
import com.eng.chula.se.hygeia.activities.History.UpdateNotificationFirebaseDataActivity;
import com.eng.chula.se.hygeia.activities.Pharmacy.ArticleBoardActivity;
import com.eng.chula.se.hygeia.activities.Pharmacy.DrugRecommendationActivity;
import com.eng.chula.se.hygeia.activities.Pharmacy.QABoardAnswerActivity;
import com.eng.chula.se.hygeia.activities.Pharmacy.QABoardQuestionActivity;
import com.eng.chula.se.hygeia.adapters.CustomListViewAdapter;
import com.eng.chula.se.hygeia.models.RowItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suttipong.k
 * on 9/11/2017.
 */

public class HistoryMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

	private static final Class[] CLASSES = new Class[]{
			SendEmailBackgroundActivity.class,
			UpdateNotificationFirebaseDataActivity.class,
	};

	public static final String[] titles = new String[] {
			"Send Email",
			"Notification",
	};

	public static final String[] descriptions = new String[] {
			"for send email to pharmacy",
			"for check data and update status of drug delivery",
	};

	public static final Integer[] images = {
			R.drawable.google_icon,
			R.drawable.notification_flat,
	};

	List<RowItem> rowItems;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_history);

		rowItems = new ArrayList<RowItem>();
		for (int i = 0; i < titles.length; i++) {
			RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
			rowItems.add(item);
		}

		listView = (ListView) findViewById(R.id.list);
		CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.history_list_item, rowItems);
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