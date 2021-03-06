package com.sasp.saspstore.view.menus;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.controller.Validator;
import com.sasp.saspstore.model.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerMenu extends Menu {
    public CustomerMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("View Personal Info", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewPersonalInfo();
                scanner.nextLine();
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
                scanner.nextLine();
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
                scanner.nextLine();
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
                scanner.nextLine();
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
                scanner.nextLine();
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
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(7, new Menu("View cart", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                viewCart();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(8, new Menu("View Product Details", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                showProduct();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(9, new Menu("Checkout", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                checkOut();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(10, new Menu("View credit", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                viewBalance();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(11, new Menu("View usable coupons", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                viewUsableCoupons();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(12, new Menu("View all orders", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                viewOrders();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(13, new Menu("View order details", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                viewOrderDetails();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(14, new Menu("Rate product", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                rateProduct();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(15, new Menu("Comment on product", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                addCommentCommand();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(16, new Menu("Increase credit", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                increaseCredit();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(17, new Menu("All products", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (allProducts()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(18, new Menu("Total price of cart", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (totalPriceOfCart()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(19, new Menu("All sales currently running", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (allSales()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(20, new Menu("Logout", this) {
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

    private boolean allSales() {
        SalesMenu menu = new SalesMenu("All Sales", this);
        menu.show();
        menu.execute();
        return false;
    }

    private boolean totalPriceOfCart() {
        Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
        if (customer.getCart().getProducts().size() == 0) {
            System.out.println("There is no product in your cart yet");
            return false;
        }
        int totalPrice = 0;
        for (Map.Entry<Product, Integer> entry : customer.getCart().getProducts().entrySet()) {
            Product key = entry.getKey();
            Integer value = entry.getValue();
            totalPrice += key.getPrice() * (1 - (double)key.getDiscountPercent()/100) * value;
        }
        System.out.println("Total cart price, including discount: " + totalPrice);
        return false;
    }

    private boolean allProducts() {
        AllProductsMenu menu = new AllProductsMenu("All Products", this, false);
        menu.show();
        menu.execute();
        return false;
    }

    private void increaseCredit() {
        System.out.print("Enter a value to increase credit by that amount: ");
        int amount = DataManager.nextInt(scanner);
        DataManager.shared().getLoggedInAccount().increaseCredit(amount);
        System.out.println("Done");
    }

    protected void viewPersonalInfo() {
        Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
        System.out.println(customer.getFirstName() + " " + customer.getLastName() + " - " + customer.getUsername());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhoneNumber());
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
    }

    private boolean logoutCommand() {
        DataManager.shared().logout();
        LoginAndRegisterMenu menu = new LoginAndRegisterMenu(null);
        menu.show();
        menu.execute();
        return true;
    }

    private void checkOut() {
        if (((Customer) DataManager.shared().getLoggedInAccount()).getCart().getProducts().size() == 0) {
            System.out.println("There is no product in your cart yet");
            return;
        }
        CheckOutMenu menu = new CheckOutMenu("Check out", this);
        menu.show();
        menu.execute();
    }

    private void rateProduct() {
        Customer currentCustomer = (Customer) DataManager.shared().getLoggedInAccount();
        System.out.print("Enter the product ID you want to rate: ");
        String id = scanner.nextLine();
        Product product = DataManager.shared().getProductWithId(id);
        if (!currentCustomer.hasPurchasedProduct(product)) {
            System.out.println("You haven't purchased the product to be able to rate it");
            return;
        }
        System.out.print("Enter a rate from 1 to 5, or 0 to cancel rating: ");
        int rating = DataManager.nextInt(scanner);
        if (rating == 0) {
            System.out.println("Rating canceled");
            return;
        }
        if (rating < 1 || rating > 5) {
            System.out.println("Rating should be in range 1 to 5");
            return;
        }
        product.addScore(rating, currentCustomer);
        System.out.println("Rating was registered successfully");
    }

    private void addCommentCommand() {
        Customer currentCustomer = (Customer) DataManager.shared().getLoggedInAccount();
        System.out.print("Enter the product ID you want to put comment on: ");
        String id = scanner.nextLine();
        Product product = DataManager.shared().getProductWithId(id);
        if (!currentCustomer.hasPurchasedProduct(product)) {
            System.out.println("You haven't purchased the product to be able to submit comments for it");
            return;
        }
        System.out.print("Enter comment's title: ");
        String title = scanner.nextLine();
        System.out.print("Enter comment's content: ");
        String text = scanner.nextLine();
        Comment comment = new Comment(currentCustomer, product, title, text);
        product.addComment(comment);
        System.out.println("Comment was registered successfully and is waiting to be reviewed");
    }

    private void viewUsableCoupons() {
        ArrayList<Coupon> usableCoupons = DataManager.shared().getAllCoupons().stream()
                .filter(coupon -> coupon.remainingUsageCountForAccount(DataManager.shared().getLoggedInAccount()) != 0)
                .collect(Collectors.toCollection(ArrayList::new));
        if (usableCoupons.size() == 0) {
            System.out.println("There are no usable coupons for your account registered");
        } else {
            usableCoupons.stream()
                    .map(usableCoupon -> "#" + usableCoupon.getId())
                    .forEach(System.out::println);
        }
    }

    private void viewCart() {
        Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
        if (customer.getCart().getProducts().size() == 0) {
            System.out.println("There is no product in your cart yet");
            return;
        }
        customer.getCart().getProducts().forEach((key, value) -> System.out.println(value + "x " + key.getName() + "\t$" + key.getPrice() + (value > 1 ? " each" : "")));
    }

    private boolean showProduct() {
        System.out.print("Enter the desired product's ID to go into its details page: ");
        String id = scanner.nextLine();
        Product product = DataManager.shared().getProductWithId(id);
        if (product == null) {
            System.out.print("No product with the given ID exists.");
            return false;
        }
        product.incrementVisitCount();
        ProductDetailsMenu menu = new ProductDetailsMenu("Product Details", this, product);
        menu.show();
        menu.execute();
        return false;
    }

    private void viewOrderDetails() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Customer currentCustomer = (Customer) DataManager.shared().getLoggedInAccount();
        System.out.print("Enter order's ID: ");
        String orderID = scanner.nextLine();
        PurchaseLog purchaseLog = DataManager.shared().purchaseLogForCustomerById(currentCustomer, orderID);
        if (purchaseLog == null) {
            System.out.println("Invalid order ID. Try again.");
            return;
        }
        System.out.println("Order #" + purchaseLog.getId());
        System.out.println("Placed on " + purchaseLog.getDate().format(dateFormatter));
        System.out.println("Delivery status: " + purchaseLog.getDeliveryStatus().toString());
        System.out.println("Total price paid: $" + purchaseLog.getPrice() + " (having $" + purchaseLog.getDiscountAmount() + " discount)");
        System.out.println("Purchased products: ");
        for (Map.Entry<Product, Integer> productIntegerEntry : purchaseLog.getProducts().entrySet()) {
            System.out.println(((Map.Entry) productIntegerEntry).getValue() + "x\t#" + ((Product) ((Map.Entry) productIntegerEntry).getKey()).getProductId() + " - " + ((Product) ((Map.Entry) productIntegerEntry).getKey()).getName());
        }
    }

    private void viewOrders() {
        Customer currentCustomer = (Customer) DataManager.shared().getLoggedInAccount();
        if (DataManager.shared().getAllLogs().size() == 0) System.out.println("You have made no purchases yet");
        else {
            DataManager.shared().getAllLogs().stream()
                    .filter(log -> log instanceof PurchaseLog && ((PurchaseLog) log).getCustomer().getUsername().equals(currentCustomer.getUsername()))
                    .map(log -> "#" + log.getId())
                    .forEach(System.out::println);
        }
    }

    private void viewBalance() {
        System.out.println("Current balance: $" + DataManager.shared().getLoggedInAccount().getCredit());
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
