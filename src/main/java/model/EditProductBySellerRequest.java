package model;

public class EditProductBySellerRequest extends Request {
    private Seller seller;
    private Product oldProduct;
    private Product newProduct;

    public EditProductBySellerRequest(Seller seller, Product oldProduct, Product newProduct) {
        this.seller = seller;
        this.oldProduct = oldProduct;
        this.newProduct = newProduct;
    }

    @Override
    public void fulfill() {

    }
}
