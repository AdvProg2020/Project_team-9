package model;

import java.util.ArrayList;

public class Score {
    private int id;
    private Customer customer;
    private int score;

    public int getScore() {
        return score;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Score(int id, Customer customer, int score) {
        this.id = id;
        this.customer = customer;
        this.score = score;
    }
}
