package controller;

import com.google.gson.*;
import model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class DataManager {
    private static DataManager sharedInstance;

    private Account loggedInAccount;
    private ArrayList<Customer> allCustomers = new ArrayList<>();
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private ArrayList<Administrator> allAdministrators = new ArrayList<>();
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Coupon> allCoupons = new ArrayList<>();
    private ArrayList<AddProductBySellerRequest> addProductBySellerRequests = new ArrayList<>();
    private ArrayList<AddSaleBySellerRequest> addSaleBySellerRequests = new ArrayList<>();
    private ArrayList<EditProductBySellerRequest> editProductBySellerRequests = new ArrayList<>();
    private ArrayList<EditSaleBySellerRequest> editSaleBySellerRequests = new ArrayList<>();
    private ArrayList<SellerRegistrationRequest> sellerRegistrationRequests = new ArrayList<>();

    private ArrayList<Category> allCategories = new ArrayList<>();
    private ArrayList<Sale> allSales = new ArrayList<>();
    private ArrayList<PurchaseLog> purchaseLogs = new ArrayList<>();
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    private Cart temporaryCart = new Cart();

    public static int nextInt(Scanner scanner) { // returns -1 if it hits problem
        try {
            return scanner.nextInt();
        } catch (Exception ignored) {
            return -1;
        }
    }

    public ArrayList<Log> getAllLogs() {
        ArrayList<Log> result = new ArrayList<>();
        result.addAll(purchaseLogs);
        result.addAll(sellLogs);
        return result;
    }

    public void logout() {
        loggedInAccount= null;
        DataManager.saveData();
    }

    public PurchaseLog purchaseLogForCustomerById(Customer customer, String id) {
        return (PurchaseLog) getAllLogs().stream()
                .filter(log -> log instanceof PurchaseLog && log.getId().equals(id)
                        && ((PurchaseLog) log).getCustomer().getUsername().equals(customer.getUsername()))
                .findFirst().orElse(null);
    }

    public Cart getTemporaryCart() {
        return temporaryCart;
    }

    public void setTemporaryCart(Cart temporaryCart) {
        this.temporaryCart = temporaryCart;
        saveData();
    }

    public static String getNewId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public ArrayList<Sale> getAllSales() {
        return allSales;
    }

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> result = new ArrayList<>();
        result.addAll(allCustomers);
        result.addAll(allSellers);
        result.addAll(allAdministrators);
        return result;
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

   // TODO: Up to here checked saveData()s...

    public ArrayList<Request> getAllRequests() {
        ArrayList<Request> result = new ArrayList<>();
        result.addAll(addProductBySellerRequests);
        result.addAll(addSaleBySellerRequests);
        result.addAll(editProductBySellerRequests);
        result.addAll(editSaleBySellerRequests);
        result.addAll(sellerRegistrationRequests);
        return result;
    }

    public void addRequest(Request request) {
        if (request instanceof AddProductBySellerRequest) {
            addProductBySellerRequests.add((AddProductBySellerRequest) request);
        } else if (request instanceof AddSaleBySellerRequest) {
            addSaleBySellerRequests.add((AddSaleBySellerRequest) request);
        } else if (request instanceof EditProductBySellerRequest) {
            editProductBySellerRequests.add((EditProductBySellerRequest) request);
        } else if (request instanceof EditSaleBySellerRequest) {
            editSaleBySellerRequests.add((EditSaleBySellerRequest) request);
        } else if (request instanceof SellerRegistrationRequest) {
            sellerRegistrationRequests.add((SellerRegistrationRequest) request);
        }
        saveData();
    }

    public Request getRequestWithID(String id) {
        for (Request request : getAllRequests()) {
            if (request.getId().equals(id)) return request;
        }
        return null;
    }

    public Log getLogWithId(String id) {
        for (Log log : getAllLogs()) {
            if (log.getId().equals(id)) return log;
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
                saveData();
                return;
            }
        }
    }

    public Account getAccountWithGivenUsername(String username) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) return account;
        }
        return null;
    }

    public DataManager.AccountType login(String username, String password) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                loggedInAccount = account;
                saveData();
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
        saveData();
        return DataManager.AccountType.NONE;
    }

    public boolean hasAnyAdminRegistered() {
        for (Account account : getAllAccounts()) {
            if (account instanceof Administrator) return true;
        }
        return false;
    }

    public boolean doesUserWithGivenUsernameExist(String username) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void registerAccount(Account account) {
        if (account instanceof Customer) {
            allCustomers.add((Customer) account);
        } else if (account instanceof Seller) {
            allSellers.add((Seller) account);
        } else if (account instanceof Administrator) {
            allAdministrators.add((Administrator) account);
        }
        saveData();
    }

    public boolean givenUsernameHasGivenPassword(String username, String password) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return account.getPassword().equalsIgnoreCase(password);
            }
        }
        return false;
    }

    public static void saveData() {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
            Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }).create();
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
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (_json, type, jsonDeserializationContext) -> {
                Instant instant = Instant.ofEpochMilli(_json.getAsJsonPrimitive().getAsLong());
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            }).create();
            sharedInstance = gson.fromJson(json, DataManager.class);
        } catch (IOException e) {
            System.out.println("Unexpected exception happened in loading data: " + e.getLocalizedMessage());
        }
    }

    public void addLog(Log log) {
        if (log instanceof SellLog) {
            sellLogs.add((SellLog) log);
        } else if (log instanceof PurchaseLog) {
            purchaseLogs.add((PurchaseLog) log);
        }
        saveData();
    }

    public void addSale(Sale sale) {
        allSales.add(sale);
        saveData();
    }

    public void addProduct(Product product) {
        allProducts.add(product);
        saveData();
    }

    public void addCoupon(Coupon coupon) {
        allCoupons.add(coupon);
        saveData();
    }

    public void addCategory(Category category) {
        allCategories.add(category);
        saveData();
    }

    public void removeCoupon(Coupon coupon) {
        allCoupons.remove(coupon);
        saveData();
    }

    public void removeRequest(Request request) {
        addProductBySellerRequests.remove(request);
        addSaleBySellerRequests.remove(request);
        editProductBySellerRequests.remove(request);
        editSaleBySellerRequests.remove(request);
        sellerRegistrationRequests.remove(request);
        saveData();
    }

    public void removeSale(Sale sale) {
        allSales.remove(sale);
        saveData();
    }

    public void removeAccount(Account account) {
        allCustomers.remove(account);
        allSellers.remove(account);
        allAdministrators.remove(account);
        saveData();
    }

    public void removeCategory(Category category, Category parent) {
        parent.getSubCategories().removeIf(subCategory -> subCategory.getId().equals(category.getId()));
        allCategories.removeIf(c -> c.getId().equals(category.getId()));
        saveData();
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
