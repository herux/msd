package com.mensa.salesdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import net.londatiga.android.QuickAction.OnActionItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mensa.salesdroid.EditTextSearch.OnSearchClickListener;

public class ProductsAdapter extends ArrayAdapter<Product> {
	Activity activity;
	OnListItemClickListener onListItemClickListener;
	OnItemSearchClickListener onItemSearchClickListener;
	final static int REFRESHITEM = 1;
	private boolean withsearch;
	private EditTextSearch etSearch;
	private String textSearch;

	public ProductsAdapter(Activity activity, int textViewResourceId,
			ArrayList<Product> products) {
		super(activity, textViewResourceId, products);
		this.activity = activity;
	}

	public void setOnListItemClickListener(OnListItemClickListener listener) {
		onListItemClickListener = listener;
	}

	public interface OnListItemClickListener {
		public abstract void OnListItemClick(View view, int position);
	}
	
	public void setOnItemSearchClickListener(OnItemSearchClickListener listener) {
		onItemSearchClickListener = listener;
	}
	
	public interface OnItemSearchClickListener {
		public abstract void OnItemSearchClick(View view);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.productlist, parent, false);
		RelativeLayout rl = (RelativeLayout) row
				.findViewById(R.id.relativeLayout1);
		if ((position % 2) == 0) {
			rl.setBackgroundResource(R.drawable.customerlistrow);
		} else {
			rl.setBackgroundResource(R.drawable.customerlistrow1);
		}
		TextView label = (TextView) row.findViewById(R.id.tvTable);
		label.setText("Name. " + getItem(position).getDESCRIPTION());
		TextView lblqty = (TextView) row.findViewById(R.id.tvQty);
		lblqty.setText("Qty. " + getItem(position).getQTY_ONHAND());
		TextView partno = (TextView) row.findViewById(R.id.tvCode);
		partno.setText("PartNo. " + getItem(position).getPART_NO());
		TextView labelharga = (TextView) row.findViewById(R.id.tvHarga);
		NumberFormat nf = NumberFormat.getInstance();
		String sharga = nf.format(getItem(position).getPRICE());
		labelharga.setText("Price. " + sharga);

		if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			lblqty.setText(Float.toString(getItem(position).getQTY_ONHAND())); 
			partno.setText(getItem(position).getPART_NO());
			labelharga.setText(sharga);
			label.setText(getItem(position).getDESCRIPTION());
		}

		if ((position == 0) && (isWithsearch())) {
			rl.removeView(label);
			rl.removeView(labelharga);
			rl.removeView(partno);
			rl.removeView(lblqty);
			etSearch = new EditTextSearch(activity);
			if (row.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				etSearch.setBackgroundResource(R.drawable.searchbkgland);
			} else {
				etSearch.setBackgroundResource(R.drawable.searchbkg);
			}
			etSearch.setTextColor(R.color.titlemaincolor);
			etSearch.setOnSearchClickListener(new OnSearchClickListener() {
				
				@Override
				public void OnSearchClick(View arg0) {
					if (onItemSearchClickListener!=null){
						onItemSearchClickListener.OnItemSearchClick(arg0);
					}
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
					HttpClient http = new HttpClient();
					String response = http
							.executeHttpPost(
									"http://simfoni.mbs.co.id/services.php?key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9zdG9jaw==&uid="
											+ ((MensaApplication) activity
													.getApplication())
													.getSalesid()
											+ "&part_no="
											+ getItem(position).getPART_NO(),
									"");
					Log.d("mensa", "response : " + response);
					if (!response.equals("null")) {
						try {
							JSONObject psObj = new JSONObject(response);
							JSONArray psArr = psObj
									.getJSONArray("product_stock");
							getItem(position).setDESCRIPTION(
									psArr.getJSONObject(0).getString(
											"DESCRIPTION"));
							getItem(position).setPRICE(
									psArr.getJSONObject(0).optDouble("HNA", 0));
							getItem(position).setQTY_ONHAND(
									psArr.getJSONObject(0).optInt("QTY", 0));
							notifyDataSetChanged();
							Toast toast = Toast.makeText(activity, "Product "
									+ getItem(position).getDESCRIPTION()
									+ " updated!.", Toast.LENGTH_SHORT);
							toast.show();

							FileInputStream jsonfile = null;
							try {
								Log.d("mensa", "file name: "
										+ getItem(position).getFileSource());
								jsonfile = new FileInputStream(new File(
										getItem(position).getFileSource()));
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String productSTR = ((MensaApplication) activity
									.getApplication()).getFileContent(jsonfile);
							psObj = new JSONObject(productSTR);
							JSONArray jsonproducts = psObj
									.getJSONArray("master_product");
							for (int i = 0; i < jsonproducts.length(); i++) {
								if (getItem(position).getPART_NO().equals(
										jsonproducts.getJSONObject(i)
												.getString("PART_NO"))) {
									jsonproducts.getJSONObject(i).put(
											"QTY_ONHAND",
											getItem(position).getQTY_ONHAND());
									jsonproducts.getJSONObject(i).put("HNA",
											getItem(position).getPRICE());
									break;
								}
							}
							File file = new File(getItem(position)
									.getFileSource());
							((MensaApplication) activity.getApplication())
									.SaveStringToFile(file, psObj.toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
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
				if (onListItemClickListener != null) {
					onListItemClickListener.OnListItemClick(view, position);
				}
			}
		});
		
		return row;
	}

	public boolean isWithsearch() {
		return withsearch;
	}

	public void setWithsearch(boolean withsearch) {
		this.withsearch = withsearch;
		if (withsearch) {
			insert(new Product("", "", "", "Search", "", "", 0, 0, 0), 0);
		}
	}

	public String getTextSearch() {
		return etSearch.getText();
	}

	public void setTextSearch(String textSearch) {
		etSearch.setText(textSearch);
	}

}
