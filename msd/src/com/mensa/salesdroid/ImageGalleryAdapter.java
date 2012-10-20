package com.mensa.salesdroid;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class ImageGalleryAdapter extends BaseAdapter {
//	private static final int ITEM_WIDTH = 136;
//	private static final int ITEM_HEIGHT = 88;
//	private final int galleryItemBackground;
	private final Context context;
	private final float density;
	
	public ImageGalleryAdapter(Context c) {
		context = c;
//		TypedArray a = obtainStyledAttributes(R.styleable.MenuGallery);
//		galleryItemBackground = a.getResourceId(
//				R.styleable.MenuGallery_android_galleryItemBackground, 0);
//		a.recycle();

		density = c.getResources().getDisplayMetrics().density;
	}
	
	public int getCount() {
		int count = 0;
//		if (getApplic().getCategories() != null) {
//			count = getApplic().getCategories().length;
//		}
		return count;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}


	

}
