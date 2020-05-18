package view.menus;

import controller.DataManager;
import model.Product;
import model.Sale;

import java.util.HashMap;

public class SaleMenu extends Menu {
    public SaleMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("Show all sales", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (showAllSalesCommand()) return;
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
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        this.setSubMenus(subMenus);
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

    private boolean showAllSalesCommand() {
        for (Sale sale : DataManager.shared().getAllSales()) {
            System.out.println("Sale #" + sale.getOffId());
            System.out.println("Products:");
            for (Product product : sale.getProducts()) {
                System.out.println("\t" + product.getName());
                double price = (1 - (double)(product.getDiscountPercent())/100) * product.getPrice();
                System.out.println("\tPrevious price (with discount): " + price);
                System.out.println("\tNew price (after sale): " + (price - sale.getDiscountAmount()));
            }
            // TODO: Sale start and end time and delivery status...
            System.out.println("Sale status: " + sale.getSaleStatus().toString() + "\n");
        }
        return false;
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
