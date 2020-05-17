package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PurchaseLog extends Log {

    public PurchaseLog(String id, LocalDateTime date, long price, ArrayList<Product> products) {
        super(id, date, price, products);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
