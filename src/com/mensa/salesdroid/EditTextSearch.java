/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 15.01.2012	
 */

package com.mensa.salesdroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class EditTextSearch extends RelativeLayout {
	OnSearchClickListener onSearchClickListener;
	private int textColor = android.R.color.black;
	private EditText textSearch;
	private Button btnSearch;

	public EditTextSearch(final Context context) {
		super(context);
		LayoutInflater layoutInflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.edittextsearch, this);
		textSearch = (EditText) findViewById(R.id.etSearch);
		textSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				textSearch.setText("");
			}
		});
		textSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				
				if ((!hasFocus)&&(textSearch.getText().equals(""))||(textSearch.getText().equals("Search"))){
					textSearch.setText("");
				}
			}
		});
		btnSearch  = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (onSearchClickListener!=null){
					onSearchClickListener.OnSearchClick(arg0);
				}
			}
		});
	}
	
	public void setOnSearchClickListener(OnSearchClickListener listener){
		onSearchClickListener = listener;
	}
	
	public interface OnSearchClickListener{
		public abstract void OnSearchClick(View arg0);
	}
	
	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		textSearch.setTextColor(textColor);
		this.textColor = textColor;
	}


	public String getText() {
		return textSearch.getText().toString();
	}

	public void setText(String text) {
		this.textSearch.setText(text);
	}

}
