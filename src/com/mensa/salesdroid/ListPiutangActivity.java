package com.mensa.salesdroid;

import android.os.Bundle;
import android.support.v4.app.ActionBar;

public class ListPiutangActivity extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listpiutang);
		
		final ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbarbackground));
		ab.setTitle("List Piutang");
	}
}
