package model;

import java.time.LocalDateTime;

public abstract class Log {
    private String id;
    private LocalDateTime date;
    private long price;
    private int discountAmount;
    private Product[] products;
    private DeliveryStatus deliveryStatus;

    public Log(String id, LocalDateTime date, long price, int discountAmount, Product[] products, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.discountAmount = discountAmount;
        this.products = products;
        this.deliveryStatus = deliveryStatus;
    }
}
