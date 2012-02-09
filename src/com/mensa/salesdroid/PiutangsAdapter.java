package com.mensa.salesdroid;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PiutangsAdapter extends ArrayAdapter<Piutang> {
	Activity activity;

	public PiutangsAdapter(Activity activity, int textViewResourceId,
			ArrayList<Piutang> piutangs) {
		super(activity, textViewResourceId, piutangs);
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.piutanglist, parent, false);
		TextView tvinvno = (TextView) row.findViewById(R.id.tvinvoiceno);
		tvinvno.setText(getItem(position).getInvoice_no());
		TextView tvdue = (TextView) row.findViewById(R.id.tvduedate);
		tvdue.setText(getItem(position).getDue_date());
		TextView tvamount = (TextView) row.findViewById(R.id.tvinvamount);
		NumberFormat nf = NumberFormat.getInstance();
		String harga = nf.format(getItem(position).getInvoice_amount());
		tvamount.setText("Rp. "+harga);
		return row;
	}

}
