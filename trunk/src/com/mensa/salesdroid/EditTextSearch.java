/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 15.01.2012	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;

public class EditTextSearch extends EditText implements OnKeyListener {
	OnSearchFoundListener onSearchFoundListener;
	private ArrayList<Object> objtosearch;

	public EditTextSearch(Context context) {
		super(context);

		this.setOnKeyListener(this);
	}

	public EditTextSearch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		this.setOnKeyListener(this);
	}

	public EditTextSearch(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.setOnKeyListener(this);
	}

	public void SetOnSearchFoundListener(OnSearchFoundListener listener) {
		onSearchFoundListener = listener;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN)
			return false;
		ArrayList<Object> objlist = new ArrayList<Object>();
		String username = getText().toString().toLowerCase();
		if (!TextUtils.isEmpty(username)) {
			for (int i = 0; i < objtosearch.size(); i++) {
				if (objtosearch.get(i).equals(username)) {
//					objlist.add(object);
					if (i >= 5) {
						break;
					}
				}
			}
			if (onSearchFoundListener != null) {
				onSearchFoundListener.OnSearchFound(objlist);
			}
		}

		return false;

	}

	public ArrayList<Object> getObjtosearch() {
		return objtosearch;
	}

	public void setObjtosearch(ArrayList<Object> objtosearch) {
		this.objtosearch = objtosearch;
	}

	public interface OnSearchFoundListener {
		public abstract void OnSearchFound(ArrayList<Object> objlist);
	}

}
