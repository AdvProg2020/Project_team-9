package model;

import controller.DataManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PurchaseLog extends Log {

    private String customer;

    public PurchaseLog(String id, LocalDateTime date, long price, int discountAmount, ArrayList<Product> products, DeliveryStatus deliveryStatus, Customer customer) {
        super(id, date, price, discountAmount, products, deliveryStatus);
        this.customer = customer.getUsername();
    }

    public Customer getCustomer() {
        return (Customer) DataManager.shared().getAccountWithGivenUsername(customer);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer.getUsername();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
