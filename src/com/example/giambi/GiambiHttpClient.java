package com.example.giambi;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class GiambiHttpClient {

    public static HttpClient httpClient = new DefaultHttpClient();

    public static class AsynReader extends
            AsyncTask<HttpRequest, Void, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(HttpRequest... requests) {
            HttpRequest req = requests[0];
            HttpResponse resp;
            try {
                resp = httpClient.execute((HttpUriRequest) req);
                Log.v("asynReader", "asynTaskComplete");
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                Log.e(this.toString(), e.toString());
                resp = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
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
