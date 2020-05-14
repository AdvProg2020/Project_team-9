package model;

public class AddProductBySellerRequest extends Request {
    private Seller seller;
    private Product product;

    public Seller getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public AddProductBySellerRequest(Seller seller, Product product) {
        this.seller = seller;
        this.product = product;
    }

    @Override
    public void fulfill() {

    }
}
