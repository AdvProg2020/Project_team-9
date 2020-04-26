package model;

import java.time.LocalDateTime;

public class PurchaseLog extends Log{

    public PurchaseLog(int id, LocalDateTime date, long price, int discountAmount, Product[] products, DeliveryStatus deliveryStatus) {
        super(id, date, price, discountAmount, products, deliveryStatus);
    }

}
