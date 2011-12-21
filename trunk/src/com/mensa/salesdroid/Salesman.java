/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

public class Salesman {
	
	public Salesman(String abranch, 
			String asalesman_code, 
			String asalesman_name, 
			String adivision, 
			String arayon_sales) {
		branch = abranch;
		salesman_code = asalesman_code;
		salesman_name = asalesman_name;
		division = adivision;
		rayon_sales = arayon_sales;
	}
	
	private String branch, 
					salesman_code, 
					salesman_name, 
					division, 
					rayon_sales;
	
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
	
	public String getSalesman_name() {
		return salesman_name;
	}
	
	public void setSalesman_name(String salesman_name) {
		this.salesman_name = salesman_name;
	}
	
	public String getDivision() {
		return division;
	}
	
	public void setDivision(String division) {
		this.division = division;
	}
	
	public String getRayon_sales() {
		return rayon_sales;
	}
	
	public void setRayon_sales(String rayon_sales) {
		this.rayon_sales = rayon_sales;
	}	
	
}
