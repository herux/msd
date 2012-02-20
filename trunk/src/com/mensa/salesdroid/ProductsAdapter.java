package com.mensa.salesdroid;

import java.text.NumberFormat;
import java.util.ArrayList;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import net.londatiga.android.QuickAction.OnActionItemClickListener;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mensa.salesdroid.EditTextSearch.OnSearchFoundListener;

public class ProductsAdapter extends ArrayAdapter<Product> {
	Activity activity;
	OnListItemClickListener onListItemClickListener;
	final static int REFRESHITEM = 1;

	public ProductsAdapter(Activity activity, int textViewResourceId,
			ArrayList<Product> products) {
		super(activity, textViewResourceId, products);
		this.activity = activity;
	}
	
	public void setOnListItemClickListener(OnListItemClickListener listener){
		onListItemClickListener = listener;
	}
	
	public interface OnListItemClickListener{
		public abstract void OnListItemClick(View view, int position);
	} 

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.productlist, parent, false);
		RelativeLayout rl = (RelativeLayout) row
				.findViewById(R.id.relativeLayout1);
		if ((position % 2) == 0) {
			rl.setBackgroundResource(R.drawable.productlistrow);
		} else {
			rl.setBackgroundResource(R.drawable.productlistrow1);
		}
		TextView label = (TextView) row.findViewById(R.id.tvTable);
		label.setText("Name. " + getItem(position).getDESCRIPTION());
		TextView partno = (TextView) row.findViewById(R.id.tvCode);
		partno.setText("PartNo. " + getItem(position).getPART_NO());
		TextView labelharga = (TextView) row.findViewById(R.id.tvHarga);
		NumberFormat nf = NumberFormat.getInstance();
		String sharga = nf.format(getItem(position).getPRICE());
		labelharga.setText(sharga);
		ImageView iv = (ImageView) row.findViewById(R.id.imageView1);

		if (position == 0) {
			rl.removeView(iv);
			rl.removeView(label);
			rl.removeView(labelharga);
			rl.removeView(partno);
			final EditTextSearch etSearch = new EditTextSearch(activity);
			if (row.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				etSearch.setBackgroundResource(R.drawable.searchbkgland);
			} else {
				etSearch.setBackgroundResource(R.drawable.searchbkg);
			}
			etSearch.setTextColor(R.color.titlemaincolor);
			etSearch.setText("Search");
			etSearch.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View arg0, boolean hasFocus) {
					if (hasFocus == true) {
						etSearch.setText("");
					} else {
						if (etSearch.getText().equals("")) {
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

		ActionItem refreshitem = new ActionItem(REFRESHITEM,
				"Update this product", activity.getResources().getDrawable(
						android.R.drawable.ic_menu_today));
		final QuickAction qa = new QuickAction(activity);
		qa.addActionItem(refreshitem);
		qa.setOnActionItemClickListener(new OnActionItemClickListener() {

			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {
				if (actionId == REFRESHITEM) {

				}
				qa.dismiss();
			}
		});
		row.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				qa.show(view);
				return false;
			}
		});
		
		row.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (onListItemClickListener!=null){
					onListItemClickListener.OnListItemClick(view, position);
				}
			}
		});

		return row;
	}

}
