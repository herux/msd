/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com
 * Created: 16.11.2011		
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

public class Customers extends BaseDataListObj {

private ArrayList<Customer> customers;
	
	public Customers() {
		customers = new ArrayList<Customer>();
	}
	
	public void addProduct(Customer ACustomer){
		customers.add(ACustomer);
	}
	
	public void removeProduct(Customer ACustomer){
		customers.remove(ACustomer);
	}
	
	public void removeProduct(int location){
		customers.remove(location);
	}
	
	public ArrayList<Customer> getProducts() {
		return customers;
	}
	
	public void setProducts(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	
}
