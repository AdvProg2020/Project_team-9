package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Coupon {
    private int id;
    private ArrayList<Product> products;
    private Status saleStatus;
    private int discountPrice;
    private int maximumDiscount;
    private int startTime;
    private int endTime;
    private HashMap<Account, Integer> remainingUsagesCount;
}
