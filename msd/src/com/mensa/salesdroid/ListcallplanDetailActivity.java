package com.mensa.salesdroid;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.mensa.salesdroid.ListcallplanActivity.CustomerDetailsFragment;

public class ListcallplanDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}
		if (savedInstanceState == null) {
			CustomerDetailsFragment details = new CustomerDetailsFragment();
			details.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction()
					.add(android.R.id.content, details).commit();
		}
	}

}
