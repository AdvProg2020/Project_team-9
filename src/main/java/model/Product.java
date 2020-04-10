package model;

import java.util.ArrayList;

public class Product {
    private int id;
    private Status status;
    private String name;
    private String brand;
    private int price;
    private ArrayList<Seller> sellers;
    private int numberAvailable;
    private Category category;
    private String description;
    private ArrayList<Comment> comments;
    private ArrayList<Score> scores;
}
