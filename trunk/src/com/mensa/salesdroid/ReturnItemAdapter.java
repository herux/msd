package com.mensa.salesdroid;

import java.util.List;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import net.londatiga.android.R;
import net.londatiga.android.QuickAction.OnActionItemClickListener;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReturnItemAdapter extends ArrayAdapter<ReturnItem> {
	Activity activity;
	final QuickAction qa;
	private static final int DELETE = 1;

	public ReturnItemAdapter(final Activity activity, int textViewResourceId,
			List<ReturnItem> returnitems) {
		super(activity, textViewResourceId, returnitems);
		this.activity = activity;
		ActionItem deleteItem = new ActionItem(DELETE, "Delete", activity.getResources()
				.getDrawable(android.R.drawable.ic_menu_delete));
		qa = new QuickAction(activity);
		qa.addActionItem(deleteItem);
		qa.setOnActionItemClickListener(new OnActionItemClickListener() {
			
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {
				if (actionId==DELETE){
					((MensaApplication)activity.getApplication()).deleteReturnItem(pos);
				}
				notifyDataSetChanged();
				qa.dismiss();
			}
		});
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.returnslist, parent, false);
		TextView tvproductcode = (TextView) row.findViewById(R.id.tvproductcoderow);
		tvproductcode.setText(getItem(position).getProductcode());
		TextView tvqty = (TextView) row.findViewById(R.id.tvqtyrow);
		tvqty.setText(Float.toString(getItem(position).getQty()));
		TextView tvdesc = (TextView) row.findViewById(R.id.tvdescrow);
		tvdesc.setText(getItem(position).getDescription());
		if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			ImageView ivproduct = (ImageView) row.findViewById(R.id.ivproductrow);
			ivproduct = getItem(position).getImage();
		}
		row.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				qa.show(view);
			}
		});
		
		return row;
	}

}
