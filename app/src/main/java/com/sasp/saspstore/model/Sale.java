package com.sasp.saspstore.model;

import com.sasp.saspstore.controller.DataManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Sale {
    private String offId;
    private ArrayList<String> products;
    // TODO: enums in Gson??
    private SaleStatus saleStatus;
    private int discountAmount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String seller;
    // TODO: enums in Gson??
    private DeliveryStatus deliveryStatus;

    // TODO: Where do we add Sales??
    public Sale(String offId, ArrayList<Product> products, SaleStatus saleStatus, int discountAmount, LocalDateTime startTime, LocalDateTime endTime, Seller seller, DeliveryStatus deliveryStatus) {
        this.offId = offId;
        this.products =  products.stream().map(Product::getProductId).collect(Collectors.toCollection(ArrayList::new));
        this.saleStatus = saleStatus;
        this.discountAmount = discountAmount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seller = seller.getUsername();
        this.deliveryStatus = deliveryStatus;
    }

    public String getOffId() {
        return offId;
    }

    public ArrayList<Product> getProducts() {
        return products.stream().map(id -> DataManager.shared().getProductWithId(id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Seller getSeller() {
        return (Seller) DataManager.shared().getAccountWithGivenUsername(seller);
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void addProduct(Product product) {
        products.add(product.getProductId());
        DataManager.saveData();
    }
}
