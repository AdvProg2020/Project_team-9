package model;

import controller.DataManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Coupon {
    private String id;
    private ArrayList<String> products;
    // TODO: enums in Gson...
    private Status saleStatus;
    private int discountPercent;
    private int maximumDiscount;
    // TODO: LocalDateTime in Gson???
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private HashMap<String, Integer> remainingUsagesCount;

    public Coupon(String id, ArrayList<Product> products) {
        this.id = id;
        this.products = products.stream().map(Product::getProductId).collect(Collectors.toCollection(ArrayList::new));
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
        remainingUsagesCount.put(account.getUsername(), Math.max(remainingUsagesCount.get(account.getUsername()) - 1, 0));
        DataManager.saveData();
    }

    public int remainingUsageCountForAccount(Account account) {
        return remainingUsagesCount.get(account.getUsername());
    }
}
