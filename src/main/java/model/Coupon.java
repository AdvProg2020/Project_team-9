package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Coupon {
    private String id;
    private ArrayList<Product> products;
    private Status saleStatus;
    private int discountPercent;
    private int maximumDiscount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Account[] accountsPermittedToUseThisCoupon;
    private HashMap<Account, Integer> remainingUsagesCount;

    public Coupon(String id, ArrayList<Product> products) {
        this.id = id;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
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

    public void setMaximumDiscount(int maximumDiscount) {
        this.maximumDiscount = maximumDiscount;
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
}
