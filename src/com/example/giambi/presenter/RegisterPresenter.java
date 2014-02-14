package com.example.giambi.presenter;

import android.view.View;
import android.view.View.OnClickListener;

import com.example.giambi.model.LoginAccount;
import com.example.giambi.util.AuthenticateException;
import com.example.giambi.util.RegisterException;
import com.example.giambi.util.Util;
import com.example.giambi.view.RegisterView;

public class RegisterPresenter {

	private RegisterView v;
	private LoginAccount account;

	public RegisterPresenter(RegisterView view) {
		this.v = view;
		v.AddClickListener(clickerListener);
	}

	private OnClickListener clickerListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			String authResult;
			account = new LoginAccount(v.getUsername(), v.getPassword1());
			try {
				if (Util.checkLogin(account)) {
					// check two passwords;
					if (v.getPassword1().equalsIgnoreCase(v.getPassword2())) {
						authResult = account.register();
						v.setResonpseText(authResult);
					} else {
						v.setDialogMessage(0, Util.PASSWORD_NOT_MATCH);
					}
				} else {
					int usernameErrorCode = Util.checkUserName(account
							.getUsername());
					int passwordErrorCode = Util.checkPassword(account
							.getPassword());
					v.setDialogMessage(usernameErrorCode, passwordErrorCode);
				}
			} catch (RegisterException e) {
				v.setResonpseText(e.getMessage());
			}

		}

	};

}
