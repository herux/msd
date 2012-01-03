/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.mensa.salesdroid.ProductviewActivity.ProductDetailsFragment;

public class ProductviewDetailsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}
		if (savedInstanceState == null) {
			ProductDetailsFragment details = new ProductDetailsFragment();
			details.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction()
					.add(android.R.id.content, details).commit();  
		}
	}

}
