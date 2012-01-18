/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 15.01.2012	
 */

package com.mensa.salesdroid;

import android.os.Bundle;
import android.support.v4.app.ActionBar;

public class InfoReturActivity extends BaseFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inforeturlayout);
		
		final ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbarbackground));
		ab.setTitle("Info Return");
	}

}
