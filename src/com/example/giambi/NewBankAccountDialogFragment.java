package com.example.giambi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class NewBankAccountDialogFragment extends DialogFragment {

    public NewBankAccountDialogFragment() {
        
    }

    /**
     * Creates the dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String message = getArguments().getString("message");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle("Error")
                .setPositiveButton(R.string.dialog_OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Do something after user acknowledges the
                                // message.
                            }
                        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
