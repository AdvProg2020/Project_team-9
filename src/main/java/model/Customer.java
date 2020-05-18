package model;

import controller.DataManager;

import java.util.ArrayList;

public class Customer extends Account {
    // TODO: Does containing cart make error for Gson??
    private Cart cart;
    private String address = "";

    public Customer(String username, String password, String email, String phone, String firstName, String lastName) {
        this(username, password, email, phone, firstName, lastName, new Cart());
    }

    public Customer(String username, String password, String email, String phoneNumber, String firstName, String lastName, Cart cart) {
        super(username, password, email, phoneNumber, firstName, lastName);
        this.cart = cart;
    }

    public Customer(Account account) {
        this(account.getUsername(), account.getPassword(), account.getEmail(), account.getPhoneNumber(), account.getFirstName(), account.getLastName());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        DataManager.saveData();
    }

    public Cart getCart() {
        return cart;
    }

    public void addProductToCart(Product product) {
        cart.addProduct(product);
        DataManager.saveData();
    }

    public boolean hasPurchasedProduct(Product product) {
        return DataManager.shared().getAllLogs().stream()
                .anyMatch(log -> log instanceof PurchaseLog
                        && ((PurchaseLog) log).getCustomer().getUsername().equals(this.getUsername())
                        && log.getProducts().containsKey(product));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer))
            return false;
        return super.equals(obj);
    }
}
