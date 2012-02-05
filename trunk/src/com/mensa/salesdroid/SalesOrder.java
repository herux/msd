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

	public SalesOrder(String ordernumber, String salesmanid, String order_date) {
		this.ordernumber = ordernumber;
		this.salesmanid = salesmanid;
		this.dates = order_date;
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
		JSONObject Sales = new JSONObject();
		JSONArray Items = new JSONArray();
		try {
			for (int i = 0; i < salesitems.size(); i++) {
				JSONObject item = new JSONObject();
				item.put("PART_NO", salesitems.get(i).getProduct().getPART_NO());
				item.put("QTY", salesitems.get(i).getQty());
				item.put("PRICE", salesitems.get(i).getHarga());
				Items.put(i, item);
			}

			Sales.put("ordernumber", getOrdernumber());
			Sales.put("salesmanid", getSalesmanid());
			Sales.put("orderdate", getDates());
			Sales.put("items", Items);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
