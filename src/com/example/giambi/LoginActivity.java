package com.example.giambi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private MultiAutoCompleteTextView username;
	private TextView password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// Show the Up button in the action bar.
		setupActionBar();

		Button login = (Button) this.findViewById(R.id.login_button);
		username = (MultiAutoCompleteTextView) this
				.findViewById(R.id.login_username);
		password = (TextView) this.findViewById(R.id.login_password);
		login.setOnClickListener(loginClickListener);
	}

	OnClickListener loginClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String name = username.getText().toString();
			String pwd = password.getText().toString();
			String authResult = authenticate(name, pwd);
			TextView tv = (TextView) findViewById(R.id.logResult);
			tv.setText(authResult);
			// Button login = (Button)v;
			// login.setText(pwd);

		}

	};

	/**
	 * @param username
	 * @return
	 */
	private boolean checkUserName(String username) {
		return false;
	}

	/**
	 * @param password
	 * @return
	 */
	private boolean checkPassword(String password) {
		return false;
	}

	/**
	 * @param username
	 * @param password
	 * @return
	 */
	private boolean checkLogin(String username, String password) {
		return false;
	}

	@SuppressWarnings("unchecked")
	private String authenticate(String username, String password) {
		// URL url = new URL("")
		// should be HttpGet
		HttpPost request = new HttpPost(
				"http://giambi-server-2340.appspot.com/registerLogin");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("username", username);
		jsonObj.put("password", password);
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("json", jsonObj.toString()));
		UrlEncodedFormEntity entity;
		Log.v("authenticate", "JSON ready");
		try {
			entity = new UrlEncodedFormEntity(postParams, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.e("EncodedForm", e.toString());
			entity = null;
		}

		request.setEntity(entity);
		String content = "";
		try {
			content = Util.HttpContentReader(GiambiHttpClient
					.getResponse(request).getEntity().getContent());
		} catch (IllegalStateException e) {
			content = e.getMessage();
		} catch (IOException e) {
			content = e.getMessage();
		}
		return content;
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

}
