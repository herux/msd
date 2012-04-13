package com.mensa.salesdroid;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.AbsListView;
import android.widget.ListView;

public class ProductsListFragment extends ListFragment implements ListView.OnScrollListener {
	private ListView listview;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listview = getListView();
		listview.setOnScrollListener(this);
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

}
