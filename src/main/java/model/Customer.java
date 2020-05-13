package model;

import java.util.ArrayList;

public class Customer extends Account {
    private ArrayList<Product> cart;

    public Customer(String username, String password, String email, String phone, String firstName, String lastName) {
        this(username, password, email, phone, firstName, lastName, new ArrayList<>());
    }

    public Customer(String username, String password, String email, String phoneNumber, String firstName, String lastName, ArrayList<Product> cart) {
        super(username, password, email, phoneNumber, firstName, lastName);
        this.cart = cart;
    }

    public Customer(Account account) {
        this(account.getUsername(), account.getPassword(), account.getEmail(), account.getPhoneNumber(), account.getFirstName(), account.getLastName());
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void addProductToCart(Product product) {
        cart.add(product);
    }

    public boolean hasPurchasedProduct(Product product) {
        return cart.contains(product);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cart=" + cart +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer))
            return false;
        return super.equals(obj);
    }
}
