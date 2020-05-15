package view.menus;

import controller.DataManager;
import model.Product;
import model.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductDetailsMenu extends Menu {
    Product currentProduct;
    Seller currentSeller;

    public ProductDetailsMenu(String name, Menu parentMenu, Product currentProduct) {
        super(name, parentMenu);
        this.currentProduct = currentProduct;
        // TODO: If no seller...???
        this.currentSeller = currentProduct.getSellers().get(0);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("Digest", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (digestCommand()) return;
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
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        // TODO: Different count of one product in cart???

        subMenus.put(3, new Menu("Remove from cart", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeFromCart()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
    }

    private boolean addToCart() {
        ArrayList<Product> cart = getCurrentCart();
        if (cart.contains(currentProduct)) {
            System.out.println("Product is already added to cart");
        } else {
            cart.add(currentProduct);
            System.out.println("Product added to the cart successfully");
        }
        return false;
    }

    private boolean removeFromCart() {
        ArrayList<Product> cart = getCurrentCart();
        if (!cart.contains(currentProduct)) {
            System.out.println("Cart doesn't contain this product");
        } else {
            cart.remove(currentProduct);
            System.out.println("Product removed from the cart successfully");
        }
        return false;
    }

    private ArrayList<Product> getCurrentCart() {
        ArrayList<Product> cart;
        if (DataManager.shared().getLoggedInAccount() == null) {
            cart = DataManager.shared().temporaryCart;
        } else {
            cart = DataManager.shared().getLoggedInAccount().cart;
        }
        return cart;
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
        System.out.println("Average score: " + currentProduct.getAverageScore());
        System.out.println("Visit count (including this time): " + currentProduct.getVisitCount());
        return false;
    }

    // TODO: Select Seller not implemented

    @Override
    protected void showHelp() {

    }
}
