/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

public class Products extends BaseDataListObj  { 

	private ArrayList<Product> products;
	
	public Products() {
		products = new ArrayList<Product>();
	}
	
	public void addProduct(Product AProduct){
		products.add(AProduct);
	}
	
	public void removeProduct(Product AProduct){
		products.remove(AProduct);
	}
	
	public void removeProduct(int location){
		products.remove(location);
	}
	
	public ArrayList<Product> getProducts() {
		return products;
	}
	
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
}
