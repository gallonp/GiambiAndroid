package com.example.giambi.model;

import android.util.Log;
import com.example.giambi.GiambiHttpClient;
import com.example.giambi.util.CreateAccountException;
import com.example.giambi.util.GetAccountException;
import com.example.giambi.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Bank account class for each login account.
 *
 * @author cwl
 */
public final class BankAccount {

    private static final String[] FIELDS = {"bankAccountName",
            "bankAccountNumber", "bankName", "balance"};

    private String alias;
    private String bankName;
    private BigDecimal balance;
    private String accountNum;
    private String loginAcc;

    public BankAccount(String loginAcc, String alias, String bankName,
                       String accountNum, String balance) {
        this.loginAcc = loginAcc;
        setAlias(alias);
        setBankName(bankName);
        setAccountNum(accountNum);
        setAccountNum(accountNum);
        setBalance(new BigDecimal(balance));
    }

    //
    // public boolean add(List<BankAccount> list) {
    // list.add(this);
    // return true;
    // }

    @SuppressWarnings("unchecked")
    public String addToServer() throws CreateAccountException {
        String encodedLoginAcc = Util.encodeString(loginAcc);
        String encodedAlias = Util.encodeString(alias);
        String encodedBankName = Util.encodeString(bankName);
        String encodedBalance = Util.encodeString(balance.toString());
        String encodedAccNum = Util.encodeString(accountNum);
        HttpPost request = new HttpPost("http://" + Util.LOCALHOST
                + ":8888/createaccount");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userAccount", encodedLoginAcc);
        jsonObj.put("bankAccountName", encodedAlias);
        jsonObj.put("bankName", encodedBankName);
        jsonObj.put("balance", encodedBalance);
        jsonObj.put("bankAccountNumber", encodedAccNum);
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
            throw new CreateAccountException("Unknown Error");
        } else {
            return content;
        }
    }

    /**
     * Get Bank Accounts from server.
     *
     * @param loginAcc
     * @param list
     * @return -2 if cookie expired; -1 if an exception exists; 0 if complete
     * normally
     * @throws GetAccountException
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static int getAccounts(String loginAcc, List<BankAccount> list)
            throws GetAccountException {
        String encodedLoginAcc = Util.encodeString(loginAcc);

        HttpPost request = new HttpPost("http://" + Util.LOCALHOST
                + ":8888/getaccount");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userAccount", encodedLoginAcc);

        request.setEntity(Util.jsonToEntity(jsonObj));

        HttpResponse response = GiambiHttpClient.getResponse(request);
        String responseCookie = "";// response.getHeaders("Cookie")[0].getValue();
        String content = "";

        try {
            content = Util.HttpContentReader(response.getEntity().getContent());
            System.out.println(content);
        } catch (IllegalStateException e) {
            Log.e("IllegalStateException", e.getMessage());
            return -1;
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
            return -1;
        }
        if (content == null) {
            throw new GetAccountException("Unknown Error");
        } else if (content == "invalid cookie") {
            return -2;
        }
        JSONArray jsonArr = null;
        JSONParser jsonParser = new JSONParser();
        try {
            jsonArr = (JSONArray) jsonParser.parse(content);
        } catch (ParseException e) {
            Log.i("onJSONArrayCreate", "Error on casting");
            return -1;
        }

        // Add accounts to bankAccounts list
        list.clear();
        if (content.equals("No accounts."))
            return 0;
        if (jsonArr.size() != 0) {
            for (int i = 0; i < jsonArr.size(); ++i) {
                Map<String, String> accountInfo = (Map<String, String>) jsonArr
                        .get(i);
                BankAccount newAccount = new BankAccount(loginAcc,
                        accountInfo.get(FIELDS[0]), accountInfo.get(FIELDS[2]),
                        accountInfo.get(FIELDS[1]), accountInfo.get(FIELDS[3]));
                list.add(newAccount);
            }
            return 0;
        }
        return -1;
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
