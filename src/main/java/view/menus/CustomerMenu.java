package view.menus;

import controller.DataManager;
import model.Comment;
import model.Customer;
import model.Product;
import model.Score;

import java.util.HashMap;
import java.util.UUID;

public class CustomerMenu extends Menu {
    Product currentProduct;

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
        subMenus.put(2, new Menu("Show Product", this) {
            @Override
            public void execute() {
                showProduct();
            }

            @Override
            protected void showHelp() {

            }
        });
        subMenus.put(3, new Menu("Increment Product's Number In Cart", this) {
            @Override
            public void execute() {
                incrementProductCountInCart();
            }

            @Override
            protected void showHelp() {

            }
        })
    }

    private void viewCart() {
        Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
        for (Product product : customer.getCart()) {
            if (customer.getCart() == null) {
                System.out.println("There is no product in your cart yet");
                break;
            }
            int counter = 0;
            counter++;
            System.out.println(counter + ". " + product);
        }
    }

    private void showProduct() {
        System.out.println("Enter the product id you want to show:");
        int id = scanner.nextInt();
        currentProduct = DataManager.shared().productWithID(id);
        if (currentProduct == null) {
            System.out.println("No product exists with the given ID");
            return;
        }
        new ProductDetailsMenu("ProductDetailsMenu", parentMenu, currentProduct);
    }


    private void incrementProductCountInCart() {

    }

    private void decrementProductCountInCart() {
    }

    private void viewPurchaseLog() {
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
