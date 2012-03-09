package com.mensa.salesdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.MenuItem.OnMenuItemClickListener;
import android.view.MenuInflater;
import android.widget.TextView;

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
		
		final ActionBar ab = getSupportActionBar();
		ab.setSubtitle("Returns List "+application.getCurrentCustomer().getNAMA());
		
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
					Intent newret = new Intent(getActivity(), InfoReturActivity.class);
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
