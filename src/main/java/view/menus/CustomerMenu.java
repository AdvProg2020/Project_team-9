package view.menus;

import controller.DataManager;
import model.*;

import java.util.HashMap;

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
        subMenus.put(3, new Menu("View PurchaseLog",this) {
            @Override
            public void execute() {
                viewPurchaseLog();
            }

            @Override
            protected void showHelp() {

            }
        })

    }

    private void viewCart() {
        Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
        for (int counter = 0; counter < customer.getCart().getProducts().size(); counter++) {
            if (customer.getCart().getProducts().size() == 0) {
                System.out.println("There is no product in your cart yet");
                break;
            }
            System.out.println((counter+1) + ". " + customer.getCart().getProducts().get(counter));
        }
    }

    private void showProduct() {
        System.out.println("Enter the product id you want to show:");
        String id = scanner.next();
        currentProduct = DataManager.shared().getProductWithId(id);
        if (currentProduct == null) {
            System.out.println("No product exists with the given ID");
            return;
        }
        currentProduct.incrementVisitCount();
        new ProductDetailsMenu("ProductDetailsMenu", parentMenu, currentProduct);
    }


    private void viewPurchaseLog() {
        Customer customer = (Customer)DataManager.shared().getLoggedInAccount();
        // TODO : need to be completed
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
