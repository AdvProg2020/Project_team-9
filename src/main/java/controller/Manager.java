package controller;

import model.*;

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

    public enum AccountType {
        CUSTOMER, ADMINISTRATOR, SELLER, NONE
    }

    public AccountType login(String username, String password) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username) && account.getUsername().equals(password)) {
                loggedInAccount = account;
                if (account instanceof Customer) {
                    return AccountType.CUSTOMER;
                } else if (account instanceof Administrator) {
                    return AccountType.ADMINISTRATOR;
                } else if (account instanceof Seller) {
                    return AccountType.SELLER;
                }
                break;
            }
        }
        loggedInAccount = null;
        return AccountType.NONE;
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

    public void registerAccount(Account account) {
        allAccounts.add(account);
        saveData();
    }

    public boolean givenUsernameHasGivenPassword(String username, String password) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) {
                return account.getPassword().equalsIgnoreCase(password);
            }
        }
        return false;
    }

    public void saveData() {
        // TODO: MMM
    }
}
