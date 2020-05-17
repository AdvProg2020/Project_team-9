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
        showEditPrompt("email");
        String newEmail = getStringWithPattern(Email.getPattern(), "Please enter a valid email.");
        getCurrentUser().setEmail(newEmail);
        declareEditSuccess("email");
    }

    private void editFirstName() {
        showEditPrompt("first name");
        getCurrentUser().setFirstName(getString());
        declareEditSuccess("first name");
    }

    private void editLastName() {
        showEditPrompt("last name");
        getCurrentUser().setLastName(getString());
        declareEditSuccess("last name");
    }

    private void editPhoneNumber() {
        showEditPrompt("phone number");
        String newPhoneNumber = getStringWithPattern(PhoneNumber.getPattern(),
                "Please enter a valid phone number.");
        getCurrentUser().setPhoneNumber(newPhoneNumber);
        declareEditSuccess("phone number");
    }

    private void changePassword() {
        showString("Please enter your current password:");
        String oldPassword = getString();
        if (!getCurrentUser().doesPasswordMatch(oldPassword)) {
            showString("Incorrect password");
            return;
        }
        showEditPrompt("password");
        String password = getString();
        if (getCurrentUser().doesPasswordMatch(password)) {
            System.out.println("Your new password cannot be the same as your old password.");
            return;
        }
        String passwordRepeat = getString();
        if (!password.equals(passwordRepeat)) {
            showString("The two passwords are not equal.");
            return;
        }
        getCurrentUser().setPassword(password);
        declareEditSuccess("password");
    }

    private void showEditPrompt(String field) {
        showString("Please enter your new " + field + ":");
    }

    private void declareEditSuccess(String field) {
        showString("Your " + field + " was successfully changed.");
    }

    private Account getCurrentUser() {
        return DataManager.shared().getLoggedInAccount();
    }
}
