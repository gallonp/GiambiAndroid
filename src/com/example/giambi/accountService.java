package com.example.giambi;


public class accountService {
	static private String cookie;
	
	static public void setCookie(String newCookie){
		cookie = newCookie;
	}
	
	static public String getCookie(){
		return cookie;
	}
}
