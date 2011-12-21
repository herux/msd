package com.mensa.salesdroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.Tab;
import android.support.v4.app.ActionBar.TabListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;

public class ListcallplanActivity extends BaseFragmentActivity {
	double LATITUDE = 37.42233;
	double LONGITUDE = -122.083;

	static ArrayList<Customer> customers;
	static CustomersAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listcallplan);

		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		CustomerLocationListener locListener = new CustomerLocationListener();
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locListener);

		customers = new ArrayList<Customer>();
		final ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbarbackground));
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayUseLogoEnabled(false);
		Tab clTab = ab.newTab();
		clTab.setText("Search from current location");
		clTab.setTabListener(new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				// GetOutletsFromLocation();
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		});
		ab.addTab(clTab);
		ab.addTab(ab.newTab().setText("Search from database"));
		showTabsNav();
		Log.d("mensa", "ListcallplanActivity OnCreate");
	}

	public static class CustomersFragment extends ListFragment {
		boolean mDualPane;
		int mCurCheckPosition = 0;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			Log.d("mensa", "CustomersFragment onActivityCreated");
			customers.add(new Customer("1", "2", "3", "No Customer", "5", "6",
					"7", "8", "9", "10", "11", "12"));
			adapter = new CustomersAdapter(getActivity(),
					R.layout.listcallplan, customers);
			setListAdapter(adapter);
		}

	}

	private void GetOutletsFromLocation() {
		int addrCount = 5;
		Log.d("mensa", "new Geocoder(this, Locale.ENGLISH");
		Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
		try {
			Log.d("mensa",
					"geocoder.getFromLocation(LATITUDE, LONGITUDE, addrCount);");
			List<Address> OutLetaddresses = geocoder.getFromLocation(LATITUDE,
					LONGITUDE, addrCount);
			Log.d("mensa", "if (OutLetaddresses != null)");
			if (OutLetaddresses != null) {
				for (int j = 0; j < addrCount; j++) {
					Log.d("mensa",
							"Address returnedAddress = OutLetaddresses.get(j)");
					Address returnedAddress = OutLetaddresses.get(j);
					StringBuilder strReturnedAddress = new StringBuilder();
					for (int i = 0; i < returnedAddress
							.getMaxAddressLineIndex(); i++) {
						strReturnedAddress.append(
								returnedAddress.getAddressLine(i)).append("\n");
					}
					Log.d("mensa", strReturnedAddress.toString());
					Customer customer = new Customer("", "", "",
							strReturnedAddress.toString(), "", "", "", "", "",
							"", "", "");
					Log.d("mensa", "customer: " + customer.getCust_name());
					customers.add(customer);
					Log.d("mensa", "customers.add");
				}
			} else {
				Customer customer = new Customer("", "", "",
						"No Address returned!", "", "", "", "", "", "", "", "");
				customers.add(customer);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Customer customer = new Customer("", "", "",
					"No Address returned!", "", "", "", "", "", "", "", "");
			customers.add(customer);
		}
		adapter.notifyDataSetChanged();
		Log.d("mensa", "after adapter.notifyDataSetChanged()");
	}

}
