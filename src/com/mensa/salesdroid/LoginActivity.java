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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginlayout);
		EditText etUsername = (EditText) findViewById(R.id.etUsername);
		EditText etPassword = (EditText) findViewById(R.id.etPassword);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
//				Toast toast = Toast.makeText(LoginActivity.this, "Login button clicked", Toast.LENGTH_SHORT);
//				toast.show();
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainmenuActivity.class);
				startActivity(intent);
			}
		});
	}

}
