package com.example.giambi;

import com.example.giambi.presenter.LoginPresenter;
import com.example.giambi.util.Util;
import com.example.giambi.view.LoginView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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
		// Show the Up button in the action bar.
//		setupActionBar();
		login = (Button) this.findViewById(R.id.login_button);
		username = (TextView) this.findViewById(R.id.login_username);
		password = (TextView) this.findViewById(R.id.login_password);
		output = (TextView) findViewById(R.id.logResult);
		loginPresenter = new LoginPresenter(this);
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

}
