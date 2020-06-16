package com.sasp.saspstore.model;

import com.sasp.saspstore.controller.DataManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Sale {
    private String offId;
    private ArrayList<String> products;
    private SaleStatus saleStatus;
    private int discountAmount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String seller;
    private DeliveryStatus deliveryStatus;

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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("حراج " + offId + "\n\n");
        result.append("محصولات:" + "\n");
        for (String product : products) {
            result.append(DataManager.shared().getProductWithId(product).getName()).append("\n");
        }
        result.append("میزان تخفیف: ").append(discountAmount).append("\n");
        result.append("زمان شروع حراج: ").append(startTime.format(DateTimeFormatter.BASIC_ISO_DATE)).append("\n");
        result.append("زمان پایان حراج: ").append(endTime.format(DateTimeFormatter.BASIC_ISO_DATE)).append("\n");
        result.append("فروشنده: ").append(DataManager.shared().getAccountWithGivenUsername(seller).getFirstName()).append(" ")
                .append(DataManager.shared().getAccountWithGivenUsername(seller).getLastName()).append("\n");
        return result.toString();
    }
}
