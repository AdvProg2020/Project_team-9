package controller;

public class Validator {
    private static Validator sharedVaildator;

    private Validator() {
    }

    public static Validator shared() {
        if (sharedVaildator == null)
            sharedVaildator = new Validator();
        return sharedVaildator;
    }

    public boolean emailIsValid(String email) {
        return email.matches("([\\w!#$%&'*+\\-/=?^_`{|}~]\\.?)+@[\\w\\-]+\\.\\w+");
    }
}
