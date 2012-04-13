package com.mensa.salesdroid;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.InflaterInputStream;

import android.util.Base64;

public class Compression {

	public static String encodeBase64(String s) {
		byte[] data;
		String base64 = "";
		try {
			data = s.getBytes("UTF-8");
			base64 = Base64.encodeToString(data, Base64.DEFAULT);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64;
	}

	public static String Decompress(String sIN) {
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
