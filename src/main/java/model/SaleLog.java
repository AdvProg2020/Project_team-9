package model;

import controller.DataManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SaleLog extends Log {
    private String customer;
    private long offAmount;

    public SaleLog(Customer customer, LocalDateTime dateTime, long offAmount, ArrayList<Product> products, String id, long price, Seller seller, int discountAmount, DeliveryStatus deliveryStatus) {
        super(id, dateTime, price, discountAmount, products, deliveryStatus);
        this.customer = customer.getUsername();
        this.offAmount = offAmount;
    }

    public Customer getCustomer() {
        return (Customer) DataManager.shared().getAccountWithGivenUsername(customer);
    }

    public long getOffAmount() {
        return offAmount;
    }
}
