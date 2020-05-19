package view.menus;

import controller.DataManager;
import model.Category;
import model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class AllProductsMenu extends Menu {

    private ArrayList<Product> currentProducts = new ArrayList<>();

    private void initProducts() {
        if (shouldOnlyShowOnSaleProducts) {
            currentProducts = DataManager.shared().getAllSales().stream().flatMap(sale -> sale.getProducts().stream()).distinct().collect(Collectors.toCollection(ArrayList::new));
        } else {
            currentProducts = DataManager.shared().getAllProducts();
        }
    }

    private ArrayList<Category> filteredCategories = new ArrayList<>();
    private HashMap<String, String> filteredAttributes = new HashMap<>();
    private String nameFilter = "";
    private String descriptionFilter = "";
    private int priceFilter = 0;
    private String brandFilter = "";
    private String sellerFilter = "";
    private String availabilityFilter = ""; // "" / "available" / "notAvailable"
    private boolean shouldOnlyShowOnSaleProducts;

    private enum SortingMethod {VISIT_COUNT, NAME, PRICE, SCORE, TIME}

    private SortingMethod sortingMethod = SortingMethod.VISIT_COUNT;

    public AllProductsMenu(String name, Menu parentMenu, boolean shouldOnlyShowOnSaleProducts) {
        super(name, parentMenu);
        this.shouldOnlyShowOnSaleProducts = shouldOnlyShowOnSaleProducts;
        initProducts();
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("View Main Categories", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewMainCategoriesCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(2, new Menu("Show Products", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewAllProductsCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(3, new Menu("Show available filters", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (showAvailableFiltersCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(4, new Menu("Show current filters", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (showCurrentFiltersCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(5, new Menu("Filter by category", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (filterByCategoryCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(6, new Menu("Filter by name", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (filterByNameCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(7, new Menu("Filter by description", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (filterByDescriptionCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(8, new Menu("Filter by price", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (filterByPriceCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(9, new Menu("Filter by brand", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (filterByBrandCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(10, new Menu("Filter by seller", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (filterBySellerCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(11, new Menu("Filter by availability", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (filterByAvailabilityCommand()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(12, new Menu("Filter by attributes", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (filterByAttributes()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(13, new Menu("Remove attributes filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeFilterByAttributes()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(14, new Menu("Remove name filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeNameFilter()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(15, new Menu("Remove description filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeDescriptionFilter()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(16, new Menu("Remove brand filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeBrandFilter()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(17, new Menu("Remove Seller filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeSellerFilter()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(18, new Menu("Remove availability filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeAvailabilityFilter()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(19, new Menu("Remove price filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removePriceFilter()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(20, new Menu("Remove category filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeCategoryFilter()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(21, new Menu("Show available sorting methods", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (showAvailableSortingMethods()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(22, new Menu("Sort by visit count", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (setSortingMethod(SortingMethod.VISIT_COUNT)) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(23, new Menu("Sort by name", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (setSortingMethod(SortingMethod.NAME)) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(24, new Menu("Sort by price", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (setSortingMethod(SortingMethod.PRICE)) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(25, new Menu("View current sort method", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewCurrentSortMethod()) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(26, new Menu("Disable sort method", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (setSortingMethod(SortingMethod.VISIT_COUNT)) return;
                scanner.nextLine();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(27, new Menu("Show product details", this) {
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

        if (DataManager.shared().getLoggedInAccount() != null) {
            subMenus.put(28, new Menu("Logout", this) {
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

    private boolean filterByAttributes() {
        System.out.print("To filter by one of the category attributes, enter its name: ");
        String feature = scanner.nextLine();
        System.out.print("Enter the desired value: ");
        String value = scanner.nextLine();
        filteredAttributes.put(feature, value);
        System.out.println("Filtered by the attribute");
        return false;
    }

    private boolean removeFilterByAttributes() {
        filteredAttributes = new HashMap<>();
        System.out.println("Done");
        return false;
    }

    private boolean logoutCommand() {
        DataManager.shared().logout();
        LoginAndRegisterMenu menu = new LoginAndRegisterMenu(null);
        menu.show();
        menu.execute();
        return true;
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
        ProductDetailsMenu productDetailsMenu = new ProductDetailsMenu("Product Details", this, product);
        productDetailsMenu.show();
        productDetailsMenu.execute();
        return false;
    }

    private boolean viewCurrentSortMethod() {
        switch (sortingMethod) {
            case VISIT_COUNT:
                System.out.println("Sorted by visit count (default sorting method)");
                break;
            case NAME:
                System.out.println("Sorted by name");
                break;
            case PRICE:
                System.out.println("Sorted by price");
                break;
            case SCORE:
                System.out.println("Sorted by score");
                break;
            case TIME:
                System.out.println("Sorted by date created");
                break;
        }
        return false;
    }

    private boolean setSortingMethod(SortingMethod sortingMethod) {
        this.sortingMethod = sortingMethod;
        viewCurrentSortMethod();
        return false;
    }

    private boolean showAvailableSortingMethods() {
        System.out.println("You can type \"Sort by visit count\" (which is the default sorting method"
                + "and therefor by sorting using visit count, other sorting methods are removed), \"Sort by name\", and \"Sort by price\".");
        return false;
    }

    private boolean viewAllProductsCommand() {
        findFilteredAndSortedProducts().stream()
                .map(product -> "#" + product.getProductId() + " - " + product.getName())
                .forEach(System.out::println);
        return false;
    }

    private ArrayList<Product> findFilteredAndSortedProducts() {
        ArrayList<Product> currentProducts = new ArrayList<>();
        findFilteredProducts(currentProducts);
        findSortedProducts(currentProducts);
        return currentProducts;
    }

    private void findSortedProducts(ArrayList<Product> currentProducts) {
        switch (sortingMethod) {
            case VISIT_COUNT:
                currentProducts.sort(Comparator.comparingInt(Product::getVisitCount));
                break;
            case NAME:
                // TODO: Does the lambda structure work for name??
                currentProducts.sort(Comparator.comparing(Product::getName));
                break;
            case PRICE:
                currentProducts.sort(Comparator.comparingInt(Product::getPrice));
                break;
            case SCORE:
                currentProducts.sort(Comparator.comparingDouble(Product::getAverageScore).reversed());
                break;
            case TIME:
                // TODO: Does the lambda structure work for date??
                currentProducts.sort(Comparator.comparing(Product::getDateCreated).reversed());
                break;
        }
    }

    private void findFilteredProducts(ArrayList<Product> currentProducts) {
        addToCurrentProducts:
        for (Product product : this.currentProducts) {
            if (!nameFilter.equals("") && !product.getName().contains(nameFilter)) continue;
            if (!descriptionFilter.equals("") && !product.getDescription().contains(descriptionFilter)) continue;
            if (!brandFilter.equals("") && !product.getBrand().equals(brandFilter)) continue;
            if (!availabilityFilter.equals("")) {
                if (availabilityFilter.equals("available") && product.getNumberAvailable() == 0 || availabilityFilter.equals("notAvailable") && product.getNumberAvailable() != 0)
                    continue;
            }
            if (!sellerFilter.equals("") && product.getSellers().stream().noneMatch(seller -> seller.getUsername().equals(sellerFilter)))
                continue;
            if (priceFilter != 0 && product.getPrice() != priceFilter) continue;
            if (filteredCategories.isEmpty()) {
                if (filteredAttributes.isEmpty()) {
                    currentProducts.add(product);
                } else {
                    boolean flag = true;
                    for (Map.Entry<String, String> stringStringEntry : filteredAttributes.entrySet()) {
                        Map.Entry pair = stringStringEntry;
                        if (!product.getFeatures().get(pair.getKey()).equals(pair.getValue())) flag = false;
                    }
                    if (flag) currentProducts.add(product);
                }
                continue;
            }
            for (Category category : filteredCategories) {
                if (product.getCategory() == category) {
                    boolean flag = true;
                    for (Map.Entry<String, String> stringStringEntry : filteredAttributes.entrySet()) {
                        Map.Entry pair = stringStringEntry;
                        if (!product.getFeatures().get(pair.getKey()).equals(pair.getValue())) flag = false;
                    }
                    if (flag) currentProducts.add(product);
                    continue addToCurrentProducts;
                }
            }
        }
    }

    private boolean removeCategoryFilter() {
        System.out.println("Current categories filtered by:");
        filteredCategories.stream().map(category -> "#" + category.getId() + " - " + category.getName())
                .forEach(System.out::println);
        System.out.print("Enter a category ID to remove it from filtered-by categories, or -1 to clear category filter: ");
        String id = scanner.nextLine();
        if (id.equals("-1")) {
            filteredCategories = new ArrayList<>();
            System.out.println("Category filter was successfully removed");
            return false;
        }
        Category category = DataManager.shared().getCategoryWithId(id);
        if (category == null) {
            System.out.println("No category with the given ID exists");
            return false;
        }
        if (!filteredCategories.contains(category)) {
            System.out.println("The entered ID was not found in filtered categories");
            return false;
        }
        filteredCategories.remove(category);
        System.out.println("Category " + category.getName() + " was successfully removed from filtered categories");
        return false;
    }

    private boolean removeNameFilter() {
        nameFilter = "";
        System.out.println("Name filter was successfully removed");
        return false;
    }

    private boolean removeDescriptionFilter() {
        descriptionFilter = "";
        System.out.println("Description filter was successfully removed");
        return false;
    }

    private boolean removeBrandFilter() {
        brandFilter = "";
        System.out.println("Brand filter was successfully removed");
        return false;
    }

    private boolean removeSellerFilter() {
        sellerFilter = "";
        System.out.println("Seller filter was successfully removed");
        return false;
    }

    private boolean removeAvailabilityFilter() {
        availabilityFilter = "";
        System.out.println("Availability filter was successfully removed");
        return false;
    }

    private boolean removePriceFilter() {
        priceFilter = 0;
        System.out.println("Price filter was successfully removed");
        return false;
    }

    private boolean showCurrentFiltersCommand() {
        if (!nameFilter.equals("")) {
            System.out.println("Filtered by name = " + nameFilter);
        }
        if (!descriptionFilter.equals("")) {
            System.out.println("Filtered by description = " + descriptionFilter);
        }
        if (!brandFilter.equals("")) {
            System.out.println("Filtered by brand = " + brandFilter);
        }
        if (!sellerFilter.equals("")) {
            System.out.println("Filtered by seller's username = " + sellerFilter);
        }
        if (!availabilityFilter.equals("")) {
            System.out.println("Filtered by availability = " + availabilityFilter);
        }
        if (priceFilter != 0) {
            System.out.println("Filtered by price = " + priceFilter);
        }
        if (filteredAttributes.size() == 0) return false;
        System.out.println("Filtered by attributes:");
        filteredAttributes.entrySet().stream().map(stringStringEntry -> ((Map.Entry) stringStringEntry).getKey() + " = " + ((Map.Entry) stringStringEntry).getValue()).forEach(System.out::println);
        if (filteredCategories.size() == 0) return false;
        if (filteredCategories.size() == 1)
            System.out.println("Filtered by category = " + filteredCategories.get(0).getName());
        else {
            System.out.println("Filtered by the following categories:");
            filteredCategories.stream().map(category -> "#" + category.getId() + " - " + category.getName())
                    .forEach(System.out::println);
        }
        return false;
    }

    private boolean filterByCategoryCommand() {
        System.out.print("Enter a new category ID to only see products in that category: ");
        while (true) {
            String id = scanner.nextLine();
            Category category = DataManager.shared().getCategoryWithId(id);
            if (category == null) {
                System.out.print("No category with the given ID exists. Please enter a new ID again: ");
                continue;
            }
            filteredCategories.add(category);
            System.out.println("Category " + category.getName() + " was added successfully to the current filters");
            break;
        }
        return false;
    }

    private boolean filterByNameCommand() {
        System.out.print("Enter a new name to only see products containing that name: ");
        nameFilter = scanner.nextLine();
        System.out.println("Name filter was successfully set");
        return false;
    }

    private boolean filterByDescriptionCommand() {
        System.out.print("Enter a new text to only see products containing that text in their description: ");
        nameFilter = scanner.nextLine();
        System.out.println("Description filter was successfully set");
        return false;
    }

    private boolean filterByBrandCommand() {
        System.out.print("Enter a new brand name to only see products with that brand: ");
        brandFilter = scanner.nextLine();
        System.out.println("Brand filter was successfully set");
        return false;
    }

    private boolean filterBySellerCommand() {
        System.out.print("Enter a seller's username to only see the products by that seller: ");
        sellerFilter = scanner.nextLine();
        System.out.println("Seller filter was successfully set");
        return false;
    }

    private boolean filterByAvailabilityCommand() {
        System.out.print("Enter \"available\" or \"notAvailable\": ");
        availabilityFilter = scanner.nextLine();
        if (!availabilityFilter.equals("available") && !availabilityFilter.equals("notAvailable")) {
            System.out.println("Invalid value");
            return false;
        }
        System.out.println("Availability filter was successfully set");
        return false;
    }

    private boolean filterByPriceCommand() {
        System.out.print("Enter a new price to only see products with that price: ");
        priceFilter = DataManager.nextInt(scanner);
        System.out.println("Price filter was successfully set");
        return false;
    }

    private boolean viewMainCategoriesCommand() {
        System.out.println("All Categories:");
        DataManager.shared().getAllCategories().stream()
                .filter(category -> category.getParentCategory() == null)
                .map(category -> "#" + category.getId() + " - " + category.getName())
                .forEach(System.out::println);
        return false;
    }

    // TODO: Multiple categories not implemented...
    // TODO: Features in categories not implemented...

    private boolean showAvailableFiltersCommand() {
        System.out.println("You can type \"Filter by category\", \"Filter by name\", \"Filter by description\" and \"Filter by price\".");
        return false;
    }

    @Override
    protected void showHelp() {

    }
}
