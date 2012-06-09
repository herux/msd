/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.loginlayout);
		
		RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.rl1);
		rl1.getBackground().setAlpha(170);
		RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.rl2);
		rl2.getBackground().setAlpha(170);
		
		final EditText etUsername = (EditText) findViewById(R.id.etUsername);
		etUsername.setText("YGY1-CP-SRN");
		final EditText etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword.setText("Z");

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				HttpClient http = new HttpClient();
				String request = "http://simfoni.mbs.co.id/services.php?key=czRMZTU0dVRvTWF0MTBu&tab=bG9naW4=&uid="
						+ etUsername.getText().toString()
						+ "&pwd="
						+ etPassword.getText().toString(); 
				String response = http.executeHttpPost(request, "");
				if ((response.equals("null"))||(response.equals(""))){
					Toast toast = Toast.makeText(LoginActivity.this,
							"Can't connect to webservices, check your internet connection..",
							Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				
				if (response.equals("-1")) {
					Toast toast = Toast.makeText(LoginActivity.this,
							"Wrong username and/or password",
							Toast.LENGTH_SHORT);
					toast.show();
				} else {
					MensaApplication app = (MensaApplication) getApplication();
					app.setSalesid(response);
					
					File fileuserlog = new File("/sdcard/" + MensaApplication.APP_DATAFOLDER + "/"
							+ MensaApplication.USERLOG_HISTORY);
					if (fileuserlog.exists()){
						FileInputStream filestream;
						try {
							filestream = new FileInputStream(fileuserlog);
							String logContent = MensaApplication.getFileContent(filestream);
							if (logContent.equals(response)){
								app.setNeedSync(false);
							}else{
								app.setNeedSync(true);
								MensaApplication.SaveStringToFile(fileuserlog, response);
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else{
						app.setNeedSync(true);
						MensaApplication.SaveStringToFile(fileuserlog, response);
					}
					
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainmenuActivity.class);
					startActivity(intent);
				}
			}
		});
	}
}
