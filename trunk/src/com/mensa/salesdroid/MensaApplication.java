/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 22.12.2011	
 */

package com.mensa.salesdroid;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

public class MensaApplication extends Application {
	static final String CUSTOMERSFILENAME = "customers.mbs";
	static final String PRODUCTSFILENAME = "products.mbs";
	static final String SALESMANSFILENAME = "salesmans.mbs";
	static final String PRODUCTSFOCUSFILENAME = "productsfocus.mbs";
	static final String PRODUCTSPROMOFILENAME = "productspromo.mbs";
	static final String PIUTANGFILENAME = "piutangs.mbs";
	static final String ORDERTYPE = "ordertype.mbs";
	static final String RETURNCAUSE = "returncause.mbs";

	static final String PRODUCTSPAGEFILENAME = "products_";
	static final String SALESMANSPAGEFILENAME = "salesmans_";
	static final String SALESORDERFILENAME = "salesorder_";
	
	static final String APP_DATAFOLDER = "mensadata";

	static String[] dataFILENAMES = { PRODUCTSFILENAME, CUSTOMERSFILENAME,
			SALESMANSFILENAME, PRODUCTSFOCUSFILENAME, PIUTANGFILENAME,
			ORDERTYPE, RETURNCAUSE };

	static final String mbs_url = "http://simfoni.mbs.co.id/services.php?";
	static final String[] paths = {
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3Byb2R1Y3Q=&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=Y2FsbF9wbGFu&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3NhbGVzbWFu",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9mb2N1cw==&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cGl1dGFuZ19jYWxscGxhbg==&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=Y3VzdF9vcmRlcl90eXBl",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cmV0dXJuX2NhdXNl" };

	private ArrayList<Product> products;
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

	public String getDataFolder() {
		File root = Environment.getExternalStorageDirectory();
		String folder = MensaApplication.APP_DATAFOLDER + "/";
		return root + "/" + folder;
	}

	public String getDateTimeStr() {
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

	public String getDateTimeInt() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		int hour = c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);

		String resStr = Integer.toString(day) + Integer.toString(month)
				+ Integer.toString(year) + Integer.toString(hour)
				+ Integer.toString(minute) + 
				Integer.toString(second);
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

}
