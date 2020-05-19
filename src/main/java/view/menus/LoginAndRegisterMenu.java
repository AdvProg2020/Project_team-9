package view.menus;

import controller.DataManager;
import controller.Validator;
import model.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginAndRegisterMenu extends Menu {
    public LoginAndRegisterMenu(Menu parentMenu) {
        super("Login and registration menu", parentMenu);
        DataManager.loadData();
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("Create Account", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (!createAccountCommand()) {
                    parentMenu.show();
                    parentMenu.execute();
                }
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(2, new Menu("Login", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (!loginCommand()) {
                    parentMenu.show();
                    parentMenu.execute();
                }
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(3, new Menu("All products", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (allProducts()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        setSubMenus(subMenus);
    }

    private boolean allProducts() {
        AllProductsMenu menu = new AllProductsMenu("All Products", this, false);
        menu.show();
        menu.execute();
        return false;
    }

    private boolean loginCommand() {
        System.out.println("Login");
        System.out.print("Username: ");
        String username;
        while (true) {
            username = scanner.nextLine();
            if (username.equals("-1")) return false;
            if (!DataManager.shared().doesUserWithGivenUsernameExist(username)) {
                System.out.print("User doesn't exists with the given username. Try again (-1 to quit): ");
            } else break;
        }
        System.out.print("Password: ");
        String password;
        while (true) {
            password = scanner.nextLine();
            if (!DataManager.shared().givenUsernameHasGivenPassword(username, password)) {
                System.out.print("Wrong password. Try again: ");
            } else break;
        }
        var result = DataManager.shared().login(username, password);
        if (result == DataManager.AccountType.NONE) {
            System.out.println("An unexpected error has occurred. Please try again.");
            return false;
        }
        System.out.println("Login Successful");
        if (result == DataManager.AccountType.CUSTOMER) {
            // TODO: Does it work??
            HashMap<Product, Integer> prods = ((Customer)(DataManager.shared().getLoggedInAccount())).getCart().getProducts();
            prods.putAll(DataManager.shared().getTemporaryCart().getProducts());
            ((Customer)(DataManager.shared().getLoggedInAccount())).getCart().setProducts(prods);
            DataManager.shared().getTemporaryCart().setProducts(new HashMap<>());
            DataManager.saveData();
            Menu menu = new CustomerMenu("\nCustomer's main menu", this);
            menu.show();
            menu.execute();
            return true;
        } else if (result == DataManager.AccountType.ADMINISTRATOR) {
            Menu menu = new AdministratorMenu("\nAdministrator's main menu", this);
            menu.show();
            menu.execute();
            return true;
        } else if (result == DataManager.AccountType.SELLER) {
            Menu menu = new SellerMenu("\nSeller's main menu", this);
            menu.show();
            menu.execute();
            return true;
        } else {
            System.out.println("An unexpected error has occurred. Please try again.");
        }
        return false;
    }

    private boolean createAccountCommand() {
        System.out.println("Create Account");
        System.out.println("Enter your desired account type: customer, seller or administrator");
        String type = scanner.nextLine();
        if (type.equalsIgnoreCase("administrator") && DataManager.shared().hasAnyAdminRegistered()) {
            System.out.println("You should log in as an administrator to add new admins.");
            return false;
        }
        if (!type.equalsIgnoreCase("administrator") && !type.equalsIgnoreCase("seller") && !type.equalsIgnoreCase("customer")) {
            System.out.println("Invalid type.");
            return false;
        }
        System.out.print("Username: ");
        String username;
        while (true) {
            username = scanner.nextLine();
            if (username.equals("-1")) return false;
            if (DataManager.shared().doesUserWithGivenUsernameExist(username)) {
                System.out.print("User exists with the given username. Try a new one or enter -1 to quit: ");
            } else break;
        }
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email;
        while (true) {
            email = scanner.nextLine();
            if (email.equals("-1")) return false;
            if (!Validator.shared().emailIsValid(email)) {
                System.out.print("Invalid email. Try a new one or enter -1 to quit: ");
            } else break;
        }
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Phone number: ");
        String phone;
        while (true) {
            phone = scanner.nextLine();
            if (phone.equals("-1")) return false;
            if (!Validator.shared().phoneNumberIsValid(phone)) {
                System.out.print("Invalid phone number. Try a new one or enter -1 to quit: ");
            } else break;
        }
        if (type.equalsIgnoreCase("seller")) {
            System.out.print("Company name: ");
            String companyDetails = scanner.nextLine();
            Seller seller = new Seller(username, password, email, phone, firstName, lastName, companyDetails);
            DataManager.shared().registerAccount(seller);
            SellerRegistrationRequest request = new SellerRegistrationRequest(DataManager.getNewId(), seller);
            DataManager.shared().addRequest(request);
            System.out.println("Account created");
        } else if (type.equalsIgnoreCase("customer")) {
            Customer customer = new Customer(username, password, email, phone, firstName, lastName);
            DataManager.shared().registerAccount(customer);
            System.out.println("Account created");
        } else if (type.equalsIgnoreCase("administrator")) {
            Administrator administrator = new Administrator(username, password, email, phone, firstName, lastName);
            DataManager.shared().registerAccount(administrator);
            System.out.println("Account created");
        } else {
            System.out.println("An unexpected error has happened.");
        }
        return false;
    }

    @Override
    protected void showHelp() {

    }
}
