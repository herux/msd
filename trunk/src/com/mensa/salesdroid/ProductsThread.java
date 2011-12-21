package com.mensa.salesdroid;

import java.util.ArrayList;

import android.os.Handler;

public class ProductsThread extends BaseThread {
	private ArrayList<Product> products;

	public ProductsThread(Handler h) {
		super(h);
	}

	@Override
	public void execute() {
		int[] datatypes = new int[1];
		datatypes[0] = DataLoader.dlPRODUCTS;
		DataLoader dtproducts = new DataLoader(datatypes);
		Products products = (Products) dtproducts.getDatalist()[DataLoader.dlPRODUCTS];
		this.products = products.getProducts();
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

}
