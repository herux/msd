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

import android.content.ClipData.Item;

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

	private ArrayList<SalesItems> salesitems;
	
	public ArrayList<SalesItems> getSalesitems() {
		return salesitems;
	}

	public void setItem(ArrayList<SalesItems> salesitems) {
		this.salesitems = salesitems;
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
	
	public class SalesItems {
		private float qty;
		private Product product;
		private float harga;
		
		public SalesItems(Product product, float qty, float harga) {
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

		public float getHarga() {
			return harga;
		}

		public void setHarga(float harga) {
			this.harga = harga;
		}
	}

}
