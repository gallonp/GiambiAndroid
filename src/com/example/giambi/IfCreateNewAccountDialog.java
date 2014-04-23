package com.example.giambi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class IfCreateNewAccountDialog extends DialogFragment {
    private DialogListener activityWithDialogListener;
    public interface DialogListener {
        public void positiveChoice();
    }
    
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activityWithDialogListener = null;
        Activity activity = getActivity();
        try {
            activityWithDialogListener = (DialogListener) activity;
        } catch (ClassCastException e){
            throw new IllegalArgumentException("Class does not implement IfCreateNewAccountDialog.DialogListener");
        }
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(
                "Your account list is empty, would you like to creat a new account?")
                .setTitle("Account")
                .setPositiveButton(R.string.dialog_OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                activityWithDialogListener.positiveChoice();
                            }
                        })
                .setNegativeButton(R.string.dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do nothing
                            }
                        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
