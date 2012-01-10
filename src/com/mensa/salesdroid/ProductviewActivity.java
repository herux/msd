/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ProductviewActivity extends BaseFragmentActivity {
	static ProductsThread productsThread;
	static ProductsAdapter adapter;
	static MensaApplication application;

	final static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.arg1;
			if (total >= 0) {
				productsThread.setState(BaseThread.STATE_DONE);
				application.setProducts(productsThread.getProducts());
				adapter.clear();
				adapter.add(new Product("", "", "", "Search", "", "", 0, 0));
				for (int i = 0; i < application.getProducts().size(); i++) {
					adapter.add(application.getProducts().get(i));
				}
				adapter.notifyDataSetChanged();
			}
		}
	};

	private static void Reload() {
		productsThread = new ProductsThread(handler);
		productsThread.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = getMensaapplication();

		setContentView(R.layout.productviewlayout);
		final ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbarbackground));
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayUseLogoEnabled(false);
		ab.addTab(ab.newTab().setText("Focus"));
		ab.addTab(ab.newTab().setText("Promo"));
		ab.addTab(ab.newTab().setText("All"));
		showTabsNav();
		Reload();
	}

	public static class ProductFragment extends ListFragment {
		boolean mDualPane;
		int mCurCheckPosition = 0;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			if (application.getProducts() == null) {
				application.setProducts(new ArrayList<Product>());
				application.getProducts().add(
						new Product("", "", "", "", "", "", 0, 0));
			}
			adapter = new ProductsAdapter(getActivity(), R.layout.productlist,
					application.getProducts());
			setListAdapter(adapter);

			View detailsFrame = getActivity().findViewById(R.id.details);
			mDualPane = detailsFrame != null
					&& detailsFrame.getVisibility() == View.VISIBLE;

			if (savedInstanceState != null) {
				mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
			}

			if (mDualPane) {
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				showDetails(mCurCheckPosition);
			}

		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putInt("curChoice", mCurCheckPosition);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			showDetails(position);
		}

		void showDetails(int index) {
			mCurCheckPosition = index;
			if (mDualPane) {
				getListView().setItemChecked(index, true);

				ProductDetailsFragment details = (ProductDetailsFragment) getFragmentManager()
						.findFragmentById(R.id.details);
				if (details == null || details.getShownIndex() != index) {
					details = ProductDetailsFragment.newInstance(index);

					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.replace(R.id.details, details);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}

			} else {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ProductviewDetailsActivity.class);
				intent.putExtra("index", index);
				startActivity(intent);
			}
		}
	}

	public static class ProductDetailsFragment extends Fragment {

		public static ProductDetailsFragment newInstance(int index) {
			ProductDetailsFragment f = new ProductDetailsFragment();

			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);

			return f;
		}

		public int getShownIndex() {
			return getArguments().getInt("index", 0);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.productdetail, null);
			TextView productdesc = (TextView) v
					.findViewById(R.id.tvProductDetail);
			productdesc.setText(application.getProducts().get(getShownIndex()).getDESCRIPTION());
			return v;

		}
	}

	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	}

}
