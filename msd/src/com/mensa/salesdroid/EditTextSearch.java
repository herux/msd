/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 15.01.2012	
 */

package com.mensa.salesdroid;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
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
		textSearch.setText("Search");
//		textSearch.setOnKeyListener(new OnKeyListener() {
//			
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				if(event.getAction()==KeyEvent.ACTION_DOWN)
//			        return false;
//				
//				Log.d("mensa", "onKey: "+textSearch.getText());
//				if (textSearch.getText().equals("Search")){
//					textSearch.setText("");
//				}
//				
//				return false;
//			}
//		});
		textSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("mensa", "onclick");
				textSearch.setText("");
			}
		});
		
		textSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				if (hasFocus){
					Log.d("mensa", "focused: "+textSearch.getText());
					if(!textSearch.getText().equals("Search")||(!textSearch.getText().equals(""))){
						textSearch.setText(textSearch.getText());
					}
				}else{
					Log.d("mensa", "not focused: "+textSearch.getText());
					if(!textSearch.getText().equals("Search")||(!textSearch.getText().equals(""))){
						textSearch.setText(textSearch.getText());
					}
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
