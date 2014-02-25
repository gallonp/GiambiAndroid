package com.example.giambi.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.giambi.GiambiHttpClient;
import com.example.giambi.util.AuthenticateException;
import com.example.giambi.util.RegisterException;
import com.example.giambi.util.Util;

import android.util.Log;

/**
 * Bank account class for each login account.
 * @author cwl
 *
 */
public final class BankAccount {

    private static final String[] FIELDS = {"bankAccountName", "bankAccountNumber",
        "bankName", "balance"};

    private String alias;
    private String bankName;
    private BigDecimal balance;
    private String accountNum;
    private LoginAccount loginAcc;
    private String cookie;

    public BankAccount(LoginAccount loginAcc, String alias, String bankName, String accountNum, String balance) {
        this.loginAcc = loginAcc;
        this.cookie = loginAcc.getCookie();
        setAlias(alias);
        setBankName(bankName);
        setAccountNum(accountNum);
        setAccountNum(accountNum);
        setBalance(new BigDecimal(balance));
    }
//
//    public boolean add(List<BankAccount> list) {
//        list.add(this);
//        return true;
//    }

    @SuppressWarnings("unchecked")
    public String addToServer() throws RegisterException {
        String encodedLoginAcc = Util.encodeString(loginAcc.getUsername());
        String encodedAlias = Util.encodeString(alias);
        String encodedBankName = Util.encodeString(bankName);
        String encodedBalance = Util.encodeString(balance.toString());
        String encodedAccNum = Util.encodeString(accountNum);
//      HttpPost request = new HttpPost(
//              "http://giambi-server-2340.appspot.com/register");
        HttpPost request = new HttpPost(
                "http://10.0.2.2:8888/createaccount");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userAccount", encodedLoginAcc);
        jsonObj.put("bankAccountName", encodedAlias);
        jsonObj.put("bankName", encodedBankName);
        jsonObj.put("balance", encodedBalance);
        jsonObj.put("bankAccountNumber", encodedAccNum);
        request.setEntity(Util.jsonToEntity(jsonObj));
        HttpResponse response = GiambiHttpClient.getResponse(request);
        String responseCookie = "";//response.getHeaders("Cookie")[0].getValue();
        String content = "";
//      if (responseCookie = "") {
            this.cookie = responseCookie;
//          return true;
//      } else {
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
//                list.add(this);
                return content;
                //return true;
//              throw new RegisterException(content);
            }
    }

    @SuppressWarnings("unchecked")
    public static void getAccouts(LoginAccount loginAcc, List<BankAccount> list)  throws AuthenticateException {
        String encodedLoginAcc = Util.encodeString(loginAcc.getUsername());
//        String encodedAlias = Util.encodeString(alias);
//        String encodedBankName = Util.encodeString(bankName);
//        String encodedBalance = Util.encodeString(balance.toString());
//        String encodedAccNum = Util.encodeString(accountNum);
//      HttpPost request = new HttpPost(
//              "http://giambi-server-2340.appspot.com/login");
        HttpPost request = new HttpPost(
                "http://10.0.2.2:8888/getaccount");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userAccount", encodedLoginAcc);
//        jsonObj.put("bankAccountName", encodedAlias);
//        jsonObj.put("bankName", encodedBankName);
//        jsonObj.put("balance", encodedBalance);
//        jsonObj.put("bankAccountNumber", encodedAccNum);
//        jsonObj.put("cookie", this.cookie);
        request.setEntity(Util.jsonToEntity(jsonObj));
        // JSONObject parsedObj = (JSONObject)
        // JSONValue.parse(request.getParams().getParameter("json").toString());
        // Log.v("request params",request.getParams().getParameter("json").toString());
        // Log.v("json parsed obj",(String)parsedObj.get("username"));
        // String content = "";
        HttpResponse response = GiambiHttpClient.getResponse(request);
        String responseCookie = "";//response.getHeaders("Cookie")[0].getValue();
        String content = "";
//      if (responseCookie != "") {
//          this.cookie = responseCookie;
//          
//      } else {
            try {
                content = Util.HttpContentReader(response.getEntity()
                        .getContent());
                System.out.println(content);
            } catch (IllegalStateException e) {
                Log.e("IllegalStateException", e.getMessage());
            } catch (IOException e) {
                Log.e("IOException", e.getMessage());
            }
            if (content == null) {
                throw new AuthenticateException("Unknown Error");
            }
            JSONArray jsonArr = null;
            JSONParser jPaser = new JSONParser();
            try {
                jsonArr = (JSONArray) jPaser.parse(content);
            } catch (ParseException e) {
                Log.i("onJSONArrayCreate", "Error on casting");
                System.out.println(e.toString());
//                System.out.println(e.getMessage());
            }

            // Add accounts to bankAccounts list
            list.clear();
            if (jsonArr.size() != 0) {
                for (int i = 0; i < jsonArr.size(); ++i) {
                    Map<String, String> accountInfo =
                            (Map<String, String>) jsonArr.get(i);
                    BankAccount newAccount = new BankAccount(loginAcc, accountInfo.get(FIELDS[0]),
                            accountInfo.get(FIELDS[2]), accountInfo.get(FIELDS[1]),
                            accountInfo.get(FIELDS[3]));
                    list.add(newAccount);
                }
//                System.out.println(jsonArr.get(0));
                System.out.println("list length: " + list.size());
            }
    }

    public boolean del(List<BankAccount> list) {
        list.remove(this);
        return true;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    } 
}
