package model;

import java.util.ArrayList;

public abstract class Account {
    public Cart cart = new Cart();
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private ArrayList<Coupon> coupons;
    private ArrayList<Log> logs;
    private int credit;

    public Account(String username, String password, String email,
                   String phoneNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coupons = new ArrayList<>();
        this.logs = new ArrayList<>();
        this.credit = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    public void addLog(Log log) {
        logs.add(log);
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(password)) {
            password = newPassword;
        }
    }

    public boolean doesPasswordMatch(String password) {
        return password.equals(this.password);
    }

    @Override
    public String toString() {
        return "Username: " + username + '\n' +
                "Email: " + email + '\n' +
                "PhoneNumber: " + phoneNumber + '\n' +
                "FirstName: " + firstName + '\n' +
                "LastName: " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account))
            return false;
        return (((Account) obj).username.equals(this.username));
    }
}
