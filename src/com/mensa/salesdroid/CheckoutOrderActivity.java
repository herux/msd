/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 23.01.2012	
 */

package com.mensa.salesdroid;

import java.text.NumberFormat;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ListFragment;
import android.widget.TextView;

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
