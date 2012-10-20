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
	
	public void addCustomer(Customer ACustomer){
		customers.add(ACustomer);
	}
	
	public void removeCustomer(Customer ACustomer){
		customers.remove(ACustomer);
	}
	
	public void removeCustomer(int location){
		customers.remove(location);
	}
	
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	
	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	
}
