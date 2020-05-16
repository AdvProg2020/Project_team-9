package model;

import java.util.ArrayList;

public class Customer extends Account {
    private Cart cart;

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

    public Cart getCart() {
        return cart;
    }

    public void addProductToCart(Product product) {
        cart.addProduct(product);
    }

    public boolean hasPurchasedProduct(Product product) {
        return cart.containsProduct(product);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer))
            return false;
        return super.equals(obj);
    }
}
