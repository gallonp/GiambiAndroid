package com.example.giambi.presenter;

import android.view.View;
import android.view.View.OnClickListener;

import com.example.giambi.model.LoginAccount;
import com.example.giambi.util.AuthenticateException;
import com.example.giambi.util.Util;
import com.example.giambi.view.LoginView;

public class LoginPresenter {

	private LoginView v;
	private LoginAccount account;

	public LoginPresenter(LoginView view) {
		this.v = view;
		view.AddClickListener(this.listener);
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
