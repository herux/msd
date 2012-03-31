package com.mensa.salesdroid;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCustomerActivity extends BaseFragmentActivity {
	EditText etcode, etname, etchain, etgroup, etbillname, etbilladdr,
			etdelname, etdeladdr, etzipcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcustomerlayout);

		final ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setSubtitle("Adding New Customer");

		etcode = (EditText) findViewById(R.id.etcode);
		etname = (EditText) findViewById(R.id.etname);
		etchain = (EditText) findViewById(R.id.etchain);
		etgroup = (EditText) findViewById(R.id.etgrup);

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
				Log.d("mensa", "json: "+json);
				String input = Compression.encodeBase64(json);
				HttpClient httpc = new HttpClient();
				try {
					input = MensaApplication.mbs_url
							+ MensaApplication.paths[10] + "&packet="
							+ URLEncoder.encode(input, "UTF-8");
					Log.d("mensa", "url: "+input);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					Toast toast = Toast.makeText(AddCustomerActivity.this,
							"Transaction Failed, error: " + e.getMessage(),
							Toast.LENGTH_LONG);
					toast.show();
				}
				String response = httpc.executeHttpPost(input, "");
				Log.d("mensa", "response= " + response);

				try {
					JSONObject statusObj = new JSONObject(response);
					String status = statusObj.getString("status");
					if (status.equals("OK")) {
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
		JSONObject cust_prop = new JSONObject();
		try {
			String salescode = getMensaapplication().getSalesid();
			String[] branches = salescode.split("-");
			cust_prop.put("branch", branches[0]);
			cust_prop.put("salesman_code", salescode);
			cust_prop.put("new_cust_code", etcode.getText().toString());
			cust_prop.put("cust_name", etname.getText().toString());
			cust_prop.put("cust_chain", etchain.getText().toString());
			cust_prop.put("cust_group", etgroup.getText().toString());
			cust_prop.put("bill_name", etbillname.getText().toString());
			cust_prop.put("bill_addr", etbilladdr.getText().toString());
			cust_prop.put("delivery_name", etdelname.getText().toString());
			cust_prop.put("delivery_addr", etdeladdr.getText().toString());
			cust_prop.put("zip_code", etzipcode.getText().toString());
			cust_prop.put("coordinate", "");
			cust_prop.put("send_status", 0);

			newcustomer.put("new_customer", cust_prop);
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
