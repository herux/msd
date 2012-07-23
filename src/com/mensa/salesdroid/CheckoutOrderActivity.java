/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 23.01.2012	
 */

package com.mensa.salesdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import net.londatiga.android.QuickAction.OnActionItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutOrderActivity extends BaseFragmentActivity {
	static SalesItemsAdapter adapter;
	static ReturnItemAdapter adapterRI;
	static MensaApplication application;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	static final int CHECKOUT_DIALOG = 1;
	private static final int DELETE = 1;
	String respAll = "";
	
	private void FreeCheckOutObj(){
		application.setCurrentCustomer(null);
		application.setSalesorder(null);
		application.setSalesitems(null);
		application.setReturnitems(null);
		application.setReturns(null);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog ad = null;
		switch (id){
		case CHECKOUT_DIALOG: {
			ad = new AlertDialog.Builder(this).create();
			ad.setCancelable(false); 
			ad.setMessage(respAll);
			ad.setButton("Ok", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					FreeCheckOutObj();
					Intent intent = new Intent();
					intent.setClass(CheckoutOrderActivity.this,
							MainmenuActivity.class);
					startActivity(intent);
					finish();
				}
				
			});
			ad.show();
			break;
		}
		}
		return ad;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		application = getMensaapplication();
		setContentView(R.layout.checkoutlayout);
		Button btnSubmit = (Button) findViewById(R.id.btnsubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				if (application.getSalesitems() != null) {
					application.getSalesorder().setSalesitems(
							application.getSalesitems());
					application.getSalesorder().setCoordinate(
							application.getLongitudelatitude());
					String input = application.getSalesorder().saveToJSON();
					String url = "";
					Log.d("mensa", "request Order: " + input);
					input = Compression.encodebase64(input);
					HttpClient httpc = new HttpClient();
					try {
						url = MensaApplication.mbs_url + MensaApplication.fullsync_paths[7];
						input = URLEncoder.encode(input, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						respAll = respAll + "| Order: Failed, error msg: "+e.getMessage()+"|";
					}
					String response = httpc.executeHttpPost(url, input);
					Log.d("mensa", "response order= " + response);

					try {
						JSONObject statusObj = new JSONObject(response);
						String status = statusObj.getString("status");
						if (status.equals("SUCCESS")) {
							respAll = respAll + "| Order: "+status+" |";
							// delete file disini
							File root = Environment
									.getExternalStorageDirectory();
							String folder = MensaApplication.APP_DATAFOLDER
									+ "/";
							File file = new File(root, folder
									+ MensaApplication.SALESORDERFILENAME
									+ application.getSalesorder()
											.getOrdernumber());
							file.delete();
							//---simpan ordernumber
							file = new File(root, folder + MensaApplication.ORDERNOLIST);
							JSONArray orderlist = null; 
							JSONObject orderobj = null;
							if (file.exists()){
								FileInputStream fileis = null;
								try {
									fileis = new FileInputStream(file);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
								
								orderlist = new JSONArray(MensaApplication.getFileContent(fileis));
							}
							orderobj = new JSONObject();
							orderobj.put("orderno", application.getSalesorder().getOrdernumber());
							orderobj.put("date", application.getDateString());
							orderlist.put(orderobj);
							JSONArray tmpOrderList = new JSONArray(); 
							for (int i = 0; i < orderlist.length(); i++) {
								orderobj = orderlist.getJSONObject(i);
								if (orderobj.getString("date").equals(application.getDateString())){
									tmpOrderList.put(orderobj);
								}
							}
							MensaApplication.SaveStringToFile(file, tmpOrderList.toString());
							//--->
							application.setSalesorder(null);
							application.setSalesitems(null);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						respAll = respAll + "| Order: Failed, error msg: "+e.getMessage()+"|";
					}
				}
				if (application.getReturnitems() != null) {
					application.getReturns().setReturnitems(
							application.getReturnitems());
					application.getReturns().setCoordinate(
							application.getLongitudelatitude());
					String input = application.getReturns().saveToJSON();
					String url = "";
					Log.d("mensa", "request Return: " + input);
					input = Compression.encodebase64(input);
					HttpClient httpc = new HttpClient();
					try {
						url = MensaApplication.mbs_url + MensaApplication.fullsync_paths[9]; 
						input = URLEncoder.encode(input, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						respAll = respAll + "| Return: Failed, error msg: "+e.getMessage()+"|";
					}
					String response = httpc.executeHttpPost(url, input);
					Log.d("mensa", "response Return= " + response);

					try {
						JSONObject statusObj = new JSONObject(response);
						String status = statusObj.getString("status");
						if (status.equals("SUCCESS")) {
							respAll = respAll + "| Return: "+status+" |";
							// delete file disini
							File root = Environment
									.getExternalStorageDirectory();
							String folder = MensaApplication.APP_DATAFOLDER
									+ "/";
							File file = new File(root, folder
									+ MensaApplication.RETURNORDERFILENAME
									+ application.getReturns().getReturnNo());
							file.delete();
							application.setReturns(null);
							application.setReturnitems(null);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						respAll = respAll + "| Return: Failed, error msg: "+e.getMessage()+"|";
					}
				}

				// ----------checkout
				JSONObject checkoutObj = new JSONObject();
				String salescode = getMensaapplication().getSalesid();
				String[] branches = salescode.split("-");
				try {
					checkoutObj.put("CABANG", branches[0]);
					checkoutObj.put("SALESMAN_CODE", getMensaapplication()
							.getSalesid());
					checkoutObj.put("CUSTOMER_CODE", getMensaapplication()
							.getCurrentCustomer().getCUSTOMER_CODE());
					checkoutObj.put("STATUS", "2");
					checkoutObj.put("WAKTU", getMensaapplication()
							.getDateTimeStr());
					checkoutObj.put("KOORDINAT", getMensaapplication()
							.getLongitudelatitude());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String input = checkoutObj.toString();
				String url = "http://simfoni.mbs.co.id/services.php?key=czRMZTU0dVRvTWF0MTBu&tab=bW9iX2NoZWNrX2lu"; 
				Log.d("mensa", "Request CheckOut="+input);
				input = Compression.encodebase64(input);
				HttpClient httpc = new HttpClient();
				try {
					input = URLEncoder.encode(input, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					respAll = respAll + "| CheckOut: Failed, error msg: "+e.getMessage()+"|";
				}
				String response = httpc.executeHttpPost(url, input);
				Log.d("mensa", "response CheckOut= " + response);
				if (response.equals("null")) {
					respAll = respAll + "| CheckOut: Failed, error msg: null response |";
				}
				
				respAll = "Checkout Report: "+respAll;
//				Toast toast = Toast.makeText(CheckoutOrderActivity.this,
//						"Checkout Report: "+respAll,
//						Toast.LENGTH_LONG);
//				toast.show();

				// ------------------

				showDialog(CHECKOUT_DIALOG);
			}
		});

		Button btnCancel = (Button) findViewById(R.id.btncancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FreeCheckOutObj();
				Toast toast = Toast.makeText(CheckoutOrderActivity.this,
						"Cancel Order and/or Return", Toast.LENGTH_SHORT);
				toast.show();
				Intent intent = new Intent();
				intent.setClass(CheckoutOrderActivity.this,
						MainmenuActivity.class);
				startActivity(intent);
				finish();
			}
		});

		final ActionBar ab = getSupportActionBar();
		ab.setSubtitle("Checkout");
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mTabsAdapter = new TabsAdapter(this, getSupportActionBar(), mViewPager);

		Tab tabOrder = ab.newTab();
		tabOrder.setText("Order");
		mTabsAdapter.addTab(tabOrder, OrderFragment.class);

		Tab tabRetur = ab.newTab();
		tabRetur.setText("Return");
		mTabsAdapter.addTab(tabRetur, ReturnFragment.class);

		if (savedInstanceState != null) {
			getSupportActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt("index"));
		}
	}

	public static class SalesItemsFragment extends ListFragment {

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			ArrayList<SalesItem> si;
			if (application.getSalesitems() != null) {
				si = application.getSalesitems();
			} else {
				si = new ArrayList<SalesItem>();
			}

			adapter = new SalesItemsAdapter(getActivity(),
					R.layout.checkoutorder, si);
			setListAdapter(adapter);

		}

	}

	public static class ReturnItemsListFragment extends ListFragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			ArrayList<ReturnItem> ri;
			if (application.getReturnitems() != null) {
				ri = application.getReturnitems();
			} else {
				ri = new ArrayList<ReturnItem>();
			}
			adapterRI = new ReturnItemAdapter(getActivity(),
					R.layout.returnslist, ri);
			setListAdapter(adapterRI);
		}

	}

	public static class ReturnFragment extends Fragment {
		QuickAction qa;
		public static ReturnFragment newInstance(int index) {
			ReturnFragment rf = new ReturnFragment();

			Bundle args = new Bundle();
			args.putInt("index", index);
			rf.setArguments(args);

			return rf;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View v = inflater.inflate(R.layout.returnfragment, null);
			if (application.getReturnitems() != null) {
				TextView lblreturnnum = (TextView) v
						.findViewById(R.id.tvreturnsnum_value_rf);
				lblreturnnum.setText(application.getReturns().getReturnNo());
				TextView lblreturndate = (TextView) v
						.findViewById(R.id.tvreturnsdate_value_rf);
				lblreturndate.setText(application.getReturns()
						.getDatetimecheckin());
				TextView lblsales = (TextView) v
						.findViewById(R.id.tvsalesid_value_rf);
				lblsales.setText(application.getReturns().getSalesid());
				CheckBox cb = (CheckBox) v.findViewById(R.id.cball_rf);
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						for (int i = 0; i < adapterRI.getCount(); i++) {
							adapterRI.setChecked(i, arg1);
						}
						if (arg1){
							ActionItem deleteItem = new ActionItem(DELETE, "Delete All checked", getActivity().getResources()
									.getDrawable(android.R.drawable.ic_menu_delete));
							qa = new QuickAction(getActivity());
							qa.addActionItem(deleteItem);
							qa.setOnActionItemClickListener(new OnActionItemClickListener() {
								
								@Override
								public void onItemClick(QuickAction source, int pos, int actionId) {
									application.getReturns().getReturnitems().removeAll(application.getReturns().getReturnitems());
									adapterRI.notifyDataSetChanged();
								}
							});
							qa.show(v);
						}
					}
				});
			}
			return v;
		}
	}

	public static class OrderFragment extends Fragment {
		QuickAction qa;

		public static OrderFragment newInstance(int index) {
			OrderFragment of = new OrderFragment();

			Bundle args = new Bundle();
			args.putInt("index", index);
			of.setArguments(args);

			return of;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View v = inflater.inflate(R.layout.orderfragment, null);
			if (application.getSalesitems() != null) {
				TextView tvtotal = (TextView) v.findViewById(R.id.tvgrandtotal);
				NumberFormat nf = NumberFormat.getInstance();
				tvtotal.setText(nf.format(application.getSalesorder()
						.getTotal()));
				TextView tvordernum = (TextView) v
						.findViewById(R.id.tvordernum_value);
				tvordernum
						.setText(application.getSalesorder().getOrdernumber());
				TextView tvorderdate = (TextView) v
						.findViewById(R.id.tvorderdate_value);
				tvorderdate.setText(application.getSalesorder().getDates());
				TextView tvsalesid = (TextView) v
						.findViewById(R.id.tvsalesid_value);
				tvsalesid.setText(application.getSalesorder().getSalesmanid());
				CheckBox cb = (CheckBox) v.findViewById(R.id.cball_or);
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						if (adapter != null) {
							for (int i = 0; i < adapter.getCount(); i++) {
								adapter.setChecked(i, arg1);
							}
						}
						
						if (arg1){
							ActionItem deleteItem = new ActionItem(DELETE, "Delete All checked", getActivity().getResources()
									.getDrawable(android.R.drawable.ic_menu_delete));
							qa = new QuickAction(getActivity());
							qa.addActionItem(deleteItem);
							qa.setOnActionItemClickListener(new OnActionItemClickListener() {
								
								@Override
								public void onItemClick(QuickAction source, int pos, int actionId) {
									application.getSalesitems().removeAll(application.getSalesitems());
									adapter.notifyDataSetChanged();
								}
							});
							qa.show(v);
						}
					}
				});
			}
			return v;
		}

	}

	public static class TabsAdapter extends FragmentPagerAdapter implements
			ViewPager.OnPageChangeListener, ActionBar.TabListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<String> mTabs = new ArrayList<String>();

		public TabsAdapter(FragmentActivity activity, ActionBar actionBar,
				ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mActionBar = actionBar;
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss) {
			mTabs.add(clss.getName());
			mActionBar.addTab(tab.setTabListener(this));
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			return Fragment.instantiate(mContext, mTabs.get(position), null);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			mViewPager.setCurrentItem(tab.getPosition());
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}
	}

}
