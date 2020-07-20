package com.sasp.saspstore.model;

import com.sasp.saspstore.controller.DataManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Account {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String profilePicPath;
    private ArrayList<String> coupons;
    private ArrayList<String> logs;
    private int credit;
    private String bankAccountNumber = "";

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public Account(String username, String password, String email,
                   String phoneNumber, String firstName, String lastName, String profilePicPath) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coupons = new ArrayList<>();
        this.logs = new ArrayList<>();
        this.credit = 0;
        this.profilePicPath = profilePicPath;
    }

    // TODO: DataManager.saveData() should be also added to all places calling new()...

    public int getCredit() {
        return credit;
    }

    public void increaseCredit(int amount) {
        credit += amount;
        DataManager.shared().syncAccounts();
    }

    public void decreaseCredit(int amount) {
        if (credit - amount >= DataManager.shared().getMimimumCredit()) {
            credit -= amount;
            DataManager.shared().syncAccounts();
        }
    }

    public void setCredit(int credit) {
        this.credit = credit;
        DataManager.shared().syncAccounts();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        DataManager.shared().syncAccounts();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
        DataManager.shared().syncAccounts();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
        DataManager.shared().syncAccounts();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
        DataManager.shared().syncAccounts();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newLastName) {
        this.lastName = newLastName;
        DataManager.shared().syncAccounts();
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons.stream().map(id -> DataManager.shared().getCouponWithId(id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Log> getLogs() {
        return logs.stream().map(id -> DataManager.shared().getLogWithId(id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public void addCoupon(Coupon coupon) {
        coupons.add(coupon.getId());
        DataManager.shared().syncAccounts();
    }

    public void addLog(Log log) {
        logs.add(log.getId());
        DataManager.shared().syncAccounts();
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(password)) {
            password = newPassword;
            DataManager.shared().syncAccounts();
        }
    }

    public boolean doesPasswordMatch(String password) {
        return password.equals(this.password);
    }

    @Override
    public String toString() {
        return username + "\n\nName: " + firstName + " " + lastName + "\n\nEmail: " + email + "\n\nPhone: " + phoneNumber +
                "\n\nOnline Status: " + (DataManager.shared().getOnlineUsernames().contains(getUsername()) ? "Online" : "Offline");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account))
            return false;
        return (((Account) obj).username.equals(this.username));
    }
}
