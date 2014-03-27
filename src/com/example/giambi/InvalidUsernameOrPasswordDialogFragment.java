package com.example.giambi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Creates a dialog to notify user when there's an error in his/her password or
 * username. Create a dialog by creating an instance of this dialog then
 * putString() the desired message to be displayed in the bundle then call
 * show(ft, string) to show the dialog exmaple: FragmentTransaction ft =
 * getFragmentManager().beginTransaction(); Bundle bundle = new Bundle();
 * bundle.putString("message",
 * getString(R.string.dialog_message_password_empty)); DialogFragment dialog =
 * new InvalidUsernameOrPasswordDialogFragment(); dialog.setArguments(bundle);
 * dialog.show(ft, "dialog");
 * 
 * @author haolidu
 * @version 0.99
 */
public class InvalidUsernameOrPasswordDialogFragment extends DialogFragment {

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
