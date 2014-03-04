package com.example.giambi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.example.giambi.util.InvalidArguementException;

public class Transaction {

    // Composite keys: name and amount
    public String name;
    public double amount;
    // username is the only foreign key

    public String username;

    // Extra info
    public String category;
    public Date createDate;
    public String merchant;
    public long accountNumber;
    // database unique key
    public Long id;

    public Transaction(String name, double amount,String username) 
             {

//        if (name.isEmpty() || username.isEmpty()) {
//            throw new InvalidArguementException(
//                    "At least one of the arguements is empty");
//        }
        this.name = name;
        this.amount = amount;
        this.username = username;
    }

    public void addExtraInfo(String category, Date createDate, String merchant,
                             long accountNumber) {
        this.category = category;
        this.createDate = createDate;
        this.merchant = merchant;
        this.accountNumber = accountNumber;
    }

    public static List<Transaction> getAccountTransactions(String username,
                                                           long accountNumber) {
        return null;
    }

    public static List<Transaction> getAllTransactions(String username) {
        return null;
    }

    public static void persistTransaction(Transaction transaction) {

    }

    
    public static Transaction t1 = new Transaction("Mc", 10 ,"giambi" );
    public static Transaction t2 = new Transaction("Publix", 150 ,"Tonny" );
    public static Transaction t3 = new Transaction("Subway", 5 ,"Jay" );
    
    private static Transaction[] dummyTransactions = new Transaction[]{t1,t2,t3};
    
    public static List<Transaction> transactions = new ArrayList<Transaction>(Arrays.asList(dummyTransactions));   

    
}
