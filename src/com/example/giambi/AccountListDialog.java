package com.example.giambi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class AccountListDialog extends DialogFragment {
    private AccountDialogListener dListener;

    public interface AccountDialogListener {
        public ArrayAdapter<String> getArrayAdapter();
        public void accountSelected(int i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        try {
            dListener = (AccountDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Class doesn't implement AccountListDialog.DialogListener");
        }
        dialog.setTitle("Account List:");
        dialog.setAdapter(dListener.getArrayAdapter(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Which indicates the position.
                dListener.accountSelected(whichButton);
            }
        });
        dialog.setNegativeButton(R.string.dialog_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Bluetooth Debug", "Cancelled device dicovery.");
                    }
                });
        return dialog.create();
    }
}
