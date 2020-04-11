package model;

import java.util.ArrayList;

public abstract class Account {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private ArrayList<Coupon> coupons;
    private ArrayList<Log> logs;

    public Account(String username, String password, String email, String phone, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }
}
