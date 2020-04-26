package model;

import java.time.LocalDateTime;

public class SaleLog extends Log {
    private Customer customer;
    private long offAmount;

    public SaleLog(Customer customer, LocalDateTime dateTime, long offAmount, Product[] products, int id, long price, Seller seller, int discountAmount, DeliveryStatus deliveryStatus) {
        super(id,dateTime,price,discountAmount,products,deliveryStatus);
        this.customer = customer;
        this.offAmount = offAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getOffAmount() {
        return offAmount;
    }
}
