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
}
