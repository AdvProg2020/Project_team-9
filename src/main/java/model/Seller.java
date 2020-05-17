package model;

import java.util.ArrayList;

public class Seller extends Account {
    private String companyDetails;
    private boolean isPermittedToSell;
    private ArrayList<String> products;
    private ArrayList<String> sales;

    public Seller(String username, String password, String email, String phone, String firstName, String lastName, String companyDetails) {
        super(username, password, email, phone, firstName, lastName);
        this.companyDetails = companyDetails;
        products = new ArrayList<>();
        sales = new ArrayList<>();
        isPermittedToSell = false;
    }

    public Seller(Account account) {
        this(account.getUsername(), account.getPassword(), account.getEmail(), account.getPhoneNumber(), account.getFirstName(), account.getLastName(), "");
    }

    public Seller(Seller seller) {
        this(seller.getUsername(), seller.getPassword(), seller.getEmail(), seller.getPhoneNumber(), seller.getFirstName(), seller.getLastName(), seller.getCompanyDetails());
        products = new ArrayList<>();
        sales = new ArrayList<>();
        isPermittedToSell = false;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public void addProduct(Product product) {
        products.add(product.getProductId());
    }

    public void addSale(Sale sale) {
        sales.add(sale.getOffId());
    }

    @Override
    public String toString() {
        return super.toString() + '\n' +
                "Company Details: " + companyDetails;
    }
}
