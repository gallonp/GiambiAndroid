package com.example.giambi.view;

import com.example.giambi.model.LoginAccount;

import android.view.View.OnClickListener;

public interface LoginView {
	String getUsername();

	String getPassword();

	void AddClickListener(OnClickListener clickerListener);

	void setResonpseText(String response);

	void setDialogMessage(int usernameErrorCode, int passwordErrorCode);

    void startOverview(LoginAccount account);
    
    void SetUser(String username);
}
