package model;

import java.util.ArrayList;

public class Seller extends Account {
    private String companyDetails;
    private boolean hasBeenConfirmedByAdminToBeSeller = false;
    private ArrayList<Product> products;
    private ArrayList<Sale> sales;
}
