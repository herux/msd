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
	public static final int dlSALESMANS = 2;
	public static final int dlPRODUCTSFOCUS = 3;
	public static final int dlPIUTANG = 4;

	private int[] dlData;
	private boolean ExtStorageAvailable = false;
	private boolean ExtStorageWriteable = false;
	private BaseDataListObj[] datalist;
	String state = Environment.getExternalStorageState();

	public DataLoader(int... dlData) {

		datalist = new BaseDataListObj[dlData.length];
		this.dlData = dlData;
		for (int i = 0; i < dlData.length; i++) {
			try {
				FileInputStream jsonfile = new FileInputStream(new File(
						"/sdcard/" + MensaApplication.APP_DATAFOLDER + "/"
								+ MensaApplication.dataFILENAMES[dlData[i]]));
				InputStreamReader inputreader = new InputStreamReader(jsonfile);
				BufferedReader buffreader = new BufferedReader(inputreader);
				StringBuilder json = new StringBuilder();
				String line;
				try {
					while ((line = buffreader.readLine()) != null) {
						json.append(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				JSONObject jsonobj;
				switch (dlData[i]) {
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
													"QTY_RESERVED"),
									jsonproducts.getJSONObject(j).getDouble(
											"HNA"));
							((Products) products).addProduct(product);
						}
						datalist[i] = products;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case dlPRODUCTSFOCUS: {

					break;
				}
				case dlPIUTANG: {
					try {
						jsonobj = new JSONObject(json.toString());
						JSONArray jsonpiutangs = jsonobj
								.getJSONArray("piutang");
						Piutang piutang;
						BaseDataListObj piutangs = (BaseDataListObj) new Piutangs();
						for (int j = 0; j < jsonpiutangs.length(); j++) {
							piutang = new Piutang(jsonpiutangs.getJSONObject(j)
									.getString("SITE"), jsonpiutangs
									.getJSONObject(j).getString("DIVISI"),
									jsonpiutangs.getJSONObject(j).getString(
											"RAYON_SALES"), jsonpiutangs
											.getJSONObject(j).getString(
													"IDENTITY"), jsonpiutangs
											.getJSONObject(j).getString(
													"INVOICE_NO"), jsonpiutangs
											.getJSONObject(j).getString(
													"INVOICE_DATE"),
									jsonpiutangs.getJSONObject(j).getString(
											"DUE_DATE"), jsonpiutangs
											.getJSONObject(j).getDouble(
													"INVOICE_AMOUNT"),
									jsonpiutangs.getJSONObject(j).getDouble(
											"OPEN_AMOUNT"));
							((Piutangs) piutangs).AddPiutang(piutang);
						}
						datalist[i] = piutangs;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
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
									.getJSONObject(j).getString("CUSTOMER_ID"),
									jsoncustomers.getJSONObject(j).getString(
											"NAMA"), jsoncustomers
											.getJSONObject(j).getString(
													"CGROUP"), jsoncustomers
											.getJSONObject(j).getString(
													"CUSTOMER_CHAIN"),
									jsoncustomers.getJSONObject(j).getString(
											"NAMA_KIRIM"), jsoncustomers
											.getJSONObject(j).getString(
													"NAMA_TAGIHAN"),
									jsoncustomers.getJSONObject(j).getString(
											"ALAMAT_KIRIM"), jsoncustomers
											.getJSONObject(j).getString(
													"ALAMAT_TAGIHAN"),
									jsoncustomers.getJSONObject(j).getString(
											"KOORDINAT"));
							((Customers) customers).addCustomer(customer);
						}
						datalist[i] = customers;
					} catch (JSONException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
					break;
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
