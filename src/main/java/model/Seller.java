package model;

import java.util.ArrayList;

public class Seller extends Account {
    private String companyDetails;
    private boolean hasBeenConfirmedByAdminToBeSeller = false;
    private ArrayList<Product> products;
    private ArrayList<Sale> sales;

    public Seller(String username, String password, String email, String phone, String firstName, String lastName, String companyDetails) {
        super(username, password, email, phone, firstName, lastName);
        this.companyDetails = companyDetails;
    }
}
