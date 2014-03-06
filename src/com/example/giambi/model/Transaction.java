package com.example.giambi.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.util.Log;

import com.example.giambi.GiambiHttpClient;
import com.example.giambi.util.InvalidArguementException;
import com.example.giambi.util.RegisterException;
import com.example.giambi.util.Util;

public class Transaction implements Serializable {

	// Composite keys: name and amount
	public String transactionName;
	public double amount;
	// username is the only foreign key

	public String username;

	// Extra info
	public String category;
	public Date createDate;
	public String merchant;
	public String accountNumber;
	// database unique key
	public Long id;

	public Transaction(String transactionName, double amount, String username) {

		// if (name.isEmpty() || username.isEmpty()) {
		// throw new InvalidArguementException(
		// "At least one of the arguements is empty");
		// }
		this.transactionName = transactionName;
		this.amount = amount;
		this.username = username;
	}

	public void addExtraInfo(String category, Date createDate, String merchant,
			String accountNumber) {
		this.category = category;
		this.createDate = createDate;
		this.merchant = merchant;
		this.accountNumber = accountNumber;
	}

	public static List<Transaction> getAccountTransactions(String username,
			String accountNumber) {
		HttpPost request = new HttpPost("http://10.0.3.2:8888/transaction");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("username", username);
		jsonObj.put("AccountNumber", accountNumber);
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
		JSONObject parsedObj = (JSONObject) JSONValue.parse(content);
		
		//some methods to decode the JSON object into list of transactions
		return null;
	}

	public static List<Transaction> getAllTransactions(String username) {

		return getAccountTransactions(username, null);
	}

	@SuppressWarnings("unchecked")
	public static void persistTransaction(Transaction transaction) {
		// Need to update the path
		HttpPost request = new HttpPost("http://10.0.3.2:8888/transaction");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("AccountNumber", transaction.accountNumber);
		jsonObj.put("Amount", transaction.amount);
		jsonObj.put("Category", transaction.category);
		jsonObj.put("CreateDate", transaction.createDate.toString());
		jsonObj.put("Merchant", transaction.merchant);
		jsonObj.put("TransactionName", transaction.transactionName);
		jsonObj.put("Username", transaction.username);
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
	}

	public static Transaction t1 = new Transaction("Mc", 10, "giambi");
	public static Transaction t2 = new Transaction("Publix", 150, "Tonny");
	public static Transaction t3 = new Transaction("Subway", 5, "Jay");

	private static Transaction[] dummyTransactions = new Transaction[] { t1,
			t2, t3 };

	public static List<Transaction> transactions = new ArrayList<Transaction>(
			Arrays.asList(dummyTransactions));

	@Override
	public String toString() {

		return accountNumber;
	}

}
