package model;

import controller.DataManager;

public class AddProductBySellerRequest extends Request {
    private String seller;
    private String product;

    // TODO: Fulfilling requests...

    public AddProductBySellerRequest(Seller seller, Product product) {
        this.seller = seller.getUsername();
        this.product = product.getProductId();
    }

    public Seller getSeller() {
        return (Seller)(DataManager.shared().getAccountWithGivenUsername(seller));
    }

    public Product getProduct() {
        return DataManager.shared().getProductWithId(product);
    }

    @Override
    public void fulfill() {
        DataManager.shared().addProduct(getProduct());
    }
}
