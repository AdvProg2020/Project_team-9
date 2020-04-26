package model;

import java.util.ArrayList;

public class Score {
    private int id;
    private Customer customer;
    private int score;
    private Product product;

    public Score(int id, Customer customer, int score, Product product) {
        this.id = id;
        this.customer = customer;
        this.score = score;
        this.product = product;
    }
}
