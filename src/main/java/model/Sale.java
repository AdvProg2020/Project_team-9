package model;

import java.util.ArrayList;

public class Sale {
    private String offId;
    private ArrayList<Product> products;
    private SaleStatus saleStatus;
    private int discountAmount;
    private int startTime;
    private int endTime;
    private Seller seller;
    private DeliveryStatus deliveryStatus;

    public Sale(String offId, ArrayList<Product> products, SaleStatus saleStatus, int discountAmount, int startTime, int endTime, Seller seller, DeliveryStatus deliveryStatus) {
        this.offId = offId;
        this.products = products;
        this.saleStatus = saleStatus;
        this.discountAmount = discountAmount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seller = seller;
        this.deliveryStatus = deliveryStatus;
    }

    public String getOffId() {
        return offId;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public Seller getSeller() {
        return seller;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
