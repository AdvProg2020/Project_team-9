package model;

import controller.DataManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Category {
    private String id;
    private String name;
    private String description;
    private ArrayList<String> subCategories;
    // TODO: By filtering by category, we don't consider subcats and parents!
    // TODO: All lists should have filter/search
    private String parentCategory;
    private ArrayList<String> products;

    public Category(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // TODO: subCategories in intializer??
    public Category(String id, String name, String description, String parentCategory, ArrayList<Product> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        this.products = products.stream().map(Product::getProductId).collect(Collectors.toCollection(ArrayList::new));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        DataManager.saveData();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        DataManager.saveData();
    }

    public ArrayList<Product> getProducts() {
        return products.stream().map(id -> DataManager.shared().getProductWithId(id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories.stream().map(id -> DataManager.shared().getCategoryWithId(id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public Category getParentCategory() {
        return DataManager.shared().getCategoryWithId(parentCategory);
    }

    // TODO: Adding subcategories and products into a product????


    @Override
    public String toString() {
        return "id: " + id + '\n' +
                "name: " + name + '\n';
    }
}
