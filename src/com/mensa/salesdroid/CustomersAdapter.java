/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 20.12.2011	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomersAdapter extends ArrayAdapter<Customer> {
	Activity activity;
	ArrayList<Customer> customersforsearch;

	public CustomersAdapter(Activity activity, int textViewResourceId,
			ArrayList<Customer> customers,
			ArrayList<Customer> customersforsearch) {
		super(activity, textViewResourceId, customers);
		this.activity = activity;
		this.customersforsearch = customersforsearch;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.customerlist, parent, false);
		RelativeLayout rl = (RelativeLayout) row
				.findViewById(R.id.relativeLayout1);
		if ((position % 2) == 0) {
			rl.setBackgroundResource(R.drawable.customerlistrow);
		} else {
			rl.setBackgroundResource(R.drawable.customerlistrow1);
		}
		ImageView iv = (ImageView) row.findViewById(R.id.ivCustomer);
		TextView label = (TextView) row.findViewById(R.id.tvCustomer);
		label.setText(getItem(position).getCustomername());
		TextView address = (TextView) row.findViewById(R.id.tvAddess);
		address.setText(getItem(position).getAlamattagihan());
//		if (position == 0) {
//			final EditTextSearch etSearch = new EditTextSearch(activity);
//			etSearch.setBackgroundResource(R.drawable.searchbkg);
//			etSearch.setText("Search");
//			etSearch.setOnKeyListener(new OnKeyListener() {
//
//				@Override
//				public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
//
//					return false;
//				}
//			});
//
//			etSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//				@Override
//				public void onFocusChange(View arg0, boolean hasFocus) {
//					if (hasFocus == true) {
//						etSearch.setText("");
//					} else {
//						if (etSearch.getText().equals("")) {
//							etSearch.setText("Search");
//						}
//					}
//				}
//			});
//			rl.addView(etSearch);
//			rl.removeView(address);
//			rl.removeView(iv);
// 		}

		return row;
	}

}
