package com.mensa.salesdroid;

import android.os.Bundle;
import android.support.v4.app.ActionBar;

public class AddCustomerActivity extends BaseFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcustomerlayout);
		
		final ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setSubtitle("Adding New Customer");
		
	}

}
