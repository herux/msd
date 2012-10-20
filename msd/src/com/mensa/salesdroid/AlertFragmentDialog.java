package com.mensa.salesdroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AlertFragmentDialog extends DialogFragment {
	OnClickPositiveButtonListener onClickPositiveButtonListener;
	OnClickNegativeButtonListener onClickNegativeButtonListener;

	public static AlertFragmentDialog newInstance(int title) {
		AlertFragmentDialog frag = new AlertFragmentDialog();
		Bundle args = new Bundle();
		args.putInt("title", title);
		frag.setArguments(args);
		return frag;
	}

	public void SetOnClickNegativeButtonListener(
			OnClickNegativeButtonListener listener) {
		onClickNegativeButtonListener = listener;
	}

	public void SetOnClickPositiveButtonListener(
			OnClickPositiveButtonListener listener) {
		onClickPositiveButtonListener = listener;
	}

	public interface OnClickPositiveButtonListener {
		public abstract void OnClick();
	}

	public interface OnClickNegativeButtonListener {
		public abstract void OnClick();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int title = getArguments().getInt("title");
		AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
		ad.setIcon(R.drawable.ic_launcher);
		ad.setTitle(title);
		ad.setPositiveButton("Yes", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if (onClickPositiveButtonListener != null) {
					onClickPositiveButtonListener.OnClick();
				}
			}
		});
		ad.setNegativeButton("No", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if (onClickNegativeButtonListener != null) {
					onClickNegativeButtonListener.OnClick();
				}
			}
		});
		return ad.create();
	}
}
