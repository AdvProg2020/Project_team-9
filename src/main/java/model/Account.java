package model;

import java.util.ArrayList;

public abstract class Account {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private ArrayList<Coupon> coupons;
    private ArrayList<Log> logs;
    private int credit;

    public Account(String username, String password, String email, String phoneNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public void addCoupon(Coupon coupon) {

    }

    public void addLog(Log log) {

    }

    public void changePassword(String oldPassword, String newPassword) {

    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void submitLog(Log log) {

    }

    public boolean doesPasswordMatch(String password) {
        return false;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", coupons=" + coupons +
                ", logs=" + logs +
                ", credit=" + credit +
                '}';
    }
}
