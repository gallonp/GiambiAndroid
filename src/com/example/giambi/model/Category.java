package com.example.giambi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Transaction categories.
 * @author zhangjialiang
 *
 */
public class Category {

    public List<String> categories = new ArrayList<String>();

    /**
     * make a new list of categories to populate fields. Deprecated.
     */
    public Category() {
        categories.add(Category.deposit);
        categories.add(Category.bookSupplies);
        categories.add(Category.cashATM);
        categories.add(Category.clothing);
        categories.add(Category.coffeeShop);
        categories.add(Category.creditCardPayment);
        categories.add(Category.fastfood);
        categories.add(Category.groceries);
        categories.add(Category.mobilePhone);
        categories.add(Category.paycheck);
        categories.add(Category.pharmacy);
        categories.add(Category.rentalCarTaxi);
        categories.add(Category.restaurants);
        categories.add(Category.shopping);
        categories.add(Category.bookSupplies);
        categories.add(Category.uncategorized);
    }

    public static final String uncategorized = "Uncategorized";
    public static final String deposit = "Deposit";
    public static final String rentalCarTaxi = "Rental Car & Taxi";
    public static final String groceries = "Groceries";
    public static final String shopping = "shopping";
    public static final String clothing = "Clothing";
    public static final String creditCardPayment = "Credit Card Payment";
    public static final String fastfood = "fastfood";
    public static final String cashATM = "Cash & ATM";
    public static final String pharmacy = "Pharmacy";
    public static final String restaurants = "Restaurants";
    public static final String bookSupplies = "Book and Supplies";
    public static final String paycheck = "PayCheck";
    public static final String mobilePhone = "Mobile Phone";
    public static final String coffeeShop = "Coffee Shop";

}
