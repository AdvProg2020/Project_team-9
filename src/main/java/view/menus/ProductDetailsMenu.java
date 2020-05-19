package view.menus;

import controller.DataManager;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ProductDetailsMenu extends Menu {
    Product currentProduct;
    Seller currentSeller;

    // TODO: Category-based attribs???

    public ProductDetailsMenu(String name, Menu parentMenu, Product currentProduct) {
        super(name, parentMenu);
        this.currentProduct = currentProduct;
        this.currentSeller = currentProduct.getSellers().get(0);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("Digest", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (digestCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(2, new Menu("Add to cart", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (addToCart()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(3, new Menu("Remove from cart", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeFromCart()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(4, new Menu("Select seller", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (selectSeller()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(5, new Menu("Filter by attributes", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (attributesCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(6, new Menu("Compare", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (compareCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(7, new Menu("View comments", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewCommentsCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(8, new Menu("Add comment", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (addCommentCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        if (DataManager.shared().getLoggedInAccount() != null) {
            subMenus.put(9, new Menu("Logout", this) {
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
        }

        this.setSubMenus(subMenus);
    }

    // TODO: Replace all scanner inputs with better equivalents

    private boolean addCommentCommand() {
        if (DataManager.shared().getLoggedInAccount() == null || !(DataManager.shared().getLoggedInAccount() instanceof Customer)) {
            System.out.println("You should log in as a customer to post comments");
            return false;
        }
        System.out.print("Enter comment's title: ");
        String title = scanner.nextLine();
        System.out.print("Enter comment's content: ");
        String text = scanner.nextLine();
        Comment comment = new Comment((Customer) DataManager.shared().getLoggedInAccount(), currentProduct, title, text);
        currentProduct.addComment(comment);
        // TODO: Save data???
        System.out.println("Comment was registered successfully and is waiting to be reviewed");
        return false;
    }

    private boolean viewCommentsCommand() {
        System.out.println("All comments:");
        currentProduct.getComments().stream()
                .filter(comment -> comment.getCommentStatus() == CommentStatus.CONFIRMED)
                .map(comment -> comment.getCustomer().getFirstName() + " "
                + comment.getCustomer().getLastName() + " (has" + (comment.hasUserPurchasedProduct() ? "n't" : "") + " purchased the product)"
                        + " said: \n" + comment.getTitle().toUpperCase() + "\n" + comment.getText())
                .forEach(System.out::println);
        return false;
    }

    private boolean logoutCommand() {
        DataManager.shared().logout();
        LoginAndRegisterMenu menu = new LoginAndRegisterMenu(null);
        menu.show();
        menu.execute();
        return true;
    }

    private boolean compareCommand() {
        System.out.print("Enter another product ID: ");
        String id = getString();
        Product product = DataManager.shared().getProductWithId(id);
        if (product == null) {
            System.out.println("No such product exists");
            return false;
        }
        Category category = product.getCategory();
        if (!category.equals(currentProduct.getCategory())) {
            System.out.println("You can only compare products from the same category");
            return false;
        }
        System.out.println("#" + currentProduct.getProductId());
        System.out.println("#" + product.getProductId());
        System.out.println();
        System.out.println("Name:");
        System.out.println(currentProduct.getName());
        System.out.println(product.getName());
        System.out.println();
        System.out.println("Brand: ");
        System.out.println(currentProduct.getBrand());
        System.out.println(product.getBrand());
        System.out.println();
        System.out.println("Price: ");
        System.out.println(currentProduct.getPrice());
        System.out.println(product.getPrice());
        System.out.println();
        System.out.println("Discount percent: ");
        System.out.println(currentProduct.getDiscountPercent());
        System.out.println(product.getDiscountPercent());
        System.out.println();
        System.out.println("Number available: ");
        System.out.println(currentProduct.getNumberAvailable());
        System.out.println(product.getNumberAvailable());
        System.out.println();
        for (String feature : category.getUniqueFeatures()) {
            System.out.println(feature + ": ");
            System.out.println(currentProduct.getFeatures().get(feature));
            System.out.println(product.getFeatures().get(feature) + "\n");
        }
        System.out.println("Description: ");
        System.out.println(currentProduct.getDescription());
        System.out.println(product.getDescription());
        System.out.println();
        return false;
    }

    public boolean attributesCommand() {
        System.out.println("Product #" + currentProduct.getProductId());
        System.out.println("Name: " + currentProduct.getName());
        System.out.println("Brand: " + currentProduct.getBrand());
        System.out.println("Price: " + currentProduct.getPrice());
        System.out.println("Discount percent: " + currentProduct.getDiscountPercent());
        System.out.println("Status: " + currentProduct.getStatus().toString());
        System.out.println("Number available: " + currentProduct.getNumberAvailable());
        System.out.println("Category attributes:");
        currentProduct.getCategory().getUniqueFeatures().stream().map(feature -> feature + ": " + currentProduct.getFeatures().get(feature)).forEach(System.out::println);
        return false;
    }

    private boolean selectSeller() {
        System.out.println("All available sellers: ");
        currentProduct.getSellers().stream()
                .map(seller -> seller.getUsername() + " - " + seller.getFirstName() + " " + seller.getLastName())
                .forEach(System.out::println);
        System.out.println("Current seller: " + currentSeller.getUsername() + " - " + currentSeller.getFirstName() + " " + currentSeller.getLastName());
        System.out.print("Type the username of the seller you want to select: ");
        String username = scanner.nextLine();
        for (Seller seller : currentProduct.getSellers()) {
            if (seller.getUsername().equals(username)) {
                currentSeller = seller;
                System.out.println("Seller successfully changed");
                return false;
            }
        }
        System.out.println("The username you entered isn't a seller of this product.");
        return false;
    }

    private boolean addToCart() {
        currentProduct.setCurrentSeller(currentSeller);
        getCurrentCart().addProduct(currentProduct);
        DataManager.saveData();
        System.out.println("Product added to the cart successfully");
        return false;
    }

    private boolean removeFromCart() {
        Cart cart = getCurrentCart();
        if (!cart.containsProduct(currentProduct)) {
            System.out.println("Cart doesn't contain this product");
        } else {
            cart.removeProduct(currentProduct);
            System.out.println("Product removed from the cart successfully");
        }
        return false;
    }


    private Cart getCurrentCart() {
        if (DataManager.shared().getLoggedInAccount() == null) {
            return DataManager.shared().getTemporaryCart();
        } else {
            if (DataManager.shared().getLoggedInAccount() != null && (DataManager.shared().getLoggedInAccount() instanceof Customer)) {
                return ((Customer)(DataManager.shared().getLoggedInAccount())).getCart();
            }
        }
        return new Cart();
    }

    private boolean digestCommand() {
        System.out.println("Product #" + currentProduct.getProductId());
        System.out.println("Name: " + currentProduct.getName());
        System.out.println("Price: " + currentProduct.getPrice());
        System.out.println("Discount percent: " + currentProduct.getDiscountPercent());
        System.out.println("Category: " + currentProduct.getCategory().getName());
        System.out.println("Sellers:");
        currentProduct.getSellers().stream()
                .map(seller -> seller.getFirstName() + " " + seller.getLastName())
                .forEach(System.out::println);
        System.out.println("Current seller: " + currentSeller.getFirstName() + " " + currentSeller.getLastName()
                + " (you can easily change the current seller using the \"Select seller\" command");
        System.out.println("Average score: " + currentProduct.getAverageScore());
        System.out.println("Visit count (including this time): " + currentProduct.getVisitCount());
        return false;
    }

    @Override
    protected void showHelp() {

    }
}
