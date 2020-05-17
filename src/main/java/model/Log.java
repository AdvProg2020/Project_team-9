package model;

import controller.DataManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Log {
    private String id;
    // TODO: Date in Gson??
    private LocalDateTime date;
    private long price;
    private int discountAmount;
    private ArrayList<String> products;
    // TODO: Enum in Gson??
    private DeliveryStatus deliveryStatus;

    public String getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public long getPrice() {
        return price;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public ArrayList<Product> getProducts() {
        return products.stream().map(id -> DataManager.shared().getProductWithId(id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public Log(String id, LocalDateTime date, long price, int discountAmount, ArrayList<Product> products, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.discountAmount = discountAmount;
        this.products = products.stream().map(Product::getProductId).collect(Collectors.toCollection(ArrayList::new));
        this.deliveryStatus = deliveryStatus;
    }
}
