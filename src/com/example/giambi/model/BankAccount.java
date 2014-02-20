package com.example.giambi.model;

/**
 * Bank account class for each login account.
 * @author cwl
 *
 */
public class BankAccount {

    private String bankName;
    private int accountNum;
    private String cookie;

    public BankAccount(String bankName, int accountNum) {
        super();
        this.bankName = bankName;
        this.accountNum = accountNum;
    }

    public String getBankName() {
        return bankName;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public String getCookie() {
        return cookie;
    }

}
