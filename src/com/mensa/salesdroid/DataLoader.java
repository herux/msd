/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;

public class DataLoader {
	public static final int dlPRODUCTS = 0;
	
	static final String PRODUCTSFILENAME = "products.mbs";
	static final String SALESMANFILENAME = "salesmans.mbs";
	
	static String[] dataFILENAMES = {PRODUCTSFILENAME, SALESMANFILENAME};
	
	static final String APP_DATAFOLDER = "mensadata";
	
	private int[] dlData;
	private boolean ExtStorageAvailable = false;
	private boolean ExtStorageWriteable = false;
	private BaseDataListObj[] datalist;
	String state = Environment.getExternalStorageState();
	
	public DataLoader(int... dlData) {
		
//		if (Environment.MEDIA_MOUNTED.equals(state)) {
//		    ExtStorageAvailable = ExtStorageWriteable = true;
//		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
//		    ExtStorageAvailable = true;
//		    ExtStorageWriteable = false;
//		} else {
//		    ExtStorageAvailable = ExtStorageWriteable = false;
//		}
		datalist = new BaseDataListObj[dlData.length];
		this.dlData = dlData;
		for (int i = 0; i < dlData.length; i++) {
			try {
				FileInputStream jsonfile  = new FileInputStream(new File("/sdcard/"+APP_DATAFOLDER+"/"+dataFILENAMES[i]));
				InputStreamReader inputreader = new InputStreamReader(jsonfile);
				BufferedReader buffreader = new BufferedReader(inputreader);
				StringBuilder json = new StringBuilder();
				String line;
				try {
					while ((line = buffreader.readLine()) != null) {
						json.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (i==dlPRODUCTS){
					JSONObject jsonobj;
					try {
						jsonobj = new JSONObject(json.toString());
						JSONArray jsonproducts = jsonobj.getJSONArray("Products");
						Product product;
						BaseDataListObj products = (BaseDataListObj) new Products();
						for (int j = 0; j < jsonproducts.length(); j++) {
							product = new Product(jsonproducts.getJSONObject(j).getString("branch"), 
												  jsonproducts.getJSONObject(j).getString("division"), 
												  jsonproducts.getJSONObject(j).getString("part_no"), 
												  jsonproducts.getJSONObject(j).getString("part_name"), 
												  jsonproducts.getJSONObject(j).getLong("qty_availabe"));
							((Products)products).addProduct(product);
						}
						datalist[i] = products; 
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	public BaseDataListObj[] getDatalist() {
		return datalist;
	}

	public int[] getDlData() {
		return dlData;
	}
	
	public boolean isExtStorageAvailable() {
		return ExtStorageAvailable;
	}
	
	public boolean isExtStorageWriteable() {
		return ExtStorageWriteable;
	}

	
}
