package model;

public class EditSaleBySellerRequest extends Request {
    private Seller seller;
    private Sale oldSale;
    private Sale newSale;

    public EditSaleBySellerRequest(Seller seller, Sale oldSale, Sale newSale) {
        this.seller = seller;
        this.oldSale = oldSale;
        this.newSale = newSale;
    }

    @Override
    public void fulfill() {

    }
}
