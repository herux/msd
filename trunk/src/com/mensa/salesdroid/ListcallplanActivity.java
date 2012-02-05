/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 01.01.2012	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mensa.salesdroid.AlertFragmentDialog.OnClickNegativeButtonListener;
import com.mensa.salesdroid.AlertFragmentDialog.OnClickPositiveButtonListener;

public class ListcallplanActivity extends BaseFragmentActivity {
	double LATITUDE = 37.42233;
	double LONGITUDE = -122.083;

	static ArrayList<Customer> customers;
	static ArrayList<Customer> customersforsearch;
	static CustomersAdapter adapter;
	static MensaApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = getMensaapplication();
		setContentView(R.layout.listcallplan);

		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		CustomerLocationListener locListener = new CustomerLocationListener();
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locListener);

		final ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setSubtitle("Sales Call Plan");

		customers = new ArrayList<Customer>();
		customersforsearch = new ArrayList<Customer>();
		int[] datatypes = new int[1];
		datatypes[0] = DataLoader.dlCUSTOMERS;
		DataLoader dtcustomers = new DataLoader(datatypes);
		Customers custs = (Customers) dtcustomers.getDatalist()[0];

		customersforsearch = custs.getCustomers();
		customers = customersforsearch;
		customers.add(0, new Customer("", "", "", "", "", "", "", "", ""));
	}

	public static class CustomersFragment extends ListFragment {
		boolean mDualPane;
		int mCurCheckPosition = 0;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			customers.add(new Customer("", "", "", "", "", "", "", "", ""));
			adapter = new CustomersAdapter(getActivity(),
					R.layout.listcallplan, customers, customersforsearch);
			setListAdapter(adapter);

			View detailsFrame = getActivity().findViewById(R.id.details);
			mDualPane = detailsFrame != null
					&& detailsFrame.getVisibility() == View.VISIBLE;

			if (savedInstanceState != null) {
				mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			}

			if (mDualPane) {
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				showDetails(mCurCheckPosition);
			}

		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putInt("curChoice", mCurCheckPosition);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			showDetails(position);
		}

		void showDetails(int index) {
			mCurCheckPosition = index;
			if (mDualPane) {
				getListView().setItemChecked(index, true);

				CustomerDetailsFragment details = (CustomerDetailsFragment) getFragmentManager()
						.findFragmentById(R.id.details);
				if (details == null || details.getShownIndex() != index) {
					details = CustomerDetailsFragment.newInstance(index);

					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.replace(R.id.details, details);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}

			} else {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ListcallplanDetailActivity.class);
				intent.putExtra("index", index);
				startActivity(intent);
			}
		}

	}

	public static class CustomerDetailsFragment extends Fragment {

		public static CustomerDetailsFragment newInstance(int index) {
			CustomerDetailsFragment f = new CustomerDetailsFragment();

			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);

			return f;
		}

		void showDialog() {
			AlertFragmentDialog alert = AlertFragmentDialog
					.newInstance(R.string.dialog_checkin);
			alert.SetOnClickPositiveButtonListener(new OnClickPositiveButtonListener() {

				@Override
				public void OnClick() {
					application.setCurrentCustomer(customers
							.get(getShownIndex()));
					Toast toast = Toast.makeText(getActivity(), "Customer check, changed.!", Toast.LENGTH_SHORT);
					toast.show();
					Intent intent = new Intent();
					intent.setClass(getActivity(), CustomerMenuActivity.class);
					startActivity(intent);
				}
			});
			alert.SetOnClickNegativeButtonListener(new OnClickNegativeButtonListener() {
				
				@Override
				public void OnClick() {
					Toast toast = Toast.makeText(getActivity(), "Re-Checkin", Toast.LENGTH_SHORT);
					toast.show();
					Intent intent = new Intent();
					intent.setClass(getActivity(), CustomerMenuActivity.class);
					startActivity(intent);
				}
			});
			alert.show(getSupportFragmentManager(), "dialog");
		}

		public int getShownIndex() {
			return getArguments().getInt("index", 0);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.callplandetail, null);
			TextView customerdesc = (TextView) v.findViewById(R.id.tvCustomer);
			customerdesc.setText(customers.get(getShownIndex())
					.getCustomername());
			TextView addresskirim = (TextView) v
					.findViewById(R.id.tvAddressKirim);
			addresskirim.setText(customers.get(getShownIndex())
					.getAlamatkirim());
			TextView addresstagih = (TextView) v
					.findViewById(R.id.tvAddressTagih);
			addresstagih.setText(customers.get(getShownIndex())
					.getAlamattagihan());
			Button btnCheckin = (Button) v.findViewById(R.id.btnCheckin);
			btnCheckin.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (application.getCurrentCustomer() == null) {
						application.setCurrentCustomer(customers
								.get(getShownIndex()));
						Intent intent = new Intent();
						intent.setClass(getActivity(), CustomerMenuActivity.class);
						startActivity(intent);
					} else {
						showDialog();
					}
				}
			});
			return v;
		}
	}

}
