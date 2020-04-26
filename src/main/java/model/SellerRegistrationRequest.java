package model;

public class SellerRegistrationRequest extends Request {
    private Seller seller;

    public SellerRegistrationRequest(Seller seller) {
        this.seller = seller;
    }

    @Override
    public void fulfill() {

    }
}
