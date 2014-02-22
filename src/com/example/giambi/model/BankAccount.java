package com.example.giambi.model;

import java.math.BigDecimal;
import java.util.List;
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

    public BankAccount(String alias, String bankName, String accountNum, String balance) {
        setAlias(alias);
        setBankName(bankName);
        setAccountNum(accountNum);
        setAccountNum(accountNum);
        setBalance(new BigDecimal(balance));
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
