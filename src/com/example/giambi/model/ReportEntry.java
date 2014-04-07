package com.example.giambi.model;

/**
 * Created by cwl on 14-3-25.
 */
public class ReportEntry {
    /**
     * id.
     */
    private int id;
    /**
     * category.
     */
    private String category;
    /**
     * amount.
     */
    private String amount;

    /**
     * constructor.
     * 
     * @param id1
     *            id
     * @param category1
     *            category
     * @param amount1
     *            amount
     */
    public ReportEntry(int id1, String category1, String amount1) {
        this.id = id1;
        this.category = category1;
        this.amount = amount1;
    }

    /**
     * get category.
     * 
     * @return category
     */
    public final String getCategory() {
        return category;
    }

    /**
     * set category.
     * 
     * @param category1
     *            category
     */
    public final void setCategory(String category1) {
        this.category = category1;
    }

    /**
     * get amount.
     * 
     * @return amount
     */
    public final String getAmount() {
        return amount;
    }

    /**
     * set amount.
     * 
     * @param amount1
     *            amount
     */
    public final void setAmount(String amount1) {
        this.amount = amount1;
    }

    /**
     * get id.
     * 
     * @return id
     */
    public final int getId() {
        return id;
    }

    /**
     * set id.
     * 
     * @param id1
     *            id
     */
    public final void setId(int id1) {
        this.id = id1;
    }
}
