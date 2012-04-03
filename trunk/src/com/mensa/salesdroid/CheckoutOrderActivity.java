/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 23.01.2012	
 */

package com.mensa.salesdroid;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutOrderActivity extends BaseFragmentActivity {
	static SalesItemsAdapter adapter;
	static MensaApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		application = getMensaapplication();
		setContentView(R.layout.checkoutlayout);
		TextView tvtotal = (TextView) findViewById(R.id.tvgrandtotal);
		NumberFormat nf = NumberFormat.getInstance();
		tvtotal.setText(nf.format(application.getSalesorder().getTotal()));
		TextView tvordernum = (TextView) findViewById(R.id.tvordernum_value);
		tvordernum.setText(application.getSalesorder().getOrdernumber());
		TextView tvorderdate = (TextView) findViewById(R.id.tvorderdate_value);
		tvorderdate.setText(application.getSalesorder().getDates());
		TextView tvsalesid = (TextView) findViewById(R.id.tvsalesid_value);
		tvsalesid.setText(application.getSalesorder().getSalesmanid());
		Button btnsubmit = (Button) findViewById(R.id.btnsubmit);
		btnsubmit.getBackground().setAlpha(200);
		btnsubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0){
				application.getSalesorder().setSalesitems(
						application.getSalesitems());
				String input = Compression.encodeBase64(application
						.getSalesorder().saveToJSON());
				HttpClient httpc = new HttpClient();
				try {
					input = MensaApplication.mbs_url
							+ MensaApplication.fullsync_paths[7] + "&packet="
							+ URLEncoder.encode(input, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					Toast toast = Toast.makeText(
							CheckoutOrderActivity.this, "Transaction Failed, error: "+e.getMessage(),
							Toast.LENGTH_LONG);
					toast.show();
				}
				String response = httpc.executeHttpPost(input, "");
				Log.d("mensa", "response= " + response);

				try {
					JSONObject statusObj = new JSONObject(response);
					String status = statusObj.getString("status");
					if (status.equals("OK")) {
						Toast toast = Toast.makeText(
								CheckoutOrderActivity.this, statusObj.getString("description"),
								Toast.LENGTH_LONG);
						toast.show();
						// delete file disini
						File root = Environment.getExternalStorageDirectory();
						String folder = MensaApplication.APP_DATAFOLDER + "/";
						File file = new File(root, folder + MensaApplication.SALESORDERFILENAME + application.getSalesorder().getOrdernumber());
						file.delete();
						application.setSalesorder(null);
						application.setSalesitems(null);
						finish();
						Intent intent = new Intent();
						intent.setClass(CheckoutOrderActivity.this, MainmenuActivity.class);
						startActivity(intent);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast toast = Toast.makeText(
							CheckoutOrderActivity.this, "Transaction Failed, error: "+e.getMessage(),
							Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});

		final ActionBar ab = getSupportActionBar();
		ab.setSubtitle("Checkout");
	}

	public static class SalesItemsFragment extends ListFragment {

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			adapter = new SalesItemsAdapter(getActivity(),
					R.layout.checkoutorder, application.getSalesitems());
			setListAdapter(adapter);

		}

	}

}
