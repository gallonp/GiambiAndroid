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

    /**
     * fields.
     */
    private static final String[] FIELDS = {"bankAccountName", "bankAccountNumber", "bankName", "balance"};

    /**
     * alias.
     */
    private String alias;
    /**
     * bank name.
     */
    private String bankName;
    /**
     * balance.
     */
    private BigDecimal balance;
    /**
     * account number.
     */
    private String accountNum;
    /**
     * login account.
     */
    private String loginAcc;

    /**
     * constructor.
     * 
     * @param loginAcc1
     *            login account
     * @param alias1
     *            alias
     * @param bankName1
     *            bank name
     * @param accountNum1
     *            account number
     * @param balance1
     *            balance
     */
    public BankAccount(String loginAcc1, String alias1, String bankName1,
            String accountNum1, String balance1) {
        this.loginAcc = loginAcc1;
        setAlias(alias1);
        setBankName(bankName1);
        setAccountNum(accountNum1);
        setAccountNum(accountNum1);
        setBalance(new BigDecimal(balance1));
    }

    /**
     * add to server.
     * 
     * @return content
     * @throws CreateAccountException
     *             error createing account
     */
    @SuppressWarnings("unchecked")
    public String addToServer() throws CreateAccountException {
        String encodedLoginAcc = Util.encodeString(loginAcc);
        String encodedAlias = Util.encodeString(alias);
        String encodedBankName = Util.encodeString(bankName);
        String encodedBalance = Util.encodeString(balance.toString());
        String encodedAccNum = Util.encodeString(accountNum);
        HttpPost request = new HttpPost("http://" + Util.LOCALHOST
                + "/createaccount");
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
     *            login account
     * @param list
     *            list of accounts
     * @return -2 if cookie expired; -1 if an exception exists; 0 if complete
     *         normally
     * @throws GetAccountException
     *             error getting accounts
     */
    @SuppressWarnings({ "unchecked", "unused" })
    public static int getAccounts(String loginAcc, List<BankAccount> list)
            throws GetAccountException {
        final int err2 = -2;
        String encodedLoginAcc = Util.encodeString(loginAcc);

        HttpPost request = new HttpPost("http://" + Util.LOCALHOST
                + "/getaccount");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userAccount", encodedLoginAcc);

        request.setEntity(Util.jsonToEntity(jsonObj));

        HttpResponse response = GiambiHttpClient.getResponse(request);
        String responseCookie = "";
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
            return err2;
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
        if (content.equals("No accounts.")) {
            return 0;
        }
        final int num = 4;
        if (jsonArr.size() != 0) {
            for (int i = 0; i < jsonArr.size(); ++i) {
                Map<String, String> accountInfo = (Map<String, String>) jsonArr
                        .get(i);
                BankAccount newAccount = new BankAccount(loginAcc,
                        accountInfo.get(FIELDS[0]), accountInfo.get(FIELDS[2]),
                        accountInfo.get(FIELDS[1]),
                        accountInfo.get(FIELDS[num - 1]));
                list.add(newAccount);
            }
            return 0;
        }
        return -1;
    }

    /**
     * delete account.
     * 
     * @param list
     *            list of accounts
     * @return true
     */
    public boolean del(List<BankAccount> list) {
        list.remove(this);
        return true;
    }

    /**
     * get alias.
     * 
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * set alias.
     * 
     * @param alias1
     *            alias
     */
    public void setAlias(String alias1) {
        this.alias = alias1;
    }

    /**
     * get balance.
     * 
     * @return balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * set balance.
     * 
     * @param balance1
     *            balance
     */
    public void setBalance(BigDecimal balance1) {
        this.balance = balance1;
    }

    /**
     * get bank name.
     * 
     * @return bank name
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * set bank name.
     * 
     * @param bankName1
     *            bank name
     */
    public void setBankName(String bankName1) {
        this.bankName = bankName1;
    }

    /**
     * get account number.
     * 
     * @return account number
     */
    public String getAccountNum() {
        return accountNum;
    }

    /**
     * set account number.
     * 
     * @param accountNum1
     *            account number
     */
    public void setAccountNum(String accountNum1) {
        this.accountNum = accountNum1;
    }
}
