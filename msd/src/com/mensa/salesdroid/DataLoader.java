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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class DataLoader {
	public static final int dlPRODUCTS = 0;
	public static final int dlCUSTOMERS = 1;
	public static final int dlSALESMANS = 2;
	public static final int dlPRODUCTSFOCUS = 3;
	public static final int dlPRODUCTSSEARCH = 5;
	public static final int dlPRODUCTSPROMO = 8;
	public static final int dlPRODUCTSNEW = 9;
	public static final int dlSALESORDER = 7;
	public static final int dlPIUTANG = 4;

	private int[] dlData;
	private boolean ExtStorageAvailable = false;
	private boolean ExtStorageWriteable = false;
	private BaseDataListObj[] datalist;
	private int page;
	public static String textToSearch = "";
	String state = Environment.getExternalStorageState();

	public DataLoader(int... dlData) {
		datalist = new BaseDataListObj[dlData.length];
		this.dlData = dlData;
		for (int i = 0; i < dlData.length; i++) {
			try {
				JSONObject jsonobj;
				switch (dlData[i]) {
				case dlPRODUCTS: {
					try {
						File root = Environment.getExternalStorageDirectory();
						String folder = MensaApplication.APP_DATAFOLDER + "/";
						String datafolder = root + "/" + folder;

						File dir = new File(datafolder);
						File[] filelist = dir.listFiles();
						int p = 0;
						for (int k = filelist.length - 1; k > 0; k--) {
							if (filelist[k].getName().contains(
									MensaApplication.PRODUCTSPAGEFILENAME)) {
								if (p == page) {
									FileInputStream productsfile = new FileInputStream(
											filelist[k]);
									Log.d("mensa",
											"file to read(i="
													+ Integer.toString(i)
													+ "): "
													+ filelist[k].getName());
									String productSTR = MensaApplication
											.getFileContent(productsfile);
									jsonobj = new JSONObject(productSTR);
									JSONArray jsonproducts = jsonobj
											.getJSONArray("master_product");

									Product product;
									BaseDataListObj products = (BaseDataListObj) new Products();
									for (int j = 0; j < jsonproducts.length(); j++) {
										product = new Product(jsonproducts
												.getJSONObject(j).getString(
														"CONTRACT"),
												jsonproducts.getJSONObject(j)
														.getString("DIV"),
												jsonproducts.getJSONObject(j)
														.getString("PART_NO"),
												jsonproducts.getJSONObject(j)
														.getString(
																"DESCRIPTION"),
												"",// jsonproducts.getJSONObject(j).getString("LOCATION_NO"),
												"", // jsonproducts.getJSONObject(j).getString("LOT_BATCH_NO")
												jsonproducts.getJSONObject(j).optLong("QTY"),
														0,// jsonproducts.getJSONObject(j).getLong("QTY_RESERVED"),
												jsonproducts.getJSONObject(j)
														.optDouble("HNA", 0));
										product.setFileSource(filelist[k]
												.getAbsolutePath());
										((Products) products).addProduct(product);
									}
									datalist[i] = products;
								}
								p = p + 1;
							}
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;
				}
				case dlPRODUCTSSEARCH: {
					Log.d("mensa", "Search product");
					BaseDataListObj products = (BaseDataListObj) new Products();
					try {
						File root = Environment.getExternalStorageDirectory();
						String folder = MensaApplication.APP_DATAFOLDER + "/";
						String datafolder = root + "/" + folder;

						File dir = new File(datafolder);
						File[] filelist = dir.listFiles();
						for (int k = filelist.length - 1; k > 0; k--) {

							if (filelist[k].getName().contains(
									MensaApplication.PRODUCTSPAGEFILENAME)) {
								FileInputStream productsfile = new FileInputStream(
										filelist[k]);
								String productSTR = MensaApplication
										.getFileContent(productsfile);
								if (!(productSTR.toUpperCase()).contains(textToSearch.toUpperCase())) {
									// Log.d("mensa", "continue");
									continue;
								}
								jsonobj = new JSONObject(productSTR);
								JSONArray jsonproducts = jsonobj
										.getJSONArray("master_product");

								for (int j = 0; j < jsonproducts.length(); j++) {
									String prodDesc = jsonproducts
											.getJSONObject(j).getString(
													"DESCRIPTION");
									// Log.d("mensa", "productDesc: "+
									// prodDesc);

									if ((prodDesc.toUpperCase()).contains(textToSearch.toUpperCase())) {
										Log.d("mensa", "found " + prodDesc
												+ " contains=" + textToSearch);
										Product product = new Product(
												jsonproducts.getJSONObject(j)
														.getString("CONTRACT"),
												jsonproducts.getJSONObject(j)
														.getString("DIV"),
												jsonproducts.getJSONObject(j)
														.getString("PART_NO"),
												jsonproducts.getJSONObject(j)
														.getString(
																"DESCRIPTION"),
												"",// jsonproducts.getJSONObject(j).getString("LOCATION_NO"),
												"", // jsonproducts.getJSONObject(j).getString("LOT_BATCH_NO")
												jsonproducts.getJSONObject(j).optLong("QTY"),
												0,// jsonproducts.getJSONObject(j).getLong("QTY_RESERVED"),
												jsonproducts.getJSONObject(j)
														.optDouble("HNA", 0));
										product.setFileSource(filelist[k]
												.getAbsolutePath());
										if (!((Products) products).getProducts().contains(product)){
											((Products) products).addProduct(product);
										}
									}
								}
							}
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
					datalist[i] = products;
					break;
				}
				case dlPRODUCTSFOCUS: {
					File filefocus = new File("/sdcard/"
							+ MensaApplication.APP_DATAFOLDER + "/"
							+ MensaApplication.FULLSYNC[3]);
					BaseDataListObj productsfocus = (BaseDataListObj) new Products();
					if (filefocus.exists()) {
						FileInputStream jsonfile = new FileInputStream(
								filefocus);
						String focus = MensaApplication
								.getFileContent(jsonfile);
						Log.d("mensa", "focus=" + focus);
						if (!focus.equals("null")) {
							JSONObject focusObj = null;
							try {
								focusObj = new JSONObject(focus);
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							JSONArray jsonproductsfocus = null;
							try {
								jsonproductsfocus = focusObj
										.getJSONArray("product_focus");
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							for (int h = 0; h < jsonproductsfocus.length(); h++) {
								Calendar c = Calendar.getInstance();
								Date d = c.getTime();
								SimpleDateFormat formatter = new SimpleDateFormat(
										"yyyy-MM-dd");
								Date min = null;
								Date max = null;
								try {
									try {
										min = formatter.parse(jsonproductsfocus
												.getJSONObject(h).getString(
														"START_DATE"));
										max = formatter.parse(jsonproductsfocus
												.getJSONObject(h).getString(
														"END_DATE"));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
								Product product = null;
								if (d.compareTo(min) >= 0
										&& d.compareTo(max) <= 0) {
									try {
										product = new Product(
												jsonproductsfocus
														.getJSONObject(h)
														.optString("CONTRACT",
																""),
												jsonproductsfocus
														.getJSONObject(h)
														.getString("DIVISI"),
												jsonproductsfocus
														.getJSONObject(h)
														.getString("PART_NO"),
												jsonproductsfocus
														.getJSONObject(h)
														.getString("KETERANGAN"),
												"",// jsonproducts.getJSONObject(h).getString("LOCATION_NO"),
												"", // jsonproducts.getJSONObject(h).getString("LOT_BATCH_NO")
												jsonproductsfocus
														.getJSONObject(h)
														.optLong("QTY", 0), 0,// jsonproducts.getJSONObject(h).getLong("QTY_RESERVED"),
												jsonproductsfocus
														.getJSONObject(h)
														.optDouble("HNA", 0));
										product.setDESCRIPTION2(jsonproductsfocus
												.getJSONObject(h).getString(
														"KETERANGAN2"));
										product.setCABANG(jsonproductsfocus
												.getJSONObject(h).getString(
														"CABANG"));
										Bitmap bmp = null;
										String base64image = jsonproductsfocus
												.getJSONObject(h).getString(
														"GAMBAR");
										if (base64image != null) {
											bmp = Compression
													.StringToBitmap(base64image);
										}
										product.setImage(bmp);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									product.setFileSource("/sdcard/"
											+ MensaApplication.APP_DATAFOLDER
											+ "/"
											+ MensaApplication.FULLSYNC[3]);
									((Products) productsfocus)
											.addProduct(product);
								}
							}
						}
					}
					datalist[i] = productsfocus;
					break;
				}
				case dlPRODUCTSPROMO: {
					File filepromo = new File("/sdcard/"
							+ MensaApplication.APP_DATAFOLDER + "/"
							+ MensaApplication.FULLSYNC[8]);
					BaseDataListObj productspromo = (BaseDataListObj) new Products();
					if (filepromo.exists()) {
						FileInputStream jsonfile = new FileInputStream(
								filepromo);
						String promo = MensaApplication
								.getFileContent(jsonfile);
						Log.d("mensa", "promo=" + promo);
						if (!promo.equals("null")) {
							JSONObject promoObj = null;
							try {
								promoObj = new JSONObject(promo);
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							JSONArray jsonproductspromo = null;
							try {
								jsonproductspromo = promoObj
										.getJSONArray("product_promo_stock");
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							for (int h = 0; h < jsonproductspromo.length(); h++) {
								Calendar c = Calendar.getInstance();
								Date d = c.getTime();
								SimpleDateFormat formatter = new SimpleDateFormat(
										"yyyy-MM-dd");
								Date min = null;
								Date max = null;
								try {
									try {
										min = formatter.parse(jsonproductspromo
												.getJSONObject(h).getString(
														"START_DATE"));
										max = formatter.parse(jsonproductspromo
												.getJSONObject(h).getString(
														"END_DATE"));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
								Product product = null;
								if (d.compareTo(min) >= 0
										&& d.compareTo(max) <= 0) {
									try {
										product = new Product(jsonproductspromo
												.getJSONObject(h).getString(
														"CONTRACT"),
												jsonproductspromo
														.getJSONObject(h)
														.getString("DIV"),
												jsonproductspromo
														.getJSONObject(h)
														.getString("PART_NO"),
												jsonproductspromo
														.getJSONObject(h)
														.getString(
																"DESCRIPTION"),
												"",// jsonproducts.getJSONObject(h).getString("LOCATION_NO"),
												"", // jsonproducts.getJSONObject(h).getString("LOT_BATCH_NO")
												jsonproductspromo
														.getJSONObject(h)
														.getLong("QTY"), 0,// jsonproducts.getJSONObject(h).getLong("QTY_RESERVED"),
												jsonproductspromo
														.getJSONObject(h)
														.optDouble("HNA", 0));
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									product.setFileSource("/sdcard/"
											+ MensaApplication.APP_DATAFOLDER
											+ "/"
											+ MensaApplication.FULLSYNC[8]);
									((Products) productspromo)
											.addProduct(product);
								}
							}
						}
					}
					datalist[i] = productspromo;
					break;
				}
				case dlPRODUCTSNEW: {

					break;
				}
				case dlPIUTANG: {
					try {
						File filepiutang = new File("/sdcard/"
								+ MensaApplication.APP_DATAFOLDER + "/"
								+ MensaApplication.FULLSYNC[dlData[i]]);
						BaseDataListObj piutangs = (BaseDataListObj) new Piutangs();
						if (filepiutang.exists()) {
							FileInputStream jsonfile = new FileInputStream(
									filepiutang);
							String json = MensaApplication
									.getFileContent(jsonfile);
							if (!json.equals("null")) {
								jsonobj = new JSONObject(json);
								JSONArray jsonpiutangs = jsonobj
										.getJSONArray("piutang_callplan");
								Piutang piutang;
								for (int j = 0; j < jsonpiutangs.length(); j++) {
									piutang = new Piutang(
											jsonpiutangs.getJSONObject(j)
													.getString("SITE"),
											jsonpiutangs.getJSONObject(j)
													.getString("DIVISI"),
											jsonpiutangs.getJSONObject(j)
													.getString("RAYON_SALES"),
											jsonpiutangs.getJSONObject(j)
													.getString("IDENTITY"),
											jsonpiutangs.getJSONObject(j)
													.getString("INVOICE_NO"),
											jsonpiutangs.getJSONObject(j)
													.getString("INVOICE_DATE"),
											jsonpiutangs.getJSONObject(j)
													.getString("DUE_DATE"),
											jsonpiutangs
													.getJSONObject(j)
													.getDouble("INVOICE_AMOUNT"),
											jsonpiutangs.getJSONObject(j)
													.getDouble("OPEN_AMOUNT"));
									((Piutangs) piutangs).AddPiutang(piutang);
								}
							}
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
						Log.d("mensa", "load call plan customers");
						File filecustomer = new File("/sdcard/"
								+ MensaApplication.APP_DATAFOLDER + "/"
								+ MensaApplication.FULLSYNC[dlData[i]]);
						BaseDataListObj customers = (BaseDataListObj) new Customers();
						if (filecustomer.exists()) {
							FileInputStream jsonfile = new FileInputStream(
									filecustomer);
							Log.d("mensa",
									"load file = " + filecustomer.getName());
							String json = MensaApplication
									.getFileContent(jsonfile);
							if (!json.equals("null")) {
								jsonobj = new JSONObject(json);
								JSONArray jsoncustomers = jsonobj
										.getJSONArray("call_plan_daily");
								Customer customer;
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
											custproperty
													.getString("CUSTOMER_CODE"),
											custproperty.getString("NAMA"),
											custproperty.getString("KLAS"),
											custproperty.optDouble(
													"VALUE_GUIDE", 0),
											custproperty
													.getString("ALAMAT_KIRIM"),
											custproperty.getString("KOTA"),
											custproperty.optDouble(
													"CREDIT_LIMIT", 0),
											custproperty
													.optDouble("PIUTANG", 0),
											"");
									((Customers) customers)
											.addCustomer(customer);
								}
							}
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
