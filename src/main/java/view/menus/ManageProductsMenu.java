package view.menus;

import controller.DataManager;
import model.Product;
import model.Seller;

public class ManageProductsMenu extends Menu {
    private Seller seller;

    public ManageProductsMenu(String name, Menu parentMenu, Seller seller) {
        super(name, parentMenu);

        this.seller = seller;

        int i = 1;
        subMenus.put(i, new Menu("View Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewProduct();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View Product Buyers", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewProductBuyers();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Edit Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                editProduct();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
    }

    private void viewProduct() {
        showString("Please enter the Product ID:");
        String productId = getString();
        Product product = DataManager.shared().getProductWithId(productId);
        if (product != null && seller.getProducts().contains(product)) {
            showString("Product:");
            showString(product);
        } else {
            showString("Could not find the product");
        }
    }

    private void viewProductBuyers() {
        //TODO show buyers
        showString("Please enter the Product ID:");
        String productId = getString();
        Product product = DataManager.shared().getProductWithId(productId);
    }

    private void editProduct() {
        //TODO edit product
        showString("Please enter the Product ID:");
        String productId = getString();
        Product product = DataManager.shared().getProductWithId(productId);
        if (product != null && seller.getProducts().contains(product)) {
            showString("Product:");
            showString(product);
        } else {
            showString("Could not find the product");
        }
    }

    @Override
    protected void showHelp() {

    }
}
