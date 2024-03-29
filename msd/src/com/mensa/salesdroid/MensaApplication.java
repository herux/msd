/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 22.12.2011	
 */

package com.mensa.salesdroid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.DisplayMetrics;

public class MensaApplication extends Application {
	static int THEME = R.style.Theme_Sherlock;
	
	static final String PREFS_MENSA = "mensa_prefs";
	static final String KEY_URL = "root";
	static final String KEY_PRODUCT = "product";
	static final String KEY_CUSTOMER = "customer";
	static final String KEY_SALESMAN = "salesman";
	static final String KEY_FOCUS = "focus";
	static final String KEY_PIUTANG = "piutang";
	static final String KEY_ORDERTYPE = "ordertype";
	static final String KEY_RETURNCAUSE = "returncause";
	static final String KEY_ORDER = "order";
	static final String KEY_PROMO = "promo";
	static final String KEY_RETURN = "return";
	static final String KEY_NEWCUSTOMER = "newcustomer";
	static final String KEY_CUSTOMERGROUP = "customergroup";
	
	static final String CUSTOMERSFILENAME = "customers.mbs";
	static final String PRODUCTSFILENAME = "products.mbs";
	static final String SALESMANSFILENAME = "salesmans.mbs";
	static final String PRODUCTSFOCUSFILENAME = "productsfocus.mbs";
	static final String PRODUCTSPROMOFILENAME = "productspromo.mbs";
	static final String PIUTANGFILENAME = "piutangs.mbs";
	static final String ORDERTYPE = "ordertype.mbs";
	static final String RETURNCAUSE = "returncause.mbs";
	static final String CUSTGROUP = "customersgroup.mbs";
	static final String USERLOG_HISTORY = "loginhistory.mbs";

	static final String PRODUCTSPAGEFILENAME = "products_";
	static final String SALESMANSPAGEFILENAME = "salesmans_";
	static final String SALESORDERFILENAME = "salesorder_";
	static final String RETURNORDERFILENAME = "returnorder_";
	static final String NEWCUSTFILENAME = "newcustomer_";
	
	static final String ORDERNOLIST = "todayordernolist.mbs"; 

	static final String APP_DATAFOLDER = "mensadata";

	static String[] FULLSYNC = { 
			PRODUCTSFILENAME, 
			CUSTOMERSFILENAME,
			SALESMANSFILENAME, 
			PRODUCTSFOCUSFILENAME, 
			PIUTANGFILENAME,
			ORDERTYPE, 
			RETURNCAUSE, 
			SALESORDERFILENAME, 
			PRODUCTSPROMOFILENAME,
			RETURNORDERFILENAME,
			NEWCUSTFILENAME,
			CUSTGROUP
			};

	static String mbs_url = "http://simfoni.mbs.co.id/services.php?";
//	static final String[] fullsync_paths = {
//			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3Byb2R1Y3Q=&uid=", //PRODUCTSFILENAME
//			"key=czRMZTU0dVRvTWF0MTBu&tab=Y2FsbF9wbGFu&uid=", //CUSTOMERSFILENAME
//			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3NhbGVzbWFu",
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9mb2N1cw==&uid=", //"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9mb2N1c19zdG9jaw==&uid=", 
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cGl1dGFuZ19jYWxscGxhbg==&uid=", //PIUTANGFILENAME
//			"key=czRMZTU0dVRvTWF0MTBu&tab=Y3VzdF9vcmRlcl90eXBl",
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cmV0dXJuX2NhdXNl",
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cG9zdF9vcmRlcg==", // post_order
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9wcm9tb19zdG9jaw==&uid=", //PRODUCTSPROMOFILENAME
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cG9zdF9yZXR1cm4=", // post return
//			"key=czRMZTU0dVRvTWF0MTBu&tab=bmV3X2N1c3RvbWVy", // newcustomer
//			"key=czRMZTU0dVRvTWF0MTBu&tab=Y3VzdF9ncnA"}; // customersgroup 
	static String[] fullsync_paths = new String[12];
	
