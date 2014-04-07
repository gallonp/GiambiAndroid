package com.example.giambi.model;

import android.util.Log;
import com.example.giambi.GiambiHttpClient;
import com.example.giambi.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Transaction Data Access Obj.
 * @author zhangjialiang
 *
 */
@SuppressWarnings("serial")
public class Transaction implements Serializable {

    // Composite keys: name and amount
    public String transactionName;
    public double amount;
    // username is the only foreign key

    public String username;

    // Extra info
    public String category;
    public String createDate;
    public String merchant;
    public String accountNumber;
    // database unique key
    public Long id;

    /**
     * constructor with necessary info.
     * 
     * @param transactionName
     *            accountNumber
     * @param amount
     *            accountNumber
     * @param username
     *            username
     */
    public Transaction(String transactionName, double amount, String username) {
        this.transactionName = transactionName;
        this.amount = amount;
        this.username = username;
    }

    /**
     * add extra, unnecessary info.
     * 
     * @param category
     *            category
     * @param createDate
     *            createDate
     * @param merchant
     *            merchant
     * @param accountNumber
     *            accountNumber
     */
    public void addExtraInfo(String category, String createDate,
            String merchant, String accountNumber) {
        this.category = category;
        this.createDate = createDate;
        this.merchant = merchant;
        this.accountNumber = accountNumber;
    }

    /**
     * Get all transactions linked to a username with a specific bank account.
     * 
     * @param username
     *            username
     * @param accountNumber
     *            username
     * @return list of transactions
     */
    public static List<Transaction> getAccountTransactions(String username,
            String accountNumber) {
        List<Transaction> transactions = new LinkedList<Transaction>();
        StringBuffer uri = prepareUrlQuery(username, accountNumber);
        Log.e("url", uri.toString());
        HttpGet request = new HttpGet(uri.toString());
        HttpResponse response = GiambiHttpClient.getResponse(request);
        String content = "";
        try {
            content = Util.HttpContentReader(response.getEntity().getContent());
        } catch (IllegalStateException e) {
            Log.e("IllegalStateException", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        JSONArray jsonArr = null;
        JSONParser jPaser = new JSONParser();
        try {
            JSONObject jSONObj = (JSONObject) jPaser.parse(content);
            // System.out.println(content);
            if (jSONObj.get("data").toString().equals("[]")) {
                return transactions;
            } else {
                jsonArr = (JSONArray) jSONObj.get("data");
            }
        } catch (ParseException e) {
            Log.i("onJSONArrayCreate", "Error on casting");
            return transactions;
        }
        if (jsonArr.size() != 0) {
            for (int i = 0; i < jsonArr.size(); ++i) {
                @SuppressWarnings("unchecked")
                Map<String, String> transactionMap = (Map<String, String>) jsonArr
                        .get(i);
                Transaction newTransaction = new Transaction(
                        transactionMap.get("transactionName"),
                        Double.parseDouble(transactionMap.get("amount")),
                        transactionMap.get("username"));
                newTransaction.id = Long.parseLong(transactionMap.get("name"));
                newTransaction.addExtraInfo(transactionMap.get("category"),
                        transactionMap.get("createDate"),
                        transactionMap.get("merchant"),
                        transactionMap.get("accountNumber"));
                transactions.add(newTransaction);
            }
        }
        return transactions;
    }

    /**
     * method to prepare url with query.
     * 
     * @param username
     *            username
     * @param accountNumber
     *            accountNumber
     * @return url
     */
    private static StringBuffer prepareUrlQuery(String username,
            String accountNumber) {
        StringBuffer url = new StringBuffer();
        url.append("http://" + Util.LOCALHOST + "/transactions?");
        if (username != null && !username.isEmpty()) {
            url.append("username=" + username + "&");
        }
        if (accountNumber != null && !accountNumber.isEmpty()) {
            url.append("accountNumber=" + accountNumber);
        }
        if (url.charAt(url.length() - 1) == '&') {
            url.deleteCharAt(url.length() - 1);
        }
        return url;
    }

    /**
     * Get all transactions linked to a username across all bankAccount.
     * 
     * @param username
     *            username
     * @return all transactions
     */
    public static List<Transaction> getAllTransactions(String username) {

        return getAccountTransactions(username, null);
    }

    /**
     * persist Transaction into database.
     * 
     * @param transaction
     *            username
     */
    @SuppressWarnings("unchecked")
    public static void persistTransaction(Transaction transaction) {
        // Need to update the path
        // if KeyId exist, update. If not, create
        HttpPost request = new HttpPost("http://" + Util.LOCALHOST
                + "/transactions");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("accountNumber", transaction.accountNumber);
        jsonObj.put("amount", transaction.amount);
        jsonObj.put("category", transaction.category);
        jsonObj.put("id", transaction.id);
        Log.v("transaction id passed to doPost", transaction.id + "");
        if (transaction.createDate != null) {
            jsonObj.put("createDate", transaction.createDate.toString());
        }
        Log.v("create transaciton json", jsonObj.toJSONString());
        jsonObj.put("merchant", transaction.merchant);
        jsonObj.put("transactionName", transaction.transactionName);
        jsonObj.put("username", transaction.username);
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
        // still need to inform the caller of persistence result
    }

    @Override
    public String toString() {

        return accountNumber;
    }

    /**
     * transaction ID.
     * 
     * @return transaction ID
     */
    public long getId() {
        return this.id;
    }

}
