package com.example.giambi.view;

import android.view.View.OnClickListener;

public interface LoginView {
	String getUsername();

	String getPassword();

	void AddClickListener(OnClickListener clickerListener);

	void setResonpseText(String response);

	void setDialogMessage(int usernameErrorCode, int passwordErrorCode);
}
