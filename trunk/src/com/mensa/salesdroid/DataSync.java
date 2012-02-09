/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 27.12.2011	
 */

package com.mensa.salesdroid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

public class DataSync extends BaseThread {
	HttpClient http;
	private String response = "";
	OnDataSyncListener onDataSyncListener;
	
	MensaApplication application;

	public DataSync(Handler h, MensaApplication app) {
		super(h);
		application = app;
	}
	
	public void setOnDataSyncListener(OnDataSyncListener listener){
		onDataSyncListener = listener;
	}
	
	public interface OnDataSyncListener{
		public abstract void OnDataSync(String dataname, int count, int max);
	} 

	@Override
	public void execute() {
		http = new HttpClient();
		File root = Environment.getExternalStorageDirectory();
		String folder = MensaApplication.APP_DATAFOLDER+"/";
		File file = new File(root, folder);
		file.mkdirs();
		for (int i = 0; i < MensaApplication.paths.length; i++) {
			if (onDataSyncListener!=null){
				onDataSyncListener.OnDataSync(MensaApplication.dataFILENAMES[i], i, 0);
			}
			String request = MensaApplication.mbs_url + MensaApplication.paths[i];
			if ((i==DataLoader.dlCUSTOMERS)||(i==DataLoader.dlPIUTANG)){
				request = request+application.getSalesid();
			}
			Log.d("mensa", "request:"+request);
			response = http.executeHttpPost(request, "");
			Log.d("mensa", "response:"+response);
			try {
				if (root.canWrite()) {
					file = new File(root, folder+MensaApplication.dataFILENAMES[i]);
					FileWriter filewriter = new FileWriter(file);
					BufferedWriter out = new BufferedWriter(filewriter);
					out.write(response);
					out.close();
					Log.d("mensa", "savetofile:"+folder+MensaApplication.dataFILENAMES[i]);
				}
			} catch (IOException e) {
				Log.e("mensa", "Could not write file" + e.getMessage());
			}

		}
	}

	public String getResponse() {
		return response;
	}
}
