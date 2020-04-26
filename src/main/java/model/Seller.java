package model;

import java.util.ArrayList;

public class Seller extends Account {
    private String companyDetails;
    private boolean isPermittedToSell;
    private ArrayList<Product> products;
    private ArrayList<Sale> sales;

    public Seller(String username, String password, String email, String phone, String firstName, String lastName, String companyDetails) {
        super(username, password, email, phone, firstName, lastName);
        this.companyDetails = companyDetails;
    }
    public void addProduct(Product product){}
    public void addSale(Sale sale){}

    @Override
    public String toString() {
        return "Seller{" +
                "companyDetails='" + companyDetails + '\'' +
                ", isPermittedToSell=" + isPermittedToSell +
                ", products=" + products +
                ", sales=" + sales +
                '}';
    }
}
