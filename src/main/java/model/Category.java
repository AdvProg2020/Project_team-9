package model;

import controller.DataManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Category {
    private String id;
    private String name;
    private String description;
    // TODO: By filtering by category, we don't consider subcats and parents!
    // TODO: All lists should have filter/search
    private String parentCategory;

    public Category(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // TODO: subCategories in intializer??
    public Category(String id, String name, String description, String parentCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
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

    public Category getParentCategory() {
        return DataManager.shared().getCategoryWithId(parentCategory);
    }

    @Override
    public String toString() {
        return "id: " + id + '\n' +
                "name: " + name + '\n';
    }
}
