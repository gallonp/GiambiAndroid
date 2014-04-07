package com.example.giambi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.example.giambi.model.BankAccount;
import com.example.giambi.presenter.AccountPresenter;
import com.example.giambi.util.CreateAccountException;
import com.example.giambi.util.Util;
import com.example.giambi.view.AccountView;

import java.math.BigDecimal;

/**
 * New BankAccount DialogFragment.
 */
public class NewBankAccountDialogFragment extends DialogFragment {

    /**
     * num of boxes.
     */
    private final int num = 4;
    /**
     * input box.
     */
    private EditText[] inputBoxs = new EditText[num];
    /**
     * button.
     */
    private Button addButton;
    /**
     * listview.
     */
    private AccountView v;
    /**
     * presenter.
     */
    private AccountPresenter p;

    /**
     * on create.
     * 
     * @param savedInstanceState
     *            default arg
     * @return dialog
     */
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        v = (AccountView) this.getActivity();
        p = v.getPresenter();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_account_dialog, null);
        builder.setTitle("Add a new account").setView(view);

        inputBoxs[0] = (EditText) view.findViewById(R.id.accAliasInput);
        inputBoxs[1] = (EditText) view.findViewById(R.id.accBankInput);
        inputBoxs[2] = (EditText) view.findViewById(R.id.accAccNumInput);
        inputBoxs[3] = (EditText) view.findViewById(R.id.accBalanceInput);
        addButton = (Button) view.findViewById(R.id.accountAddButton);
        addButton.setOnClickListener(onClickListener);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * on click listener.
     */
    private OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v1) {
            String[] params = new String[num];
            for (int i = 0; i < num; ++i) {
                params[i] = inputBoxs[i].getText().toString();
            }

            updateResult(params);
        }

    };

    /**
     * update result.
     * 
     * @param inputText
     *            input text
     */
    public final void updateResult(String[] inputText) {
        if (Util.isNumeric(inputText[2])) {
            try {
                new BigDecimal(inputText[3]);
            } catch (NumberFormatException e) {
                this.dismiss();
                v.setDialogMessage(Util.INVALID_BALANCE);
                Log.i("onAddBankAccount", e.getMessage());
                return;
            }
        } else {
            this.dismiss();
            v.setDialogMessage(Util.INVALID_ACCOUNT_NUMBER);
            return;
        }
        BankAccount newAcc = new BankAccount(v.getUsername(), inputText[0],
                inputText[1], inputText[2], inputText[3]);
        try {
            newAcc.addToServer();
        } catch (CreateAccountException e) {
            Log.i("onAddBankAccount", e.getMessage());
        }
        p.getAccounts(v.getUsername());
        this.dismiss();
    }
}
