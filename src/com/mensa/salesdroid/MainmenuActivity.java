/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainmenuActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        
        Button btnProductview = (Button)findViewById(R.id.btn_productlist);
        btnProductview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this, ProductviewActivity.class);
				startActivity(intent);
			}
		});
        
        Button btnListCallP = (Button)findViewById(R.id.btn_salescallplan);
        btnListCallP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainmenuActivity.this, ListcallplanActivity.class);
				startActivity(intent);
			}
		});
	}

}
