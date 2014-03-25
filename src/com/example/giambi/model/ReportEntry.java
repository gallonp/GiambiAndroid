package com.example.giambi.model;

/**
 * Created by cwl on 14-3-25.
 */
public class ReportEntry {
    private int id;
    private String category;
    private String amount;


    public ReportEntry(int id, String category, String amount) {
        this.id = id;
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
