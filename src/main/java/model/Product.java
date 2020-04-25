package model;

import java.util.ArrayList;

public class Product {
    private int ProductId;
    private Status status;
    private String name;
    private String brand;
    private int price;
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

    public Product newProduct(Product oldProduct) {

    }

    public int getAverageScore() {
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
