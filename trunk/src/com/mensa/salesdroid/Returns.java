package com.mensa.salesdroid;

public class Returns {
	private String ReturnNo, datetimecheckin, salesid; 
	private Customer customer;
	private String coordinate;

	public Returns(String ReturnNo, String datetimecheckin, String salesid,
			Customer customer, String coordinate) {
		this.setReturnNo(ReturnNo);
		this.setDatetimecheckin(datetimecheckin);
		this.setSalesid(salesid);
		this.setCustomer(customer);
		this.setCoordinate(coordinate);
	}

	public String getReturnNo() {
		return ReturnNo;
	}

	public void setReturnNo(String returnNo) {
		ReturnNo = returnNo;
	}

	public String getDatetimecheckin() {
		return datetimecheckin;
	}

	public void setDatetimecheckin(String datetimecheckin) {
		this.datetimecheckin = datetimecheckin;
	}

	public String getSalesid() {
		return salesid;
	}

	public void setSalesid(String salesid) {
		this.salesid = salesid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

}
