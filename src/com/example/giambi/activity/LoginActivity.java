package com.example.giambi.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.giambi.InvalidUsernameOrPasswordDialogFragment;
import com.example.giambi.R;
import com.example.giambi.model.LoginAccount;
import com.example.giambi.presenter.LoginPresenter;
import com.example.giambi.util.Util;
import com.example.giambi.view.LoginView;

public class LoginActivity extends Activity implements LoginView {

    private TextView username;
    private TextView password;
    private Button login;
    private TextView output;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) this.findViewById(R.id.login_button);
        username = (TextView) this.findViewById(R.id.login_username);
        password = (TextView) this.findViewById(R.id.login_password);
        output = (TextView) findViewById(R.id.logResult);
        username.requestFocus();
        onFocusChange(username.isFocused());
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public String getUsername() {
        return this.username.getText().toString();
    }

    @Override
    public String getPassword() {
        return this.password.getText().toString();
    }

    @Override
    public void AddClickListener(OnClickListener clickerListener) {
        login.setOnClickListener(clickerListener);
    }

    @Override
    public void setResonpseText(String response) {
        output.setText(response);
    }

    @Override
    public void startOverview(LoginAccount account) {
        Intent i = new Intent(this, AccountActivity.class);
        Log.v(ACCOUNT_SERVICE, "Intent initialize complete.");
        startActivity(i);
    }

    @Override
    public void setDialogMessage(int usernameErrorCode, int passwordCode) {
        Log.i("Login Error", "Invalid username or password");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        switch (usernameErrorCode) {
            case Util.USERNAME_EMPTY:
                bundle.putString("message",
                        getString(R.string.dialog_message_username_empty));
                break;
            case Util.USERNAME_LENGTH:
                bundle.putString("message",
                        getString(R.string.dialog_message_username_length));
                break;
            case Util.USERNAME_NOT_EMAIL:
                bundle.putString("message",
                        getString(R.string.dialog_message_username_not_email));
                break;
            default:
                switch (passwordCode) {
                    case Util.PASSWORD_EMPTY:
                        bundle.putString("message",
                                getString(R.string.dialog_message_password_empty));
                        break;
                    case Util.PASSWORD_LENGTH:
                        bundle.putString("message",
                                getString(R.string.dialog_message_password_length));
                        break;
                    case Util.PASSWORD_EASY:
                        bundle.putString("message",
                                getString(R.string.dialog_message_password_easy));
                        break;
                }
        }
        DialogFragment dialog = new InvalidUsernameOrPasswordDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(ft, "dialog");
    }

    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;

        (new Handler()).postDelayed(new Runnable() {

            @Override
            public void run() {
                com.example.giambi.util.Util
                        .imeSwitch((View) username, isFocus); // Switch IME
            }
        }, 100);
    }

    @Override
    public void SetUser(String username) {
        SharedPreferences prefs = this.getSharedPreferences("com.example.app",
                Context.MODE_PRIVATE);
        String name = prefs.getString("USERNAME_GIAMBI", null);
        if (name != null) {
            prefs.edit().remove("USERNAME_GIAMBI").commit();
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("USERNAME_GIAMBI", username);
        editor.commit();
    }

}
