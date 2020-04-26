package model;

import java.util.ArrayList;

public class Customer extends Account {
    //private int credit;
    private ArrayList<Product> cart;

    public Customer(String username, String password, String email, String phone, String firstName, String lastName) {
        super(username, password, email, phone, firstName, lastName);
    }

    public Product[] getCart(){}
    public void addProductToCart(Product product){}
    public boolean hasPurchasedProduct(Product product){}

    @Override
    public String toString() {
        return "Customer{" +
                "cart=" + cart +
                '}';
    }
}
