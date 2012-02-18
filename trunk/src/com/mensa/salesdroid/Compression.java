package com.mensa.salesdroid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import android.util.Base64;

public class Compression {
	
	public static String CompressString(String sIN){
		String res = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream(sIN.length());
		DeflaterOutputStream dis = new DeflaterOutputStream(baos);
		try {
			dis.write(sIN.getBytes());
			dis.close();
			byte[] compressed = baos.toByteArray();
			baos.close();
			byte[] encode = Base64.encode(compressed, Base64.DEFAULT);
			res = new String(encode);
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static String Decompress(String sIN){
		byte[] decodestr = Base64.decode(sIN, Base64.DEFAULT); 
		ByteArrayInputStream bais = new ByteArrayInputStream(decodestr);
		InflaterInputStream iis = new InflaterInputStream(bais);
		StringBuilder sb = new StringBuilder();
		
		byte[] data = new byte[32];
		int bytesread;
		try {
			while ((bytesread = iis.read(data)) != -1) {
				sb.append(new String(data, 0, bytesread));
			}
			iis.close();
			bais.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

}
