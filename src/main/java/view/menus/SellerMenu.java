package view.menus;

import controller.DataManager;
import controller.Validator;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SellerMenu extends UserMenu {
    public SellerMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        int i = 0;
        subMenus.put(i, new Menu("View Personal Info", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewPersonalInfo();
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
                viewPersonalInfo();
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
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View Sales History", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewAllSales();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("Manage Products", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewAllSales();
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
//                viewAllSales(); TODO add product
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
//                viewAllSales(); TODO remove product
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
//                viewAllSales(); TODO view off sales
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
        subMenus.put(i, new Menu("View balance", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                viewBalance();
                parentMenu.show();
                parentMenu.execute();
            }

            @Override
            protected void showHelp() {
            }
        });
        i++;
    }

    private void viewCompanyInformation() {
        showString("Company information");
        showString(((Seller)getCurrentUser()).getCompanyDetails());
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
        showString("Categories:");
        ArrayList<Category> categories = DataManager.shared().getAllCategories();
        int i = 1;
        for (Category category : categories) {
            showString(i + ") " + category);
        }
    }

    private void viewSellLog() {
        showString("Sale logs:");
        showString(getCurrentUser().getLogs());
    }

    private void showProduct(Product product) {
        showString("Product:");
        showString(product);
    }

    private void seeCustomerOfProduct(Product product) {
    }

    private void viewAllSellingProducts() {
        showString("Products:");
        showString(((Seller)getCurrentUser()).getProducts());
    }

    private void startAddingProduct() {
    }

    private void startEditingProduct(Product product) {
    }

    private void requestEditingProductName(Product product) {
    }

    private void requestEditingProductBrand(Product product) {
    }

    private void requestEditingProductPrice(Product product) {
    }

    private void requestEditingProductCategory(Product product) {
    }

    private void requestEditingProductDescription(Product product) {
    }

    private void requestEditingProductNumberAvailable(Product product) {
    }

    private void requestRemovingProduct(Product product) {
    }

    private void showCategories() {
    }

    private void viewAllSales() {
        showString("Off sales:");
        showString(((Seller)getCurrentUser()).getSales());
    }

    private void viewCurrentSales() {
        showString("Current Sales:");

    }

    private void filterSalesByDiscountAmount(int discountAmount) {
    }

    private void filterSalesByTimeRange(LocalDateTime from, LocalDateTime to) {
    }

    private void startAddingSale() {
    }

    private void startEditingSale(Sale sale) {
    }

    private void requestAddingProduct(Product product) {
    }

    private void requestAddingSale(Sale sale) {
    }

    private void requestAddingProductToSale(Sale sale, int productId) {
    }

    private void requestRemovingProductFromSale(Sale sale, int productId) {
    }

    private void requestEditingSaleStatus(Sale sale) {
    }

    private void requestEditingSaleDiscountAmount(Sale sale) {
    }

    private void requestEditingSaleStartTime(Sale sale) {
    }

    private void requestEditingSaleEndTime(Sale sale) {
    }

    private void requestEditingSaleDeliveryStatus(Sale sale) {
        showString("New status:");
//        Sale newSale = new Sale(sale.getOffId(), sale.getProducts(), sale.getSaleStatus(), sale.getDiscountAmount(), sale.getStartTime(), sale.getEndTime(), sale.getSeller(),);
//        DataManager.shared().addRequest(new EditSaleBySellerRequest(getCurrentUser(), sale, ));
    }

    @Override
    protected void showHelp() {

    }

    private void logout() {
    }
}
