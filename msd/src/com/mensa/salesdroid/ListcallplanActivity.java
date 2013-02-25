/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 01.01.2012	
 */

package com.mensa.salesdroid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mensa.salesdroid.AlertFragmentDialog.OnClickNegativeButtonListener;
import com.mensa.salesdroid.AlertFragmentDialog.OnClickPositiveButtonListener;
import com.mensa.salesdroid.GPSLocationListener.OnGPSLocationChanged;

public class ListcallplanActivity extends BaseFragmentActivity {
	static final int EMPTYDATA_DIALOG = 0; 

	static ArrayList<Customer> customers;
	static CustomersAdapter adapter;
	static MensaApplication application;
	private static Button btnCheckin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = getMensaapplication();
		setContentView(R.layout.listcallplan);

		final LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		final GPSLocationListener mlocListener = new GPSLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);
		mlocListener.setOnGPSLocationChanged(new OnGPSLocationChanged() {

			@Override
			public void OnLatAndLongChanged(double longitude, double latitude) {
				String longitudelatitude = Double.toString(longitude) + ","
						+ Double.toString(latitude);
				application.setLongitudelatitude(longitudelatitude);
				mlocManager.removeUpdates(mlocListener);
				Toast toast = Toast.makeText(ListcallplanActivity.this,
						"location: " + longitudelatitude, Toast.LENGTH_LONG);
				toast.show();
//				if (btnCheckin != null){
//					btnCheckin.setEnabled(true);
//				}
			}
		});

		final ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setSubtitle("Sales Call Plan");

		customers = new ArrayList<Customer>();
		int[] datatypes = new int[1];
		datatypes[0] = DataLoader.dlCUSTOMERS;
		DataLoader dtcustomers = new DataLoader(datatypes);
		Customers custs = (Customers) dtcustomers.getDatalist()[0];
		customers = custs.getCustomers();
		if (customers.size() == 0) {
			showDialog(EMPTYDATA_DIALOG);
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog ad = null;
		switch (id) {
		case EMPTYDATA_DIALOG: {
			ad = new AlertDialog.Builder(this).create();
			ad.setCancelable(false); 
			ad.setMessage("Data call plan not found, Make sure that the sync process is successful");
			ad.setButton("OK", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        dialog.dismiss();                    
			    }
			});
			ad.show();
			break;
		}
		}
		return ad;
	}

	public static class CustomersFragment extends ListFragment {
		boolean mDualPane;
		int mCurCheckPosition = 0;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			adapter = new CustomersAdapter(getActivity(),
					R.layout.listcallplan, customers);
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
				getActivity().finish();
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
					Toast toast = Toast.makeText(getActivity(),
							"Customer check, changed.!", Toast.LENGTH_SHORT);
					toast.show();
					getActivity().finish();
					Intent intent = new Intent();
					intent.setClass(getActivity(), CustomerMenuActivity.class);
					startActivity(intent);
				}
			});
			alert.SetOnClickNegativeButtonListener(new OnClickNegativeButtonListener() {

				@Override
				public void OnClick() {
					Toast toast = Toast.makeText(getActivity(), "Re-Checkin",
							Toast.LENGTH_SHORT);
					toast.show();
					getActivity().finish();
					Intent intent = new Intent();
					intent.setClass(getActivity(), CustomerMenuActivity.class);
					startActivity(intent);
				}
			});
//			alert.show(getSupportFragmentManager(), "dialog");
		}

		public int getShownIndex() {
			return getArguments().getInt("index", 0);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.callplandetail, null);
			RelativeLayout rl2 = (RelativeLayout) v
					.findViewById(R.id.relativeLayout2);
			rl2.getBackground().setAlpha(170);
			RelativeLayout rl1 = (RelativeLayout) v
					.findViewById(R.id.relativeLayout1);
			rl1.getBackground().setAlpha(170);
			TextView customerdesc = (TextView) v
					.findViewById(R.id.tvCustomerValue);
			customerdesc.setText(customers.get(getShownIndex()).getNAMA());
			TextView addresskirim = (TextView) v
					.findViewById(R.id.tvdeliveryaddressValue);
			addresskirim.setText(customers.get(getShownIndex())
					.getALAMAT_KIRIM());
			TextView addresstagih = (TextView) v
					.findViewById(R.id.tvBillAddressValue);
			addresstagih.setText(customers.get(getShownIndex())
					.getALAMAT_KIRIM());
			btnCheckin = (Button) v.findViewById(R.id.btnCheckin);
			btnCheckin.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (application.getCurrentCustomer() == null) {
						application.setCurrentCustomer(customers
								.get(getShownIndex()));
						Log.d("mensa", "Checkin:"
								+ application.getCurrentCustomer()
										.getCUSTOMER_CODE() + ":"
								+ application.getCurrentCustomer().getNAMA());
						
						// ----------checkin
						JSONObject checkoutObj = new JSONObject();
						String salescode = application.getSalesid();
						String[] branches = salescode.split("-");
						try {
							checkoutObj.put("CABANG", branches[0]);
							checkoutObj.put("SALESMAN_CODE", application
									.getSalesid());
							checkoutObj.put("CUSTOMER_CODE", application
									.getCurrentCustomer().getCUSTOMER_CODE());
							checkoutObj.put("STATUS", "1");
							checkoutObj.put("WAKTU", application
									.getDateTimeStr());
							checkoutObj.put("KOORDINAT", application.getLongitudelatitude()); 
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						String input = checkoutObj.toString();
						String url = "http://simfoni.mbs.co.id/services.php?key=czRMZTU0dVRvTWF0MTBu&tab=bW9iX2NoZWNrX2lu";
						Log.d("mensa", "Request Checkin="+input);
						input = Compression.encodebase64(input);
						HttpClient httpc = new HttpClient();
						try {
							input = URLEncoder.encode(input, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							Toast toast = Toast.makeText(getActivity(),
									"Submit Checkout failed, error: " + e.getMessage(),
									Toast.LENGTH_LONG);
							toast.show();
						}
						String response = httpc.executeHttpPost(url, input);
						Log.d("mensa", "response Checkin= " + response);
						if ((response.equals("null"))||(response.equals(""))) {
							Toast toast = Toast.makeText(getActivity(),
									"Submit Checkout failed, error: null response",
									Toast.LENGTH_LONG);
							toast.show();
							return;
						}

						// ------------------
						getActivity().finish();
						Intent intent = new Intent();
						intent.setClass(getActivity(),
								CustomerMenuActivity.class);
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
