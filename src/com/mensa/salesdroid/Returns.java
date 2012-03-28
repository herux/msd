package com.mensa.salesdroid;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Base64;

public class Returns {
	private String ReturnNo, datetimecheckin, salesid;
	private Customer customer;
	private String coordinate;
	private ArrayList<ReturnItem> returnitems;

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

	public String saveToJSON() {
		JSONObject returnhead = new JSONObject();
		JSONObject returns = new JSONObject();
		JSONArray Items = new JSONArray();
		try {
			for (int i = 0; i < returnitems.size(); i++) {
				JSONObject item = new JSONObject();
				item.put("productid", returnitems.get(i).getProductcode());
				item.put("qty", returnitems.get(i).getQty());
				item.put("reason_code", returnitems.get(i).getDescription());
				item.put("description", returnitems.get(i).getDescription());

				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				returnitems.get(i).getImage().compress(CompressFormat.JPEG, 90, bao);
				byte[] ba = bao.toByteArray();
				String ba1= Base64.encodeToString(ba, Base64.DEFAULT);
				
				item.put("pic", ba1); 
				Items.put(i, item);
			}
			returns.put("returnnumber", getReturnNo());
			returns.put("datetimecheckin", getDatetimecheckin());
			returns.put("cabang", "YGY1");
			returns.put("customerid", getCustomer().getCUSTOMER_CODE());
			returns.put("coordinate", getCoordinate());
			returns.put("returndetail", Items);

			returnhead.put("returnhead", returns);
			File root = Environment.getExternalStorageDirectory();
			String folder = MensaApplication.APP_DATAFOLDER + "/";
			File file = new File(root, folder
					+ MensaApplication.RETURNORDERFILENAME + getReturnNo());
			MensaApplication.SaveStringToFile(file, returnhead.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnhead.toString();
	}

	public ArrayList<ReturnItem> getReturnitems() {
		return returnitems;
	}

	public void setReturnitems(ArrayList<ReturnItem> returnitems) {
		this.returnitems = returnitems;
	}

}
