package view.menus;

import model.*;

public class AdministratorMenu extends Menu{
    public AdministratorMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    private void viewPersonalInfo() {
    }

    private void editEmail() {
    }

    private void editFirstName() {
    }

    private void editLastName() {
    }

    private void editPhoneNumber() {
    }

    private void changePassword() {
    }
    private void seeAllRequests(){}
    private void fulfillRequest(Request request){}
    private void seeAllCoupons(){}
    private void filterCouponsByName(String name){}
    private void startEditingCoupon(Coupon coupon){}
    private void startAddingCoupon(){}
    private void seeAllAccounts(){}
    private void seeAccountDetails(Account account){}
    private void startDeletingAccount(Account account){}
    private void startAddingNewAdministrator(){}
    private void seeAllCategories(){}
    private void filterCategoriesByName(String name){}
    private void filterCategoriesByDescription(String description){}
    private void startEditingCategoryName(Category category){}
    private void startEditingCategoryDescription(Category category){}
    private void startAddingProductToCategory(Category category){}
    private void startRemovingProductOfCategory(Category category){}
    private void startAddingSubCategoryToCategory(Category category){}
    private void startAddingCategory(){}
    private void startEditingProduct(Product product){}
    private void startRemovingProduct(Product product){}

    @Override
    protected void showHelp() {

    }
    private void logout(){}
}
