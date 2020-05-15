package view.menus;

import controller.DataManager;
import model.Customer;
import model.Product;
import model.Score;

import java.util.HashMap;
import java.util.UUID;

public class CustomerMenu extends Menu {
    public CustomerMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer,Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("view cart",this) {
            @Override
            public void execute() {
                viewCart();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp(){
            }
        });
    }


    private void viewCart() {
        Customer customer = (Customer) DataManager.shared().getLoggedInAccount();
        for (Product product : customer.getCart()) {
            if (customer.getCart() == null){
                System.out.println("There is no product in your cart yet");
                break;
            }
            int counter = 0;
            counter++;
            System.out.println(counter +". "+product);
        }
    }

    private void showProduct(Product product) {
        System.out.println("Product #" + product.getProductId());
        System.out.println("Name: " + product.getName());
        System.out.println("Brand: " + product.getBrand());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Discount percent: " + product.getDiscountPercent());
        System.out.println("Status: " + product.getStatus().toString());
        System.out.println("Number available: " + product.getNumberAvailable());
    }

    private void startSubmittingScoreForProduct(Product product) {
        System.out.println("Please enter your score to this product :");
        int newScore =scanner.nextInt();
        // TODO : check regex
        Score score = new Score(UUID.randomUUID().toString(),(Customer)DataManager.shared().getLoggedInAccount(),newScore);
        product.addScore(score);
    }

    private void startSubmittingCommentForProduct(Product product) {
    }

    private void incrementProductCountInCart(Product product) {
    }

    private void decrementProductCountInCart(Product product) {
    }

    private void viewPurchaseLog() {
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
