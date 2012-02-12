package com.mensa.salesdroid;

import java.text.NumberFormat;
import java.util.ArrayList;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import net.londatiga.android.QuickAction.OnActionItemClickListener;
import net.londatiga.android.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SalesItemsAdapter extends ArrayAdapter<SalesItem> {
	Activity activity;
	final QuickAction qa;
	
	private static final int DELETE = 1;
	private static final int CHAQTY = 2;

	public SalesItemsAdapter(final Activity activity, int textViewResourceId,
			ArrayList<SalesItem> salesitems) {
		super(activity, textViewResourceId, salesitems);
		this.activity = activity;

		ActionItem deleteItem = new ActionItem(DELETE, "Delete", activity.getResources()
				.getDrawable(android.R.drawable.ic_menu_delete));
		ActionItem changeqty = new ActionItem(CHAQTY, "Change Qty", activity.getResources()
				.getDrawable(android.R.drawable.ic_menu_edit));
		qa = new QuickAction(activity);
		qa.addActionItem(deleteItem);
		qa.addActionItem(changeqty);
		qa.setOnActionItemClickListener(new OnActionItemClickListener() {
			
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {
				if (actionId==DELETE){
					((MensaApplication)activity.getApplication()).deleteSalesitem(pos);
				}
				if (actionId==CHAQTY){
//					((MensaApplication)activity.getApplication()).changeQtySalesitem(pos, newQty);
				}
				notifyDataSetChanged();
				qa.dismiss();
			}
		});
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
		Double totharga = getItem(position).getQty()
				* getItem(position).getHarga();
		String stotharga = nf.format(totharga);
		TextView lbltotal = (TextView) row.findViewById(R.id.tvtotalrow);
		lbltotal.setText(stotharga);
		if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			TextView lblprice = (TextView) row.findViewById(R.id.tvpricerow);
			Double price = getItem(position).getHarga();
			String sprice = nf.format(price);
			lblprice.setText(sprice);
		}
		row.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				qa.show(arg0);
			}
		});
		return row;
	}
}
