package view.menus;

import controller.DataManager;
import model.Account;
import model.userdata.Email;
import model.userdata.PhoneNumber;

public abstract class UserMenu extends Menu {
    public UserMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    private void viewPersonalInfo() {
        System.out.println(DataManager.shared().getLoggedInAccount());
    }

    private void editEmail() {
        String newEmail = getStringWithPattern(Email.getPattern());
        getCurrentUser().setEmail(newEmail);
    }

    private void editFirstName() {
        getCurrentUser().setFirstName(getString());
    }

    private void editLastName() {
        getCurrentUser().setLastName(getString());
    }

    private void editPhoneNumber() {
        String newPhoneNumber = getStringWithPattern(PhoneNumber.getPattern());
        getCurrentUser().setPhoneNumber(newPhoneNumber);
    }

    private void changePassword() {
        String password = getString();
        if (getCurrentUser().doesPasswordMatch(password)) {
            System.out.println("Your new password cannot be the same as your old password.");
            return;
        }
        getCurrentUser().setPassword(password);
    }

    private Account getCurrentUser() {
        return DataManager.shared().getLoggedInAccount();
    }
}
