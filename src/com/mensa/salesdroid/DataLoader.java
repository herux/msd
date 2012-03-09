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

import android.app.Application;
import android.os.Environment;
import android.util.Log;

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

	private String getFileContent(FileInputStream filename) {
		InputStreamReader inputreader = new InputStreamReader(filename);
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
		return json.toString();
	}

	public DataLoader(int... dlData) {

		datalist = new BaseDataListObj[dlData.length];
		this.dlData = dlData;
		for (int i = 0; i < dlData.length; i++) {
			try {
				FileInputStream jsonfile = new FileInputStream(new File(
						"/sdcard/" + MensaApplication.APP_DATAFOLDER + "/"
								+ MensaApplication.dataFILENAMES[dlData[i]]));
				String json = getFileContent(jsonfile);
				JSONObject jsonobj;
				switch (dlData[i]) {
				case dlPRODUCTS: {
					try {
						File root = Environment.getExternalStorageDirectory();
						String folder = MensaApplication.APP_DATAFOLDER + "/";
						String datafolder = root + "/" + folder;
						File dir = new File(datafolder);
						File[] filelist = dir.listFiles();
						for (int k = 0; k < filelist.length; k++) {
							if (filelist[k].getName().contains(
									MensaApplication.PRODUCTSPAGEFILENAME)) {
								FileInputStream productsfile = new FileInputStream(
										filelist[k]);
								String productSTR = getFileContent(productsfile);
								jsonobj = new JSONObject(productSTR);
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
															"DESCRIPTION"),"",//jsonproducts.getJSONObject(j).getString("LOCATION_NO"),
														"", //jsonproducts.getJSONObject(j).getString("LOT_BATCH_NO")
														0, //jsonproducts.getJSONObject(j).getLong("QTY_ONHAND"), 
														0,//jsonproducts.getJSONObject(j).getLong("QTY_RESERVED"),
											jsonproducts.getJSONObject(j).optDouble(
													"HNA", 0));
									((Products) products).addProduct(product);
								}
								datalist[i] = products;

							}
						}

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
						jsonobj = new JSONObject(json);
						JSONArray jsonpiutangs = jsonobj
								.getJSONArray("piutang_callplan");
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
						jsonobj = new JSONObject(json);
						JSONArray jsoncustomers = jsonobj
								.getJSONArray("call_plan_daily");
						Customer customer;
						BaseDataListObj customers = (BaseDataListObj) new Customers();
						for (int j = 0; j < jsoncustomers.length(); j++) {
							JSONObject custproperty = jsoncustomers
									.getJSONObject(j);
							customer = new Customer(
									custproperty.getString("CB_NOMOR"),
									custproperty.getString("CABANG"),
									custproperty.getString("PERSON_ID"),
									custproperty.getString("RAYON"),
									custproperty.getString("PERIODE"),
									custproperty.getString("DIVISI"),
									custproperty.getString("CUSTOMER_CODE"),
									custproperty.getString("NAMA"),
									custproperty.getString("KLAS"),
									Double.valueOf(custproperty
											.getString("VALUE_GUIDE")),
									custproperty.getString("ALAMAT_KIRIM"),
									custproperty.getString("KOTA"),
									Double.valueOf(custproperty
											.getString("CREDIT_LIMIT")),
									Double.valueOf(custproperty
											.getString("PIUTANG")), "");
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
