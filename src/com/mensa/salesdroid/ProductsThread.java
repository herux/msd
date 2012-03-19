package com.mensa.salesdroid;

import java.util.ArrayList;

import android.os.Handler;
import android.util.Log;

public class ProductsThread extends BaseThread {
	private ArrayList<Product> products;
	private ArrayList<Product> productsfocus = new ArrayList<Product>();

	public ProductsThread(Handler h) {
		super(h);
	}

	@Override
	public void execute() {
		int[] datatypes = new int[2];
		datatypes[0] = DataLoader.dlPRODUCTS;
		datatypes[1] = DataLoader.dlPRODUCTSFOCUS;
		DataLoader dtproducts = new DataLoader(datatypes);
		Products products = (Products) dtproducts.getDatalist()[0];
		Products productsfocus = (Products) dtproducts.getDatalist()[1];
		this.products = products.getProducts(); 
		this.productsfocus = productsfocus.getProducts();
	}

	public ArrayList<Product> getProducts() {
		return products;
	}
	
	public ArrayList<Product> getProductsfocus() {
		return productsfocus;
	}

}
