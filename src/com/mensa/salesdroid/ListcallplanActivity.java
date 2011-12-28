package com.mensa.salesdroid;

import java.util.ArrayList;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mensa.salesdroid.ProductviewActivity.ProductDetailsFragment;

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
	}

	public static class CustomersFragment extends ListFragment {
		boolean mDualPane;
		int mCurCheckPosition = 0;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			customers.add(new Customer("1", "2", "3", "No Customer", "5", "6",
					"7", "8", "9", "10", "11", "12"));
			adapter = new CustomersAdapter(getActivity(),
					R.layout.listcallplan, customers);
			setListAdapter(adapter);
		}

	}

	public static class CustomerDetailsFragment extends Fragment {
		public static ProductDetailsFragment newInstance(int index) {
			ProductDetailsFragment f = new ProductDetailsFragment();

			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);

			return f;
		}

		public int getShownIndex() {
			return getArguments().getInt("index", 0);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			if (container == null) {
				return null;
			}

			ScrollView scroller = new ScrollView(getActivity());
			TextView text = new TextView(getActivity());
			int padding = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 4, getActivity()
							.getResources().getDisplayMetrics());
			text.setPadding(padding, padding, padding, padding);
			scroller.addView(text);
			// text.setText(cus.get(getShownIndex()).getPart_name());
			return scroller;
		}
	}

}
