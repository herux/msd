package com.mensa.salesdroid;

import java.util.ArrayList;

import android.os.Handler;
import android.util.Log;

public class ProductsThread extends BaseThread {
	private ArrayList<Product> products;
	private ArrayList<Product> productsfocus = new ArrayList<Product>();
	private ArrayList<Product> productspromo = new ArrayList<Product>();
	private ArrayList<Product> productssearch = new ArrayList<Product>();
	private int page;
	private String textToSearch;
	public static final int LOADPRODUCT = 0;
	public static final int SEARCHPRODUCT = 1;
	private int LoadType;

	public ProductsThread(Handler h, int loadtype) {
		super(h);
		setLoadType(loadtype);
	}

	@Override
	public void execute() {
		switch (getLoadType()) {
		case LOADPRODUCT: {
			int[] datatypes = new int[3];
			datatypes[0] = DataLoader.dlPRODUCTS;
			datatypes[1] = DataLoader.dlPRODUCTSFOCUS;
			datatypes[2] = DataLoader.dlPRODUCTSPROMO;

			DataLoader dtproducts = new DataLoader(datatypes);
			dtproducts.setPage(page);
			Products products = (Products) dtproducts.getDatalist()[0];
			Products productsfocus = (Products) dtproducts.getDatalist()[1];
			Products productspromo = (Products) dtproducts.getDatalist()[2];
			this.products = products.getProducts();
			this.productsfocus = productsfocus.getProducts();
			this.productspromo = productspromo.getProducts();

			break;
		}
		case SEARCHPRODUCT: {
			int[] datatypes = new int[1];
			datatypes[0] = DataLoader.dlPRODUCTSSEARCH;

			DataLoader.textToSearch = getTextToSearch();
			DataLoader dtproducts = new DataLoader(datatypes);
			Products productssearch = (Products) dtproducts.getDatalist()[0];
			if (productssearch != null) { 
				this.setProductssearch(productssearch.getProducts());
			}else{
				this.setProductssearch(new ArrayList<Product>());
			}
			break;
		}
		}
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLoadType() {
		return LoadType;
	}

	public void setLoadType(int loadType) {
		LoadType = loadType;
	}

	public String getTextToSearch() {
		return textToSearch;
	}

	public void setTextToSearch(String textToSearch) {
		this.textToSearch = textToSearch;
	}

	public ArrayList<Product> getProductssearch() {
		return productssearch;
	}

	public void setProductssearch(ArrayList<Product> productssearch) {
		this.productssearch = productssearch;
	}

}
