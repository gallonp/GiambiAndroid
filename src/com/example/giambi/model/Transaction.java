package com.example.giambi.model;

import android.util.Log;
import com.example.giambi.GiambiHttpClient;
import com.example.giambi.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
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
		List<Transaction> transactions = new LinkedList<Transaction>();
		HttpGet request = new HttpGet("http://10.0.3.2:8888/transactions");
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter("username", username);
		Log.v("username params", username);
		httpParams.setParameter("accountNumber", accountNumber);
		Log.v("accountNumber params: ", accountNumber);
		request.setParams(httpParams);
		HttpResponse response = GiambiHttpClient.getResponse(request);
		String content = "";
		try {
			content = Util.HttpContentReader(response.getEntity().getContent());
		} catch (IllegalStateException e) {
			Log.e("IllegalStateException", e.getMessage());
		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
//		Log.v("Get transactions content",content);
		JSONArray jsonArr = null;
		JSONParser jPaser = new JSONParser();
		try {
			JSONObject jSONObj = (JSONObject) jPaser.parse(content);
//			Log.v("jSONObj", jSONObj.get("data").toString());
			if (jSONObj.get("data").toString().equals("[]")){
				return transactions;
			} else {
//				Log.v("JSONArr to parse",content);
				jsonArr = (JSONArray) jSONObj.get("data");
			}
		} catch (ParseException e) {
			Log.i("onJSONArrayCreate", "Error on casting");
			// throws exceptions;
			return transactions;
		}
//		Log.v("JsonArr size",Integer.toString(jsonArr.size()));
//		Log.v("JsonArr string", jsonArr.toJSONString());
		if (jsonArr.size() != 0) {
			for (int i = 0; i < jsonArr.size(); ++i) {
				@SuppressWarnings("unchecked")
				Map<String, String> transactionMap = (Map<String, String>) jsonArr
						.get(i);
				Transaction newTransaction = new Transaction(
						transactionMap.get("transactionName"),
						Double.parseDouble(transactionMap.get("amount")),
						transactionMap.get("username"));
//				Log.v("transactionMap.get(\"name\")=",transactionMap.get("name"));
				newTransaction.id = Long.parseLong(transactionMap.get("name"));
//				Log.v("newTransaction id",newTransaction.id+"");
				newTransaction.addExtraInfo(transactionMap.get("category"),
						Util.stringToDate(transactionMap.get("createDate")),
						transactionMap.get("merchant"),
						transactionMap.get("accountNumber"));
//				Log.v("Adding transaction","added one!");
				transactions.add(newTransaction);
			}
		}

		// some methods to decode the JSON object into list of transactions
		return transactions;
	}

	public static List<Transaction> getAllTransactions(String username) {

		return getAccountTransactions(username, null);
	}

	@SuppressWarnings("unchecked")
	public static void persistTransaction(Transaction transaction) {
		// Need to update the path

		// if KeyId exist, update. If not, create

		HttpPost request = new HttpPost("http://10.0.3.2:8888/transactions");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("accountNumber", transaction.accountNumber);
		jsonObj.put("amount", transaction.amount);
		jsonObj.put("category", transaction.category);
		jsonObj.put("id", transaction.id);
		Log.v("transaction id passed to doPost", transaction.id+"");
		if (transaction.createDate!= null){
			jsonObj.put("createDate", transaction.createDate.toString());
		}
		jsonObj.put("merchant", transaction.merchant);
		jsonObj.put("transactionName", transaction.transactionName);
		jsonObj.put("username", transaction.username);
//		Log.v("transaction username passed to doPost", transaction.username+"");
		
		Log.v("username sent via json", transaction.username);
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

    public long getId() {
        return this.id;
    }

}
