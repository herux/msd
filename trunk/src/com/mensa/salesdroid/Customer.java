/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com		
 * Created: 16.11.2011
 */

package com.mensa.salesdroid;

public class Customer {
	private String branch, 
		salesman_code, 
		cust_code, 
		cust_name,
		cust_chain,
		cust_group,
		bill_name,
		bill_address,
		delivery_name,
		delivery_address,
		zip_code,
		coordinate;
	
	public Customer(String abranch, 
			String asalesman_code, 
			String acust_code, 
			String acust_name,
			String acust_chain,
			String acust_group,
			String abill_name,
			String abill_address,
			String adelivery_name,
			String adelivery_address,
			String azip_code,
			String acoordinate) {
		
		branch = abranch; 
		salesman_code = asalesman_code;
		cust_code = acust_code; 
		cust_name = acust_name;
		cust_chain = acust_chain;
		cust_group = acust_group;
		bill_name = abill_name;
		bill_address = abill_address;
		delivery_name = adelivery_name;
		delivery_address = adelivery_address;
		zip_code = azip_code;
		coordinate = acoordinate;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getSalesman_code() {
		return salesman_code;
	}

	public void setSalesman_code(String salesman_code) {
		this.salesman_code = salesman_code;
	}

	public String getCust_code() {
		return cust_code;
	}

	public void setCust_code(String cust_code) {
		this.cust_code = cust_code;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCust_chain() {
		return cust_chain;
	}

	public void setCust_chain(String cust_chain) {
		this.cust_chain = cust_chain;
	}

	public String getCust_group() {
		return cust_group;
	}

	public void setCust_group(String cust_group) {
		this.cust_group = cust_group;
	}

	public String getBill_name() {
		return bill_name;
	}

	public void setBill_name(String bill_name) {
		this.bill_name = bill_name;
	}

	public String getBill_address() {
		return bill_address;
	}

	public void setBill_address(String bill_address) {
		this.bill_address = bill_address;
	}

	public String getDelivery_name() {
		return delivery_name;
	}

	public void setDelivery_name(String delivery_name) {
		this.delivery_name = delivery_name;
	}

	public String getDelivery_address() {
		return delivery_address;
	}

	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	
	
}
