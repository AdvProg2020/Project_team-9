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

    public Seller getSeller() {
        return seller;
    }

    public Product getOldProduct() {
        return oldProduct;
    }

    public Product getNewProduct() {
        return newProduct;
    }

    @Override
    public void fulfill() {

    }
}
