package view.menus;

import controller.DataManager;
import model.Category;
import model.Product;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

public class AllProductsMenu extends Menu {

    private ArrayList<Category> filteredCategories = new ArrayList<>();
    private String nameFilter = "";
    private String descriptionFilter = "";
    private int priceFilter = 0;

    public AllProductsMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new Menu("View Main Categories", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewMainCategoriesCommand()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(2, new Menu("View all products matching the current filters", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (viewMainCategoriesCommand()) return;
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
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(9, new Menu("Remove name filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeNameFilter()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(10, new Menu("Remove description filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeDescriptionFilter()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(11, new Menu("Remove price filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removePriceFilter()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });

        subMenus.put(12, new Menu("Remove category filter", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                if (removeCategoryFilter()) return;
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
    }

    private boolean removeCategoryFilter() {
        System.out.println("Current categories filtered by:");
        filteredCategories.stream().map(category -> "#" + category.getId() + " - " + category.getName())
                .forEach(System.out::println);
        System.out.print("Enter a category ID to remove it from filtered-by categories, or -1 to clear category filter: ");
        int id = scanner.nextInt();
        if (id == -1) {
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

    private boolean removePriceFilter() {
        // TODO: Free products??
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
        if (priceFilter != 0) {
            System.out.println("Filtered by price = " + priceFilter);
        }
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
            int id = scanner.nextInt();
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

    private boolean filterByPriceCommand() {
        System.out.print("Enter a new price to only see products with that price: ");
        priceFilter = scanner.nextInt();
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

    private boolean showAvailableFiltersCommand() {
        System.out.println("You can type \"Filter by category\", \"Filter by name\", \"Filter by description\" and \"Filter by price\".");
        return false;
    }

    private void searchByInputCommand() {
    }

    private void filterByBrand(String filter) {
    }

    private void removeFilterByBrand() {
    }

    private void filterByAvailability(boolean shouldBeAvailable) {
    }

    private void removeFilterByAvailability() {
    }

    private void filterByPriceRange(int from, int to) {
    }

    private void filterByName(String name) {
    }

    private void showProduct(Product product) {
    }

    private void startComparingProducts(int firstProductId, int secondProductId) {
    }

    private void showProductDigest(Product product) {
    }

    private void showProductAttributes(Product product) {
    }

    private void addProductToCart(Product product) {
    }

    @Override
    protected void showHelp() {

    }

    private void checkOut() {
    }
}
