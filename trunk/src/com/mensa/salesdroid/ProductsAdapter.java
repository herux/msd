package com.mensa.salesdroid;

import java.util.ArrayList;

import com.mensa.salesdroid.EditTextSearch.OnSearchFoundListener;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
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
		RelativeLayout rl = (RelativeLayout) row.findViewById(R.id.relativeLayout1);
		if ((position % 2)==0 ) {
			rl.setBackgroundResource(R.drawable.backgrndselectlist3);
		}else{
			rl.setBackgroundResource(R.drawable.backgrndselectlist4);
		}
		TextView label = (TextView) row.findViewById(R.id.tvTable);
		label.setText("Name. "+getItem(position).getDESCRIPTION());
		TextView partno = (TextView) row.findViewById(R.id.tvCode);
		partno.setText("PartNo. "+getItem(position).getPART_NO());
		TextView labelharga = (TextView) row.findViewById(R.id.tvHarga);
		ImageView iv = (ImageView)row.findViewById(R.id.imageView1);
		
		if (position == 0) {
			rl.removeView(iv);
			rl.removeView(label);
			rl.removeView(labelharga);
			rl.removeView(partno);
			final EditTextSearch etSearch = new EditTextSearch(activity);
			if (row.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				etSearch.setBackgroundResource(R.drawable.searchbkgland);
			}else{
				etSearch.setBackgroundResource(R.drawable.searchbkg);
			}
			etSearch.setTextColor(R.color.titlemaincolor);
			etSearch.setText("Search");
			etSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View arg0, boolean hasFocus) {
					if (hasFocus == true){
						etSearch.setText("");
					}else{
						if (etSearch.getText().equals("")){
							etSearch.setText("Search");
						}
					}
				}
			});
			etSearch.SetOnSearchFoundListener(new OnSearchFoundListener() {
				
				@Override
				public void OnSearchFound(ArrayList<Object> objlist) {
					// hasil pencarian set lewat sini 
				}
			});
			rl.addView(etSearch);
		}

		return row;
	}

}
