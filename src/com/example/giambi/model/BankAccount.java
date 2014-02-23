package com.example.giambi.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONObject;

import com.example.giambi.GiambiHttpClient;
import com.example.giambi.util.RegisterException;
import com.example.giambi.util.Util;

import android.util.Log;

/**
 * Bank account class for each login account.
 * @author cwl
 *
 */
public final class BankAccount {

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
    public String add(List<BankAccount> list) throws RegisterException {
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
                list.add(this);
                return content;
                //return true;
//              throw new RegisterException(content);
            }
    }

    public boolean get(List<BankAccount> list) {
        list.get(list.indexOf(this));
        return true;
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
