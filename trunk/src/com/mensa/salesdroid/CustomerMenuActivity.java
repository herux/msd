/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 14.01.2012	
 */

package com.mensa.salesdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CustomerMenuActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customermenu);
		
		Button btninforeturn = (Button) findViewById(R.id.btn_inforeturn);
		btninforeturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CustomerMenuActivity.this, InfoReturActivity.class);
				startActivity(intent);
			}
		});
		
		Button btncaptureorder = (Button) findViewById(R.id.btn_capturorder);
		btncaptureorder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CustomerMenuActivity.this, ProductviewActivity.class);
				startActivity(intent);
			}
		});

	}

}
