package com.example.apphotel.Booking.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import androidx.fragment.app.DialogFragment;

public class YesNoDialogFragment extends DialogFragment {

    public interface YesNoDialogListener {
        void onYesClicked();
        void onNoClicked();
    }

    private YesNoDialogListener mListener;
    private String customMessage;

    public YesNoDialogFragment(String customMessage) {
        this.customMessage = customMessage;
    }

    public void setListener(YesNoDialogListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage(customMessage)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (mListener != null) {
                            mListener.onYesClicked();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (mListener != null) {
                            mListener.onNoClicked();
                        }
                    }
                });

        return builder.create();
    }
}
