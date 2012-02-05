package com.mensa.salesdroid;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SalesItemsAdapter extends ArrayAdapter<SalesItem> {
	Activity activity;

	public SalesItemsAdapter(Activity activity, int textViewResourceId,
			ArrayList<SalesItem> salesitems) {
		super(activity, textViewResourceId, salesitems);
		this.activity = activity;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.checkoutorder, parent, false);
		TextView lblname = (TextView) row.findViewById(R.id.tvnamerow);
		lblname.setText(getItem(position).getProduct().getDESCRIPTION()); 
		TextView lblqty = (TextView) row.findViewById(R.id.tvqtyrow);
		lblqty.setText(Float.toString((getItem(position).getQty())));
		NumberFormat nf = NumberFormat.getInstance();
		Double totharga = getItem(position).getQty() * getItem(position).getHarga(); 
		String stotharga = nf.format(totharga);
		TextView lbltotal = (TextView) row.findViewById(R.id.tvtotalrow);
		lbltotal.setText("Rp. "+stotharga);
		return row;
	}

}
