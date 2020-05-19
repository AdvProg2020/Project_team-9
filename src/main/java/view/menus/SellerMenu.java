package view.menus;

import controller.DataManager;
import controller.Validator;
import model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SellerMenu extends UserMenu {
    public SellerMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        int i = 1;
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(i, new Menu("View Personal Info", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewPersonalInfo();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Edit email", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                editEmail();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Edit First name", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                editFirstName();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Edit last name", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                editLastName();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Edit Phone number", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                editPhoneNumber();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Edit Company Details", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                editCompanyInformation();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Change Password", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                changePassword();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View Company Information", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewCompanyInformation();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View sell history", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewAllSells();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View all selling products", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewAllSellingProducts();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Add Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                addProductCommand();
                scanner.nextLine();
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
                editProductCommand();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Remove Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                removeProductCommand();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Show Categories", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewAllCategories();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View Off Sales", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewOffSales();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Add Off Sale", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                addSale();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Edit Off Sale", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                editSale();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View credit", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewBalance();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Add yourself as a seller for a product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                addYourselfAsASeller();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View product details", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewProductDetails();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View people who have purchased a product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewPurchasorsOfAProduct();
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("All products in detail", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (allProducts()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Logout", this) {
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

    private boolean allProducts() {
        AllProductsMenu menu = new AllProductsMenu("All Products", this, false);
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

    private void viewPurchasorsOfAProduct() {
        System.out.print("Enter the desired product's ID: ");
        String id = scanner.nextLine();
        Product product = DataManager.shared().getProductWithId(id);
        if (product == null) {
            System.out.print("No product with the given ID exists.");
            return;
        }
        for (Log log : DataManager.shared().getAllLogs()) {
            if (log instanceof PurchaseLog && log.getProducts().containsKey(product)) {
                System.out.println(((PurchaseLog) log).getCustomer().getUsername() + " - " + ((PurchaseLog) log).getCustomer().getFirstName() + " " + ((PurchaseLog) log).getCustomer().getLastName());
            }
        }
    }

    private void viewProductDetails() {
        System.out.print("Enter the desired product's ID to go into its details page: ");
        String id = scanner.nextLine();
        Product product = DataManager.shared().getProductWithId(id);
        if (product == null) {
            System.out.print("No product with the given ID exists.");
            return;
        }
        product.incrementVisitCount();
        ProductDetailsMenu menu = new ProductDetailsMenu("Product Details", this, product);
        menu.show();
        menu.execute();
    }

    private void addYourselfAsASeller() {
        Account currentAccount = DataManager.shared().getLoggedInAccount();
        if (!(currentAccount instanceof Seller) || !((Seller)currentAccount).isPermittedToSell()) {
            System.out.println("You are not allowed to make actions by the admin yet");
            return;
        }
        System.out.print("Enter the ID of the product: ");
        String id = scanner.nextLine();
        Product product = DataManager.shared().getProductWithId(id);
        if (product == null) {
            System.out.println("Invalid ID");
            return;
        }
        product.addSeller((Seller) DataManager.shared().getLoggedInAccount());
        DataManager.saveData();
        System.out.println("Done");
    }

    private void editSale() {
        Account currentAccount = DataManager.shared().getLoggedInAccount();
        if (!(currentAccount instanceof Seller) || !((Seller)currentAccount).isPermittedToSell()) {
            System.out.println("You are not allowed to make actions by the admin yet");
            return;
        }
        System.out.print("Enter the ID of the sale you want to edit: ");
        String saleID = scanner.nextLine();
        Sale sale = DataManager.shared().getSaleWithId(saleID);
        if (sale == null) {
            System.out.println("Invalid sale ID");
            return;
        }
        System.out.println("Enter the ID of the products to be included into the sale, and -1 to continue:");
        ArrayList<Product> products = new ArrayList<>();
        while (true) {
            String id = scanner.nextLine();
            if (id.equals("-1")) break;
            Product product = DataManager.shared().getProductWithId(id);
            if (product == null) {
                System.out.print("Invalid ID. Enter again: ");
                continue;
            }
            if (products.contains(product)) {
                System.out.println("Don't enter repeated IDs");
                continue;
            }
            products.add(product);
        }
        System.out.print("Enter the discount amount of the product: ");
        int discountAmount = DataManager.nextInt(scanner);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.print("Enter start time in format of yyyy-MM-dd HH:mm: ");
        String startInput = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startInput, dateFormatter);
        System.out.print("Enter end time in format of yyyy-MM-dd HH:mm: ");
        String endInput = scanner.nextLine();
        LocalDateTime endTime = LocalDateTime.parse(endInput, dateFormatter);
        Sale newSale = new Sale(sale.getOffId(), products, SaleStatus.CONFIRMED, discountAmount, startTime, endTime, (Seller) DataManager.shared().getLoggedInAccount(), DeliveryStatus.ORDERED);
        EditSaleBySellerRequest request = new EditSaleBySellerRequest(DataManager.getNewId(), (Seller) DataManager.shared().getLoggedInAccount(), sale, newSale);
        DataManager.shared().addRequest(request);
        System.out.println("Request sent to admin");
    }

    private void addSale() {
        Account currentAccount = DataManager.shared().getLoggedInAccount();
        if (!(currentAccount instanceof Seller) || !((Seller)currentAccount).isPermittedToSell()) {
            System.out.println("You are not allowed to make actions by the admin yet");
            return;
        }
        System.out.println("Enter the ID of the products to be included into the sale, and -1 to continue:");
        ArrayList<Product> products = new ArrayList<>();
        while (true) {
            String id = scanner.nextLine();
            if (id.equals("-1")) break;
            Product product = DataManager.shared().getProductWithId(id);
            if (product == null) {
                System.out.print("Invalid ID. Enter again: ");
                continue;
            }
            if (products.contains(product)) {
                System.out.println("Don't enter repeated IDs");
                continue;
            }
            products.add(product);
        }
        System.out.print("Enter the discount amount of the product: ");
        int discountAmount = DataManager.nextInt(scanner);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.print("Enter start time in format of yyyy-MM-dd HH:mm: ");
        String startInput = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startInput, dateFormatter);
        System.out.print("Enter end time in format of yyyy-MM-dd HH:mm: ");
        String endInput = scanner.nextLine();
        LocalDateTime endTime = LocalDateTime.parse(endInput, dateFormatter);
        Sale sale = new Sale(DataManager.getNewId(), products, SaleStatus.CONFIRMED, discountAmount, startTime, endTime, (Seller) DataManager.shared().getLoggedInAccount(), DeliveryStatus.ORDERED);
        AddSaleBySellerRequest request = new AddSaleBySellerRequest(DataManager.getNewId(), (Seller) DataManager.shared().getLoggedInAccount(), sale);
        DataManager.shared().addRequest(request);
        System.out.println("Request sent to admin");
    }

    private void removeProductCommand() {
        Account currentAccount = DataManager.shared().getLoggedInAccount();
        if (!(currentAccount instanceof Seller) || !((Seller)currentAccount).isPermittedToSell()) {
            System.out.println("You are not allowed to make actions by the admin yet");
            return;
        }
        System.out.print("Enter the product ID you want to remove: ");
        String id = scanner.nextLine();
        Product product = DataManager.shared().getProductWithId(id);
        if (product == null) {
            System.out.println("Invalid product ID");
            return;
        }
        DataManager.shared().removeProduct(product.getProductId());
        System.out.println("Done");
    }

    private void editProductCommand() {
        Account currentAccount = DataManager.shared().getLoggedInAccount();
        if (!(currentAccount instanceof Seller) || !((Seller)currentAccount).isPermittedToSell()) {
            System.out.println("You are not allowed to make actions by the admin yet");
            return;
        }
        System.out.print("Enter the product ID you want to edit: ");
        String id = scanner.nextLine();
        Product product = DataManager.shared().getProductWithId(id);
        if (product == null) {
            System.out.println("Invalid product ID");
            return;
        }
        System.out.print("Enter the name of the product: ");
        String name = scanner.nextLine();
        System.out.print("Enter the brand of the product: ");
        String brand = scanner.nextLine();
        System.out.print("Enter the price of the product: ");
        int price = DataManager.nextInt(scanner);
        System.out.print("Enter the discount percent of the product: ");
        int discountPercent = DataManager.nextInt(scanner);
        if (discountPercent < 0 || discountPercent > 100) {
            System.out.println("Invalid format");
            return;
        }
        System.out.print("Enter the available number of the product: ");
        int availableNumber = DataManager.nextInt(scanner);
        System.out.print("Enter the category ID of the product: ");
        String categoryID = scanner.nextLine();
        Category category = DataManager.shared().getCategoryWithId(categoryID);
        if (category == null) {
            System.out.println("Invalid category ID");
            return;
        }
        HashMap<String, String> features = new HashMap<>();
        for (String feature : category.getUniqueFeatures()) {
            System.out.print("Enter value for the feature " + feature + ": ");
            String value = scanner.nextLine();
            features.put(feature, value);
        }
        System.out.print("Enter the description of the product: ");
        String description = scanner.nextLine();
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add((Seller) DataManager.shared().getLoggedInAccount());
        Product newProduct = new Product(product.getProductId(), Status.CONFIRMED, name, brand, price, discountPercent, sellers, availableNumber, category, description, LocalDateTime.now(), features);
        EditProductBySellerRequest request = new EditProductBySellerRequest(DataManager.getNewId(), (Seller) DataManager.shared().getLoggedInAccount(), product, newProduct);
        DataManager.shared().addRequest(request);
        System.out.println("Request sent to admin");
    }

    private void addProductCommand() {
        Account currentAccount = DataManager.shared().getLoggedInAccount();
        if (!(currentAccount instanceof Seller) || !((Seller)currentAccount).isPermittedToSell()) {
            System.out.println("You are not allowed to make actions by the admin yet");
            return;
        }
        System.out.print("Enter the name of the product: ");
        String name = scanner.nextLine();
        System.out.print("Enter the brand of the product: ");
        String brand = scanner.nextLine();
        System.out.print("Enter the price of the product: ");
        int price = DataManager.nextInt(scanner);
        System.out.print("Enter the discount percent of the product: ");
        int discountPercent = DataManager.nextInt(scanner);
        if (discountPercent < 0 || discountPercent > 100) {
            System.out.println("Invalid format");
            return;
        }
        System.out.print("Enter the available number of the product: ");
        int availableNumber = DataManager.nextInt(scanner);
        System.out.print("Enter the category ID of the product: ");
        String categoryID = scanner.nextLine();
        Category category = DataManager.shared().getCategoryWithId(categoryID);
        if (category == null) {
            System.out.println("Invalid category ID");
            return;
        }
        HashMap<String, String> features = new HashMap<>();
        for (String feature : category.getUniqueFeatures()) {
            System.out.print("Enter value for the feature " + feature + ": ");
            String value = scanner.nextLine();
            features.put(feature, value);
        }
        System.out.print("Enter the description of the product: ");
        String description = scanner.nextLine();
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add((Seller) DataManager.shared().getLoggedInAccount());
        Product product = new Product(DataManager.getNewId(), Status.CONFIRMED, name, brand, price, discountPercent, sellers, availableNumber, category, description, LocalDateTime.now(), features);
        AddProductBySellerRequest request = new AddProductBySellerRequest(DataManager.getNewId(), (Seller) DataManager.shared().getLoggedInAccount(), product);
        DataManager.shared().addRequest(request);
        System.out.println("Request sent to admin");
    }

    private void viewAllSells() {
        System.out.println("Log of all sells:");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Log log : DataManager.shared().getAllLogs()) {
            if (log instanceof SellLog) {
                System.out.println("Log #" + log.getId());
                System.out.println("Placed on " + log.getDate().format(dateFormatter));
                System.out.println("Total price paid: $" + log.getPrice() + "(having $" + log.getDiscountAmount() + " discount)");
                System.out.println("Purchased products: ");
                for (Map.Entry<Product, Integer> productIntegerEntry : log.getProducts().entrySet()) {
                    System.out.println(((Map.Entry) productIntegerEntry).getValue() + "x\t#" + ((Product) ((Map.Entry) productIntegerEntry).getKey()).getProductId() + " - " + ((Product) ((Map.Entry) productIntegerEntry).getKey()).getName());
                }
            }
        }
    }

    private void viewOffSales() {
        for (Sale sale : DataManager.shared().getAllSales()) {
            System.out.println("Sale #" + sale.getOffId());
            System.out.println("Products:");
            for (Product product : sale.getProducts()) {
                System.out.println("\t" + product.getName());
                double price = (1 - (double)(product.getDiscountPercent())/100) * product.getPrice();
                System.out.println("\tPrevious price (with discount): " + price);
                System.out.println("\tNew price (after sale): " + (price - sale.getDiscountAmount()));
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println("Starting from " + sale.getStartTime().format(dateFormatter) + " and ending in " + sale.getEndTime().format(dateFormatter));
        }
    }

    private void viewCompanyInformation() {
        showString("Company information");
        showString(((Seller) getCurrentUser()).getCompanyDetails());
    }

    private void editCompanyInformation() {
        showEditPrompt("company information");
        getCurrentUser().setLastName(getString());
        declareEditSuccess("company information");
    }

    private void viewBalance() {
        showString("Balance:");
        showString(getCurrentUser().getCredit());
    }

    private void viewAllCategories() {
        showString("All categories:");
        DataManager.shared().getAllCategories().stream().map(category -> "#" + category.getId() + " - " + category.getName()).forEach(System.out::println);
    }

    private void viewAllSellingProducts() {
        showString("Products:");
        DataManager.shared().getAllProducts().stream().map(product -> "#" + product.getProductId() + " - " + product.getName()).forEach(System.out::println);
    }

    @Override
    protected void showHelp() {

    }
}
