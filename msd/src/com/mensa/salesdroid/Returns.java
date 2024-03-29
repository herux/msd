package com.mensa.salesdroid;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

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

				String bas = "";
				if (returnitems.get(i).getImage() != null) {
					
//					byte[] originalBytes = ba;
//
//					Deflater deflater = new Deflater();
//					deflater.setInput(originalBytes);
//					deflater.finish();
//
//					ByteArrayOutputStream baos = new ByteArrayOutputStream();
//					byte[] buf = new byte[8192];
//					while (!deflater.finished()) {
//						int byteCount = deflater.deflate(buf);
//						baos.write(buf, 0, byteCount);
//					}
//					deflater.end();

//					byte[] compressedBytes = baos.toByteArray();
//					bas = Base64.encodeToString(ba, Base64.DEFAULT);
					
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					returnitems.get(i).getImage()
							.compress(CompressFormat.JPEG, 90, bao);
					bas = new String(Base64.encodeToString(bao.toByteArray(), Base64.DEFAULT));
				}
				Log.d("mensa", "savetojson image: "+bas);
				item.put("pic", bas);
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
