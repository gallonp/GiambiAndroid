package com.example.giambi.model;

import android.os.Parcel;
import android.util.Log;
import com.example.giambi.GiambiHttpClient;
import com.example.giambi.util.AuthenticateException;
import com.example.giambi.util.RegisterException;
import com.example.giambi.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONObject;

import java.io.IOException;

public class LoginAccount {

	private String username;
	private String password;

	public LoginAccount(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public LoginAccount(Parcel source) {
	    this.username = source.readString();
	    this.password = source.readString();
	}

	@SuppressWarnings("unchecked")
	public String register() throws RegisterException {
		String encodedUsername = Util.encodeString(username);
		String encodedPassword = Util.encodeString(password);
//		HttpPost request = new HttpPost(
//				"http://giambi-server-2340.appspot.com/register");
		HttpPost request = new HttpPost(
                "http://" + Util.LOCALHOST + ":8888/register");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("username", encodedUsername);
		jsonObj.put("password", encodedPassword);
		request.setEntity(Util.jsonToEntity(jsonObj));
		HttpResponse response = GiambiHttpClient.getResponse(request);
		String content = "";
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
	

	
	@SuppressWarnings({ "unchecked", "unused" })
	public String authenticate() throws AuthenticateException {
		String encodedUsername = Util.encodeString(username);
		String encodedPassword = Util.encodeString(password);
//		HttpPost request = new HttpPost(
//				"http://giambi-server-2340.appspot.com/login");
		HttpPost request = new HttpPost(
                "http://" + Util.LOCALHOST + ":8888/login");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("username", encodedUsername);
		jsonObj.put("password", encodedPassword);
		request.setEntity(Util.jsonToEntity(jsonObj));
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
			}
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