	static String[] FASTSYNC = { 
		PRODUCTSFOCUSFILENAME,
		PRODUCTSPROMOFILENAME,
		CUSTOMERSFILENAME,
		PIUTANGFILENAME,
		CUSTGROUP,
		ORDERTYPE, 
		RETURNCAUSE};
//	static final String[] fastsync_paths = {
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9mb2N1cw==&uid=",
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9wcm9tb19zdG9jaw==&uid=",
//			"key=czRMZTU0dVRvTWF0MTBu&tab=Y2FsbF9wbGFu&uid=",
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cGl1dGFuZ19jYWxscGxhbg==&uid=",
//			"key=czRMZTU0dVRvTWF0MTBu&tab=Y3VzdF9ncnA&uid=",
//			"key=czRMZTU0dVRvTWF0MTBu&tab=Y3VzdF9vcmRlcl90eXBl&uid=",
//			"key=czRMZTU0dVRvTWF0MTBu&tab=cmV0dXJuX2NhdXNl&uid="
//			};
	
	static String[] fastsync_paths = new String[7];
	
//	static String[] PENDSYNC = {
//		"key=czRMZTU0dVRvTWF0MTBu&tab=cG9zdF9vcmRlcg==", // post_order
//		"key=czRMZTU0dVRvTWF0MTBu&tab=cG9zdF9yZXR1cm4=", // post return
//		"key=czRMZTU0dVRvTWF0MTBu&tab=bmV3X2N1c3RvbWVy" // newcustomer
//	};
	
	static String[] PENDSYNC = new String[3];

	private ArrayList<Product> productsearchs;
	private ArrayList<Product> products;
	private ArrayList<Product> productsfocus;
	private ArrayList<Product> productspromo;
	private SalesOrder salesorder;
	private ArrayList<SalesItem> salesitems;
	private Returns returns;
	private ArrayList<ReturnItem> returnitems;
	private Customer currentCustomer;
	private String salesid;
	private String longitudelatitude = "0,0";
	private boolean needSync;
	private boolean needFastSync;
	private SharedPreferences settings;
	public static final String FULLSYNC_LOG = "FullSL";
	public static final String FASTSYNC_LOG = "FastSL";

	@Override
	public void onCreate() {
		setSettings(getSharedPreferences(PREFS_MENSA, MODE_PRIVATE));
		super.onCreate();
		SharedPreferences settingsRead = getSharedPreferences(PREFS_MENSA, MODE_PRIVATE);
		mbs_url = settingsRead.getString(KEY_URL, "");
		
		fullsync_paths[0] = settingsRead.getString(KEY_PRODUCT, "");
		fullsync_paths[1] = settingsRead.getString(KEY_CUSTOMER, "");
		fullsync_paths[2] = settingsRead.getString(KEY_SALESMAN, "");
		fullsync_paths[3] = settingsRead.getString(KEY_FOCUS, "");
		fullsync_paths[4] = settingsRead.getString(KEY_PIUTANG, "");
		fullsync_paths[5] = settingsRead.getString(KEY_ORDERTYPE, "");
		fullsync_paths[6] = settingsRead.getString(KEY_RETURNCAUSE, "");
		fullsync_paths[7] = settingsRead.getString(KEY_ORDER, "");
		fullsync_paths[8] = settingsRead.getString(KEY_PROMO, "");
		fullsync_paths[9] = settingsRead.getString(KEY_RETURN, "");
		fullsync_paths[10] = settingsRead.getString(KEY_NEWCUSTOMER, "");
		fullsync_paths[11] = settingsRead.getString(KEY_CUSTOMERGROUP, "");
		
		fastsync_paths[0] = settingsRead.getString(KEY_FOCUS, "");
		fastsync_paths[1] = settingsRead.getString(KEY_PROMO, "");
		fastsync_paths[2] = settingsRead.getString(KEY_CUSTOMER, "");
		fastsync_paths[3] = settingsRead.getString(KEY_PIUTANG, "");
		fastsync_paths[4] = settingsRead.getString(KEY_CUSTOMERGROUP, "");
		fastsync_paths[5] = settingsRead.getString(KEY_ORDERTYPE, "");
		fastsync_paths[6] = settingsRead.getString(KEY_RETURNCAUSE, "");
		
		PENDSYNC[0] = settingsRead.getString(KEY_ORDER, "");
		PENDSYNC[1] = settingsRead.getString(KEY_RETURN, "");
		PENDSYNC[2] = settingsRead.getString(KEY_NEWCUSTOMER, "");
	}
	
