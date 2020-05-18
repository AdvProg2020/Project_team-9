package view.menus;

import controller.DataManager;
import model.*;

import javax.swing.text.DateFormatter;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class AdministratorMenu extends UserMenu {
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

        subMenus.put(12, new Menu("Remove Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeProduct()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(13, new Menu("Create Coupon", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (createDiscountCode()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(14, new Menu("View All Coupons", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewAllCoupons()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(15, new Menu("Remove Coupon", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeCoupon()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(16, new Menu("View Coupon Details", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewCoupon();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(17, new Menu("Edit Coupon Details", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (editCoupon()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(18, new Menu("View all requests", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewAllRequests()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(19, new Menu("View request details", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewRequestDetails()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(20, new Menu("Accept request", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (resolveRequest(true)) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(21, new Menu("Decline request", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (resolveRequest(false)) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(22, new Menu("View all categories", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewAllCategories()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(23, new Menu("Edit category details", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (editCategory()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(24, new Menu("Add category", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (addCategory()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(25, new Menu("Remove category", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeCategory()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(26, new Menu("Administrator Menu Help", this) {
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

        subMenus.put(27, new Menu("Logout", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                if (logoutCommand()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });

        this.setSubMenus(subMenus);
    }

    private boolean logoutCommand() {
        DataManager.shared().logout();
        return true;
    }

    private boolean editCoupon() {
        Coupon coupon = viewCoupon();
        if (coupon == null) return false;
        System.out.println("Enter \"d\" to edit discount percent, \"m\" to edit maximum possible discount, \"s\" to edit start time and \"e\" to edit end time");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "d":
                editCouponDiscountPercent(coupon);
                break;
            case "m":
                editCouponMaximumDiscount(coupon);
                break;
            case "s":
                editCouponStartTime(coupon);
                break;
            case "e":
                editCouponEndTime(coupon);
                break;
            default:
                System.out.println("Wrong letter was typed.");
                return false;
        }
        System.out.println("Changed successfully");
        return false;
    }

    private void editCouponDiscountPercent(Coupon coupon) {
        System.out.println("Current discount percent: " + coupon.getDiscountPercent());
        System.out.print("Enter a new value for discount percent (between 0 and 100): ");
        while (true) {
            int newValue = scanner.nextInt();
            if (newValue < 0 || newValue > 100) {
                System.out.print("Impossible value. Please enter a new value again: ");
                continue;
            }
            coupon.setDiscountPercent(newValue);
            break;
        }
    }

    private void editCouponMaximumDiscount(Coupon coupon) {
        System.out.println("Current maximum discount: " + coupon.getMaximumDiscount());
        System.out.print("Enter a new value for maximum discount: ");
        int newValue = scanner.nextInt();
        coupon.setMaximumDiscount(newValue);
    }

    private void editCouponStartTime(Coupon coupon) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Current coupon's start time: " + coupon.getStartTime().format(dateFormatter));
        System.out.print("Enter a new start date in format of yyyy-MM-dd HH:mm: ");
        String input = scanner.nextLine();
        // TODO: Invalid date??
        coupon.setStartTime(LocalDateTime.parse(input, dateFormatter));
        System.out.println("New start time was set");
    }

    private void editCouponEndTime(Coupon coupon) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Current coupon's end time: " + coupon.getEndTime().format(dateFormatter));
        System.out.print("Enter a new end date in format of yyyy-MM-dd HH:mm: ");
        String input = scanner.nextLine();
        // TODO: Invalid date??
        coupon.setEndTime(LocalDateTime.parse(input, dateFormatter));
        System.out.println("New end time was set");
    }

    private Coupon viewCoupon() {
        System.out.print("Enter coupon's ID: ");
        String id = scanner.nextLine();
        Coupon coupon = DataManager.shared().getCouponWithId(id);
        if (coupon == null) {
            System.out.println("No coupon with the given ID exists");
            return null;
        }
        showCouponDetails(coupon);
        return coupon;
    }

    private void showCouponDetails(Coupon coupon) {
        System.out.println("Coupon #" + coupon.getId());
        //System.out.println("Sale status: " + coupon.getSaleStatus().toString());
        System.out.println("Discount percent: " + coupon.getDiscountPercent());
        System.out.println("Maximum possible discount: " + coupon.getDiscountPercent());
        System.out.println("Start time: " + coupon.getStartTime());
        System.out.println("End time: " + coupon.getEndTime());
    }

    private boolean removeCoupon() {
        System.out.print("Enter an ID to remove the related coupon: ");
        String id = scanner.nextLine();
        Coupon coupon = DataManager.shared().getCouponWithId(id);
        if (coupon == null) {
            System.out.println("No coupon with the given ID exists");
            return false;
        }
        DataManager.shared().removeCoupon(coupon);
        System.out.println("Coupon Removed");
        return false;
    }

    private boolean viewAllCoupons() {
        System.out.println("All Coupons");
        for (Coupon coupon : DataManager.shared().getAllCoupons()) {
            System.out.println("#" + coupon.getId() + " - " + coupon.getDiscountPercent() + " percent discount");
        }
        return false;
    }

    private boolean createDiscountCode() {
        System.out.println("New coupon");
        ArrayList<Product> products = new ArrayList<>();
        System.out.println("Enter ID of the products you want to be included in the offer, one in a line, then enter -1 to continue:");
        while (true) {
            String id = scanner.nextLine();
            if (id.equals("-1")) break;
            Product product = DataManager.shared().getProductWithId(id);
            if (product == null) {
                System.out.print("Invalid product ID. Enter the last one correctly again: ");
                continue;
            }
            if (products.contains(product)) {
                System.out.println("Don't enter repeated IDs");
                continue;
            }
            products.add(product);
        }
        System.out.print("Enter coupon's discount percent (between 0 and 100): ");
        int discountPercent = scanner.nextInt();
        System.out.print("Enter coupon's maximum discount amount: ");
        int maximumDiscount = scanner.nextInt();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.print("Enter the coupon's start date in format of yyyy-MM-dd HH:mm: ");
        String startDateInput = scanner.nextLine();
        // TODO: Invalid date??
        LocalDateTime startTime = LocalDateTime.parse(startDateInput, dateFormatter);
        System.out.print("Enter the coupon's end date in format of yyyy-MM-dd HH:mm: ");
        String endDateInput = scanner.nextLine();
        // TODO: Invalid date??
        LocalDateTime endTime = LocalDateTime.parse(endDateInput, dateFormatter);
        System.out.println("Enter the username of customers who could use the coupon. After entering any of the usernames, you will be asked to enter how many times the user can use the coupon. After entering the users, to finalize the coupon generation, enter -1:");
        HashMap<String, Integer> remainingUsagesCount = new HashMap<>();
        while (true) {
            String username = scanner.nextLine();
            if (username.equals("-1")) break;
            Account account = DataManager.shared().getLoggedInAccount();
            if (!(account instanceof Customer)) {
                System.out.print("Invalid customer username. Enter again: ");
                continue;
            }
            if (remainingUsagesCount.containsKey(account.getUsername())) {
                System.out.println("Don't enter repeated usernames");
                continue;
            }
            System.out.print("How many times do you want " + account.getFirstName() + " " + account.getLastName() + " to use this coupon? ");
            int numberOfTimes = scanner.nextInt();
            if (numberOfTimes <= 0) {
                System.out.print("Invalid number of times. Enter the customer's username again: ");
                continue;
            }
            remainingUsagesCount.put(account.getUsername(), numberOfTimes);
        }
        Coupon coupon = new Coupon(DataManager.getNewId(), products, Status.CONFIRMED, discountPercent, maximumDiscount, startTime, endTime, remainingUsagesCount);
        DataManager.shared().addCoupon(coupon);
        System.out.println("Coupon added successfully");
        return false;
    }

    private Request getRequestWithIDFromUser() {
        System.out.print("Enter the request ID: ");
        String id = scanner.nextLine();
        return DataManager.shared().getRequestWithID(id);
    }

    private boolean resolveRequest(boolean shouldAccept) {
        Request request = getRequestWithIDFromUser();
        if (request == null) {
            System.out.println("No such request exists.");
            return false;
        }
        if (shouldAccept) {
            request.fulfill();
            System.out.println("Request Accepted");
        } else {
            DataManager.shared().removeRequest(request);
            System.out.println("Request Declined");
        }
        return false;
    }

    private boolean viewRequestDetails() {
        Request request = getRequestWithIDFromUser();
        if (request == null) {
            System.out.println("No such request exists.");
            return false;
        }
        if (request instanceof AddProductBySellerRequest) {
            System.out.println("Add Product By Seller Request - Seller's username: " + ((AddProductBySellerRequest)request).getSeller().getUsername() + ", Product's ID: " + ((AddProductBySellerRequest)request).getProduct().getProductId());
        } else if (request instanceof AddSaleBySellerRequest) {
            System.out.println("Add Sale By Seller Request - Seller's username: " + ((AddSaleBySellerRequest)request).getSeller().getUsername() + ", Sale's ID: " + ((AddSaleBySellerRequest)request).getSale().getOffId());
        } else if (request instanceof EditProductBySellerRequest) {
            System.out.println("Edit Product By Seller Request - Seller's username: " + ((EditProductBySellerRequest)request).getSeller().getUsername() + ", wants to change details of product #" + ((EditProductBySellerRequest)request).getOldProduct().getProductId());
        } else if (request instanceof EditSaleBySellerRequest) {
            System.out.println("Edit Sale By Seller Request - Seller's username: " + ((EditSaleBySellerRequest)request).getSeller().getUsername() + ", wants to change details of sale #" + ((EditSaleBySellerRequest)request).getOldSale().getOffId());
        } else if (request instanceof SellerRegistrationRequest) {
            System.out.println("Seller Registration Request - Seller's username: " + ((SellerRegistrationRequest)request).getSeller().getUsername() + ", name: " + ((SellerRegistrationRequest)request).getSeller().getFirstName() + " " + ((SellerRegistrationRequest)request).getSeller().getLastName());
        } else {
            System.out.println("Request ID: #" + request.getId());
        }
        return false;
    }

    private boolean viewAllRequests() {
        System.out.println("All Requests");
        for (Request request : DataManager.shared().getAllRequests()) {
            System.out.println("#" + request.getId());
        }
        return false;
    }

    private void showCategoryDetails(Category category) {
        System.out.println("Category #" + category.getId());
        System.out.println("Name: " + category.getName());
        System.out.println("Description: " + category.getDescription());
    }

    private Category viewCategory() {
        System.out.print("Enter category's ID: ");
        String id = scanner.nextLine();
        Category category = DataManager.shared().getCategoryWithId(id);
        if (category == null) {
            System.out.println("No category with the given ID exists");
            return null;
        }
        showCategoryDetails(category);
        return category;
    }

    public void editCategoryName(Category category) {
        System.out.println("Current category name: " + category.getName());
        System.out.print("Enter a new value for category's name ");
        String newValue = scanner.nextLine();
        category.setName(newValue);
    }

    public void editCategoryDescription(Category category) {
        System.out.println("Current category description: " + category.getDescription());
        System.out.print("Enter a new value for category's description ");
        String newValue = scanner.nextLine();
        category.setDescription(newValue);
    }

    private boolean editCategory() {
        Category category = viewCategory();
        if (category == null) return false;
        System.out.println("Enter \"n\" to edit name and \"d\" to edit description");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "n":
                editCategoryName(category);
                break;
            case "d":
                editCategoryDescription(category);
                break;
            default:
                System.out.println("Wrong letter was typed.");
                return false;
        }
        System.out.println("Changed successfully");
        return false;
    }

    private boolean removeCategory() {
        Category category = viewCategory();
        System.out.println("Are you sure you want to remove this category? Type \"y\" for yes and anything else for no.");
        String confirmation = scanner.nextLine().trim();
        if (confirmation.equals("y")) {
            DataManager.shared().removeCategory(category, category.getParentCategory());
        }
        return false;
    }

    private boolean addCategory() {

        return false;
    }

    private boolean viewAllCategories() {
        System.out.println("All Categories");
        for (Category category : DataManager.shared().getAllCategories()) {
            System.out.println("#" + category.getId() + " - " + category.getName());
            System.out.println(category.getDescription());
        }
        return false;
    }

    private boolean removeProduct() {
        System.out.print("Enter the id of the product you want to remove completely: ");
        String id = scanner.nextLine();
        Product product = DataManager.shared().getProductWithId(id);
        if (product == null) {
            System.out.println("No product exists with the given ID");
            return false;
        }
        DataManager.shared().removeProduct(id);
        System.out.println("Product \"" + product.getName() + "\" was removed successfully");
        return false;
    }

    private boolean summaryOfAllProducts() {
        for (Product product : DataManager.shared().getAllProducts()) {
            System.out.println("#" + product.getProductId() + " - " + product.getName());
        }
        return false;
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
