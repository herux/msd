/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 27.12.2011	
 */

package com.mensa.salesdroid;

import java.io.File;

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

	@Override
	public void execute() {
		http = new HttpClient();
		File root = Environment.getExternalStorageDirectory();
		String folder = MensaApplication.APP_DATAFOLDER + "/";
		File file = new File(root, folder);
		file.mkdirs();
		for (int i = 0; i < MensaApplication.paths.length; i++) {
			if (onDataSyncListener != null) {
				onDataSyncListener.OnDataSync(MensaApplication.FULLSYNC[i], i,
						0);
			}
			String request = MensaApplication.mbs_url
					+ MensaApplication.paths[i];
			if ((i == DataLoader.dlCUSTOMERS) || (i == DataLoader.dlPIUTANG)
					|| (i == DataLoader.dlPRODUCTS)
					|| (i == DataLoader.dlPRODUCTSFOCUS)
					|| (i == DataLoader.dlPRODUCTSPROMO)) {
				request = request + application.getSalesid();
			}

			File[] filelist = MensaApplication.getListFiles();
			if ((i == DataLoader.dlPRODUCTS) || (i == DataLoader.dlSALESMANS)) {
				// sebelum singkron products or salesmans, hapus dulu file2 lama
				String files;
				if (i == DataLoader.dlPRODUCTS) {
					files = MensaApplication.PRODUCTSPAGEFILENAME;
				} else {
					files = MensaApplication.SALESMANSPAGEFILENAME;
				}
				Log.d("mensa", "filelist: " + filelist.length);
				for (int j = 0; j < filelist.length; j++) {
					if (filelist[j].getName().contains(files)) {
						Log.d("mensa", "Deleting file " + filelist[j].getName());
						filelist[j].delete();
					}
				}
				
				response = http.executeHttpPost(request + "&mode=1", "");
				try {
					JSONObject pageobj = new JSONObject(response);
					int page = pageobj.getInt("PAGE_COUNT");
					Log.d("mensa", "Download product base page: " + page);
					for (int j = 1; j < page; j++) {
						response = http.executeHttpPost(request + "&page="
								+ Integer.toString(j), "");
						String filename = folder + files + Integer.toString(j)
								+ ".mbs";
						file = new File(root, filename);
						MensaApplication.SaveStringToFile(file, response);
						Log.d("mensa", "Save to filename : " + filename);

					}
					continue;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(i == DataLoader.dlSALESORDER){
				Log.d("mensa", "SalesOrder Sync here ..");
				continue;
			}

			Log.d("mensa", "request:" + request);
			response = http.executeHttpPost(request, "");
			Log.d("mensa", "response:" + response);
			if (response.equals("null") || response.equals("")) {
				Log.d("mensa", "Webservices no response");
				continue;
			}
			if (root.canWrite()) {
				file = new File(root, folder + MensaApplication.FULLSYNC[i]);
				MensaApplication.SaveStringToFile(file, response);
				Log.d("mensa", "savetofile:" + folder
						+ MensaApplication.FULLSYNC[i]);
			}

		}
	}

	public String getResponse() {
		return response;
	}
}
