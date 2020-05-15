package view.menus;

import model.Product;
import model.Seller;

import java.util.HashMap;

public class ProductDetailsMenu extends Menu {
    Product currentProduct;

    public ProductDetailsMenu(String name, Menu parentMenu, Product currentProduct) {
        super(name, parentMenu);
        this.currentProduct = currentProduct;
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
