package model;

public class Score {
    private String id;
    private Customer customer;
    private int score;

    public Score(String id, Customer customer, int score) {
        this.id = id;
        this.customer = customer;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Customer getCustomer() {
        return customer;
    }
}
