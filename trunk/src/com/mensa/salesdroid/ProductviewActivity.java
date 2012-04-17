/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * created: 16.11.2011	
 */

package com.mensa.salesdroid;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.Tab;
import android.support.v4.app.ActionBar.TabListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mensa.salesdroid.ProductsAdapter.OnListItemClickListener;

public class ProductviewActivity extends BaseFragmentActivity {
	static ProductsThread productsThread;
	static ProductsAdapter adapter;
	static MensaApplication application;
	static final int LOADDATADIALOG = 0;
	static boolean loaded = false;
	static final int FOCUSTAB = 0;
	static final int PROMOTAB = 1;
	static final int ALLTAB = 2;
	static final int proCAPTURORDER = 0;
	static final int proBROWSER = 1;
	static ProgressDialogFragment progressDialog;
	static DialogFragment newFragment;
	static int tabIndex;
	static int proType;
	static int page = 0;
	static ArrayList<Product> products;

	final static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.arg1;
			if (total >= 0) {
				productsThread.setState(BaseThread.STATE_DONE);
				application.setProducts(productsThread.getProducts());
				application.setProductsfocus(productsThread.getProductsfocus());
				application.setProductspromo(productsThread.getProductspromo());

				ArrayList<Product> tmpProduct = new ArrayList<Product>();
				switch (tabIndex) {
				case FOCUSTAB: {
					adapter.setWithsearch(false);
					tmpProduct = application.getProductsfocus();
					break;
				}
				case PROMOTAB: {
					adapter.setWithsearch(false);
					tmpProduct = application.getProductspromo();
					break;
				}
				case ALLTAB: {
					adapter.setWithsearch(true);
					tmpProduct = application.getProducts();
					break;
				}
				}
				adapter.clear();
				tmpProduct.addAll(products);
				products = tmpProduct;
				for (int i = 0; i < products.size(); i++) {
					adapter.add(products.get(i));
				}
				adapter.notifyDataSetChanged();
				loaded = true;
				closeDialog();
				page = page + 1;
			}
		}
	};

	public void Reload(int page) {
		showProgressDialog("Load Products", "Load data product, please wait...");
		productsThread = new ProductsThread(handler);
		productsThread.setPage(page);
		productsThread.start();
	}

	public void showProgressDialog(String title, String message) {
		newFragment = ProgressDialogFragment.newInstance(title, message);
		newFragment.show(getSupportFragmentManager(), "progress dialog");
	}

	static void closeDialog() {
		newFragment.dismiss();
	}

	public static class ProgressDialogFragment extends DialogFragment {

		public static ProgressDialogFragment newInstance(String title,
				String message) {
			ProgressDialogFragment fragment = new ProgressDialogFragment();
			Bundle args = new Bundle();
			args.putString("title", title);
			args.putString("message", message);
			fragment.setArguments(args);

			return fragment;
		}

		@Override
		public ProgressDialog onCreateDialog(Bundle savedInstanceState) {
			String title = getArguments().getString("title");
			String message = getArguments().getString("message");

			ProgressDialog progressDialog = new ProgressDialog(getActivity());
			progressDialog.setTitle(title);
			progressDialog.setMessage(message);

			progressDialog.show();

			return progressDialog;
		}
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
				tabIndex = FOCUSTAB;
				if (loaded) {
					adapter.setWithsearch(false);
					products = application.getProductsfocus();
					adapter.clear();
					for (int i = 0; i < products.size(); i++) {
						adapter.add(products.get(i));
					}
					adapter.notifyDataSetChanged();
				}
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
				Log.d("mensa", "loaded:" + Boolean.toString(loaded));
				tabIndex = PROMOTAB;
				if (loaded) {
					adapter.setWithsearch(false);
					products = application.getProductspromo();
					adapter.clear();
					for (int i = 0; i < products.size(); i++) {
						adapter.add(products.get(i));
					}
					adapter.notifyDataSetChanged();
				}
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
				tabIndex = ALLTAB;
				if (loaded) {
					adapter.setWithsearch(true);
					products = application.getProducts();
					adapter.clear();
					for (int i = 0; i < products.size(); i++) {
						adapter.add(products.get(i));
					}
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		});
		actionbar.addTab(tabAll);
		showTabsNav();
		proType = getIntent().getIntExtra("protype", proCAPTURORDER);
		tabIndex = getIntent().getIntExtra("opentab", FOCUSTAB);
		switch (tabIndex) {
		case FOCUSTAB: {
			tabfocus.select();
			break;
		}
		case PROMOTAB: {
			tabPromo.select();
			break;
		}
		case ALLTAB: {
			tabAll.select();
			break;
		}
		}

		Reload(page);
	}

	public static class ProductFragment extends ProductsListFragment {
		boolean mDualPane;
		int mCurCheckPosition = 0;

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			super.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
			if ((firstVisibleItem + visibleItemCount == totalItemCount)
					&& (totalItemCount > visibleItemCount) && (loaded)) {
//				Toast toast = Toast.makeText(getActivity(),
//						"firstVisibleItem:" + firstVisibleItem
//								+ " visibleItemCount:" + visibleItemCount
//								+ " totalItemCount" + totalItemCount,
//						Toast.LENGTH_SHORT);
//				toast.show();
				Log.d("mensa", "page: " + page);
				loaded = false;
				((ProductviewActivity) getActivity()).Reload(page);
			}
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			if (products == null) {
				products = new ArrayList<Product>();
			}
			adapter = new ProductsAdapter(getActivity(), R.layout.productlist,
					products);
			adapter.setOnListItemClickListener(new OnListItemClickListener() {

				@Override
				public void OnListItemClick(View view, int position) {
					switch (proType) {
					case proCAPTURORDER: {
						showDetails(position);
						break;
					}
					case proBROWSER: {
						Intent returnIntent = new Intent();
						returnIntent.putExtra("protype", position);
						getActivity().setResult(RESULT_OK, returnIntent);
						getActivity().finish();
						break;
					}
					}
				}
			});
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

		// @Override
		// public void onListItemClick(ListView l, View v, int position, long
		// id) {
		// showDetails(position);
		// }

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
			if (products.size() > 0) {
				productdesc.setText(products.get(getShownIndex())
						.getDESCRIPTION());
			} else {
				productdesc.setText("Description");
			}
			ImageView iv = (ImageView) v.findViewById(R.id.imgProduct);
			iv.setImageDrawable(getResources().getDrawable(R.drawable.box));

			final TextView tvqty = (TextView) v.findViewById(R.id.edtQty);
			tvqty.setWidth(50);
			tvqty.setText("1");

			Button btnaddbasket = (Button) v.findViewById(R.id.btnAdd);
			if (application.getCurrentCustomer() == null) {
				btnaddbasket.setEnabled(false);
				tvqty.setEnabled(false);
			}
			btnaddbasket.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					int idx = getShownIndex();
					ArrayList<SalesItem> sis = application.getSalesitems();
					if (sis == null) {
						sis = new ArrayList<SalesItem>();
					}
					SalesItem si = new SalesItem(products.get(idx), Integer
							.parseInt(tvqty.getText().toString()), products
							.get(idx).getPRICE());
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
						so = new SalesOrder("", "", "", "", "");
						so.setDates(application.getDateTimeStr());
						so.setOrdernumber("SOM."
								+ application.getTimeInt()
								+ "."
								+ application.getCurrentCustomer()
										.getCUSTOMER_CODE());
						so.setSalesmanid(application.getSalesid());
						so.setCustomerid(application.getCurrentCustomer()
								.getCUSTOMER_CODE());
					}
					so.setTotal(total);
					application.setSalesorder(so);

					Toast toast = Toast.makeText(getActivity(), "Product "
							+ products.get(idx).getDESCRIPTION()
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
