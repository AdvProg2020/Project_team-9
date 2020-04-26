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

    public Sale(int offId, ArrayList<Product> products, SaleStatus saleStatus, int discountAmount, int startTime, int endTime, Seller seller, DeliveryStatus deliveryStatus) {
        this.offId = offId;
        this.products = products;
        this.saleStatus = saleStatus;
        this.discountAmount = discountAmount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seller = seller;
        this.deliveryStatus = deliveryStatus;
    }
    public Sale newSale(Sale oldSale){
    }
    public void addProduct(Product product){}
}
