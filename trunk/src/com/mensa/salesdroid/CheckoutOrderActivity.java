/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 23.01.2012	
 */

package com.mensa.salesdroid;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutOrderActivity extends BaseFragmentActivity {
	static SalesItemsAdapter adapter;
	static ReturnItemAdapter adapterRI;
	static MensaApplication application;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	static final int CHECKOUT_DIALOG = 1;

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
					application.getSalesorder().setCoordinate(application.getLongitudelatitude());
					String input = application.getSalesorder().saveToJSON();
					Log.d("mensa", "request: "+input);
					input = Compression.encodeBase64(input);
					HttpClient httpc = new HttpClient();
					try {
						input = MensaApplication.mbs_url
								+ MensaApplication.fullsync_paths[7]
								+ "&packet="
								+ URLEncoder.encode(input, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						Toast toast = Toast.makeText(
								CheckoutOrderActivity.this,
								"Transaction Failed, error: " + e.getMessage(),
								Toast.LENGTH_LONG);
						toast.show();
					}
					String response = httpc.executeHttpPost(input, "");
					Log.d("mensa", "response= " + response);

					try {
						JSONObject statusObj = new JSONObject(response);
						String status = statusObj.getString("status");
						if (status.equals("OK")) {
							Toast toast = Toast.makeText(
									CheckoutOrderActivity.this,
									statusObj.getString("description"),
									Toast.LENGTH_LONG);
							toast.show();
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
							application.setSalesorder(null);
							application.setSalesitems(null);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast toast = Toast.makeText(
								CheckoutOrderActivity.this,
								"Submit Transaction Order failed, error: "
										+ e.getMessage(), Toast.LENGTH_LONG);
						toast.show();
					}
				}
				if (application.getReturnitems() != null) {
					application.getReturns().setReturnitems(
							application.getReturnitems());
					application.getReturns().setCoordinate(application.getLongitudelatitude());
					String input = application.getReturns().saveToJSON();
					Log.d("mensa", "request: "+input);
					input = Compression.encodeBase64(input);
					HttpClient httpc = new HttpClient();
					try {
						input = MensaApplication.mbs_url
								+ MensaApplication.fullsync_paths[9]
								+ "&packet="
								+ URLEncoder.encode(input, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						Toast toast = Toast.makeText(
								CheckoutOrderActivity.this,
								"Submit Transaction Return failed, error: "
										+ e.getMessage(), Toast.LENGTH_LONG);
						toast.show();
					}
					String response = httpc.executeHttpPost(input, "");
					Log.d("mensa", "response= " + response);

					try {
						JSONObject statusObj = new JSONObject(response);
						String status = statusObj.getString("status");
						if (status.equals("OK")) {
							Toast toast = Toast.makeText(
									CheckoutOrderActivity.this,
									statusObj.getString("description"),
									Toast.LENGTH_LONG);
							toast.show();
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
						Toast toast = Toast.makeText(
								CheckoutOrderActivity.this,
								"Transaction Failed, error: " + e.getMessage(),
								Toast.LENGTH_LONG);
						toast.show();
					}
				}
				application.setCurrentCustomer(null);
				Intent intent = new Intent();
				intent.setClass(CheckoutOrderActivity.this,
						MainmenuActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		Button btnCancel = (Button) findViewById(R.id.btncancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				application.setCurrentCustomer(null);
				application.setSalesorder(null);
				application.setSalesitems(null);
				application.setReturnitems(null);
				application.setReturns(null);
				Toast toast = Toast.makeText(CheckoutOrderActivity.this, "Cancel Order and/or Return", Toast.LENGTH_SHORT);
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
			View v = inflater.inflate(R.layout.returnfragment, null);
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
			}
			return v;
		}
	}

	public static class OrderFragment extends Fragment {

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
			View v = inflater.inflate(R.layout.orderfragment, null);
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
