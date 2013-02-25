package com.mensa.salesdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCustomerActivity extends BaseFragmentActivity {
	EditText etcode, etname, etchain, etbillname, etbilladdr, etdelname,
			etdeladdr, etzipcode;
	Spinner spgrup;
	String groupid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcustomerlayout);

		final ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setSubtitle("Adding New Customer");

		etcode = (EditText) findViewById(R.id.etcode);
		String dt = getMensaapplication().getDateTimeInt();
		int pjg;
		if (dt.length() >= 12){
			pjg = 12;
		}else{
			pjg = dt.length(); 
		}
			
		etcode.setText(("C-"+dt.substring(1, pjg)));
		etname = (EditText) findViewById(R.id.etname);
		etchain = (EditText) findViewById(R.id.etchain);

		// ----load json returncause
		FileInputStream jsonfile = null;
		try {
			jsonfile = new FileInputStream(new File("/sdcard/"
					+ MensaApplication.APP_DATAFOLDER + "/"
					+ MensaApplication.FULLSYNC[11]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			String rcContent = MensaApplication.getFileContent(jsonfile);
			JSONObject rcObj = new JSONObject(rcContent);
			JSONArray rcGroup = rcObj.getJSONArray("mob_customer_group");
			String[] groups  = new String[rcGroup.length()];
			final String[] groupids= new String[rcGroup.length()];
			for (int i = 0; i < rcGroup.length(); i++) {
				groups[i]  = rcGroup.getJSONObject(i).getString("DESCRIPTION");
				groupids[i]= rcGroup.getJSONObject(i).getString("CUST_GRP");
			}
			spgrup = (Spinner) findViewById(R.id.spgrup);
			spgrup.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View v,
						int position, long id) {
					groupid = groupids[position];
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}

			});
			ArrayAdapter adapter = new ArrayAdapter(this,
					android.R.layout.simple_spinner_dropdown_item, groups);
			spgrup.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// ----

		etbillname = (EditText) findViewById(R.id.etnamatagihan);
		etbilladdr = (EditText) findViewById(R.id.etalamattagihan);
		etdelname = (EditText) findViewById(R.id.etnamakirim);
		etdeladdr = (EditText) findViewById(R.id.etalamatkirim);

		etzipcode = (EditText) findViewById(R.id.etzipcode);

		Button btnsubmit = (Button) findViewById(R.id.btnsubmit);
		btnsubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String json = saveToJSON();
				Log.d("mensa", "json: " + json);
				String input = Compression.encodebase64(json);
				String url = MensaApplication.mbs_url + MensaApplication.fullsync_paths[10]; 
				HttpClient httpc = new HttpClient();
				try {
					input = URLEncoder.encode(input, "UTF-8");
					Log.d("mensa", "url: " + input);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					Toast toast = Toast.makeText(AddCustomerActivity.this,
							"Transaction Failed, error: " + e.getMessage(),
							Toast.LENGTH_LONG);
					toast.show();
				}
				String response = httpc.executeHttpPost(url, input);
				Log.d("mensa", "response= " + response);

				try {
					JSONObject statusObj = new JSONObject(response);
					String status = statusObj.getString("status");
					if (status.equals("SUCCESS")) {
						Toast toast = Toast.makeText(AddCustomerActivity.this,
								statusObj.getString("description"),
								Toast.LENGTH_LONG);
						toast.show();
						// delete file disini
						File root = Environment.getExternalStorageDirectory();
						String folder = MensaApplication.APP_DATAFOLDER + "/";
						File file = new File(root, folder
								+ MensaApplication.NEWCUSTFILENAME
								+ etcode.getText().toString());
						file.delete();
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast toast = Toast.makeText(AddCustomerActivity.this,
							"Transaction Failed, error: " + e.getMessage(),
							Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
	}

	public String saveToJSON() {
		JSONObject newcustomer = new JSONObject();
		JSONArray customers = new JSONArray();
		JSONObject cust_prop = new JSONObject();
		try {
			String salescode = getMensaapplication().getSalesid();
			String[] branches = salescode.split("-");
			cust_prop.put("branch", branches[0]);
			cust_prop.put("salesman_code", salescode);
			cust_prop.put("new_cust_code", etcode.getText().toString());
			cust_prop.put("cust_name", etname.getText().toString());
			cust_prop.put("cust_chain", etchain.getText().toString());
			cust_prop.put("cust_group", groupid);
			cust_prop.put("bill_name", etbillname.getText().toString());
			cust_prop.put("bill_addr", etbilladdr.getText().toString());
			cust_prop.put("delivery_name", etdelname.getText().toString());
			cust_prop.put("delivery_addr", etdeladdr.getText().toString());
			cust_prop.put("zip_code", etzipcode.getText().toString());
			cust_prop.put("coordinate", null);
			cust_prop.put("send_status", 0);

			customers.put(0, cust_prop);
			newcustomer.put("new_customer", customers);
			File root = Environment.getExternalStorageDirectory();
			String folder = MensaApplication.APP_DATAFOLDER + "/";
			File file = new File(root, folder
					+ MensaApplication.NEWCUSTFILENAME
					+ etcode.getText().toString());
			MensaApplication.SaveStringToFile(file, newcustomer.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newcustomer.toString();
	}

}
