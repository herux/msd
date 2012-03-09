/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 20.01.2012	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SalesOrder {

	public SalesOrder(String ordernumber, String salesmanid, String order_date,
			String customerid, String coordinate) {
		this.ordernumber = ordernumber;
		this.salesmanid = salesmanid;
		this.dates = order_date;
		this.customerid = customerid;
		this.coordinate = coordinate;
	}
	
	private String coordinate;
	
	public String getCoordinate() {
		return coordinate;
	}
	
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	private String customerid;

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	private String ordernumber;

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}

	private String salesmanid;

	public String getSalesmanid() {
		return salesmanid;
	}

	public void setSalesmanid(String salesmanid) {
		this.salesmanid = salesmanid;
	}

	private String dates;

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	private ArrayList<SalesItem> salesitems;

	public ArrayList<SalesItem> getSalesitems() {
		return salesitems;
	}

	public void setSalesitems(ArrayList<SalesItem> salesitems) {
		this.salesitems = salesitems;
	}

	private double total;

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String saveToJSON() {
		JSONObject orderhead = new JSONObject();
		JSONObject Sales = new JSONObject();
		JSONArray Items = new JSONArray();
		try {
			for (int i = 0; i < salesitems.size(); i++) {
				JSONObject item = new JSONObject();
				item.put("productid", salesitems.get(i).getProduct().getPART_NO());
				item.put("qty", salesitems.get(i).getQty());
				item.put("subtotal", salesitems.get(i).getHarga()*salesitems.get(i).getQty());
				Items.put(i, item);
			}

			Sales.put("ordernumber", getOrdernumber());
			Sales.put("datetimecheckin", getDates());
			Sales.put("salesid", getSalesmanid());
			Sales.put("customerid", getCustomerid());
			Sales.put("coordinate", getCoordinate());
			Sales.put("total", getTotal());
			Sales.put("order_type", "PDA");
			Sales.put("orderdetail", Items);
			
			orderhead.put("orderhead", Sales);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
