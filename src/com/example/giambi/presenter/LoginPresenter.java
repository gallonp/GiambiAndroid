package com.example.giambi.presenter;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.giambi.InvalidUsernameOrPasswordDialogFragment;
import com.example.giambi.model.LoginAccount;
import com.example.giambi.util.AuthenticateException;
import com.example.giambi.util.Util;
import com.example.giambi.view.LoginView;

public class LoginPresenter {

    private LoginView v;
    private LoginAccount account;

    public LoginPresenter(LoginView view) {
        this.v = view;
        view.addClickListener(this.listener);
    }

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            String authResult;
            account = new LoginAccount(v.getUsername(), v.getPassword());
            try {
                if (Util.checkLogin(account)) {
                    authResult = account.authenticate();
                    v.setResonpseText(authResult);
                    if (authResult.contains("Login succeeded!")) {
                        v.SetUser(account.getUsername());
                        v.startOverview(account);
                    } else {
                        Log.i("Login Error", authResult);
                        FragmentTransaction ft = ((Activity) v)
                                .getFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("message", authResult);
                        DialogFragment dialog = new InvalidUsernameOrPasswordDialogFragment();
                        dialog.setArguments(bundle);
                        dialog.show(ft, "dialog");
                    }

                    // pass account to new activity;
                } else {
                    int usernameErrorCode = Util.checkUserName(account
                            .getUsername());
                    int passwordErrorCode = Util.checkPassword(account
                            .getPassword());
                    v.setDialogMessage(usernameErrorCode, passwordErrorCode);
                }
            } catch (AuthenticateException e) {
                v.setResonpseText(e.getMessage());
            }
        }
    };
}
