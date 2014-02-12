package com.example.giambi.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import android.os.AsyncTask;
import android.util.Log;

import com.example.giambi.GiambiHttpClient;
import com.example.giambi.util.AuthenticateException;
import com.example.giambi.util.RegisterException;
import com.example.giambi.util.Util;

public class LoginAccount {

	private String username;
	private String password;
	private String cookie;

	public LoginAccount(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@SuppressWarnings("unchecked")
	public String register() throws RegisterException {
		String encodedUsername = encodeString(username);
		String encodedPassword = encodeString(password);
		HttpPost request = new HttpPost(
				"http://giambi-server-2340.appspot.com/register");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("username", encodedUsername);
		jsonObj.put("password", encodedPassword);
		request.setEntity(jsonToEntity(jsonObj));
		HttpResponse response = GiambiHttpClient.getResponse(request);
		String responseCookie = "";//response.getHeaders("Cookie")[0].getValue();
		String content = "";
//		if (responseCookie = "") {
			this.cookie = responseCookie;
//			return true;
//		} else {
			try {
				content = Util.HttpContentReader(response.getEntity()
						.getContent());
			} catch (IllegalStateException e) {
				Log.e("IllegalStateException", e.getMessage());
			} catch (IOException e) {
				Log.e("IOException", e.getMessage());
			}
			if (content == null) {
				throw new RegisterException("Unknown Error");
			} else {
				return content;
				//return true;
//				throw new RegisterException(content);
			}
			
//		}		
	}
	

	
	@SuppressWarnings("unchecked")
	public String authenticate() throws AuthenticateException {
		String encodedUsername = encodeString(username);
		String encodedPassword = encodeString(password);
		HttpPost request = new HttpPost(
				"http://giambi-server-2340.appspot.com/login");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("username", encodedUsername);
		jsonObj.put("password", encodedPassword);
		jsonObj.put("cookie", this.cookie);
		request.setEntity(jsonToEntity(jsonObj));
		// JSONObject parsedObj = (JSONObject)
		// JSONValue.parse(request.getParams().getParameter("json").toString());
		// Log.v("request params",request.getParams().getParameter("json").toString());
		// Log.v("json parsed obj",(String)parsedObj.get("username"));
		// String content = "";
		HttpResponse response = GiambiHttpClient.getResponse(request);
		String responseCookie = "";//response.getHeaders("Cookie")[0].getValue();
		String content = "";
//		if (responseCookie != "") {
//			this.cookie = responseCookie;
//			
//		} else {
			try {
				content = Util.HttpContentReader(response.getEntity()
						.getContent());
			} catch (IllegalStateException e) {
				Log.e("IllegalStateException", e.getMessage());
			} catch (IOException e) {
				Log.e("IOException", e.getMessage());
			}
			if (content == null) {
				throw new AuthenticateException("Unknown Error");
			} else {
				return content;
//				throw new AuthenticateException(content);
			}
//		}
		//
		// String content = "";
		// try {
		// content = Util.HttpContentReader((GiambiHttpClient.getResponse(
		// request).getEntity().getContent()));

		// JSONParser parser = new JSONParser();
		// JSONObject parsedResponse;
		// try {
		// parsedResponse = (JSONObject) parser.parse(content);
		// String cookie = (String) parsedResponse.get("cookie");
		// if (cookie != null) {
		// this.cookie = cookie;
		// return true;
		// } else {
		// throw new AuthenticateException("Empty cookie!");
		// }
		// } catch (ParseException e) {
		// Log.e("Parse response", e.getMessage());
		// throw new AuthenticateException(content);
		// }
	}

	private UrlEncodedFormEntity jsonToEntity(JSONObject obj){
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
	
	private String encodeString(String str){
		String encodedString="";
		try {
			encodedString = URLEncoder.encode(str, "UTF-8");
			Log.v("coded username", encodedString);
		} catch (UnsupportedEncodingException e1) {
			Log.v("URLEoder", "UnsupportedEncodingException:" + e1.getMessage());
		}		
		return encodedString;
	}
	
	
	public String getCookie() {
		return cookie;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
