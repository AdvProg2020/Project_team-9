package view.menus;

import controller.DataManager;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerMenu extends Menu {
    public CustomerMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("View cart", this) {
            @Override
            public void execute() {
                viewCart();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        subMenus.put(2, new Menu("View Product Details", this) {
            @Override
            public void execute() {
                showProduct();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(4, new Menu("Checkout", this) {
            @Override
            public void execute() {
                checkOut();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(5, new Menu("View balance", this) {
            @Override
            public void execute() {
                viewBalance();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(6, new Menu("View usable coupons", this) {
            @Override
            public void execute() {
                viewUsableCoupons();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(7, new Menu("View all orders", this) {
            @Override
            public void execute() {
                viewOrders();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(8, new Menu("View order details", this) {
            @Override
            public void execute() {
                viewOrderDetails();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(9, new Menu("Rate product", this) {
            @Override
            public void execute() {
                rateProduct();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(10, new Menu("Comment on product", this) {
            @Override
            public void execute() {
                addCommentCommand();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(11, new Menu("Logout", this) {
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

    private void checkOut() {
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
        customer.getCart().getProducts().forEach((key, value) -> System.out.println(value + "x " + key.getName() + "\t$" + key.getPrice()));
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
        Customer currentCustomer = (Customer) DataManager.shared().getLoggedInAccount();
        System.out.print("Enter order's ID: ");
        String orderID = scanner.nextLine();
        PurchaseLog purchaseLog = DataManager.shared().purchaseLogForCustomerById(currentCustomer, orderID);
        if (purchaseLog == null) {
            System.out.println("Invalid order ID. Try again.");
            return;
        }
        System.out.println("Order #" + purchaseLog.getId());
        System.out.println("Placed on " + purchaseLog.getDate().toString());
        System.out.println("Delivery status: " + purchaseLog.getDeliveryStatus().toString());
        System.out.println("Total price paid: $" + purchaseLog.getPrice() + "(having $" + purchaseLog.getDiscountAmount() + " discount)");
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
