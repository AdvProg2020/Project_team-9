package model;

import java.util.ArrayList;

public class Administrator extends Account {
    private ArrayList<Request> requests;
    private ArrayList<Coupon> allCoupons;
    private ArrayList<Account> allAccounts;
    private ArrayList<Category> allCategories;
    private ArrayList<Sale> sales;

    public Administrator(String username, String password, String email, String phone, String firstName, String lastName) {
        super(username, password, email, phone, firstName, lastName);
    }

    public ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "requests=" + requests +
                ", allCoupons=" + allCoupons +
                ", allAccounts=" + allAccounts +
                ", allCategories=" + allCategories +
                ", sales=" + sales +
                '}';
    }
}