package model;

import controller.DataManager;

public class EditSaleBySellerRequest extends Request {
    private String seller;
    private String oldSale;
    private String newSale;

    public EditSaleBySellerRequest(Seller seller, Sale oldSale, Sale newSale) {
        this.seller = seller.getUsername();
        this.oldSale = oldSale.getOffId();
        this.newSale = newSale.getOffId();
    }

    public Sale getNewSale() {
        return DataManager.shared().getSaleWithId(newSale);
    }

    public Sale getOldSale() {
        return DataManager.shared().getSaleWithId(oldSale);
    }

    public Seller getSeller() {
        return (Seller) DataManager.shared().getAccountWithGivenUsername(seller);
    }

    @Override
    public void fulfill() {
        try {
            Sale oldSale = DataManager.shared().getSaleWithId(this.oldSale);
            Sale newSale = DataManager.shared().getSaleWithId(this.oldSale);
            DataManager.shared().getAllSales().add(newSale);
            DataManager.shared().getAllSales().remove(oldSale);
        } catch (Exception e) {

        }
    }
}
