package com.example.giambi.view;

import android.view.View.OnClickListener;

public interface RegisterView {
    String getUsername();

    String getPassword1();

    String getPassword2();

    void addClickListener(OnClickListener clickerListener);

    void setResonpseText(String response);

    void setDialogMessage(int usernameErrorCode, int passwordErrorCode);
}
