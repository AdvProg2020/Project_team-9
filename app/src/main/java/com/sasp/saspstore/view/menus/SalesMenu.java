package com.sasp.saspstore.view.menus;

import com.sasp.saspstore.controller.DataManager;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.Sale;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class SalesMenu extends Menu {
    public SalesMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("Show all sales", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (showAllSalesCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(2, new Menu("Show product details", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (showProductDetailsCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(3, new Menu("View all products in sale", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewAllProductsInSale()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(4, new Menu("Logout", this) {
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

    private boolean viewAllProductsInSale() {
        AllProductsMenu menu = new AllProductsMenu("All Products", this, true);
        menu.show();
        menu.execute();
        return false;
    }

    private boolean showProductDetailsCommand() {
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

    private boolean logoutCommand() {
        DataManager.shared().logout();
        LoginAndRegisterMenu menu = new LoginAndRegisterMenu(null);
        menu.show();
        menu.execute();
        return true;
    }

    private boolean showAllSalesCommand() {
        for (Sale sale : DataManager.shared().getAllSales()) {
            System.out.println("Sale #" + sale.getOffId());
            System.out.println("- Products:");
            for (Product product : sale.getProducts()) {
                System.out.println("\t#" + product.getProductId() + " - " + product.getName());
                double price = (1 - (double)(product.getDiscountPercent())/100) * product.getPrice();
                System.out.println("\t\tPrevious price (with discount): " + price);
                System.out.println("\t\tNew price (after sale): " + (price - sale.getDiscountAmount()));
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println("- Starting from " + sale.getStartTime().format(dateFormatter) + " and ending in " + sale.getEndTime().format(dateFormatter));
        }
        return false;
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
