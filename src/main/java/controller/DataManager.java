package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import javax.xml.crypto.Data;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

public class DataManager {
    private static DataManager sharedInstance;

    private Account loggedInAccount;
    private ArrayList<Account> allAccounts = new ArrayList<>();
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Coupon> allCoupons = new ArrayList<>();
    private ArrayList<Request> allRequests = new ArrayList<>();
    private ArrayList<Category> allCategories = new ArrayList<>();
    private ArrayList<Sale> allSales = new ArrayList<>();
    private Cart temporaryCart = new Cart();

    public Cart getTemporaryCart() {
        return temporaryCart;
    }

    public void setTemporaryCart(Cart temporaryCart) {
        this.temporaryCart = temporaryCart;
    }

    public static String getNewId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public ArrayList<Sale> getAllSales() {
        return allSales;
    }

    public ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public Request getRequestWithID(String id) {
        for (Request request : allRequests) {
            if (request.getId().equals(id)) return request;
        }
        return null;
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public ArrayList<Coupon> getAllCoupons() {
        return allCoupons;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public enum AccountType {
        CUSTOMER, ADMINISTRATOR, SELLER, NONE
    }

    private DataManager() {
    }

    public static DataManager shared() {
        if (sharedInstance == null) {
            sharedInstance = new DataManager();
        }
        return sharedInstance;
    }

    public void removeProduct(String productID) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productID)) {
                allProducts.remove(product);
                return;
            }
        }
    }

    public Product productWithID(String id) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Account getAccountWithGivenUsername(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) return account;
        }
        return null;
    }

    public DataManager.AccountType login(String username, String password) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username) && account.getUsername().equals(password)) {
                loggedInAccount = account;
                if (account instanceof Customer) {
                    return DataManager.AccountType.CUSTOMER;
                } else if (account instanceof Administrator) {
                    return DataManager.AccountType.ADMINISTRATOR;
                } else if (account instanceof Seller) {
                    return DataManager.AccountType.SELLER;
                }
                break;
            }
        }
        loggedInAccount = null;
        return DataManager.AccountType.NONE;
    }

    public boolean hasAnyAdminRegistered() {
        for (Account account : allAccounts) {
            if (account instanceof Administrator) return true;
        }
        return false;
    }

    public boolean doesUserWithGivenUsernameExist(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void registerAccount(Account account) {
        allAccounts.add(account);
        saveData();
    }

    public boolean givenUsernameHasGivenPassword(String username, String password) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) {
                return account.getPassword().equalsIgnoreCase(password);
            }
        }
        return false;
    }

    public static void saveData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(sharedInstance);
        try (PrintStream out = new PrintStream(new FileOutputStream("data.txt"))) {
            out.print(json);
        } catch (IOException e) {
            System.out.println("Unexpected exception happened in saving data: " + e.getLocalizedMessage());
        }
    }

    public static void loadData() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("data.txt")));
            Gson gson = new Gson();
            sharedInstance = gson.fromJson(json, DataManager.class);
        } catch (IOException e) {
            System.out.println("Unexpected exception happened in loading data: " + e.getLocalizedMessage());
        }
    }

    public void addCoupon(Coupon coupon) {
        allCoupons.add(coupon);
    }

    public void addRequest(Request request) {
    }

    public void addCategory(Category category) {
    }

    public void addSale(Sale sale) {
    }

    public void removeCoupon(Coupon coupon) {
        allCoupons.remove(coupon);
    }

    public void removeRequest(Request request) {
        allRequests.remove(request);
    }

    public void removeAccount(Account account) {
        allAccounts.remove(account);
    }

    public void removeCategory(Category category, Category parent) {
        parent.getSubCategories().removeIf(subCategory -> subCategory.getId().equals(category.getId()));
        allCategories.removeIf(c -> c.getId().equals(category.getId()));
    }

    public void removeSale(Sale sale) {
    }

    public Request getRequestWithId(int id) {
        return null;
    }

    public Coupon getCouponWithId(String id) {
        return allCoupons.stream().filter(coupon -> coupon.getId().equals(id)).findFirst().orElse(null);
    }

    public Category getCategoryWithId(String id) {
        return allCategories.stream().filter(category -> category.getId().equals(id)).findFirst().orElse(null);
    }

    public Product getProductWithId(String id) {
        return allProducts.stream().filter(product -> product.getProductId().equals(id)).findFirst().orElse(null);
    }

    public Sale getSaleWithId(String id) {
        return allSales.stream().filter(sale -> sale.getOffId().equals(id)).findFirst().orElse(null);
    }

}
