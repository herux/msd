package com.mensa.salesdroid;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity {
	private MensaApplication mensaapplication;
	
	public MensaApplication getMensaapplication() {
		return mensaapplication;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mensaapplication = (MensaApplication) getApplication();
	}

	protected void showTabsNav() {
		ActionBar ab = getSupportActionBar();
		if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
			ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		}
	}

	protected void showDropDownNav() {
		ActionBar ab = getSupportActionBar();
		if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_LIST) {
			ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		}
	}

}
