package com.mensa.salesdroid;

import android.graphics.Bitmap;

public class ReturnItem {
	private String productcode; 
	private float qty; 
	private String description; 
	private Bitmap image;
	
	public ReturnItem(String productcode, float qty, String description, Bitmap image) {
		this.setProductcode(productcode);
		this.setQty(qty);
		this.setDescription(description);
		this.setImage(image);
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	

}
