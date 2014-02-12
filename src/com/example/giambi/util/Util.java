package com.example.giambi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.giambi.model.LoginAccount;

import android.util.Log;

public class Util {
    public static final int USERNAME_EMPTY = 1;
    public static final int USERNAME_LENGTH = 2;
    public static final int USERNAME_NOT_EMAIL = 3;

    public static final int PASSWORD_EMPTY = 4;
    public static final int PASSWORD_LENGTH = 5;
    public static final int PASSWORD_EASY = 6;

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
        } else if (username.lastIndexOf("@") == -1
                || username.lastIndexOf("@") != username.indexOf("@")) {
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
            Log.d("password",""+Long.parseLong(password));
            return PASSWORD_EASY;
          //If the password can not be parsed to a long then it contains letters
        } catch (NumberFormatException e) { 
            Log.d("password", "NUMBER" + password);
        }
        return 0;
    }

    /**
     * @param username
     * @param password
     * @return
     */
    public static boolean checkLogin(LoginAccount loginAccount) {
        return (checkUserName(loginAccount.getUsername()) == 0) && (checkPassword(loginAccount.getPassword()) == 0);
    }
}
