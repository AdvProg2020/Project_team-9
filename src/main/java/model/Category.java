package model;

import java.util.ArrayList;

public class Category {
    private String id;
    private String name;
    private String description;
    private ArrayList<Category> subCategories;
    private Category parentCategory;
    private ArrayList<Product> products;

    public Category(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public ArrayList<Product> getAllProductsInCategories() {
        return products;
    }

    public void addSubcategory(Category subcategory) {

    }

    public void addProduct(Product product) {

    }
}
