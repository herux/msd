/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.Tab;
import android.support.v4.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductviewActivity extends BaseFragmentActivity {
	static ProductsThread productsThread;
	static ProductsAdapter adapter;
	static MensaApplication application;

	final static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.arg1;
			if (total >= 0) {
				productsThread.setState(BaseThread.STATE_DONE);
				application.addProduct(0, new Product("", "", "", "Search", "",
						"", 0, 0, 0));
				application.setProducts(productsThread.getProducts());

				// get products focus, karena tab pertama adalah focus
				// JSONObject productfocus = new JSONObject();
				// productfocus
				adapter.clear();
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

		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setSubtitle("View products list");

		Tab tabfocus = actionbar.newTab();
		tabfocus.setText("Focus");
		tabfocus.setTabListener(new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {

			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		});
		actionbar.addTab(tabfocus);

		Tab tabPromo = actionbar.newTab();
		tabPromo.setText("Promo");
		tabPromo.setTabListener(new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		});
		actionbar.addTab(tabPromo);

		Tab tabAll = actionbar.newTab();
		tabAll.setText("All");
		tabAll.setTabListener(new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				application.ProductsClear();
				application.addProduct(0, new Product("", "", "", "Search", "",
						"", 0, 0, 0));
				adapter.clear();
				for (int i = 0; i < application.getProducts().size(); i++) {
					adapter.add(application.getProducts().get(i));
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		});
		actionbar.addTab(tabAll);
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
				application.addProduct(0, new Product("", "", "", "", "", "",
						0, 0, 0));
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
			productdesc.setText(application.getProducts().get(getShownIndex())
					.getDESCRIPTION());

			final TextView tvqty = (TextView) v.findViewById(R.id.edtQty);
			tvqty.setWidth(50);
			tvqty.setText("1");

			Button btnaddbasket = (Button) v.findViewById(R.id.btnAdd);
			btnaddbasket.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					int idx = getShownIndex();
					ArrayList<SalesItem> sis = application.getSalesitems();
					if (sis == null) {
						sis = new ArrayList<SalesItem>();
					}
					SalesItem si = new SalesItem(application.getProducts().get(
							idx), Integer.parseInt(tvqty.getText().toString()),
							application.getProducts().get(idx).getPRICE());
					sis.add(si);
					application.setSalesitems(sis);

					double total = 0;
					for (int i = 0; i < application.getSalesitems().size(); i++) {
						total = total
								+ (application.getSalesitems().get(i).getQty() * application
										.getSalesitems().get(i).getHarga());
					}

					SalesOrder so = application.getSalesorder();
					if (so == null) {
						so = new SalesOrder("", "", "");
						so.setDates(application.getDateTimeStr());
						so.setOrdernumber("OrderNumber");
						so.setSalesmanid(application.getSalesid());
					}
					so.setTotal(total);
					application.setSalesorder(so);

					Toast toast = Toast.makeText(getActivity(), "Product "
							+ application.getProducts().get(idx)
									.getDESCRIPTION()
							+ " successfully add to basket", Toast.LENGTH_LONG);
					toast.show();
					if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
						getActivity().finish();
					}
				}
			});
			return v;
		}

	}

	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	}

}
