/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * Created: 16.11.2011	
 */

package com.mensa.salesdroid;

public class Product {
	private String branch, division, part_no, part_name;
	private float qty;
	
	public Product(String abranch, 
			String adivision, 
			String apart_no, 
			String apart_name,
			float aqty) {
		branch = abranch;
		division = adivision;
		part_no = apart_no;
		part_name = apart_name;
		qty = aqty;
	}
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPart_no() {
		return part_no;
	}
	public void setPart_no(String part_no) {
		this.part_no = part_no;
	}
	public String getPart_name() {
		return part_name;
	}
	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	
	
}
