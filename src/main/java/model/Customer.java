package model;

import java.util.ArrayList;

public class Customer extends Account {
    //private int credit;
    private ArrayList<Product> cart;

    public Customer(String username, String password, String email, String phone, String firstName, String lastName) {
        super(username, password, email, phone, firstName, lastName);
    }

    public Customer(Account account) {
        this(account.getUsername(), account.getPassword(), account.getEmail(), account.getPhoneNumber(), account.getFirstName(), account.getLastName());
    }

    public Product[] getCart() {
        return null;
    }

    public void addProductToCart(Product product) {
    }

    public boolean hasPurchasedProduct(Product product) {
        return false;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cart=" + cart +
                '}';
    }
}
