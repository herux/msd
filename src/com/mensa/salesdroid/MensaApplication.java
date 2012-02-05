/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 22.12.2011	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

import android.app.Application;

public class MensaApplication extends Application {
	static final String CUSTOMERSFILENAME = "customers.mbs";
	static final String PRODUCTSFILENAME = "products.mbs";
	static final String SALESMANSFILENAME = "salesmans.mbs";
	static final String PRODUCTSFOCUSFILENAME = "productsfocus.mbs";
	static final String PRODUCTSPROMOFILENAME = "productspromo.mbs";
	static final String PIUTANGFILENAME = "piutangs.mbs";

	static final String APP_DATAFOLDER = "mensadata";

	static String[] dlFILENAMES = { "isi_mob_product_master.zip",
			"isi_mob_master_customerv.zip", "isi_mob_master_salesman.zip",
			"isi_mob_product_focus_t.zip", "isi_mob_piutang.zip" };

	static String[] dataFILENAMES = { PRODUCTSFILENAME, CUSTOMERSFILENAME,
			SALESMANSFILENAME, PRODUCTSFOCUSFILENAME, PIUTANGFILENAME };

	static final String mbs_url = "http://simfoni.mbs.co.id/services.php?";
	static final String[] paths = {
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3Byb2R1Y3Q=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX2N1c3RvbWVy&uid=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3NhbGVzbWFu",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cHJvZHVjdF9mb2N1cw==",
			"key=czRMZTU0dVRvTWF0MTBu&tab=cGl1dGFuZw==" };

	private ArrayList<Product> products;
	private SalesOrder salesorder;
	private ArrayList<SalesItem> salesitems;
	private Customer currentCustomer;
	private String salesid;

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

	public void ProductsClear() {
		this.products.clear();
	}

	public SalesOrder getSalesorder() {
		return salesorder;
	}

	public void setSalesorder(SalesOrder salesorder) {
		this.salesorder = salesorder;
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

}
