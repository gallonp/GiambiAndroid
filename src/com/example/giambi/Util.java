package com.example.giambi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class Util {

	public static String HttpContentReader(InputStream input){
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(input));
		
		String inputLine="";
		StringBuffer response = new StringBuffer();
 
		try {
			Log.v("HttpReader","start reader");
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("HttpReader",e.toString());
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("HttpReader",e.toString());
		}
		return response.toString();
	}
}
