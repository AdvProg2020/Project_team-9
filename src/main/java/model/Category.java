package model;

import java.util.ArrayList;

public class Category {
    private int id;
    private String name;
    private String description;
    private ArrayList<Category> subCategories;
    private ArrayList<Product> products;
}
