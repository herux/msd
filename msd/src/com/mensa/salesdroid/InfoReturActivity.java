/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 15.01.2012	
 */

package com.mensa.salesdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class InfoReturActivity extends BaseFragmentActivity {
	int CAMERA_PIC_REQUEST = 2;
	int SELECT_PICTURE = 1;
	int SELECT_PRODUCT = 3;
	private String selectedImagePath;
	static MensaApplication application;
	static ImageView image;
	Bitmap thumbnail;
	Button btnBarcode;
	Fragment fragment;
	JSONArray rcCause;
	int spinnerid = 0;
	String selectedProductName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inforeturlayout);
		application = getMensaapplication();

		// ----load json returncause
		FileInputStream jsonfile = null;
		try {
			jsonfile = new FileInputStream(new File("/sdcard/"
					+ MensaApplication.APP_DATAFOLDER + "/"
					+ MensaApplication.FULLSYNC[6]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			String rcContent = MensaApplication.getFileContent(jsonfile);
			JSONObject rcObj = new JSONObject(rcContent);
			rcCause = rcObj.getJSONArray("return_cause");
			String[] returnCauses = new String[rcCause.length()];
			for (int i = 0; i < rcCause.length(); i++) {
				returnCauses[i] = rcCause.getJSONObject(i).getString(
						"DESCRIPTION");
			}
			Spinner s = (Spinner) findViewById(R.id.Spreturncause);
			s.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View v,
						int position, long id) {
					spinnerid = position;
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}

			});
			ArrayAdapter adapter = new ArrayAdapter(this,
					android.R.layout.simple_spinner_dropdown_item, returnCauses);
			s.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// ----

		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setSubtitle("Return Product");

		image = (ImageView) findViewById(R.id.ivImageRetur);

		Button btncamera = (Button) findViewById(R.id.btnCamera);
		btncamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
			}
		});

		Button btnGallery = (Button) findViewById(R.id.btnGallery);
		btnGallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						SELECT_PICTURE);
			}
		});

		btnBarcode = (Button) findViewById(R.id.btnbarcode);
		btnBarcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(InfoReturActivity.this,
						ProductviewActivity.class);
				intent.putExtra("opentab", ProductviewActivity.ALLTAB);
				intent.putExtra("protype", ProductviewActivity.proBROWSER);
				startActivityForResult(intent, SELECT_PRODUCT);
			}
		});

		Button btnsave = (Button) findViewById(R.id.btnsave);
		btnsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!btnBarcode.getText().toString().equals("...")) {
					Returns returns = application.getReturns();
					ArrayList<ReturnItem> ris = application.getReturnitems();
					if (ris == null) {
						returns = new Returns("RE" + application.getTimeInt()
						// + "."
						// + application.getCurrentCustomer().getCUSTOMER_CODE()
								, application.getDateTimeStr(), application
										.getSalesid(), application
										.getCurrentCustomer(), "");
						ris = new ArrayList<ReturnItem>();
					}
					application.setReturns(returns);
					EditText etqty = (EditText) findViewById(R.id.etqty);
					Spinner spinnerdesc = (Spinner) findViewById(R.id.Spreturncause);
					ReturnItem ri = null;
					try {
						ri = new ReturnItem(btnBarcode.getText().toString(),
								selectedProductName, Float.parseFloat(etqty
										.getText().toString()), rcCause
										.getJSONObject(spinnerid).getString(
												"RETURN_REASON"), thumbnail);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ris.add(ri);
					application.setReturnitems(ris);
					application.getReturns().setReturnitems(ris);
					Toast toast = Toast.makeText(InfoReturActivity.this,
							"Data return successfully saved!",
							Toast.LENGTH_SHORT);
					toast.show();
					finish();
					Intent list = new Intent(InfoReturActivity.this,
							InfoReturActivity.class);
					startActivity(list);
				} else {
					Toast toast = Toast.makeText(InfoReturActivity.this,
							"Null productcode is not allowed!.",
							Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		fragment = fm.findFragmentByTag("f1");
		if (fragment == null) {
			fragment = new MenuReturns();
			ft.add(fragment, "f1");
		}
		ft.show(fragment);
		ft.commit();
	}

	public static class MenuReturns extends Fragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// MenuItem returnlist = menu.add("Returns List");
			// returnlist.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			// returnlist
			// .setOnMenuItemClickListener(new OnMenuItemClickListener() {
			//
			// @Override
			// public boolean onMenuItemClick(MenuItem item) {
			// if (application.getReturnitems() != null) {
			// Intent newret = new Intent(getActivity(),
			// InfoReturListActivity.class);
			// startActivity(newret);
			// getActivity().finish();
			// } else {
			// Toast toast = Toast
			// .makeText(
			// getActivity(),
			// "List is still empty, please fill in at least one",
			// Toast.LENGTH_LONG);
			// toast.show();
			// }
			// return false;
			// }
			// });
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				thumbnail = BitmapFactory.decodeFile(selectedImagePath);
				image.setImageBitmap(thumbnail);
			}

			if (requestCode == CAMERA_PIC_REQUEST) {
				thumbnail = (Bitmap) data.getExtras().get("data");
				ImageView image = (ImageView) findViewById(R.id.ivImageRetur);
				image.setImageBitmap(thumbnail);
			}

			if (requestCode == SELECT_PRODUCT) {
				int productPos = data.getIntExtra("protype", -1);
				btnBarcode.setText(application.getProducts().get(productPos)
						.getPART_NO());
				selectedProductName = application.getProducts().get(productPos)
						.getDESCRIPTION();
			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
