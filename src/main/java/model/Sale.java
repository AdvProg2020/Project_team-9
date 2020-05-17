package model;

import controller.DataManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Sale {
    private String offId;
    private ArrayList<String> products;
    // TODO: enums in Gson??
    private SaleStatus saleStatus;
    private int discountAmount;
    private int startTime;
    private int endTime;
    private String seller;
    // TODO: enums in Gson??
    private DeliveryStatus deliveryStatus;

    public Sale(String offId, ArrayList<Product> products, SaleStatus saleStatus, int discountAmount, int startTime, int endTime, Seller seller, DeliveryStatus deliveryStatus) {
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

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
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
