package model;

public class AddSaleBySellerRequest extends Request {
    private Seller seller;
    private Sale sale;

    public AddSaleBySellerRequest(Seller seller, Sale sale) {
        this.seller = seller;
        this.sale = sale;
    }

    public Seller getSeller() {
        return seller;
    }

    public Sale getSale() {
        return sale;
    }

    @Override
    public void fulfill() {

    }
}
