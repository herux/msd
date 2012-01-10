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
	
	static final String APP_DATAFOLDER = "mensadata";

	static String[] dataFILENAMES = { PRODUCTSFILENAME, CUSTOMERSFILENAME, SALESMANSFILENAME};
	static final String mbs_url = "http://simfoni.mbs.co.id/services.php?";
	static final String[] paths = {
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3Byb2R1Y3Q=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX2N1c3RvbWVy",
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3NhbGVzbWFu"
			 };
	
	private ArrayList<Product> products;

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

}
