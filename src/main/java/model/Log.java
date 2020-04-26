package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Log {
    private int id;
    private LocalDateTime date;
    private long price;
    private int discountAmount;
    private Product[] products;
    private DeliveryStatus deliveryStatus;

    public Log(int id, LocalDateTime date, long price, int discountAmount, Product[] products, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.date = date;
        this.price =  price;
        this.discountAmount = discountAmount;
        this.products = products;
        this.deliveryStatus = deliveryStatus;
    }
}
