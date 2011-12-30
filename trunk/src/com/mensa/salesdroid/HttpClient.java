/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 21.11.2011	
 */

package com.mensa.salesdroid;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Handler;

public class HttpClient {
	public static void isNetworkAvailable(final String AURL,
			final Handler handler, final int timeout) {
		new Thread() {
			private boolean responded = false;

			@Override
			public void run() {
				new Thread() {
					@Override
					public void run() {
						HttpGet requestForTest = new HttpGet(AURL);
						try {
							new DefaultHttpClient().execute(requestForTest);
							responded = true;
						} catch (Exception e) {
						}
					}

				}.start();

				try {
					int waited = 0;
					while (!responded && (waited < timeout)) {
						sleep(100);
						if (!responded) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
				} finally {
					if (!responded) {
						handler.sendEmptyMessage(0);
					} else {
						handler.sendEmptyMessage(1);
					}
				}
			}
		}.start();

	}

	public String executeHttpPost(String AURL, String AInput) {
		DefaultHttpClient http = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(AURL);
		String ret = "";
		try {
			StringEntity se = new StringEntity(AInput, HTTP.UTF_8);
			httppost.setEntity(se);

			HttpResponse httpresponse = http.execute(httppost);
			HttpEntity resEntity = httpresponse.getEntity();
			ret = EntityUtils.toString(resEntity);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}