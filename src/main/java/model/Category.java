package model;

import java.util.ArrayList;

public class Category {
    private int id;
    private String name;
    private String description;
    private ArrayList<Category> subCategories;
    private ArrayList<Product> products;

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ArrayList<Product> getAllProductsInCategories() {
        return products;
    }

    public void addSubcategory(Category subcategory) {

    }

    public void addProduct(Product product) {

    }
}
