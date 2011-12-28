package com.mensa.salesdroid;

import android.app.Application;

public class MensaApplication extends Application {
	static final String CUSTOMERSFILENAME = "customers.mbs";
	static final String PRODUCTSFILENAME = "products.mbs";
	static final String SALESMANSFILENAME = "salesmans.mbs";
	static final String APP_DATAFOLDER = "mensadata";

	static String[] dataFILENAMES = { PRODUCTSFILENAME, SALESMANSFILENAME,
			CUSTOMERSFILENAME };
	static final String mbs_url = "http://simfoni.mbs.co.id/services.php?";
	static final String[] paths = {
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3Byb2R1Y3Q=",
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX3NhbGVzbWFu",
			"key=czRMZTU0dVRvTWF0MTBu&tab=bWFzdGVyX2N1c3RvbWVy" };

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

}
