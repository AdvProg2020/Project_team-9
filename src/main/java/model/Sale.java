package model;

import java.util.ArrayList;

public class Sale {
    private int offId;
    private ArrayList<Product> products;
    private SaleStatus saleStatus;
    private int discountAmount;
    private int startTime;
    private int endTime;
    private Seller seller;
    private DeliveryStatus deliveryStatus;
}
