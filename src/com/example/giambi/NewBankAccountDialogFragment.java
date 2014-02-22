package com.example.giambi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewBankAccountDialogFragment extends DialogFragment {

    public interface EditDialogListener {
        void updateResult(String[] inputText);
    }

    private EditText[] inputBoxs = new EditText[4];
    private Button addButton;

    public NewBankAccountDialogFragment() {
        //Do something here
    }


    /**
     * Creates the dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_account_dialog, null);
        builder.setTitle("Add a new account").setView(view);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        dialog.setView(view);

        inputBoxs[0] = (EditText) view.findViewById(R.id.accAliasInput);
        inputBoxs[1] = (EditText) view.findViewById(R.id.accBankInput);
        inputBoxs[2] = (EditText) view.findViewById(R.id.accAccNumInput);
        inputBoxs[3] = (EditText) view.findViewById(R.id.accBalanceInput);
        addButton = (Button) view.findViewById(R.id.accountAddButton);
        addButton.setOnClickListener(onClickListener);

     // Create the AlertDialog object and return it
        return builder.create();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.new_account_dialog, container, false);
//
//        return view;
//    }

    private OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            EditDialogListener activity = (EditDialogListener) getActivity();
            String[] params = new String[4];
            for (int i = 0; i < 4; ++i) {
                params[i] = inputBoxs[i].getText().toString();
            }
            activity.updateResult(params);

            dismiss();
        }

    };

}
