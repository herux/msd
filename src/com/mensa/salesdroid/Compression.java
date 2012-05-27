package com.mensa.salesdroid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import android.graphics.Bitmap;
import android.util.Base64;


public class Compression {

	public static String encodebase64(String s) {
		return Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
	}
	
	public static String imageToString(Bitmap bmp){
		ByteArrayOutputStream bao = new ByteArrayOutputStream();

		bmp.compress(Bitmap.CompressFormat.JPEG, 90, bao);

        byte [] ba = bao.toByteArray();

        String bal = Base64.encodeToString(ba, Base64.DEFAULT);
        return bal;
	}
	
	public static String Compress(String sIN) {
		String res = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream(sIN.length());
		DeflaterOutputStream dis = new DeflaterOutputStream(baos);
		try {
			dis.write(sIN.getBytes());
			dis.close();
			byte[] compressed = baos.toByteArray();
			baos.close();
			res = Base64.encodeToString(compressed, Base64.DEFAULT);
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
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
