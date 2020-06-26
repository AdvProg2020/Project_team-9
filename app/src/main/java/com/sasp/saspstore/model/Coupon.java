package com.sasp.saspstore.model;

import com.sasp.saspstore.controller.DataManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Coupon {
    private String id;
    private ArrayList<String> products;
    // TODO: enums in Gson...
    // TODO: The next one is not being used...
    private Status saleStatus;
    private int discountPercent;
    private int maximumDiscount;
    // TODO: LocalDateTime in Gson???
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private HashMap<String, Integer> remainingUsagesCount;

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public HashMap<String, Integer> getRemainingUsagesCount() {
        return remainingUsagesCount;
    }

    public Coupon(String id, ArrayList<Product> products) {
        this.id = id;
        this.products = products.stream().map(Product::getProductId).collect(Collectors.toCollection(ArrayList::new));
    }

    public Coupon(String id, ArrayList<Product> products, Status saleStatus, int discountPercent, int maximumDiscount, LocalDateTime startTime, LocalDateTime endTime, HashMap<String, Integer> remainingUsagesCount) {
        this.id = id;
        this.products = products.stream().map(Product::getProductId).collect(Collectors.toCollection(ArrayList::new));
        this.saleStatus = saleStatus;
        this.discountPercent = discountPercent;
        this.maximumDiscount = maximumDiscount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remainingUsagesCount = remainingUsagesCount;
    }

    public String getId() {
        return id;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
        DataManager.saveData();
    }

    public ArrayList<Product> getProducts() {
        return products.stream().map(id -> DataManager.shared().getProductWithId(id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public Status getSaleStatus() {
        return saleStatus;
    }

    public int getMaximumDiscount() {
        return maximumDiscount;
    }

    public void setMaximumDiscount(int maximumDiscount) {
        this.maximumDiscount = maximumDiscount;
        DataManager.saveData();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void decrementRemainingUsagesCountForAccount(Account account) {
        if (remainingUsagesCount == null) return;
        remainingUsagesCount.put(account.getUsername(), Math.max(remainingUsagesCount.get(account.getUsername()) - 1, 0));
        DataManager.saveData();
    }

    public int remainingUsageCountForAccount(Account account) {
        return remainingUsagesCount.get(account.getUsername());
    }

    @Override
    public String toString() {
        return id;
    }
}
