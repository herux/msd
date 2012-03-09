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

import org.json.JSONException;
import org.json.JSONObject;

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

	public void setOnDataSyncListener(OnDataSyncListener listener) {
		onDataSyncListener = listener;
	}

	public interface OnDataSyncListener {
		public abstract void OnDataSync(String dataname, int count, int max);
	}
	
	private File[] getListFiles(){
		String datafolder = application.getDataFolder();
		File dir = new File(datafolder);
		File[] filelist = dir.listFiles();
		return filelist;
	}

	@Override
	public void execute() {
		http = new HttpClient();
		File root = Environment.getExternalStorageDirectory();
		String folder = MensaApplication.APP_DATAFOLDER + "/";
		File file = new File(root, folder);
		file.mkdirs();
		for (int i = 0; i < MensaApplication.paths.length; i++) {
			if (onDataSyncListener != null) {
				onDataSyncListener.OnDataSync(
						MensaApplication.dataFILENAMES[i], i, 0);
			}
			String request = MensaApplication.mbs_url
					+ MensaApplication.paths[i];
			if ((i == DataLoader.dlCUSTOMERS) || (i == DataLoader.dlPIUTANG)
					|| (i == DataLoader.dlPRODUCTS)||(i==DataLoader.dlPRODUCTSFOCUS)) {
				request = request + application.getSalesid();
			}
			
			File[] filelist = getListFiles();
			if ((i == DataLoader.dlPRODUCTS)||(i == DataLoader.dlSALESMANS)){
				// sebelum singkron products or salesmans, hapus dulu file2 lama
				String files;
				if (i == DataLoader.dlPRODUCTS){
					files = MensaApplication.PRODUCTSPAGEFILENAME;
				}else{
					files = MensaApplication.SALESMANSPAGEFILENAME;
				}
				Log.d("mensa", "filelist: "+filelist.length);
				for (int j = 0; j < filelist.length; j++) {
					if (filelist[j].getName().contains(files)){
						Log.d("mensa", "Deleting file "+filelist[j].getName());
						filelist[j].delete();
					}
				} 
				
				response = http.executeHttpPost(request+"&mode=1", "");
				try {
					JSONObject pageobj = new JSONObject(response);
					int page = pageobj.getInt("PAGE_COUNT");
					Log.d("mensa", "Download product base page: "+page);
					for (int j = 1; j < page; j++) {
						response = http.executeHttpPost(request+"&page="+Integer.toString(j), "");
						String filename = folder + files+Integer.toString(j)+".mbs";
						file = new File(root, filename);
						FileWriter filewriter;
						try {
							filewriter = new FileWriter(file);
							BufferedWriter out = new BufferedWriter(filewriter);
							out.write(response);
							out.close();
							Log.d("mensa", "Save to filename : "+filename);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					continue;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			Log.d("mensa", "request:" + request);
			response = http.executeHttpPost(request, "");
			Log.d("mensa", "response:" + response);
			try {
				if (response.equals("null") || response.equals("")) {
					Log.d("mensa", "Webservices no response");
					continue;
				}
				if (root.canWrite()) {
					file = new File(root, folder
							+ MensaApplication.dataFILENAMES[i]);
					FileWriter filewriter = new FileWriter(file);
					BufferedWriter out = new BufferedWriter(filewriter);
					out.write(response);
					out.close();
					Log.d("mensa", "savetofile:" + folder
							+ MensaApplication.dataFILENAMES[i]);
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
