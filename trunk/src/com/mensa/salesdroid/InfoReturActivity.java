/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 15.01.2012	
 */

package com.mensa.salesdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class InfoReturActivity extends BaseFragmentActivity {
	int CAMERA_PIC_REQUEST = 2; 
	
	Fragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inforeturlayout);
		
		FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fragment = fm.findFragmentByTag("f1");
        if (fragment == null) {
        	fragment = new MenuReturn();
            ft.add(fragment, "f1");
        }
        ft.show(fragment);
        ft.commit();      
		
		Button btncamera = (Button) findViewById(R.id.btnCamera);
		btncamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
			}
		});
		
		Button btnGallery = (Button) findViewById(R.id.btnGallery);
		btnGallery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				Intent galleryIntent = new Intent(android.provider.LiveFolders.ACTION_IMAGE_CAPTURE);
//	            startActivityForResult(galleryIntent, CAMERA_PIC_REQUEST);
			}
		});     
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	    if( requestCode == CAMERA_PIC_REQUEST)
	    {   
//	        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//	        ImageView image =(ImageView) findViewById(R.id.ivImageRetur);
//	        image.setImageBitmap(thumbnail);
	    }
	    else 
	    {
	        Toast.makeText(InfoReturActivity.this, "Picture not taken", Toast.LENGTH_LONG);
	    }
	}
	
	public static class MenuReturn extends Fragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            menu.add("New").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        	return super.onOptionsItemSelected(item);
        }
    }

}
