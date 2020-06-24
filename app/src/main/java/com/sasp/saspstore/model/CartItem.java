package com.sasp.saspstore.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Seyyed Parsa Neshaei on 6/24/20
 * All Rights Reserved
 */
public class CartItem extends Product {

    private int count;

    public CartItem(String productId, Status status, String name, String brand, int price, int discountPercent, ArrayList<Seller> sellers, int numberAvailable, Category category, String description, LocalDateTime dateCreated, HashMap<String, String> features, int count) {
        super(productId, status, name, brand, price, discountPercent, sellers, numberAvailable, category, description, dateCreated, features);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}