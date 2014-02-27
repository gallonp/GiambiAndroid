package com.example.giambi;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class GiambiHttpClient {

    public static DefaultHttpClient httpClient = new DefaultHttpClient();
    public static CookieStore cookieStore = httpClient.getCookieStore();
    public static class AsynReader extends
            AsyncTask<HttpRequest, Void, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(HttpRequest... requests) {
            HttpRequest req = requests[0];
            HttpResponse resp;
            try {
            	for (Header header:req.getAllHeaders()){
            		Log.v("Headers",header.getName()+","+header.getValue());
            	}
            	for (Cookie cookie : cookieStore.getCookies()){
            		req.addHeader(cookie.getName(),cookie.getValue());
            		Log.v("cookie",cookie.getExpiryDate().toLocaleString());
            	}
            	for (Header header:req.getAllHeaders()){
            		Log.v("Headers after cookies added",header.getName()+","+header.getValue());
            	}
                resp = httpClient.execute((HttpUriRequest) req);
                Log.v("asynReader", "asynTaskComplete");
                
            } catch (ClientProtocolException e) {
                Log.e(this.toString(), e.toString());
                resp = null;
            } catch (IOException e) {
                Log.e(this.toString(), e.toString());
                resp = null;
            }
            return resp;
        }
    }

    public static HttpResponse getResponse(HttpRequest request) {
        AsynReader asynReader = new AsynReader();
        asynReader.execute(request);
        HttpResponse resp;
        Log.v("getResponse", "asynTaskComplete");
        try {
            resp = asynReader.get();
            Log.v("getResponse", "response got");
            return resp;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            Log.e("getResponse", e.toString());
            return null;
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            Log.e("getResponse", e.toString());
            return null;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            Log.e("getResponse", e.toString());
            return null;
        }

    }
}
