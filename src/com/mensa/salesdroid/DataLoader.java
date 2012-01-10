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
	public static final int dlCUSTOMERS = 1;

	private int[] dlData;
	private boolean ExtStorageAvailable = false;
	private boolean ExtStorageWriteable = false;
	private BaseDataListObj[] datalist;
	String state = Environment.getExternalStorageState();

	public DataLoader(int... dlData) {

		// if (Environment.MEDIA_MOUNTED.equals(state)) {
		// ExtStorageAvailable = ExtStorageWriteable = true;
		// } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		// ExtStorageAvailable = true;
		// ExtStorageWriteable = false;
		// } else {
		// ExtStorageAvailable = ExtStorageWriteable = false;
		// }
		datalist = new BaseDataListObj[dlData.length];
		this.dlData = dlData;
		for (int i = 0; i < dlData.length; i++) {
			try {
				FileInputStream jsonfile = new FileInputStream(new File(
						"/sdcard/" + MensaApplication.APP_DATAFOLDER + "/"
								+ MensaApplication.dataFILENAMES[i]));
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

				JSONObject jsonobj;
				switch (i) {
				case dlPRODUCTS: {
					try {
						jsonobj = new JSONObject(json.toString());
						JSONArray jsonproducts = jsonobj
								.getJSONArray("master_product");
						Product product;
						BaseDataListObj products = (BaseDataListObj) new Products();
						for (int j = 0; j < jsonproducts.length(); j++) {
							product = new Product(jsonproducts.getJSONObject(j)
									.getString("CONTRACT"), jsonproducts
									.getJSONObject(j).getString("DIV"),
									jsonproducts.getJSONObject(j).getString(
											"PART_NO"), jsonproducts
											.getJSONObject(j).getString(
													"DESCRIPTION"),
									jsonproducts.getJSONObject(j).getString(
											"LOCATION_NO"), jsonproducts
											.getJSONObject(j).getString(
													"LOT_BATCH_NO"),
									jsonproducts.getJSONObject(j).getLong(
											"QTY_ONHAND"), jsonproducts
											.getJSONObject(j).getLong(
													"QTY_RESERVED"));
							((Products) products).addProduct(product);
						}
						datalist[i] = products;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				case dlCUSTOMERS: {
					try {
						jsonobj = new JSONObject(json.toString());
						JSONArray jsoncustomers = jsonobj
								.getJSONArray("master_customer");
						Customer customer;
						BaseDataListObj customers = (BaseDataListObj) new Customers();
						for (int j = 0; j < jsoncustomers.length(); j++) {
							customer = new Customer(jsoncustomers
									.getJSONObject(j).getString("CABANG"), "",
									jsoncustomers.getJSONObject(j).getString(
											"CUSTOMER_CODE"), jsoncustomers
											.getJSONObject(j).getString(
													"CUSTOMER_NAME"), "chain",
									"group", "bill_name", "bill_address",
									"delivery_name", jsoncustomers
											.getJSONObject(j).getString(
													"ALAMAT_KIRIM"), "zipcode",
									"coordinate");
							((Customers) customers).addCustomer(customer);
						}
						datalist[i] = customers;
					} catch (JSONException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
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
