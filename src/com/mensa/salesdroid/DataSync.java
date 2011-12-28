package com.mensa.salesdroid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

public class DataSync extends BaseThread {
	HttpClient http;
	private String response = "";

	public DataSync(Handler h) {
		super(h);
	}

	@Override
	public void execute() {
		http = new HttpClient();
		for (int i = 0; i < MensaApplication.paths.length; i++) {
			response = http.executeHttpPost(MensaApplication.mbs_url
					+ MensaApplication.paths[i], "");
			try {
				File root = Environment.getExternalStorageDirectory();
				if (root.canWrite()) {
					File file = new File(root, MensaApplication.APP_DATAFOLDER+"/"+MensaApplication.dataFILENAMES[i]);
					FileWriter filewriter = new FileWriter(file);
					BufferedWriter out = new BufferedWriter(filewriter);
					out.write(response);
					out.close();
				}
			} catch (IOException e) {
				Log.e("mensa", "Could not write file" + e.getMessage());
			}

		}
	}

	public String getResponse() {
		return response;
	}
}
