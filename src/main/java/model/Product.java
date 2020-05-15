package model;

import java.util.ArrayList;

public class Product {
    private int productId;
    private Status status;
    private String name;
    private String brand;
    private int price;
    private int visitCount = 0;
    private ArrayList<Seller> sellers;
    private ArrayList<Customer> customers;
    private int numberAvailable;
    private Category category;
    private String description;
    private ArrayList<Comment> comments;
    private ArrayList<Score> scores;

    public Product(Status status, String name, String brand, int price, ArrayList<Seller> sellers, int numberAvailable, Category category, String description) {
        this.status = status;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.sellers = sellers;
        this.numberAvailable = numberAvailable;
        this.category = category;
        this.description = description;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public Category getCategory() {
        return category;
    }

    public void incrementVisitCount() {
        visitCount += 1;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Product newProduct(Product oldProduct) {
        return null;
    }

    public int getAverageScore() {
        return 0;
    }

    public void addSeller(Seller seller) {

    }

    public void addCustomer(Customer customer) {

    }

    public void addComment(Comment comment) {

    }

    public void addScore(Score score) {

    }
}
