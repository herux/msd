package com.mensa.salesdroid;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.TextView;

public class ListPiutangActivity extends BaseFragmentActivity {
	static PiutangsAdapter adapter;
	static ArrayList<Piutang> alPiutangs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listpiutang);

		final ActionBar ab = getSupportActionBar();
		ab.setSubtitle("List Piutang "
				+ getMensaapplication().getCurrentCustomer().getNAMA());

		int[] datatypes = new int[1];
		datatypes[0] = DataLoader.dlPIUTANG;
		DataLoader dtpiutangs = new DataLoader(datatypes);
		Piutangs piutangs = (Piutangs) dtpiutangs.getDatalist()[0];

		alPiutangs = new ArrayList<Piutang>();
		double grandtotal = 0;
		for (int i = 0; i < piutangs.getPiutangs().size(); i++) {
			Customer customer = getMensaapplication().getCurrentCustomer();
			Piutang piutang = piutangs.getPiutangs().get(i); 
			if (customer.getCUSTOMER_CODE().equals(piutang.getCustomerid())){
				alPiutangs.add(piutang);
				grandtotal = grandtotal + piutang.getInvoice_amount(); 
			}
		}
		
		NumberFormat nf = NumberFormat.getInstance();
		String grandtotalStr = nf.format(grandtotal);
		TextView tvGrandTotal = (TextView) findViewById(R.id.tvgrandtotal);
		tvGrandTotal.setText(grandtotalStr);
	}

	public static class PiutangList extends ListFragment {

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			adapter = new PiutangsAdapter(getActivity(), R.layout.piutanglist,
					alPiutangs);
			setListAdapter(adapter);
		}

	}
}
