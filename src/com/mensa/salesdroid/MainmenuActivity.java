/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 13.01.2012	
 */

package com.mensa.salesdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
	static final int NEEDFASTSYNCDIALOG = 3;
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
		LinearLayout ll4 = (LinearLayout) findViewById(R.id.ll4);
		ll4.getBackground().setAlpha(170);

		if (mensaapplication.isNeedSync()) {
			showDialog(NEEDFULLSYNCDIALOG);
		}

		if (mensaapplication.isNeesFastSync()) {
			showDialog(NEEDFASTSYNCDIALOG);
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
				intent.setClass(MainmenuActivity.this, CalendarView.class);
				Calendar calendar = Calendar.getInstance();
				intent.putExtra(
						"date",
						calendar.get(Calendar.YEAR) + "-"
								+ calendar.get(Calendar.MONTH) + "-"
								+ calendar.get(Calendar.DAY_OF_MONTH));
				startActivityForResult(intent, 1);
				// startActivity(intent);
			}
		});

		Button btnCompare = (Button) findViewById(R.id.btn_compareorder);
		btnCompare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				File root = Environment.getExternalStorageDirectory();
				String folder = MensaApplication.APP_DATAFOLDER + "/";
				File file = new File(root, folder
						+ MensaApplication.ORDERNOLIST);
				FileInputStream fileis = null;
				if (!file.exists()) {
					Toast toast = Toast.makeText(MainmenuActivity.this,
							"not yet transaction order for today",
							Toast.LENGTH_LONG);
					toast.show();
				} else {
					try {
						fileis = new FileInputStream(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

					JSONArray orderlistObj;
					JSONObject orderobj = null;
					try {
						String fileContent = mensaapplication
								.getFileContent(fileis);
						orderlistObj = new JSONArray(fileContent);
						JSONArray onolist = new JSONArray();
						for (int i = 0; i < orderlistObj.length(); i++) {
							onolist.put(orderlistObj.getJSONObject(i).get(
									"orderno"));
						}
						orderobj = new JSONObject();
						orderobj.put("tanggal_order",
								mensaapplication.getDateString());
						orderobj.put("order_no", onolist);
					} catch (JSONException e) {
						e.printStackTrace();
					}

					String input = Compression.encodebase64(orderobj.toString());
					try {
						input = URLEncoder.encode(orderobj.toString(),"UTF-8");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Log.d("mensa", "input for compare : " + orderobj.toString());
					HttpClient httpc = new HttpClient();
					String response = httpc
							.executeHttpPost(
									"http://simfoni.mbs.co.id/services.php?key=czRMZTU0dVRvTWF0MTBu&tab=Y29tcGFyZQ==&uid="
											+ mensaapplication.getSalesid(),
									input);
					Log.d("mensa", "reponse compare : " + response);
					try {
						JSONObject respObj = new JSONObject(response);
						String message = "";
						if (respObj.getString("status").equals("SUCCESS")) {
							message = respObj.getString("description");
						} else {
							message = respObj.getString("description");
							JSONArray orderlist = respObj
									.getJSONArray("order_no");
							for (int i = 0; i < orderlist.length(); i++) {
								message = message + " "
										+ orderlist.getString(i);
							}
						}
						Toast toast = Toast.makeText(MainmenuActivity.this,
								message, Toast.LENGTH_LONG);
						toast.show();
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
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
					SharedPreferences.Editor editor = mensaapplication
							.getSettings().edit();
					editor.putString(MensaApplication.FULLSYNC_LOG,
							mensaapplication.getSalesid());
					editor.commit();
					mensaapplication.setNeedSync(false);
					mensaapplication.setNeesFastSync(false);
					dialog.dismiss();
					showDialog(DataSync.FULLSYNC);
					sync = new DataSync(handler, mensaapplication);
					sync.setSyncMethod(0);
					progressDialog.setTitle("Full Data Synchronization");
					sync.setOnDataSyncListener(new OnDataSyncListener() {

						@Override
						public void OnDataSync(String dataname, int count,
								int max) {
							progressDialog.setProgress(count);
							progressDialog.setMax(max);
						}
					});
					sync.start();
				}
			});
			ad.setButton2("Cancel", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					mensaapplication.setNeedSync(true);
					dialog.dismiss();
					finish();
				}
			});
			ad.show();
			ret = ad;
			break;
		}
		case NEEDFASTSYNCDIALOG: {
			AlertDialog ad = new AlertDialog.Builder(this).create();
			ad.setCancelable(false);
			ad.setMessage("FULL SYNC process has not done for today, do it now?");
			ad.setButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences.Editor editor = mensaapplication
							.getSettings().edit();
					editor.putString(MensaApplication.FASTSYNC_LOG,
							mensaapplication.getDateString());
					editor.commit();
					mensaapplication.setNeesFastSync(false);
					dialog.dismiss();
					showDialog(SYNCDIALOG);
					sync = new DataSync(handler, mensaapplication);
					// sync.setSyncMethod(DataSync.FASTSYNC); dulu fast diganti
					// jadi full, jadi pakai baris di bawahnya..
					sync.setSyncMethod(DataSync.FULLSYNC);
					progressDialog.setTitle("Full Data Synchronization");
					sync.setOnDataSyncListener(new OnDataSyncListener() {

						@Override
						public void OnDataSync(String dataname, int count,
								int max) {
							progressDialog.setProgress(count);
							progressDialog.setMax(max);
						}
					});
					sync.start();
				}
			});
			ad.setButton2("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					mensaapplication.setNeesFastSync(true);
					dialog.dismiss();
					finish();
				}
			});
			ad.show();
			ret = ad;
			break;
		}
		case SYNCDIALOG: {
			progressDialog = new ProgressDialog(MainmenuActivity.this);
			progressDialog.setTitle("Data Synchronization");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMax(MensaApplication.FULLSYNC.length);
			ret = progressDialog;
			break;
		}
		case CHOOSESYNCDIALOG: {
			final CharSequence[] items = { "Full Data ", "Fast Data ",
					"Resend Pending " };

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("select the synchronization method");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					Toast.makeText(getApplicationContext(), items[item],
							Toast.LENGTH_SHORT).show();
					showDialog(SYNCDIALOG);

					if ((item == 0) || (item == 1)) {
						SharedPreferences.Editor editor = mensaapplication
								.getSettings().edit();
						editor.putString(MensaApplication.FASTSYNC_LOG,
								mensaapplication.getDateString());
						editor.commit();
					}

					sync = new DataSync(handler, mensaapplication);
					sync.setSyncMethod(item);
					progressDialog.setTitle(items[item] + "Syncronization");
					sync.setOnDataSyncListener(new OnDataSyncListener() {

						@Override
						public void OnDataSync(String dataname, int count,
								int max) {
							progressDialog.setProgress(count);
							progressDialog.setMax(max);
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
