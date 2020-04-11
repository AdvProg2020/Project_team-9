package controller;

import model.Account;
import model.Administrator;
import model.RegisterResult;
import model.Seller;

import java.util.ArrayList;

public class Manager {
    private Account loggedInAccount;
    private ArrayList<Account> allAccounts = new ArrayList<>();

    public ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public boolean hasAnyAdminRegistered() {
        for (Account account : allAccounts) {
            if (account instanceof Administrator) return true;
        }
        return false;
    }

    public boolean doesUserWithGivenUsernameExist(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void registerSeller(Seller seller) {
        allAccounts.add(seller);
        saveData();
    }

    public void saveData() {
        // TODO: MMM
    }
}
