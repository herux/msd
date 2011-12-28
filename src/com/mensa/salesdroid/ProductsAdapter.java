package com.mensa.salesdroid;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductsAdapter extends ArrayAdapter<Product> {
	Activity activity;

	public ProductsAdapter(Activity activity, int textViewResourceId,
			ArrayList<Product> products) {
		super(activity, textViewResourceId, products);
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.productlist, parent, false);
		TextView label = (TextView) row.findViewById(R.id.tvTable);
		label.setText(getItem(position).getDESCRIPTION());

		return row;
	}

}
