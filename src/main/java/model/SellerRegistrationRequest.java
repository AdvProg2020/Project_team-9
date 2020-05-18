package model;

import controller.DataManager;

public class SellerRegistrationRequest extends Request {
    private String seller;

    public SellerRegistrationRequest(Seller seller) {
        this.seller = seller.getUsername();
    }

    public Seller getSeller() {
        return (Seller) DataManager.shared().getAccountWithGivenUsername(seller);
    }

    @Override
    public void fulfill() {
        getSeller().setPermittedToSell(true);
        DataManager.saveData();
    }
}
