package model;

import controller.DataManager;

public class Comment {
    private String id;
    private Customer customer;
    private Product product;
    private String title;
    private String text;
    private CommentStatus commentStatus;
    //private boolean hasUserPurchasedProduct;

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public String getText() {
        return text;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public Comment(Customer customer, Product product, String title, String text) {
        this.id = DataManager.getNewId();
        this.customer = customer;
        this.product = product;
        this.title = title;
        this.text = text;
        this.commentStatus = CommentStatus.WAITING_FOR_REVIEW;
    }

    public String getTitle() {
        return title;
    }

    public boolean hasUserPurchasedProduct() {
        // TODO: Not implemented (and also as a result, when a user sees this...)
        return false;
    }
}
