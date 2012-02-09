package com.mensa.salesdroid;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.ListFragment;

public class ListPiutangActivity extends BaseFragmentActivity {
	static PiutangsAdapter adapter;
	static ArrayList<Piutang> alPiutangs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listpiutang);

		final ActionBar ab = getSupportActionBar();
		ab.setSubtitle("List Piutang "
				+ getMensaapplication().getCurrentCustomer().getCustomername());

		int[] datatypes = new int[1];
		datatypes[0] = DataLoader.dlPIUTANG;
		DataLoader dtpiutangs = new DataLoader(datatypes);
		Piutangs piutangs = (Piutangs) dtpiutangs.getDatalist()[0];
		alPiutangs = piutangs.getPiutangs();
//		for (int i = 0; i < piutangs.getPiutangs().size(); i++) {
//			Log.d("mensa", "custid:"
//					+ piutangs.getPiutangs().get(i).getCustomerid());
//			Log.d("mensa", "curren:"
//					+ getMensaapplication().getCurrentCustomer()
//							.getCustomerid());
//			if (getMensaapplication().getCurrentCustomer().getCustomerid()
//					.equals(piutangs.getPiutangs().get(i).getCustomerid())) {
//				alPiutangs.add(piutangs.getPiutangs().get(i));
//			}
//		}
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
