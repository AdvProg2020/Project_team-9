package model;

import controller.DataManager;

public class EditProductBySellerRequest extends Request {
    private String seller;
    private String oldProduct;
    private String newProduct;

    public EditProductBySellerRequest(Seller seller, Product oldProduct, Product newProduct) {
        this.seller = seller.getUsername();
        this.oldProduct = oldProduct.getProductId();
        this.newProduct = newProduct.getProductId();
    }

    public Seller getSeller() {
        return (Seller) DataManager.shared().getAccountWithGivenUsername(seller);
    }

    public Product getOldProduct() {
        return DataManager.shared().getProductWithId(oldProduct);
    }

    public Product getNewProduct() {
        return DataManager.shared().getProductWithId(newProduct);
    }

    @Override
    public void fulfill() {
        DataManager.shared().removeProduct(oldProduct);
        DataManager.shared().addProduct(getNewProduct());
    }
}
