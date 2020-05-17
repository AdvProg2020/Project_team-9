package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PurchaseLog extends Log {

    public PurchaseLog(String id, LocalDateTime date, long price, int discountAmount, ArrayList<Product> products, DeliveryStatus deliveryStatus) {
        super(id, date, price, discountAmount, products, deliveryStatus);
    }

}
