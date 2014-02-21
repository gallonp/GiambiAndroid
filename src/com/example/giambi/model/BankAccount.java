package com.example.giambi.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * Bank account class for each login account.
 * @author cwl
 *
 */
public class BankAccount {

    private String alias;
    private String bankName;
    private BigDecimal balance;
    private String accountNum;

    public BankAccount(String alias, String bankName, String accountNum, String balance) {
        super();
        this.setAlias(alias);
        this.setBankName(bankName);
        this.setAccountNum(accountNum);
        if (isNumeric(accountNum)) {
            this.setAccountNum(accountNum);
        } else {
            Log.e("omBankAccountCreate", "Invalid Account Number");
        }
        this.setBalance(new BigDecimal(balance));
    }

    public boolean add(List<BankAccount> list) {
        list.add(this);
        return true;
    }

    public boolean get(List<BankAccount> list) {
        list.get(list.indexOf(this));
        return true;
    }

    public boolean del(List<BankAccount> list) {
        list.remove(this);
        return true;
    }

    private static boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("[0-9]*"); 
        return pattern.matcher(str).matches();    
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
