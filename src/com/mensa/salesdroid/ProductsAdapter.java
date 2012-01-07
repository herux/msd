package com.mensa.salesdroid;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
		TextView labelharga = (TextView) row.findViewById(R.id.tvHarga);
		ImageView iv = (ImageView)row.findViewById(R.id.imageView1);
		
		if (position == 0) {
			RelativeLayout rl = (RelativeLayout) row
					.findViewById(R.id.relativeLayout1);
			rl.removeView(iv);
			rl.removeView(label);
			rl.removeView(labelharga);
			EditText etSearch = new EditText(activity);
			etSearch.setBackgroundResource(R.drawable.searchbkg);
			etSearch.setTextColor(R.color.titlemaincolor);
			etSearch.setText("Search");
			rl.addView(etSearch);
		}

		return row;
	}

}
