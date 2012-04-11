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

import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.util.DisplayMetrics;

public class MensaApplication extends Application {
	static final String CUSTOMERSFILENAME = "customers.mbs";
	static final String PRODUCTSFILENAME = "products.mbs";
	static final String SALESMANSFILENAME = "salesmans.mbs";
	static final String PRODUCTSFOCUSFILENAME = "productsfocus.mbs";
	static final String PRODUCTSPROMOFILENAME = "productspromo.mbs";
	static final String PIUTANGFILENAME = "piutangs.mbs";
	static final String ORDERTYPE = "ordertype.mbs";
	static final String RETURNCAUSE = "returncause.mbs";
	static final String CUSTGROUP = "customersgroup.mbs";

	static final String PRODUCTSPAGEFILENAME = "products_";
	static final String SALESMANSPAGEFILENAME = "salesmans_";
	static final String SALESORDERFILENAME = "salesorder_";
	static final String RETURNORDERFILENAME = "returnorder_";
	static final String NEWCUSTFILENAME = "newcustomer_";

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

	static final String mbs_url = "http://simfoni.mbs.co.id/services.php?";
	static final String[] fullsync_paths = {
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3Byb2R1Y3Q=&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=Y2FsbF9wbGFu&uid=", //CUSTOMERSFILENAME
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3NhbGVzbWFu",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9mb2N1c19zdG9jaw==&uid=", 
			"key=czRMZTU0dVRvTWF0MTBu&tab=cGl1dGFuZ19jYWxscGxhbg==&uid=", //PIUTANGFILENAME
			"key=czRMZTU0dVRvTWF0MTBu&tab=Y3VzdF9vcmRlcl90eXBl",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cmV0dXJuX2NhdXNl",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cG9zdF9vcmRlcg==", // post_order
			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9wcm9tb19zdG9jaw==&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cG9zdF9yZXR1cm4=", // post return
			"key=czRMZTU0dVRvTWF0MTBu&tab=bmV3X2N1c3RvbWVy", // newcustomer
			"key=czRMZTU0dVRvTWF0MTBu&tab=Y3VzdF9ncnA"}; // customersgroup 
	
	static String[] FASTSYNC = { 
		PRODUCTSFOCUSFILENAME,
		PRODUCTSPROMOFILENAME,
		CUSTOMERSFILENAME,
		PIUTANGFILENAME,
		CUSTGROUP,
		ORDERTYPE, 
		RETURNCAUSE};
	static final String[] fastsync_paths = {
			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9mb2N1c19zdG9jaw==&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9wcm9tb19zdG9jaw==&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=Y2FsbF9wbGFu&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cGl1dGFuZ19jYWxscGxhbg==&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=Y3VzdF9ncnA&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=Y3VzdF9vcmRlcl90eXBl&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cmV0dXJuX2NhdXNl&uid="
			};
	
	static String[] PENDSYNC = {
		
	};

	private ArrayList<Product> products;
	private ArrayList<Product> productsfocus;
	private ArrayList<Product> productspromo;
	private SalesOrder salesorder;
	private ArrayList<SalesItem> salesitems;
	private Returns returns;
	private ArrayList<ReturnItem> returnitems;
	private Customer currentCustomer;
	private String salesid;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
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

	static public String getDateTimeStr() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
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
		int month = c.get(Calendar.MONTH);
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

}
