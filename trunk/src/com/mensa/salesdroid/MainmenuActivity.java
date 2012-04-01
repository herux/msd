/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 13.01.2012	
 */

package com.mensa.salesdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);

		LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll1.getBackground().setAlpha(170);
		LinearLayout ll2 = (LinearLayout) findViewById(R.id.ll2);
		ll2.getBackground().setAlpha(170);
		LinearLayout ll3 = (LinearLayout) findViewById(R.id.ll3);
		ll3.getBackground().setAlpha(170);
		
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
				startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(null, MensaCalendarActivity.MIME_TYPE));
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog ret = null;
		switch (id) {
		case SYNCDIALOG: {
			progressDialog = new ProgressDialog(MainmenuActivity.this);
			progressDialog.setTitle("Data Synchronization");
			progressDialog.setMessage(MensaApplication.FULLSYNC[0]);
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
					MensaApplication app = (MensaApplication) getApplication();
					sync = new DataSync(handler, app);
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
