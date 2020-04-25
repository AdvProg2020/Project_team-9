package model;

public class Comment {
    private int id;
    private Customer customer;
    private Product product;
    private String text;
    private CommentStatus commentStatus;
    //private boolean hasUserPurchasedProduct;

    public Comment(int id, Customer customer, Product product, String text, CommentStatus commentStatus) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.text = text;
        this.commentStatus = commentStatus;
    }

    public boolean hasUserPurchasedProduct(){

    }
}
