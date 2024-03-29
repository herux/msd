package com.mensa.salesdroid;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.MenuItem.OnMenuItemClickListener;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoReturListActivity extends BaseFragmentActivity {
	static ReturnItemAdapter adapter;
	static MensaApplication application;
	Fragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = getMensaapplication();
		setContentView(R.layout.returnslistlayout);

		TextView lblreturnnum = (TextView) findViewById(R.id.tvreturnsnum_value);
		lblreturnnum.setText(application.getReturns().getReturnNo());
		TextView lblreturndate = (TextView) findViewById(R.id.tvreturnsdate_value);
		lblreturndate.setText(application.getReturns().getDatetimecheckin());
		TextView lblsales = (TextView) findViewById(R.id.tvsalesid_value);
		lblsales.setText(application.getReturns().getSalesid());
		Button btnsubmit = (Button) findViewById(R.id.btnsubmit);
		btnsubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				application.getReturns().setReturnitems(application.getReturnitems());
				Intent intent = new Intent();
				intent.setClass(InfoReturListActivity.this,
						CustomerMenuActivity.class);
				startActivity(intent);
				finish();
			}
		});

		final ActionBar ab = getSupportActionBar();
		ab.setSubtitle("Returns List "
				+ application.getCurrentCustomer().getNAMA());

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		fragment = fm.findFragmentByTag("f1");
		if (fragment == null) {
			fragment = new MenuReturnsList();
			ft.add(fragment, "f1");
		}
		ft.show(fragment);
		ft.commit();
	}

	public static class MenuReturnsList extends Fragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			MenuItem newreturn = menu.add("New Returns");
			newreturn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			newreturn.setOnMenuItemClickListener(new OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					Intent newret = new Intent(getActivity(),
							InfoReturActivity.class);
					startActivity(newret);
					getActivity().finish();
					return false;
				}
			});

		}

	}

	public static class ReturnItemsFragment extends ListFragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			adapter = new ReturnItemAdapter(getActivity(),
					R.layout.returnslist, application.getReturnitems());
			setListAdapter(adapter);
		}

	}

}
