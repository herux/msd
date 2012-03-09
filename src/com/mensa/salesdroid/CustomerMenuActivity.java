/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 14.01.2012	
 */

package com.mensa.salesdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CustomerMenuActivity extends BaseFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customermenu);

		final ActionBar ab = getSupportActionBar();
		ab.setSubtitle("Customer menu ("
				+ getMensaapplication().getCurrentCustomer().getNAMA()
				+ ") - Sales: "+getMensaapplication().getSalesid());

		Button btninforeturn = (Button) findViewById(R.id.btn_inforeturn);
		btninforeturn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CustomerMenuActivity.this,
						InfoReturActivity.class);
				startActivity(intent);
			}
		});

		Button btncaptureorder = (Button) findViewById(R.id.btn_capturorder);
		btncaptureorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CustomerMenuActivity.this,
						ProductviewActivity.class);
				startActivity(intent);
			}
		});

		Button btncheckoutorder = (Button) findViewById(R.id.btn_checkout);
		btncheckoutorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (getMensaapplication().getSalesitems() == null) {
					Toast toast = Toast
							.makeText(
									CustomerMenuActivity.this,
									"There has not been selected product, please go to the menu's 'capture order' to choose",
									Toast.LENGTH_SHORT);
					toast.show();
				} else {
					Intent intent = new Intent();
					intent.setClass(CustomerMenuActivity.this,
							CheckoutOrderActivity.class);
					startActivity(intent);
				}
			}
		});

		Button btnlistpiutang = (Button) findViewById(R.id.btn_listpiutang);
		btnlistpiutang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CustomerMenuActivity.this,
						ListPiutangActivity.class);
				startActivity(intent);
			}
		});

	}

}
