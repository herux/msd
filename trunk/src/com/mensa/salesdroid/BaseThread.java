package com.mensa.salesdroid;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public abstract class BaseThread extends Thread {
	final static int STATE_DONE = 0;
	final static int STATE_RUNNING = 1;

	Handler handler;
	int state;
	int total;

	public BaseThread(Handler h) {
		handler = h;
		state = STATE_RUNNING;
	}

	public abstract void execute();

	public void run() {
		total = 0;
		while (state == STATE_RUNNING) {
			execute();
			Message msg = handler.obtainMessage();
			msg.arg1 = total;
			handler.sendMessage(msg);
			total++;
			state = STATE_DONE;
		}
	}

	public void setState(int state) {
		this.state = state;
	}

}