	public int getscreenWidth(Activity activity){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		return displaymetrics.widthPixels;
	}
	
	public int getscreenHeight(Activity activity){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		return displaymetrics.heightPixels;
	}

	static public String getFileContent(FileInputStream filename) {
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
			return "";
		}
		return json.toString();
	}

	static public void SaveStringToFile(File file, String string) {
		try {
			FileWriter filewriter = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(filewriter);
			out.write(string);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static public String getDataFolder() {
		File root = Environment.getExternalStorageDirectory();
		String folder = MensaApplication.APP_DATAFOLDER + "/";
		return root + "/" + folder;
	}

	static public File[] getListFiles() {
		String datafolder = getDataFolder();
		File dir = new File(datafolder);
		File[] filelist = dir.listFiles();
		return filelist;
	}
	
	public String getDateString() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		return Integer.toString(day) + "-" + Integer.toString(month) + "-"
				+ Integer.toString(year);
	}
	
	public long getDateLong(){
		Date date = new Date(System.currentTimeMillis());
		return date.getTime();
	}

	public String getDateTimeStr() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
		int hour = c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		return Integer.toString(day) + "-" + Integer.toString(month) + "-"
				+ Integer.toString(year) + " " + Integer.toString(hour) + ":"
				+ Integer.toString(minute) + ":" + Integer.toString(second);
	}

	public static long daysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}
	
	static public String getDateTimeInt() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
		int hour = c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);

		String resStr = Integer.toString(day) + Integer.toString(month)
				+ Integer.toString(year) + Integer.toString(hour)
				+ Integer.toString(minute) + Integer.toString(second);
		return resStr;
	}

	static public String getTimeInt() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);

		String resStr = Integer.toString(hour) + Integer.toString(minute)
				+ Integer.toString(second);
		return resStr;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public void addProduct(int index, Product product) {
		this.products.add(index, product);
	}

	public void ProductsClear() {
		this.products.clear();
	}

	public SalesOrder getSalesorder() {
		return salesorder;
	}

	public void setSalesorder(SalesOrder salesorder) {
		this.salesorder = salesorder;
	}

	public void deleteSalesitem(int index) {
		salesitems.remove(index);
	}

	public void changeQtySalesitem(int index, float newQty) {
		SalesItem si = salesitems.get(index);
		si.setQty(newQty);
	}

	public ArrayList<SalesItem> getSalesitems() {
		return salesitems;
	}

	public void setSalesitems(ArrayList<SalesItem> salesitems) {
		this.salesitems = salesitems;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public String getSalesid() {
		return salesid;
	}

	public void setSalesid(String salesid) {
		this.salesid = salesid;
	}

	public Returns getReturns() {
		return returns;
	}

	public void setReturns(Returns returns) {
		this.returns = returns;
	}

	public void deleteReturnItem(int index) {
		returnitems.remove(index);
	}

	public ArrayList<ReturnItem> getReturnitems() {
		return returnitems;
	}

	public void setReturnitems(ArrayList<ReturnItem> returnitems) {
		this.returnitems = returnitems;
	}

	public ArrayList<Product> getProductsfocus() {
		return productsfocus;
	}

	public void setProductsfocus(ArrayList<Product> productsfocus) {
		this.productsfocus = productsfocus;
	}

	public ArrayList<Product> getProductspromo() {
		return productspromo;
	}

	public void setProductspromo(ArrayList<Product> productspromo) {
		this.productspromo = productspromo;
	}

	public String getLongitudelatitude() {
		return longitudelatitude;
	}

	public void setLongitudelatitude(String longitudelatitude) {
		this.longitudelatitude = longitudelatitude;
	}

	public ArrayList<Product> getProductsearchs() {
		return productsearchs;
	}

	public void setProductsearchs(ArrayList<Product> productsearchs) {
		this.productsearchs = productsearchs;
	}

	public boolean isNeedSync() {
		return needSync;
	}

	public void setNeedSync(boolean needSync) {
		this.needSync = needSync;
	}

	public boolean isNeedFastSync() {
		return needFastSync;
	}

	public void setNeedFastSync(boolean neesFastSync) {
		this.needFastSync = neesFastSync;
	}

	public SharedPreferences getSettings() {
		return settings;
	}

	public void setSettings(SharedPreferences settings) {
		this.settings = settings;
	}

}
