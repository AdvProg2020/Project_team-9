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
}
