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

/**
 * LoginAccount Data Access Obj
 * @author zhangjialiang
 *
 */
public class LoginAccount {

    private String username;
    private String password;

    /**
     * Default creator.
     * @param username username
     * @param password password
     */
    public LoginAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Parse Parcel into loginAccount.
     * @param source source Parcel
     */
    public LoginAccount(Parcel source) {
        this.username = source.readString();
        this.password = source.readString();
    }

    /**
     * Register DAO.
     * @return Register Result
     * @throws RegisterException RegisterException
     */
    @SuppressWarnings("unchecked")
    public String register() throws RegisterException {
        String encodedUsername = Util.encodeString(username);
        String encodedPassword = Util.encodeString(password);
        HttpPost request = new HttpPost("http://" + Util.LOCALHOST
                + "/register");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("username", encodedUsername);
        jsonObj.put("password", encodedPassword);
        request.setEntity(Util.jsonToEntity(jsonObj));
        HttpResponse response = GiambiHttpClient.getResponse(request);
        String content = "";
        try {
            content = Util.HttpContentReader(response.getEntity().getContent());
        } catch (IllegalStateException e) {
            Log.e("IllegalStateException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        if (content == null) {
            throw new RegisterException("Unknown Error");
        } else {
            return content;
        }

        // }
    }

    /**
     * authenticate user.
     * @return authenticate Result
     * @throws AuthenticateException AuthenticateException
     */
    @SuppressWarnings({"unchecked", "unused" })
    public String authenticate() throws AuthenticateException {
        String encodedUsername = Util.encodeString(username);
        String encodedPassword = Util.encodeString(password);
        HttpPost request = new HttpPost("http://" + Util.LOCALHOST + "/login");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("username", encodedUsername);
        jsonObj.put("password", encodedPassword);
        request.setEntity(Util.jsonToEntity(jsonObj));
        HttpResponse response = GiambiHttpClient.getResponse(request);
        String responseCookie = ""; // response.getHeaders("Cookie")[0].getValue();
        String content = "";
        try {
            content = Util.HttpContentReader(response.getEntity().getContent());
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

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

}
