/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 13.01.2012	
 */

package com.mensa.salesdroid;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mensa.salesdroid.DataSync.OnDataSyncListener;

public class MainmenuActivity extends Activity {
	private static DataSync sync;
	static ProgressDialog progressDialog;
	static final int SYNCDIALOG = 0;
	static final int CHOOSESYNCDIALOG = 1;
	static final int NEEDFULLSYNCDIALOG = 2;
	MensaApplication mensaapplication;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		mensaapplication = (MensaApplication) getApplication();

		LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll1.getBackground().setAlpha(170);
		LinearLayout ll2 = (LinearLayout) findViewById(R.id.ll2);
		ll2.getBackground().setAlpha(170);
		LinearLayout ll3 = (LinearLayout) findViewById(R.id.ll3);
		ll3.getBackground().setAlpha(170);
		
		if (mensaapplication.isNeedSync()){
			showDialog(NEEDFULLSYNCDIALOG);
			mensaapplication.setNeedSync(false);
		}
		
		Button btnProductview = (Button) findViewById(R.id.btn_productlist);
		btnProductview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this,
						ProductviewActivity.class);
				startActivity(intent);
			}
		});

		Button btnListCallP = (Button) findViewById(R.id.btn_salescallplan);
		btnListCallP.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this,
						ListcallplanActivity.class);
				startActivity(intent);
			}
		});
		Button btnSync = (Button) findViewById(R.id.btn_synchronize);
		btnSync.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDialog(CHOOSESYNCDIALOG);
			}
		});
		Button btnInfopromo = (Button) findViewById(R.id.btn_infopromo);
		btnInfopromo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this,
						ProductviewActivity.class);
				intent.putExtra("opentab", ProductviewActivity.PROMOTAB);
				startActivity(intent);
			}
		});
		Button btnAddCust = (Button) findViewById(R.id.btn_addcustomer);
		btnAddCust.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this,
						AddCustomerActivity.class);
				startActivity(intent);
			}
		});
		
		Button btnCalendar = (Button) findViewById(R.id.btn_calendar);
		btnCalendar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this,
						CalendarView.class);
				Calendar calendar = Calendar.getInstance();
				intent.putExtra("date", calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
	    		startActivityForResult(intent, 1);
//				startActivity(intent);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog ret = null;
		switch (id) {
		case NEEDFULLSYNCDIALOG: {
			AlertDialog ad = new AlertDialog.Builder(this).create();
			ad.setCancelable(false); 
			ad.setMessage("application detects that the user has changed since last login. Klik OK, for FULL SYNC");
			ad.setButton("OK", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        dialog.dismiss();
			        showDialog(SYNCDIALOG);
			        sync = new DataSync(handler, mensaapplication);
					sync.setSyncMethod(0);
					sync.setOnDataSyncListener(new OnDataSyncListener() {
	
						@Override
						public void OnDataSync(String dataname, int count, int max) {
							
						}
					});
					sync.start();
			    }
			});
			ad.show();
			ret = ad;
			break;
		}
		case SYNCDIALOG: {
			progressDialog = new ProgressDialog(MainmenuActivity.this);
			progressDialog.setTitle("Data Synchronization");
//			progressDialog.setMessage(MensaApplication.FULLSYNC[0]);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMax(MensaApplication.FULLSYNC.length);
			ret = progressDialog;
			break;
		}
		case CHOOSESYNCDIALOG: {
			final CharSequence[] items = {"Full Sync", "Fast Sync", "Resend Pending"};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("select the synchronization method");
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
					showDialog(SYNCDIALOG);
					
					sync = new DataSync(handler, mensaapplication);
					sync.setSyncMethod(item);
					sync.setOnDataSyncListener(new OnDataSyncListener() {
	
						@Override
						public void OnDataSync(String dataname, int count, int max) {
							progressDialog.setProgress(count);
						}
					});
					sync.start();

			    }
			});
			AlertDialog alert = builder.create();
			ret = alert;
			break;
		}
		}
		return ret;
	}

	final static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.arg1;
			if (total >= 0) {
				progressDialog.dismiss();
			}
		}
	};

}
