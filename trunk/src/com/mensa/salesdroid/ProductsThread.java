package com.mensa.salesdroid;

import java.util.ArrayList;

import android.os.Handler;

public class ProductsThread extends BaseThread {
	private ArrayList<Product> products;
	private ArrayList<Product> productsfocus = new ArrayList<Product>();
	private ArrayList<Product> productspromo = new ArrayList<Product>();

	public ProductsThread(Handler h) {
		super(h);
	}

	@Override
	public void execute() {
		int[] datatypes = new int[3];
		datatypes[0] = DataLoader.dlPRODUCTS;
		datatypes[1] = DataLoader.dlPRODUCTSFOCUS;
		datatypes[2] = DataLoader.dlPRODUCTSPROMO;
		
		DataLoader dtproducts = new DataLoader(datatypes);
		Products products = (Products) dtproducts.getDatalist()[0];
		Products productsfocus = (Products) dtproducts.getDatalist()[1];
		Products productspromo = (Products) dtproducts.getDatalist()[2];
		this.products = products.getProducts(); 
		this.productsfocus = productsfocus.getProducts();
		this.productspromo = productspromo.getProducts();
	}

	public ArrayList<Product> getProducts() {
		return products;
	}
	
	public ArrayList<Product> getProductsfocus() {
		return productsfocus;
	}
	
	public ArrayList<Product> getProductspromo() {
		return productspromo;
	}

}
