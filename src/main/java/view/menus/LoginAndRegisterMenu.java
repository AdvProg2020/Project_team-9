package view.menus;

import model.Account;
import model.Administrator;
import model.Customer;
import model.Seller;

import java.util.HashMap;

public class LoginAndRegisterMenu extends Menu {
    public LoginAndRegisterMenu(Menu parentMenu) {
        super("Login and registration menu", parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("Create Account", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                createAccountCommand();
                parentMenu.show();
                parentMenu.execute();
            }
        });
        subMenus.put(2, new Menu("MainMenu Help", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + " - Enter Back to return");
            }

            @Override
            public void execute() {
                System.out.println("Available Commands:");
                System.out.println("Command one");
                System.out.println("Command two");
                while (true) {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("back")) {
                        this.parentMenu.show();
                        this.parentMenu.execute();
                        break;
                    }
                }
            }
        });
        setSubMenus(subMenus);
    }

    private static void loginCommand() {
        System.out.println("Create Account");
        System.out.print("Username: ");
        String username;
        while (true) {
            username = scanner.nextLine();
            if (!manager.doesUserWithGivenUsernameExist(username)) {
                System.out.print("User doesn't exists with the given username. Try again: ");
            } else break;
        }
        System.out.print("Password: ");
        String password;
        while (true) {
            password = scanner.nextLine();
            if (!manager.givenUsernameHasGivenPassword(username, password)) {
                System.out.print("Wrong password. Try again: ");
            } else break;
        }
    }

    private void createAccountCommand() {
        System.out.println("Create Account");
        System.out.println("Enter your desired account type: customer, seller or administrator");
        String type = scanner.nextLine();
        if (type.equalsIgnoreCase("administrator") && manager.hasAnyAdminRegistered()) {
            System.out.println("You should log in as an administrator to add new admins.");
            return;
        }
        if (!type.equalsIgnoreCase("administrator") && !type.equalsIgnoreCase("seller") && !type.equalsIgnoreCase("customer")) {
            System.out.println("Invalid type.");
            return;
        }
        System.out.print("Username: ");
        String username;
        while (true) {
            username = scanner.nextLine();
            if (manager.doesUserWithGivenUsernameExist(username)) {
                System.out.print("User exists with the given username. Try a new one: ");
            } else break;
        }
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Phone number: ");
        String phone = scanner.nextLine();
        if (type.equalsIgnoreCase("seller")) {
            System.out.print("Company name: ");
            String companyDetails = scanner.nextLine();
            Seller seller = new Seller(username, password, email, phone, firstName, lastName, companyDetails);
            manager.registerAccount(seller);
            System.out.println("Account created");
        } else if (type.equalsIgnoreCase("customer")) {
            Customer customer = new Customer(username, password, email, phone, firstName, lastName);
            manager.registerAccount(customer);
            System.out.println("Account created");
        } else if (type.equalsIgnoreCase("administrator")) {
            Administrator administrator = new Administrator(username, password, email, phone, firstName, lastName);
            manager.registerAccount(administrator);
            System.out.println("Account created");
        } else {
            System.out.println("An unexpected error has happened.");
        }
    }
}