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
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
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

		LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll1.getBackground().setAlpha(170);
		LinearLayout ll2 = (LinearLayout) findViewById(R.id.ll2);
		ll2.getBackground().setAlpha(170);
		LinearLayout ll3 = (LinearLayout) findViewById(R.id.ll3);
		ll3.getBackground().setAlpha(170);
		
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
									"Checkout without ordering product!.",
									Toast.LENGTH_SHORT);
					toast.show();
					getMensaapplication().setCurrentCustomer(null);
					finish();
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
		
		Button btninfopromo= (Button) findViewById(R.id.btn_infopromo);
		btninfopromo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CustomerMenuActivity.this,
						ProductviewActivity.class);
				intent.putExtra("opentab", ProductviewActivity.PROMOTAB);
				startActivity(intent);
			}
		});

	}

}
