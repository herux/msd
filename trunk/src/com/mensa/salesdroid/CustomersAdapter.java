package com.mensa.salesdroid;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomersAdapter extends ArrayAdapter<Customer> {
	Activity activity;

	public CustomersAdapter(Activity activity, int textViewResourceId,
			ArrayList<Customer> customers) {
		super(activity, textViewResourceId, customers);
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.customerlist, parent, false);
		TextView label = (TextView) row.findViewById(R.id.tvCustomer); 
		label.setText(getItem(position).getCust_name());

		return row;
	}

}