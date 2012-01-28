/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 22.12.2011	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

import com.mensa.salesdroid.SalesOrder.SalesItems;

import android.app.Application;

public class MensaApplication extends Application {
	static final String CUSTOMERSFILENAME = "customers.mbs";
	static final String PRODUCTSFILENAME = "products.mbs";
	static final String SALESMANSFILENAME = "salesmans.mbs";
	static final String PRODUCTSFOCUSFILENAME = "productsfocus.mbs";
	static final String PRODUCTSPROMOFILENAME = "productspromo.mbs";
	static final String PIUTANGFILENAME = "piutangs.mbs";

	static final String APP_DATAFOLDER = "mensadata";

	static String[] dataFILENAMES = { PRODUCTSFILENAME, CUSTOMERSFILENAME,
			SALESMANSFILENAME, PRODUCTSFOCUSFILENAME, PIUTANGFILENAME}; //, PRODUCTSPROMOFILENAME 
	static final String mbs_url = "http://simfoni.mbs.co.id/services.php?";
	static final String[] paths = {
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3Byb2R1Y3Q=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX2N1c3RvbWVy",
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3NhbGVzbWFu",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9mb2N1cw==",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cGl1dGFuZw=="//,
//			""
			};

	private ArrayList<Product> products;
	private SalesOrder salesorder;
	private SalesItems salesitems;
	private Customer currentCustomer;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
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
	
	public void ProductsClear(){
		this.products.clear();
	}

	public SalesOrder getSalesorder() {
		return salesorder;
	}

	public void setSalesorder(SalesOrder salesorder) {
		this.salesorder = salesorder;
	}

	public SalesItems getSalesitems() {
		return salesitems;
	}

	public void setSalesitems(SalesItems salesitems) {
		this.salesitems = salesitems;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

}
