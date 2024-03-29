/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	String userLogFull;
//	String userLogFast;
	long userLogFast;
	MensaApplication app;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.preferencesmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnu_preferences:
			openPreferences();
			return true;
		case R.id.mnu_help:
			// showHelp();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void openPreferences(){
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, PrefActivity.class);
		startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.loginlayout);

		RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.rl1);
		rl1.getBackground().setAlpha(170);
		RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.rl2);
		rl2.getBackground().setAlpha(170);

		final EditText etUsername = (EditText) findViewById(R.id.etUsername);
		etUsername.setText("");
		final EditText etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword.setText("");

		app = (MensaApplication) getApplication();
		userLogFull = app.getSettings().getString(
				MensaApplication.FULLSYNC_LOG, "");
//		userLogFast = app.getSettings().getString(
//				MensaApplication.FASTSYNC_LOG, "");
		userLogFast = app.getSettings().getLong(MensaApplication.FASTSYNC_LOG, 0);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			TextView tvLastUser = (TextView) findViewById(R.id.tvLastUser);
			tvLastUser.setText("Last User Log: " + userLogFull);
			tvLastUser.setTextColor(Color.BLUE);
			tvLastUser.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					etUsername.setText(userLogFull);
				}
			});

			TextView tvLastSync = (TextView) findViewById(R.id.tvLastSync);
			tvLastSync.setText("Date Last Sync: " + userLogFast);
		}

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				HttpClient http = new HttpClient();
				String request = "http://simfoni.mbs.co.id/services.php?key=czRMZTU0dVRvTWF0MTBu&tab=bG9naW4=&uid="
						+ etUsername.getText().toString()
						+ "&pwd="
						+ etPassword.getText().toString();
				String response = http.executeHttpPost(request, "");
				if ((response.equals("null")) || (response.equals(""))) {
					Toast toast = Toast
							.makeText(
									LoginActivity.this,
									"Can't connect to webservices, check your internet connection..",
									Toast.LENGTH_SHORT);
					toast.show();
					return;
				}

				if (response.equals("-1")) {
					Toast toast = Toast.makeText(LoginActivity.this,
							"Wrong username and/or password",
							Toast.LENGTH_SHORT);
					toast.show();
				} else {
					app.setSalesid(response);

					Log.d("mensa", "userLogFull = " + userLogFull + " # "
							+ response);
					if (userLogFull.equals("") || !userLogFull.equals(response)) {
						Log.d("mensa",
								"need full sync cause different user login before");
						app.setNeedSync(true);
					} else {
						app.setNeedSync(false);
						if (userLogFast == 0) {
							app.setNeedFastSync(true);
						} else {
//							if (userLogFast.equals(app.getDateString())) {
//								app.setNeedFastSync(false);
//							} else {
//								app.setNeedFastSync(true);
//							}
							int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
							Date spDate = new Date(userLogFast);
							int diffInDays = (int) ((app.getDateLong() - spDate.getTime())/ DAY_IN_MILLIS );
							if (diffInDays >= 2){
								app.setNeedFastSync(true);
							}else{
								app.setNeedFastSync(false);
							}
						}
					}

					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainmenuActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
	}
}
