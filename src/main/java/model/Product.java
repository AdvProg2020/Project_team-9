package model;

import java.util.ArrayList;

public class Product {
    private String productId;
    private Status status;
    private String name;
    private String brand;
    private int price;
    private int discountPercent;
    private int visitCount = 0;
    private ArrayList<Seller> sellers;
    private int numberAvailable;
    private Category category;
    private String description;
    private ArrayList<Comment> comments;
    private ArrayList<Score> scores;

    public Product(Status status, String name, String brand, int price, int discountPercent, ArrayList<Seller> sellers, int numberAvailable, Category category, String description) {
        this.status = status;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.discountPercent = discountPercent;
        this.sellers = sellers;
        this.numberAvailable = numberAvailable;
        this.category = category;
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public String getBrand() {
        return brand;
    }

    public int getNumberAvailable() {
        return numberAvailable;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
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

    public String getProductId() {
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

    public double getAverageScore() {
        int total = 0;
        for (Score score : scores) {
            total += score.getScore();
        }
        return (double) (total) / scores.size();
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
