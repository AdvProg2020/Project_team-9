package view.menus;

import controller.DataManager;
import controller.Validator;
import model.Account;
import model.Product;
import model.Sale;

import java.time.LocalDateTime;

public class SellerMenu extends UserMenu {
    public SellerMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    private void editCompanyInformation() {

    }

    private void viewSellLog() {
    }

    private void showProduct(Product product) {
    }

    private void seeCustomerOfProduct(Product product) {
    }

    private void viewAllSellingProducts() {
    }

    private void startAddingProduct() {
    }

    private void startEditingProduct(Product product) {
    }

    private void requestEditingProductName(Product product) {
    }

    private void requestEditingProductBrand(Product product) {
    }

    private void requestEditingProductPrice(Product product) {
    }

    private void requestEditingProductCategory(Product product) {
    }

    private void requestEditingProductDescription(Product product) {
    }

    private void requestEditingProductNumberAvailable(Product product) {
    }

    private void requestRemovingProduct(Product product) {
    }

    private void showCategories() {
    }

    private void viewAllSales() {
    }

    private void filterSalesByDiscountAmount(int discountAmount) {
    }

    private void filterSalesByTimeRange(LocalDateTime from, LocalDateTime to) {
    }

    private void startAddingSale() {
    }

    private void startEditingSale(Sale sale) {
    }

    private void requestAddingProduct(Product product) {
    }

    private void requestAddingSale(Sale sale) {
    }

    private void requestAddingProductToSale(Sale sale, int productId) {
    }

    private void requestRemovingProductFromSale(Sale sale, int productId) {
    }

    private void requestEditingSaleStatus(Sale sale) {
    }

    private void requestEditingSaleDiscountAmount(Sale sale) {
    }

    private void requestEditingSaleStartTime(Sale sale) {
    }

    private void requestEditingSaleEndTime(Sale sale) {
    }

    private void requestEditingSaleDeliveryStatus(Sale sale) {
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
