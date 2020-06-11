package com.sasp.saspstore.model;

import com.sasp.saspstore.controller.DataManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Seller extends Account {
    private String companyDetails;
    private boolean isPermittedToSell;

    public boolean isPermittedToSell() {
        return isPermittedToSell;
    }

    public void setPermittedToSell(boolean permittedToSell) {
        isPermittedToSell = permittedToSell;
    }

    public Seller(String username, String password, String email, String phone, String firstName, String lastName, String companyDetails) {
        super(username, password, email, phone, firstName, lastName);
        this.companyDetails = companyDetails;
        isPermittedToSell = false;
    }

    public Seller(Account account) {
        this(account.getUsername(), account.getPassword(), account.getEmail(), account.getPhoneNumber(), account.getFirstName(), account.getLastName(), "");
    }

    public Seller(Seller seller) {
        this(seller.getUsername(), seller.getPassword(), seller.getEmail(), seller.getPhoneNumber(), seller.getFirstName(), seller.getLastName(), seller.getCompanyDetails());
        isPermittedToSell = false;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public ArrayList<Product> getProducts() {
        return DataManager.shared().getAllProducts().stream().filter(product -> product.getSellers().contains(this)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Sale> getSales() {
        return DataManager.shared().getAllSales().stream().filter(sale -> sale.getSeller().equals(this)).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String toString() {
        return super.toString() + '\n' +
                "Company Details: " + companyDetails;
    }
}
