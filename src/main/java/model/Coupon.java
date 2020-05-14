package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Coupon {
    private int id;
    private ArrayList<Product> products;
    private Status saleStatus;
    private int discountPercent;
    private int maximumDiscount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Account[] accountsPermittedToUseThisCoupon;
    private HashMap<Account, Integer> remainingUsagesCount;

    public Coupon(int id, ArrayList<Product> products) {
        this.id = id;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Status getSaleStatus() {
        return saleStatus;
    }

    public int getMaximumDiscount() {
        return maximumDiscount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Account[] getAccountsPermittedToUseThisCoupon() {
        return accountsPermittedToUseThisCoupon;
    }

    public HashMap<Account, Integer> getRemainingUsagesCount() {
        return remainingUsagesCount;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setMaximumDiscount(int maximumDiscount) {
        this.maximumDiscount = maximumDiscount;
    }
}
