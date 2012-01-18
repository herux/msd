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
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomersAdapter extends ArrayAdapter<Customer> {
	Activity activity;
	ArrayList<Customer> customersforsearch;

	public CustomersAdapter(Activity activity, int textViewResourceId,
			ArrayList<Customer> customers, ArrayList<Customer> customersforsearch) {
		super(activity, textViewResourceId, customers);
		this.activity = activity;
		this.customersforsearch = customersforsearch;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.customerlist, parent, false);
		TextView label = (TextView) row.findViewById(R.id.tvCustomer);
		label.setText(getItem(position).getCustomername());
		if (position == 0) {
			RelativeLayout rl = (RelativeLayout) row
					.findViewById(R.id.relativeLayout1);
			EditText etSearch = new EditText(activity);
			etSearch.setBackgroundResource(R.drawable.searchbkg);
			etSearch.setText("Search");
			etSearch.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
					
					return false;
				}
			});
			rl.addView(etSearch);
		}

		return row;
	}

}
