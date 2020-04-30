package view.menus;

import controller.DataManager;
import model.*;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class AdministratorMenu extends Menu {
    public AdministratorMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("View Personal Info", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewPersonalInfo()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(2, new Menu("Edit first name", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (editFirstName()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(3, new Menu("Edit last name", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (editLastName()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(4, new Menu("Edit email", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (editEmail()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(5, new Menu("Edit phone number", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (editPhoneNumber()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(6, new Menu("Change password", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (changePassword()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(7, new Menu("View User Info", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewUserInfo()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(8, new Menu("Change user role", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (changeUserType()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(9, new Menu("Remove user", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (deleteUser()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(10, new Menu("Add admin", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (addAdmin()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(11, new Menu("Summary of all products", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (summaryOfAllProducts()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(12, new Menu("Administrator Menu Help", this) {
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

            @Override
            protected void showHelp() {

            }
        });
    }

    private boolean summaryOfAllProducts() {
        for (Product product : DataManager.shared().getAllProducts()) {
            System.out.println("#" + product.getProductId() + " - " + product.getName());
        }
    }

    private boolean addAdmin() {
        System.out.print("Add admin\nUsername: ");
        String username;
        while (true) {
            username = scanner.nextLine();
            if (DataManager.shared().doesUserWithGivenUsernameExist(username)) {
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
        Administrator administrator = new Administrator(username, password, email, phone, firstName, lastName);
        DataManager.shared().registerAccount(administrator);
        System.out.println("Account created");
        return false;
    }

    private boolean viewPersonalInfo() {
        Administrator administrator = (Administrator) DataManager.shared().getLoggedInAccount();
        System.out.println(administrator.getFirstName() + " " + administrator.getLastName() + " - " + administrator.getUsername());
        System.out.println("Email: " + administrator.getEmail());
        System.out.println("Phone: " + administrator.getPhoneNumber());
        return false;
    }

    private boolean viewUserInfo() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        Account account = DataManager.shared().getAccountWithGivenUsername(username);
        if (account == null) {
            System.out.println("No account with the given username exists");
            return false;
        }
        System.out.println(account.getFirstName() + " " + account.getLastName());
        System.out.println("Email: " + account.getEmail());
        System.out.println("Phone: " + account.getPhoneNumber());
        if (account instanceof Seller) System.out.println("Company Details: " + ((Seller) account).getCompanyDetails());
        return false;
    }

    private boolean changeUserType() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        Account account = DataManager.shared().getAccountWithGivenUsername(username);
        if (account == null) {
            System.out.println("No account with the given username exists");
            return false;
        }

        System.out.print("Enter 1 to change user's type to customer, 2 to seller and 3 to administrator: ");
        int result = scanner.nextInt();
        switch (result) {
            case 1:
                Customer customer = new Customer(account);
                DataManager.shared().removeAccount(account);
                DataManager.shared().registerAccount(customer);
                break;
            case 2:
                Seller seller = new Seller(account);
                DataManager.shared().removeAccount(account);
                DataManager.shared().registerAccount(seller);
                break;
            case 3:
                Administrator administrator = new Administrator(account);
                DataManager.shared().removeAccount(account);
                DataManager.shared().registerAccount(administrator);
                break;
            default:
                return false;
        }
        System.out.println(username + "'s role has been changed successfully");
        return false;
    }

    private boolean deleteUser() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        Account account = DataManager.shared().getAccountWithGivenUsername(username);
        if (account == null) {
            System.out.println("No account with the given username exists");
            return false;
        }
        DataManager.shared().removeAccount(account);
        System.out.println("Account " + username + " was deleted successfully");
        return false;
    }

    private boolean editEmail() {
        System.out.print("Enter your new email address: ");
        String email = scanner.nextLine();
        // TODO: Check regex!
        DataManager.shared().getLoggedInAccount().setEmail(email);
        return false;
    }

    private boolean editFirstName() {
        System.out.print("Enter your new first name: ");
        String firstName = scanner.nextLine();
        DataManager.shared().getLoggedInAccount().setFirstName(firstName);
        return false;
    }

    private boolean editLastName() {
        System.out.print("Enter your new last name: ");
        String lastName = scanner.nextLine();
        DataManager.shared().getLoggedInAccount().setLastName(lastName);
        return false;
    }

    private boolean editPhoneNumber() {
        System.out.print("Enter your new phone number: ");
        String phone = scanner.nextLine();
        // TODO: Check regex!
        DataManager.shared().getLoggedInAccount().setPhoneNumber(phone);
        return false;
    }

    private boolean changePassword() {
        System.out.print("Enter your old password: ");
        String oldPassword = scanner.nextLine();
        if (!DataManager.shared().getLoggedInAccount().getPassword().equals(oldPassword)) {
            System.out.println("Your password is wrong. Try again.");
            return false;
        }
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        if (oldPassword.equals(newPassword)) {
            System.out.println("Your new password should be different from the previous password. Try again.");
            return false;
        }
        DataManager.shared().getLoggedInAccount().setPassword(newPassword);
        System.out.println("New password has been set");
        return false;
    }

    private void seeAllRequests() {
    }

    private void fulfillRequest(Request request) {
    }

    private void seeAllCoupons() {
    }

    private void filterCouponsByName(String name) {
    }

    private void startEditingCoupon(Coupon coupon) {
    }

    private void startAddingCoupon() {
    }

    private void seeAllAccounts() {
    }

    private void seeAccountDetails(Account account) {
    }

    private void startDeletingAccount(Account account) {
    }

    private void startAddingNewAdministrator() {
    }

    private void seeAllCategories() {
    }

    private void filterCategoriesByName(String name) {
    }

    private void filterCategoriesByDescription(String description) {
    }

    private void startEditingCategoryName(Category category) {
    }

    private void startEditingCategoryDescription(Category category) {
    }

    private void startAddingProductToCategory(Category category) {
    }

    private void startRemovingProductOfCategory(Category category) {
    }

    private void startAddingSubCategoryToCategory(Category category) {
    }

    private void startAddingCategory() {
    }

    private void startEditingProduct(Product product) {
    }

    private void startRemovingProduct(Product product) {
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
