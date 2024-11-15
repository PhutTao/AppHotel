package com.example.apphotel.Booking.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogFragment {
    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the OK button click
                        dialog.dismiss(); // Close the dialog
                    }
                })
                .setCancelable(false); // Prevent dismissing on outside touch or back button

        // Create and show the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
