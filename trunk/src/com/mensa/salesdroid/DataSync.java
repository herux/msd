/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 27.12.2011	
 */

package com.mensa.salesdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

public class DataSync extends BaseThread {
	HttpClient http;
	private String response = "";
	OnDataSyncListener onDataSyncListener;
	static final int FULLSYNC = 0;
	static final int FASTSYNC = 1;
	static final int PENDSYNC = 2;
	private int syncMethod;

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
		switch (getSyncMethod()) {
		case FULLSYNC: {
			for (int i = 0; i < MensaApplication.fullsync_paths.length; i++) {
				String request = MensaApplication.mbs_url
						+ MensaApplication.fullsync_paths[i];
				if ((i == DataLoader.dlCUSTOMERS)
						|| (i == DataLoader.dlPIUTANG)
						|| (i == DataLoader.dlPRODUCTS)
						|| (i == DataLoader.dlPRODUCTSFOCUS)
						|| (i == DataLoader.dlPRODUCTSPROMO)) {
					request = request + application.getSalesid();
				}

				File[] filelist = MensaApplication.getListFiles();
				if ((i == DataLoader.dlPRODUCTS)
						|| (i == DataLoader.dlSALESMANS)) {
					// sebelum singkron products or salesmans, hapus dulu file2
					// lama
					String files;
					if (i == DataLoader.dlPRODUCTS) {
						files = MensaApplication.PRODUCTSPAGEFILENAME;
					} else {
						files = MensaApplication.SALESMANSPAGEFILENAME;
					}
					Log.d("mensa", "filelist: " + filelist.length);
					for (int j = 0; j < filelist.length; j++) {
						if (filelist[j].getName().contains(files)) {
							if (onDataSyncListener != null) {
								onDataSyncListener.OnDataSync(MensaApplication.FULLSYNC[i],
										j, filelist.length);
							}
							Log.d("mensa",
									"Deleting file " + filelist[j].getName());
							filelist[j].delete();
						}
					}

					response = http.executeHttpPost(request + "&mode=1", "");
					try {
						JSONObject pageobj = new JSONObject(response);
						int page = pageobj.getInt("PAGE_COUNT");
						Log.d("mensa", "Download product base page: " + page);
						for (int j = 1; j < page; j++) {
							if (onDataSyncListener != null) {
								onDataSyncListener.OnDataSync(MensaApplication.FULLSYNC[i],
										j, page);
							}
							response = http.executeHttpPost(request + "&page="
									+ Integer.toString(j), "");
							String filename = folder + files
									+ Integer.toString(j) + ".mbs";
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

				if (i == DataLoader.dlSALESORDER) {
					Log.d("mensa", "SalesOrder Sync here ..");
					continue;
				}
				
				if (onDataSyncListener != null) {
					onDataSyncListener.OnDataSync(MensaApplication.FULLSYNC[i],
							i, MensaApplication.fullsync_paths.length);
				}

				Log.d("mensa", "request:" + request);
				response = http.executeHttpPost(request, "");
				Log.d("mensa", "response:" + response);
//				if (response.equals("null") || response.equals("")) {
//					Log.d("mensa", "Webservices no response");
//					continue;
//				}
				if (root.canWrite()) {
					file = new File(root, folder + MensaApplication.FULLSYNC[i]);
					MensaApplication.SaveStringToFile(file, response);
					Log.d("mensa", "savetofile:" + folder
							+ MensaApplication.FULLSYNC[i]);
				}
			}
			break;
		}
		case FASTSYNC: {
			for (int i = 0; i < MensaApplication.fastsync_paths.length; i++) {
				if (onDataSyncListener != null) {
					onDataSyncListener.OnDataSync(MensaApplication.FASTSYNC[i], i, MensaApplication.fastsync_paths.length);
				}
				String request = MensaApplication.mbs_url
						+ MensaApplication.fastsync_paths[i];
				
				request = request + application.getSalesid();
				Log.d("mensa", "request:" + request);
				response = http.executeHttpPost(request, "");
				Log.d("mensa", "response:" + response);
				if (response.equals("null") || response.equals("")) {
					Log.d("mensa", "Webservices no response");
					continue;
				}
				if (root.canWrite()) {
					file = new File(root, folder + MensaApplication.FASTSYNC[i]);
					MensaApplication.SaveStringToFile(file, response);
					Log.d("mensa", "savetofile:" + folder
							+ MensaApplication.FASTSYNC[i]);
				}
			}
			break;
		}
		case PENDSYNC: {
			for (int i = 0; i < MensaApplication.PENDSYNC.length; i++) {
				String datafolder = root + "/" + folder;
				File dir = new File(datafolder);
				File[] filelist = dir.listFiles();
				// urutan sync adalah : order, return, new cust
				// jika i = 0, cari file berawalan nama order,
				// semua file yang ketemu di post sesuai path i,
				// dan seterusnya 
				switch (i) {
				case 0:{
					// order pending
					for (int j = 0; j < filelist.length; j++) {
						if (filelist[j].getName().contains(
								MensaApplication.SALESORDERFILENAME)) {
							// ambil content file
							Log.d("mensa", "orderfilename="+filelist[j].getName());
							FileInputStream orderfile = null;
							try {
								orderfile = new FileInputStream(filelist[j]);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String ordercontents = MensaApplication.getFileContent(orderfile);
							ordercontents = Compression.encodebase64(ordercontents);
							String url = "";
							HttpClient httpc = new HttpClient();
							try {
								url = MensaApplication.mbs_url + MensaApplication.PENDSYNC[i];
								ordercontents = URLEncoder.encode(ordercontents, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							Log.d("mensa", "send order url="+url+" content="+ordercontents);
							String response = httpc.executeHttpPost(url, ordercontents);
							Log.d("mensa", "response order = "+response);
							String status = null;
							try {
								JSONObject statusObj = new JSONObject(response);
								status = statusObj.getString("status");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (status.equals("SUCCESS")) {
								Log.d("mensa", "delete file order");
								filelist[j].delete();
							}
						}
					}
					break;
				}
				case 1:{
					// return pending
					for (int j = 0; j < filelist.length; j++) {
						if (filelist[j].getName().contains(
								MensaApplication.RETURNORDERFILENAME)) {
							Log.d("mensa", "return filename="+filelist[j].getName());
							// ambil content file 
							FileInputStream orderfile = null;
							try {
								orderfile = new FileInputStream(filelist[j]);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String returncontents = MensaApplication.getFileContent(orderfile);
							returncontents = Compression.encodebase64(returncontents);
							String url = "";
							HttpClient httpc = new HttpClient();
							try {
								url = MensaApplication.mbs_url + MensaApplication.PENDSYNC[i];
								returncontents = URLEncoder.encode(returncontents, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							String response = httpc.executeHttpPost(url, returncontents);
							Log.d("mensa", "response return = "+response);
							String status = null;
							try {
								JSONObject statusObj = new JSONObject(response);
								status = statusObj.getString("status");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (status.equals("SUCCESS")) {
								Log.d("mensa", "delete file return");
								filelist[j].delete();
							}
						}
					}
					break;
				}
				case 2:{
					// new customers pending
					for (int j = 0; j < filelist.length; j++) {
						if (filelist[j].getName().contains(
								MensaApplication.NEWCUSTFILENAME)) {
							Log.d("mensa", "new cust filename="+filelist[j].getName());
							// ambil content file 
							FileInputStream orderfile = null;
							try {
								orderfile = new FileInputStream(filelist[j]);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String newcustcontents = MensaApplication.getFileContent(orderfile);
							newcustcontents = Compression.encodebase64(newcustcontents);
							String url = "";
							HttpClient httpc = new HttpClient();
							try {
								url = MensaApplication.mbs_url + MensaApplication.PENDSYNC[i];
								newcustcontents = URLEncoder.encode(newcustcontents, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							String response = httpc.executeHttpPost(url, newcustcontents);
							Log.d("mensa", "response new cust = "+response);
							String status = null;
							try {
								JSONObject statusObj = new JSONObject(response);
								status = statusObj.getString("status");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (status.equals("SUCCESS")) {
								Log.d("mensa", "delete file new customers");
								filelist[j].delete();
							}
						}
					}
					break;
				}
				default:
					break;
				}
				
				if (onDataSyncListener != null) {
					onDataSyncListener.OnDataSync(MensaApplication.FULLSYNC[i], i, 0);
				}
			}
			break;
		}
		}
	}

	public String getResponse() {
		return response;
	}

	public int getSyncMethod() {
		return syncMethod;
	}

	public void setSyncMethod(int syncMethod) {
		this.syncMethod = syncMethod;
	}
}
