/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 13.01.2012	
 */

package com.mensa.salesdroid;

import com.mensa.salesdroid.DataSync.OnDataSyncListener;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class MainmenuActivity extends Activity {
	private static DataSync sync;
	static ProgressDialog progressDialog;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        
        Button btnProductview = (Button)findViewById(R.id.btn_productlist);
        btnProductview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this, ProductviewActivity.class);
				startActivity(intent);
			}
		});
        
        Button btnListCallP = (Button)findViewById(R.id.btn_salescallplan);
        btnListCallP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this, ListcallplanActivity.class);
				startActivity(intent);
			}
		});
        Button btnSync = (Button)findViewById(R.id.btn_synchronize);
        btnSync.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDialog(0);
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
        Button btnInfopromo = (Button)findViewById(R.id.btn_infopromo);
        btnInfopromo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this, ProductviewActivity.class);
				startActivity(intent);
			}
		});
        Button btnAddCust = (Button)findViewById(R.id.btn_addcustomer);
        btnAddCust.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this, AddCustomerActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
        case 0:{
        	progressDialog = new ProgressDialog(MainmenuActivity.this);
        	progressDialog.setTitle("Data Synchronization");
        	progressDialog.setMessage(MensaApplication.dataFILENAMES[0]);
        	progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        	progressDialog.setMax(MensaApplication.dataFILENAMES.length);
        	}
        }
		return progressDialog;
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
