package com.example.giambi.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.example.giambi.model.LoginAccount;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Util {
	public static final int USERNAME_EMPTY = 1;
	public static final int USERNAME_LENGTH = 2;
	public static final int USERNAME_NOT_EMAIL = 3;

	public static final int PASSWORD_EMPTY = 4;
	public static final int PASSWORD_LENGTH = 5;
	public static final int PASSWORD_EASY = 6;
	public static final int PASSWORD_NOT_MATCH = 7;

	public static final int INVALID_ACCOUNT_NUMBER = 8;
	public static final int INVALID_BALANCE = 9;

	public static final String LOCALHOST = "10.0.2.2";

	private static final Pattern rfc2822 = Pattern
			.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

	public static String HttpContentReader(InputStream input) {

		BufferedReader in = new BufferedReader(new InputStreamReader(input));

		String inputLine = "";
		StringBuffer response = new StringBuffer();

		try {
			Log.v("HttpReader", "start reader");
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("HttpReader", e.toString());
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("HttpReader", e.toString());
		}
		return response.toString();
	}

	/**
	 * @param username
	 * @return
	 */
	public static int checkUserName(String username) {
		if (username.length() == 0) {
			return USERNAME_EMPTY;
		} else if (username.length() <= 6) {
			return USERNAME_LENGTH;
		} else if (!rfc2822.matcher(username).matches()) {
			return USERNAME_NOT_EMAIL;
		}
		return 0;
	}

	/**
	 * @param password
	 * @return
	 */
	public static int checkPassword(String password) {
		if (password.length() == 0) {
			return PASSWORD_EMPTY;
		} else if (password.length() <= 8) {
			return PASSWORD_LENGTH;
		}
		try {
			Log.d("password", "" + Long.parseLong(password));
			return PASSWORD_EASY;
			// If the password can not be parsed to a long then it contains
			// letters
		} catch (NumberFormatException e) {
			Log.d("password", "NUMBER" + password);
		}
		return 0;
	}

	/**
	 * @param loginAccount
	 * @return
	 */
	public static boolean checkLogin(LoginAccount loginAccount) {
		String username = loginAccount.getUsername();
		String password = loginAccount.getPassword();
		if (username.equalsIgnoreCase("admin")
				&& password.equalsIgnoreCase("12345")) {
			return true;
		}
		return (checkUserName(username) == 0) && (checkPassword(password) == 0);
	}

	public static void imeSwitch(View v, boolean isFocus) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (isFocus) {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		} else {
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static UrlEncodedFormEntity jsonToEntity(JSONObject obj) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("json", obj.toString()));
		UrlEncodedFormEntity entity;
		Log.v("authenticate", "JSON ready");
		try {
			entity = new UrlEncodedFormEntity(postParams, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e("EncodedForm", e.toString());
			entity = null;
		}
		return entity;
	}

	public static String encodeString(String str) {
		String encodedString = "";
		try {
			encodedString = URLEncoder.encode(str, "UTF-8");
			Log.v("coded username", encodedString);
		} catch (UnsupportedEncodingException e1) {
			Log.v("URLEoder", "UnsupportedEncodingException:" + e1.getMessage());
		}
		return encodedString;
	}

	public static Date stringToDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss z yyyy");
		try {
			Date date = formatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			Log.v("Date Parse Error", dateString
					+ " can't be parsed using current format");
			return null;
		}
	}
}
