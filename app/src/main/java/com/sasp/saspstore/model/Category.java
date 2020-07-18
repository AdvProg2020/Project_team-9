package com.sasp.saspstore.model;

import com.sasp.saspstore.controller.DataManager;

import java.util.ArrayList;

public class Category {
    private String id;
    private String name;
    private String description;
    // TODO: All lists should have filter/search
    private String parentCategory;
    private ArrayList<String> uniqueFeatures;

    public ArrayList<String> getUniqueFeatures() {
        return uniqueFeatures;
    }

    public void setUniqueFeatures(ArrayList<String> uniqueFeatures) {
        this.uniqueFeatures = uniqueFeatures;
    }

    public Category(String id, String name, String description, String parentCategory, ArrayList<String> uniqueFeatures) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        this.uniqueFeatures = uniqueFeatures;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        DataManager.shared().syncCategories();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        DataManager.shared().syncCategories();
    }

    public Category getParentCategory() {
        return DataManager.shared().getCategoryWithId(parentCategory);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(name + "\n" + "ویژگی‌ها: ");
        for (String uniqueFeature : getUniqueFeatures()) {
            stringBuilder.append("\n").append(uniqueFeature);
        }
        return stringBuilder.toString();
    }
}
