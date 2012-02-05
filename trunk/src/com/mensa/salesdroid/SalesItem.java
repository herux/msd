package com.mensa.salesdroid;

public class SalesItem {
	
	private float qty;
	private Product product;
	private double harga;
	
	public SalesItem(Product product, float qty, float harga) {
		this.product = product;
		this.qty = qty;
		this.harga = harga;
	}
	
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public float getQty() {
		return qty;
	}
	
	public void setQty(float qty) {
		this.qty = qty;
	}

	public double getHarga() {
		return harga;
	}

	public void setHarga(double harga) {
		this.harga = harga;
	}

}
