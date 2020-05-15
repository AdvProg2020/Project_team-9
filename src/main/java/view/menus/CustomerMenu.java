package view.menus;

import controller.DataManager;
import model.Product;

public class CustomerMenu extends UserMenu {
    public CustomerMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    private void viewPersonalInfo() {
        System.out.println(DataManager.shared().getLoggedInAccount());
    }

    private void editEmail() {
        
    }

    private void editFirstName() {
    }

    private void editSecondName() {
    }

    private void editPhoneNumber() {
    }

    private void changePassword() {
    }

    private void viewCart() {
    }

    private void showProduct(Product product) {
    }

    private void startSubmittingScoreForProduct(Product product) {
    }

    private void startSubmittingCommentForProduct(Product product) {
    }

    private void incrementProductCountInCart(Product product) {
    }

    private void decrementProductCountInCart(Product product) {
    }

    private void viewPurchaseLog() {
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
