package view.menus;

import controller.DataManager;
import controller.Validator;
import model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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
                viewPersonalInfo();
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
                editFirstName();
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
                editLastName();
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
                editEmail();
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
                editPhoneNumber();
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
                changePassword();
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
                viewUserInfo();
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

        subMenus.put(26, new Menu("Review comments", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (reviewComments()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(27, new Menu("Accept comment", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (acceptComment()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(28, new Menu("Decline comment", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (declineComment()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(29, new Menu("All products in detail", this) {
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

        subMenus.put(30, new Menu("Logout", this) {
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

    private boolean allProducts() {
        AllProductsMenu menu = new AllProductsMenu("All Products", this);
        menu.show();
        menu.execute();
        return false;
    }

    private boolean acceptComment() {
        System.out.print("Enter the comment's ID to accept: ");
        String id = scanner.nextLine();
        AtomicBoolean hasFound = new AtomicBoolean(false);
        DataManager.shared().getAllProducts().stream().flatMap(product -> product.getComments().stream()).filter(comment -> comment.getId().equals(id)).forEach(comment -> {
            comment.setCommentStatus(CommentStatus.CONFIRMED);
            System.out.println("Done");
            hasFound.set(true);
        });
        if (!hasFound.get()) {
            System.out.println("No comment exists with the given ID");
        }
        return false;
    }

    private boolean declineComment() {
        System.out.print("Enter the comment's ID to decline: ");
        String id = scanner.nextLine();
        AtomicBoolean hasFound = new AtomicBoolean(false);
        DataManager.shared().getAllProducts().stream().flatMap(product -> product.getComments().stream()).filter(comment -> comment.getId().equals(id)).forEach(comment -> {
            comment.setCommentStatus(CommentStatus.DISALLOWED);
            System.out.println("Done");
            hasFound.set(true);
        });
        if (!hasFound.get()) {
            System.out.println("No comment exists with the given ID");
        }
        return false;
    }

    private boolean reviewComments() {
        System.out.println("All comments waiting for review:");
        DataManager.shared().getAllProducts().stream().flatMap(product -> product.getComments().stream()).filter(comment -> comment.getCommentStatus() == CommentStatus.WAITING_FOR_REVIEW).map(comment -> "#" + comment.getId() + " - " + comment.getCustomer().getUsername() + " wrote about product " + comment.getProduct().getName() + ": *" + comment.getTitle() + "*: " + comment.getText()).forEach(System.out::println);
        return false;
    }

    private boolean logoutCommand() {
        DataManager.shared().logout();
        LoginAndRegisterMenu menu = new LoginAndRegisterMenu(null);
        menu.show();
        menu.execute();
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
            int newValue = DataManager.nextInt(scanner);
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
        int newValue = DataManager.nextInt(scanner);
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Coupon #" + coupon.getId());
        System.out.println("Discount percent: " + coupon.getDiscountPercent());
        System.out.println("Maximum possible discount: " + coupon.getMaximumDiscount());
        System.out.println("Start time: " + coupon.getStartTime().format(dateFormatter));
        System.out.println("End time: " + coupon.getEndTime().format(dateFormatter));
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
        if (DataManager.shared().getAllCoupons().size() == 0) {
            System.out.println("No coupon exists");
            return false;
        }
        System.out.println("All Coupons");
        for (Coupon coupon : DataManager.shared().getAllCoupons()) {
            System.out.println("#" + coupon.getId() + " - " + coupon.getDiscountPercent() + " percent discount");
        }
        return false;
    }

    private boolean createDiscountCode() {
        System.out.println("New coupon");
        ArrayList<Product> products = getProductsListFromUser();
        System.out.print("Enter coupon's discount percent (between 0 and 100): ");
        int discountPercent = DataManager.nextInt(scanner);
        System.out.print("Enter coupon's maximum discount amount: ");
        int maximumDiscount = DataManager.nextInt(scanner);
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
            Account account = DataManager.shared().getAccountWithGivenUsername(username);
            if (!(account instanceof Customer)) {
                System.out.print("Invalid customer username. Enter again: ");
                continue;
            }
            if (remainingUsagesCount.containsKey(account.getUsername())) {
                System.out.println("Don't enter repeated usernames");
                continue;
            }
            System.out.print("How many times do you want " + account.getFirstName() + " " + account.getLastName() + " to use this coupon? ");
            int numberOfTimes = DataManager.nextInt(scanner);
            if (numberOfTimes <= 0) {
                System.out.print("Invalid number of times. Enter the customer's username again: ");
                continue;
            }
            remainingUsagesCount.put(account.getUsername(), numberOfTimes);
        }
        String couponID = DataManager.getNewId();
        Coupon coupon = new Coupon(couponID, products, Status.CONFIRMED, discountPercent, maximumDiscount, startTime, endTime, remainingUsagesCount);
        DataManager.shared().addCoupon(coupon);
        System.out.println("Coupon added successfully with code " + couponID);
        return false;
    }

    private ArrayList<Product> getProductsListFromUser() {
        ArrayList<Product> products = new ArrayList<>();
        System.out.println("Enter ID of the products you want to be included, one in a line, then enter -1 to continue:");
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
        return products;
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
            DataManager.shared().removeRequest(request);
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
        if (confirmation.equals("y") && category != null) {
            DataManager.shared().removeCategory(category, category.getParentCategory());
        }
        return false;
    }

    private boolean addCategory() {
        System.out.print("Enter category's name: ");
        String name = scanner.nextLine();
        System.out.println("Enter a description for the category: ");
        String description = scanner.nextLine();
        Category parent;
        while (true) {
            System.out.print("Enter the ID of the parent category (-1 for no parent): ");
            String parentID = scanner.nextLine();
            parent = DataManager.shared().getCategoryWithId(parentID);
            if (parent == null && !parentID.equals("-1")) {
                System.out.print("Invalid category ID. Try again: ");
                continue;
            }
            break;
        }
        String categoryID = DataManager.getNewId();
        String parentGetID = "";
        if (parent != null) parentGetID = parent.getId();
        Category category = new Category(categoryID, name, description, parentGetID);
        DataManager.shared().addCategory(category);
        System.out.println("Successfully added category with ID #" + categoryID);
        return false;
    }

    private boolean viewAllCategories() {
        System.out.println("All Categories");
        for (Category category : DataManager.shared().getAllCategories()) {
            System.out.println("#" + category.getId() + " - " + category.getName());
            System.out.println("\t" + category.getDescription());
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
        DataManager.shared().getAllProducts().stream().map(product -> "#" + product.getProductId() + " - " + product.getName()).forEach(System.out::println);
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

    protected void viewPersonalInfo() {
        Administrator administrator = (Administrator) DataManager.shared().getLoggedInAccount();
        System.out.println(administrator.getFirstName() + " " + administrator.getLastName() + " - " + administrator.getUsername());
        System.out.println("Email: " + administrator.getEmail());
        System.out.println("Phone: " + administrator.getPhoneNumber());
    }

    protected void viewUserInfo() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        Account account = DataManager.shared().getAccountWithGivenUsername(username);
        if (account == null) {
            System.out.println("No account with the given username exists");
            return;
        }
        if (account instanceof Customer) System.out.println("Customer");
        else if (account instanceof Administrator) System.out.println("Administrator");
        else if (account instanceof Seller) System.out.println("Seller");
        System.out.println(account.getFirstName() + " " + account.getLastName());
        System.out.println("Email: " + account.getEmail());
        System.out.println("Phone: " + account.getPhoneNumber());
        if (account instanceof Seller) System.out.println("Company Details: " + ((Seller) account).getCompanyDetails());
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
        int result = DataManager.nextInt(scanner);
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

    protected void editEmail() {
        System.out.print("Enter your new email address: ");
        String email = scanner.nextLine();
        if (Validator.shared().emailIsValid(email)) {
            DataManager.shared().getLoggedInAccount().setEmail(email);
            System.out.println("Done");
        } else {
            System.out.println("Invalid email");
        }
    }

    protected void editFirstName() {
        System.out.print("Enter your new first name: ");
        String firstName = scanner.nextLine();
        DataManager.shared().getLoggedInAccount().setFirstName(firstName);
        System.out.println("Done");
    }

    protected void editLastName() {
        System.out.print("Enter your new last name: ");
        String lastName = scanner.nextLine();
        DataManager.shared().getLoggedInAccount().setLastName(lastName);
        System.out.println("Done");
    }

    protected void editPhoneNumber() {
        System.out.print("Enter your new phone number: ");
        String phone = scanner.nextLine();
        if (Validator.shared().phoneNumberIsValid(phone)) {
            DataManager.shared().getLoggedInAccount().setPhoneNumber(phone);
            System.out.println("Done");
        } else {
            System.out.println("Invalid phone number");
        }
    }

    protected void changePassword() {
        System.out.print("Enter your old password: ");
        String oldPassword = scanner.nextLine();
        if (!DataManager.shared().getLoggedInAccount().getPassword().equals(oldPassword)) {
            System.out.println("Your password is wrong. Try again.");
            return;
        }
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        if (oldPassword.equals(newPassword)) {
            System.out.println("Your new password should be different from the previous password. Try again.");
            return;
        }
        DataManager.shared().getLoggedInAccount().setPassword(newPassword);
        System.out.println("New password has been set");
        return;
    }

    @Override
    protected void showHelp() {

    }
}
