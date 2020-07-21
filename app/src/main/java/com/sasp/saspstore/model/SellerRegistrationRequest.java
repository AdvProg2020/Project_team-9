package com.sasp.saspstore.model;

import com.sasp.saspstore.controller.DataManager;

public class SellerRegistrationRequest extends Request {
    private String seller;

    public SellerRegistrationRequest(String id, Seller seller) {
        super(id);
        this.seller = seller.getUsername();
    }

    public Seller getSeller() {
        return (Seller) DataManager.shared().getAccountWithGivenUsername(seller);
    }

    @Override
    public void fulfill() {
        getSeller().setPermittedToSell(true);
        DataManager.shared().syncAccounts();
        DataManager.shared().removeRequest(this);
    }
}
