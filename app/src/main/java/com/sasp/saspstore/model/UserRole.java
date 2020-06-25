package com.sasp.saspstore.model;

import androidx.annotation.NonNull;

public enum UserRole {
    CUSTOMER, SELLER, ADMIN;

    @NonNull
    @Override
    public String toString() {
        switch (this) {
            case ADMIN:
                return "admin";
            case SELLER:
                return "seller";
            case CUSTOMER:
                return "customer";
            default:
                return "";
        }
    }
}
