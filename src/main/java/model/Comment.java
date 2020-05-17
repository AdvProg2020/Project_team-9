package model;

import controller.DataManager;

public class Comment {
    private String id;
    private String customer;
    private String product;
    private String title;
    private String text;
    // TODO: Does enum make problem for Gson??
    private CommentStatus commentStatus;
    //private boolean hasUserPurchasedProduct;

    public Comment(Customer customer, Product product, String title, String text) {
        this.id = DataManager.getNewId();
        this.customer = customer.getUsername();
        this.product = product.getProductId();
        this.title = title;
        this.text = text;
        this.commentStatus = CommentStatus.WAITING_FOR_REVIEW;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return (Customer) DataManager.shared().getAccountWithGivenUsername(customer);
    }

    public Product getProduct() {
        return DataManager.shared().getProductWithId(product);
    }

    public String getText() {
        return text;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public String getTitle() {
        return title;
    }

    public boolean hasUserPurchasedProduct() {
        return getCustomer().hasPurchasedProduct(getProduct());
    }
}
