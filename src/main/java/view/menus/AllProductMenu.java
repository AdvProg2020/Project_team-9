package view.menus;

import model.Product;

public class AllProductMenu extends Menu {
    private Product[] products;

    public AllProductMenu(String name, Menu parentMenu, Product[] products) {
        super(name, parentMenu);
        this.products = products;
    }

    private void listOfAllProductsCommand() {
    }

    private void searchByInputCommand() {
    }

    private void filterByBrand(String filter) {
    }

    private void removeFilterByBrand() {
    }

    private void filterByAvailability(boolean shouldBeAvailable) {
    }

    private void removeFilterByAvailability() {
    }

    private void filterByPriceRange(int from, int to) {
    }

    private void filterByName(String name) {
    }

    private void showProduct(Product product) {
    }

    private void startComparingProducts(int firstProductId, int secondProductId) {
    }

    private void showProductDigest(Product product) {
    }

    private void showProductAttributes(Product product) {
    }

    private void addProductToCart(Product product) {
    }

    @Override
    protected void showHelp() {

    }

    private void checkOut() {
    }
}
