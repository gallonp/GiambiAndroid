package com.example.giambi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.example.giambi.model.LoginAccount;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
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
		setupActionBar();
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

	// @SuppressWarnings("unchecked")
	// private String authenticate(String username, String password) {
	// // URL url = new URL("")
	// // should be HttpGet
	// String encodedUsername = "";
	// String encodedPassword = "";
	// try {
	// encodedUsername = URLEncoder.encode(username, "UTF-8");
	// encodedPassword = URLEncoder.encode(password, "UTF-8");
	// Log.v("coded username", encodedUsername);
	// Log.v("coded password", encodedPassword);
	//
	// } catch (UnsupportedEncodingException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// HttpPost request = new HttpPost(
	// "http://giambi-server-2340.appspot.com/login");
	// JSONObject jsonObj = new JSONObject();
	// jsonObj.put("username", encodedUsername);
	// jsonObj.put("password", encodedPassword);
	// // HttpParams params = new BasicHttpParams();
	// // params.setParameter("json",jsonObj.toJSONString());
	// // request.setParams(params);
	// List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	// postParams.add(new BasicNameValuePair("json", jsonObj.toString()));
	// UrlEncodedFormEntity entity;
	// Log.v("authenticate", "JSON ready");
	// try {
	// entity = new UrlEncodedFormEntity(postParams, "UTF-8");
	// } catch (UnsupportedEncodingException e) {
	// Log.e("EncodedForm", e.toString());
	// entity = null;
	// }
	// request.setEntity(entity);
	// // JSONObject parsedObj = (JSONObject)
	// // JSONValue.parse(request.getParams().getParameter("json").toString());
	// //
	// Log.v("request params",request.getParams().getParameter("json").toString());
	// // Log.v("json parsed obj",(String)parsedObj.get("username"));
	// // String content = "";
	// String content = "";
	// try {
	// content = Util.HttpContentReader((GiambiHttpClient.getResponse(
	// request).getEntity().getContent()));
	// } catch (IllegalStateException e) {
	// // TODO Auto-generated catch block
	// Log.e("IllegalStateException", e.getMessage());
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// Log.e("IOException", e.getMessage());
	// e.printStackTrace();
	// }
	// return content;
	// // List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	// // postParams.add(new BasicNameValuePair("json", jsonObj.toString()));
	// // UrlEncodedFormEntity entity = null;
	// // Log.v("authenticate", "JSON ready");
	// // try {
	// // entity = new UrlEncodedFormEntity(postParams, "UTF-8");
	// // //request.setEntity(entity);
	// // String content = "";
	// // try {
	// // content = Util.HttpContentReader(GiambiHttpClient
	// // .getResponse(request).getEntity().getContent());
	// // } catch (IllegalStateException e) {
	// // content = e.getMessage();
	// // } catch (IOException e) {
	// // content = e.getMessage();
	// // }
	// // return content;
	// // } catch (UnsupportedEncodingException e) {
	// // Log.e("EncodedFormError", e.toString());
	// // return "";
	// // }
	// }
}
