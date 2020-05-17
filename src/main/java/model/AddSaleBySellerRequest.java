package model;

import controller.DataManager;

public class AddSaleBySellerRequest extends Request {
    private String seller;
    private String sale;

    public AddSaleBySellerRequest(Seller seller, Sale sale) {
        this.seller = seller.getUsername();
        this.sale = sale.getOffId();
    }

    public Seller getSeller() {
        return (Seller)(DataManager.shared().getAccountWithGivenUsername(seller));
    }

    public Sale getSale() {
        return DataManager.shared().getSaleWithId(sale);
    }

    @Override
    public void fulfill() {

    }
}
