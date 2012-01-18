/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 15.01.2012	
 */

package com.mensa.salesdroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class InfoReturActivity extends BaseFragmentActivity {
	int CAMERA_PIC_REQUEST = 1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inforeturlayout);
		
		final ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbarbackground));
		ab.setTitle("Info Return");
		
		Button btncamera = (Button) findViewById(R.id.btnCamera);
		btncamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	    if( requestCode == CAMERA_PIC_REQUEST)
	    {   
	        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	        ImageView image =(ImageView) findViewById(R.id.ivImageRetur);
	        image.setImageBitmap(thumbnail);
	    }
	    else 
	    {
	        Toast.makeText(InfoReturActivity.this, "Picture NOt taken", Toast.LENGTH_LONG);
	    }
	}

}
