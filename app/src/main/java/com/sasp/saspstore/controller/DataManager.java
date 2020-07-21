package com.sasp.saspstore.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.sasp.saspstore.Gonnect;
import com.sasp.saspstore.model.Account;
import com.sasp.saspstore.model.Ad;
import com.sasp.saspstore.model.AddAdBySellerRequest;
import com.sasp.saspstore.model.AddProductBySellerRequest;
import com.sasp.saspstore.model.AddSaleBySellerRequest;
import com.sasp.saspstore.model.Administrator;
import com.sasp.saspstore.model.Assistant;
import com.sasp.saspstore.model.AssistantMessage;
import com.sasp.saspstore.model.Auction;
import com.sasp.saspstore.model.Cart;
import com.sasp.saspstore.model.Category;
import com.sasp.saspstore.model.Coupon;
import com.sasp.saspstore.model.Customer;
import com.sasp.saspstore.model.EditProductBySellerRequest;
import com.sasp.saspstore.model.EditSaleBySellerRequest;
import com.sasp.saspstore.model.Log;
import com.sasp.saspstore.model.Product;
import com.sasp.saspstore.model.PurchaseLog;
import com.sasp.saspstore.model.Request;
import com.sasp.saspstore.model.Sale;
import com.sasp.saspstore.model.SellLog;
import com.sasp.saspstore.model.Seller;
import com.sasp.saspstore.model.SellerRegistrationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

// TODO: IMPORTANT: All LocalDateTimes are removed!!

public class DataManager {
    public static final String IP_SERVER = "http://10.0.2.2:8111/"; // AVD
//    public static final String IP_SERVER = "http://10.0.3.2:8111/"; // Genymotion
    public static Context context;
    private static DataManager sharedInstance;
    private Account loggedInAccount;
    private ArrayList<Customer> allCustomers = new ArrayList<>();
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private ArrayList<Administrator> allAdministrators = new ArrayList<>();
    private ArrayList<Assistant> allAssistants = new ArrayList<>();
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Coupon> allCoupons = new ArrayList<>();
    private ArrayList<AddProductBySellerRequest> addProductBySellerRequests = new ArrayList<>();
    private ArrayList<AddSaleBySellerRequest> addSaleBySellerRequests = new ArrayList<>();
    private ArrayList<EditProductBySellerRequest> editProductBySellerRequests = new ArrayList<>();
    private ArrayList<EditSaleBySellerRequest> editSaleBySellerRequests = new ArrayList<>();
    private ArrayList<SellerRegistrationRequest> sellerRegistrationRequests = new ArrayList<>();
    private ArrayList<Ad> allAds = new ArrayList<>();
    private ArrayList<AddAdBySellerRequest> adRequests = new ArrayList<>();
    private ArrayList<AssistantMessage> allAssistantMessages = new ArrayList<>();

    private ArrayList<Category> allCategories = new ArrayList<>();
    private ArrayList<Sale> allSales = new ArrayList<>();
    private ArrayList<PurchaseLog> purchaseLogs = new ArrayList<>();
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    private ArrayList<Auction> auctions = new ArrayList<>();
    private Cart temporaryCart = new Cart();

    private String token = "";
    private String adminBankAccountNumber = "";
    private ArrayList<String> onlineUsernames;
    private int mimimumCredit = 0;
    private int karmozd = 0;

    public ArrayList<AssistantMessage> getAllAssistantMessages() {
        return allAssistantMessages;
    }

    public int getKarmozd() {
        return karmozd;
    }

    public void setKarmozd(int karmozd) {
        this.karmozd = karmozd;
    }

    public ArrayList<Auction> getAuctions() {
        return auctions;
    }

    public int getMimimumCredit() {
        return mimimumCredit;
    }

    public void setMimimumCredit(int mimimumCredit) {
        this.mimimumCredit = mimimumCredit;
    }

    public void setAuctions(ArrayList<Auction> auctions) {
        this.auctions = auctions;
    }

    public boolean isMadeAdminBankAccount() {
        return !adminBankAccountNumber.equals("");
    }

    public ArrayList<String> getOnlineUsernames() {
        return onlineUsernames;
    }

    public void setOnlineUsernames(ArrayList<String> onlineUsernames) {
        this.onlineUsernames = onlineUsernames;
    }

    public void addAuction(Auction auction) {
        auctions.add(auction);
    }

    public String getAdminBankAccountNumber() {
        return adminBankAccountNumber;
    }

    public void setAdminBankAccountNumber(String adminBankAccountNumber) {
        this.adminBankAccountNumber = adminBankAccountNumber;
    }

    private DataManager() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Ad> getAllAds() {
        return allAds;
    }

    public static String encode(String input) {
        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void populateDataa() {
        Gonnect.getData(IP_SERVER + "req?action=getAllAds", (b, s) -> {
            ArrayList<Ad> allAds = new Gson().fromJson(s, new TypeToken<ArrayList<Ad>>() {
            }.getType());
            DataManager.shared().setAllAds(allAds);
        });
    }

    public void populateData() {
        Gonnect.getData(IP_SERVER + "req?action=getKarmozd", (b, s) -> {
            try {
                DataManager.shared().setKarmozd(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
        Gonnect.getData(IP_SERVER + "req?action=getMinimumCredit", (b, s) -> {
            try {
                DataManager.shared().setMimimumCredit(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
        Gonnect.getData(IP_SERVER + "req?action=getAllAds", (b, s) -> {
            ArrayList<Ad> allAds = new Gson().fromJson(s, new TypeToken<ArrayList<Ad>>() {
            }.getType());
            DataManager.shared().setAllAds(allAds);
        });
        Gonnect.getData(IP_SERVER + "?action=getAllPurchaseLogs", (b, s) -> {
            ArrayList<PurchaseLog> allLogs = new Gson().fromJson(s, new TypeToken<ArrayList<PurchaseLog>>() {
            }.getType());
            DataManager.shared().setPurchaseLogs(allLogs);
        });
        Gonnect.getData(IP_SERVER + "?action=getAllSellLogs", (b, s) -> {
            ArrayList<SellLog> allLogs = new Gson().fromJson(s, new TypeToken<ArrayList<SellLog>>() {
            }.getType());
            DataManager.shared().setSellLogs(allLogs);
        });
        Gonnect.getData(IP_SERVER + "req?action=getTemporaryCart", (b, s) -> {
            Cart cart = new Gson().fromJson(s, Cart.class);
            DataManager.shared().setTemporaryCart(cart);
        });
        populateAllAccountsData();
        Gonnect.getData(IP_SERVER + "req?action=getAllCategories", (b, s) -> {
            ArrayList<Category> categories = new Gson().fromJson(s, new TypeToken<ArrayList<Category>>() {
            }.getType());
            DataManager.shared().setAllCategories(categories);
        });
        Gonnect.getData(IP_SERVER + "req?action=getAddProductBySellerRequests", (b, s) -> {
            ArrayList<AddProductBySellerRequest> requests = new Gson().fromJson(s,
                    new TypeToken<ArrayList<AddProductBySellerRequest>>() {
                    }.getType());
            DataManager.shared().setAddProductBySellerRequests(requests);
        });
        Gonnect.getData(IP_SERVER + "req?action=getAddSaleBySellerRequests", (b, s) -> {
            ArrayList<AddSaleBySellerRequest> requests = new Gson().fromJson(s,
                    new TypeToken<ArrayList<AddSaleBySellerRequest>>() {
                    }.getType());
            DataManager.shared().setAddSaleBySellerRequests(requests);
        });
        Gonnect.getData(IP_SERVER + "req?action=getEditProductBySellerRequests", (b, s) -> {
            ArrayList<EditProductBySellerRequest> requests = new Gson().fromJson(s,
                    new TypeToken<ArrayList<EditProductBySellerRequest>>() {
                    }.getType());
            DataManager.shared().setEditProductBySellerRequests(requests);
        });
        Gonnect.getData(IP_SERVER + "req?action=getEditSaleBySellerRequests", (b, s) -> {
            ArrayList<EditSaleBySellerRequest> requests = new Gson().fromJson(s,
                    new TypeToken<ArrayList<EditSaleBySellerRequest>>() {
                    }.getType());
            DataManager.shared().setEditSaleBySellerRequests(requests);
        });
        Gonnect.getData(IP_SERVER + "req?action=getSellerRegistrationRequests", (b, s) -> {
            ArrayList<SellerRegistrationRequest> requests = new Gson().fromJson(s,
                    new TypeToken<ArrayList<SellerRegistrationRequest>>() {
                    }.getType());
            DataManager.shared().setSellerRegistrationRequests(requests);
        });
        Gonnect.getData(IP_SERVER + "req?action=getAdRequests", (b, s) -> {
            ArrayList<AddAdBySellerRequest> requests = new Gson().fromJson(s,
                    new TypeToken<ArrayList<AddAdBySellerRequest>>() {
                    }.getType());
            DataManager.shared().setAdRequests(requests);
        });
        Gonnect.getData(IP_SERVER + "req?action=getAllCoupons", (b, s) -> {
            ArrayList<Coupon> coupons = new Gson().fromJson(s,
                    new TypeToken<ArrayList<Coupon>>() {
                    }.getType());
            DataManager.shared().setAllCoupons(coupons);
        });
        Gonnect.getData(IP_SERVER + "req?action=getAllProducts", (b, s) -> {
            ArrayList<Product> products = new Gson().fromJson(s,
                    new TypeToken<ArrayList<Product>>() {
                    }.getType());
            DataManager.shared().setAllProducts(products);
        });
        Gonnect.getData(IP_SERVER + "req?action=getAdminBankAccountNumber", (b, s) -> {
            DataManager.shared().setAdminBankAccountNumber(s);
        });
        Gonnect.getData(IP_SERVER + "req?action=getOnlineUsernames", (b, s) -> {
            ArrayList<String> usernames = new Gson().fromJson(s,
                    new TypeToken<ArrayList<String>>() {
                    }.getType());
            DataManager.shared().setOnlineUsernames(usernames);
        });
    }

    // TODO: Online users not tested...

    public void populateAllAccountsData() {
        Gonnect.getData(IP_SERVER + "req?action=getAllCustomers", (b, s) -> {
            ArrayList<Customer> customers = new Gson().fromJson(s, new TypeToken<ArrayList<Customer>>() {
            }.getType());
            DataManager.shared().setAllCustomers(customers);
        });
        Gonnect.getData(IP_SERVER + "req?action=getAllSellers", (b, s) -> {
            ArrayList<Seller> sellers = new Gson().fromJson(s, new TypeToken<ArrayList<Seller>>() {
            }.getType());
            DataManager.shared().setAllSellers(sellers);
        });
        Gonnect.getData(IP_SERVER + "req?action=getAllAdministrators", (b, s) -> {
            ArrayList<Administrator> administrators = new Gson().fromJson(s, new TypeToken<ArrayList<Administrator>>() {
            }.getType());
            DataManager.shared().setAllAdministrators(administrators);
        });
        Gonnect.getData(IP_SERVER + "req?action=getAllAssistants", (b, s) -> {
            ArrayList<Assistant> assistants = new Gson().fromJson(s, new TypeToken<ArrayList<Assistant>>() {
            }.getType());
            DataManager.shared().setAllAssistants(assistants);
        });
    }

    public ArrayList<Assistant> getAllAssistants() {
        return allAssistants;
    }

    public void setAllAssistants(ArrayList<Assistant> allAssistants) {
        this.allAssistants = allAssistants;
    }

    // populateData

    /* j */

//    public void populateData() {
//        ContentValues cv0 = new ContentValues();
//        cv0.put("action", "getAllAds");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv0, (b, s) -> {
//            android.util.Log.wtf("String from getAllAds: " + s + "b: " + b, "String from getAllAds: " + s);
//            ArrayList<Ad> allAds = new Gson().fromJson(s, new TypeToken<ArrayList<Ad>>() {
//            }.getType());
//            DataManager.shared().setAllAds(allAds);
//        });
//
//        ContentValues cv = new ContentValues();
//        cv.put("action", "getAllPurchaseLogs");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv, (b, s) -> {
//            ArrayList<PurchaseLog> allLogs = new Gson().fromJson(s, new TypeToken<ArrayList<PurchaseLog>>() {
//            }.getType());
//            DataManager.shared().setPurchaseLogs(allLogs);
//        });
//
//        ContentValues cv2 = new ContentValues();
//        cv2.put("action", "getAllSellLogs");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv2, (b, s) -> {
//            ArrayList<SellLog> allLogs = new Gson().fromJson(s, new TypeToken<ArrayList<SellLog>>() {
//            }.getType());
//            DataManager.shared().setSellLogs(allLogs);
//        });
//
//        ContentValues cv3 = new ContentValues();
//        cv3.put("action", "getTemporaryCart");
//        Gonnect.sendRequest("http://10.0.2.2:8111/req", cv3, (b, s) -> {
//            android.util.Log.wtf("S in getTemp: " + s, " S in getTemp: " + s);
//            Cart cart = new Gson().fromJson(s, Cart.class);
//            DataManager.shared().setTemporaryCart(cart);
//        });
//
//        ContentValues cv4 = new ContentValues();
//        cv4.put("action", "getAllCustomers");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv4, (b, s) -> {
//            ArrayList<Customer> customers = new Gson().fromJson(s, new TypeToken<ArrayList<Customer>>() {
//            }.getType());
//            DataManager.shared().setAllCustomers(customers);
//        });
//
//        ContentValues cv5 = new ContentValues();
//        cv5.put("action", "getAllSellers");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv5, (b, s) -> {
//            ArrayList<Seller> sellers = new Gson().fromJson(s, new TypeToken<ArrayList<Seller>>() {
//            }.getType());
//            DataManager.shared().setAllSellers(sellers);
//        });
//
//        ContentValues cv6 = new ContentValues();
//        cv6.put("action", "getAllAdministrators");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv6, (b, s) -> {
//            ArrayList<Administrator> administrators = new Gson().fromJson(s, new TypeToken<ArrayList<Administrator>>() {
//            }.getType());
//            DataManager.shared().setAllAdministrators(administrators);
//        });
//
//        ContentValues cv7 = new ContentValues();
//        cv7.put("action", "getAllCategories");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv7, (b, s) -> {
//            ArrayList<Category> categories = new Gson().fromJson(s, new TypeToken<ArrayList<Category>>() {
//            }.getType());
//            DataManager.shared().setAllCategories(categories);
//        });
//
//        ContentValues cv8 = new ContentValues();
//        cv8.put("action", "getAddProductBySellerRequests");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv8, (b, s) -> {
//            ArrayList<AddProductBySellerRequest> requests = new Gson().fromJson(s,
//                    new TypeToken<ArrayList<AddProductBySellerRequest>>() {
//                    }.getType());
//            DataManager.shared().setAddProductBySellerRequests(requests);
//        });
//
//        ContentValues cv9 = new ContentValues();
//        cv9.put("action", "getAddSaleBySellerRequests");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv9, (b, s) -> {
//            ArrayList<AddSaleBySellerRequest> requests = new Gson().fromJson(s,
//                    new TypeToken<ArrayList<AddSaleBySellerRequest>>() {
//                    }.getType());
//            DataManager.shared().setAddSaleBySellerRequests(requests);
//        });
//
//        ContentValues cv10 = new ContentValues();
//        cv10.put("action", "getEditProductBySellerRequests");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv10, (b, s) -> {
//            ArrayList<EditProductBySellerRequest> requests = new Gson().fromJson(s,
//                    new TypeToken<ArrayList<EditProductBySellerRequest>>() {
//                    }.getType());
//            DataManager.shared().setEditProductBySellerRequests(requests);
//        });
//
//        ContentValues cv11 = new ContentValues();
//        cv11.put("action", "getEditSaleBySellerRequests");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv11, (b, s) -> {
//            ArrayList<EditSaleBySellerRequest> requests = new Gson().fromJson(s,
//                    new TypeToken<ArrayList<EditSaleBySellerRequest>>() {
//                    }.getType());
//            DataManager.shared().setEditSaleBySellerRequests(requests);
//        });
//
//        ContentValues cv12 = new ContentValues();
//        cv12.put("action", "getSellerRegistrationRequests");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv12, (b, s) -> {
//            ArrayList<SellerRegistrationRequest> requests = new Gson().fromJson(s,
//                    new TypeToken<ArrayList<SellerRegistrationRequest>>() {
//                    }.getType());
//            DataManager.shared().setSellerRegistrationRequests(requests);
//        });
//
//        ContentValues cv13 = new ContentValues();
//        cv13.put("action", "getAdRequests");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv13, (b, s) -> {
//            ArrayList<AddAdBySellerRequest> requests = new Gson().fromJson(s,
//                    new TypeToken<ArrayList<AddAdBySellerRequest>>() {
//                    }.getType());
//            DataManager.shared().setAdRequests(requests);
//        });
//
//        ContentValues cv14 = new ContentValues();
//        cv14.put("action", "getAllCoupons");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv14, (b, s) -> {
//            ArrayList<Coupon> coupons = new Gson().fromJson(s,
//                    new TypeToken<ArrayList<Coupon>>() {
//                    }.getType());
//            DataManager.shared().setAllCoupons(coupons);
//        });
//
//        ContentValues cv15 = new ContentValues();
//        cv15.put("action", "getAllProducts");
//        Gonnect.sendRequest("http://10.0.2.2:8111/", cv15, (b, s) -> {
//            ArrayList<Product> products = new Gson().fromJson(s,
//                    new TypeToken<ArrayList<Product>>() {
//                    }.getType());
//            DataManager.shared().setAllProducts(products);
//        });
//    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void setAllCoupons(ArrayList<Coupon> allCoupons) {
        this.allCoupons = allCoupons;
    }

    public void setAddProductBySellerRequests(ArrayList<AddProductBySellerRequest> addProductBySellerRequests) {
        this.addProductBySellerRequests = addProductBySellerRequests;
    }

    public void setAddSaleBySellerRequests(ArrayList<AddSaleBySellerRequest> addSaleBySellerRequests) {
        this.addSaleBySellerRequests = addSaleBySellerRequests;
    }

    public void setEditProductBySellerRequests(ArrayList<EditProductBySellerRequest> editProductBySellerRequests) {
        this.editProductBySellerRequests = editProductBySellerRequests;
    }

    public void setEditSaleBySellerRequests(ArrayList<EditSaleBySellerRequest> editSaleBySellerRequests) {
        this.editSaleBySellerRequests = editSaleBySellerRequests;
    }

    public void setSellerRegistrationRequests(ArrayList<SellerRegistrationRequest> sellerRegistrationRequests) {
        this.sellerRegistrationRequests = sellerRegistrationRequests;
    }

    public void setAdRequests(ArrayList<AddAdBySellerRequest> adRequests) {
        this.adRequests = adRequests;
    }

    public void setAllCustomers(ArrayList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public void setAllSellers(ArrayList<Seller> allSellers) {
        this.allSellers = allSellers;
    }

    public void setAllAdministrators(ArrayList<Administrator> allAdministrators) {
        this.allAdministrators = allAdministrators;
    }

    public void setAllCategories(ArrayList<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public static int nextInt(Scanner scanner) { // returns -1 if it hits problem
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getNewId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static DataManager shared() {
        if (sharedInstance == null) {
            sharedInstance = new DataManager();
        }
        return sharedInstance;
    }

//    public static void saveData() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
//
//        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
//
//        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
//
//        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
//
//        Gson gson = gsonBuilder.setPrettyPrinting().create();
//
//        String json = gson.toJson(sharedInstance);
//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("data.txt", Context.MODE_PRIVATE));
//            outputStreamWriter.write(json);
//            outputStreamWriter.close();
//        } catch (IOException e) {
//            // TODO: Here
//        }
////        try (PrintStream out = new PrintStream(new FileOutputStream("data.txt"))) {
////            out.print(json);
////        } catch (IOException e) {
////            System.out.println("Unexpected exception happened in saving data: " + e.getLocalizedMessage());
////        }
//    }
//
//    public static void loadData() {
//        String json = "";
//
//        try {
//            InputStream inputStream = context.openFileInput("data.txt");
//
//            if (inputStream != null) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString;
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while ((receiveString = bufferedReader.readLine()) != null) {
//                    stringBuilder.append("\n").append(receiveString);
//                }
//
//                inputStream.close();
//                json = stringBuilder.toString();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
//
//        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
//
//        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
//
//        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
//
//        Gson gson = gsonBuilder.setPrettyPrinting().create();
//        sharedInstance = gson.fromJson(json, DataManager.class);
//    }

    public void setAllAds(ArrayList<Ad> allAds) {
        this.allAds = allAds;
    }

    public ArrayList<Log> getAllLogs() {
        ArrayList<Log> result = new ArrayList<>();
        result.addAll(purchaseLogs);
        result.addAll(sellLogs);
        return result;
    }

    public void setPurchaseLogs(ArrayList<PurchaseLog> purchaseLogs) {
        this.purchaseLogs = purchaseLogs;
    }

    public void setSellLogs(ArrayList<SellLog> sellLogs) {
        this.sellLogs = sellLogs;
    }

    public void logout() {
        if (loggedInAccount != null) {
            loggedInAccount = null;
            ContentValues cv = new ContentValues();
            cv.put("action", "logout");
            cv.put("token", getToken());
//            Gonnect.sendRequest("http://10.0.2.2:8111/", cv, (b, s) -> {
//            });
            Gonnect.getData(IP_SERVER + "req?action=logout&token="
                    + DataManager.encode(getToken()), (b, s) -> {
            });
        }
    }

    public void addAd(Ad ad) {
        allAds.add(ad);
        ContentValues cv = new ContentValues();
        cv.put("action", "addAd");
        cv.put("id", ad.getId());
        cv.put("content", ad.getContent());
        Gonnect.getData(IP_SERVER + "req?action=addAd&id=" + ad.getId() + "&content="
                + DataManager.encode(ad.getContent()), (b, s) -> {
        });
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
    }

    public ArrayList<Sale> getAllSales() {
        return allSales;
    }

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> result = new ArrayList<>();
        result.addAll(allCustomers);
        result.addAll(allSellers);
        result.addAll(allAdministrators);
        result.addAll(allAssistants);
        return result;
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public ArrayList<Request> getAllRequests() {
        ArrayList<Request> result = new ArrayList<>();
        result.addAll(addProductBySellerRequests);
        result.addAll(addSaleBySellerRequests);
        result.addAll(editProductBySellerRequests);
        result.addAll(editSaleBySellerRequests);
        result.addAll(sellerRegistrationRequests);
        result.addAll(adRequests);
        return result;
    }

    public void addRequest(Request request) {
        String type = "";
        String requestStr = new Gson().toJson(request);
        ContentValues cv = new ContentValues();
        cv.put("action", "addRequest");
        if (request instanceof AddProductBySellerRequest) {
            addProductBySellerRequests.add((AddProductBySellerRequest) request);
            cv.put("request", new Gson().toJson(request));
            type = "AddProductBySellerRequest";
        } else if (request instanceof AddSaleBySellerRequest) {
            addSaleBySellerRequests.add((AddSaleBySellerRequest) request);
            cv.put("request", new Gson().toJson(request));
            type = "AddSaleBySellerRequest";
        } else if (request instanceof EditProductBySellerRequest) {
            editProductBySellerRequests.add((EditProductBySellerRequest) request);
            cv.put("request", new Gson().toJson(request));
            type = "EditProductBySellerRequest";
        } else if (request instanceof EditSaleBySellerRequest) {
            editSaleBySellerRequests.add((EditSaleBySellerRequest) request);
            cv.put("request", new Gson().toJson(request));
            type = "EditSaleBySellerRequest";
        } else if (request instanceof SellerRegistrationRequest) {
            sellerRegistrationRequests.add((SellerRegistrationRequest) request);
            cv.put("request", new Gson().toJson(request));
            type = "SellerRegistrationRequest";
        } else if (request instanceof AddAdBySellerRequest) {
            adRequests.add((AddAdBySellerRequest) request);
            cv.put("request", new Gson().toJson((AddAdBySellerRequest) request));
            type = "AddAdBySellerRequest";
        }
        cv.put("requestType", type);
        Gonnect.getData(IP_SERVER + "req?action=addRequest&requestType=" + type + "&request="
                + DataManager.encode(requestStr), (b, s) -> {
        });
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

    public void removeProduct(String productID) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productID)) {
                ContentValues cv = new ContentValues();
                cv.put("action", "removeProduct");
                cv.put("id", product.getProductId());
                Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
                });
                allProducts.remove(product);
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

    public void syncProducts() {
        ContentValues cv = new ContentValues();
        cv.put("action", "syncProducts");
        cv.put("products", new Gson().toJson(getAllProducts()));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncAuctions() {
        ContentValues cv = new ContentValues();
        cv.put("action", "syncAuctions");
        cv.put("auctions", new Gson().toJson(getAuctions()));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncAssistantMessages() {
        ContentValues cv = new ContentValues();
        cv.put("action", "syncAssistantMessages");
        cv.put("messages", new Gson().toJson(getAllAssistantMessages()));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncAccounts() {
        syncCustomers();
        syncSellers();
        syncAdministrators();
    }

    public void syncCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        for (Account account : getAllAccounts()) {
            if (account instanceof Customer) customers.add((Customer) account);
        }
        ContentValues cv = new ContentValues();
        cv.put("action", "syncCustomers");
        cv.put("customers", new Gson().toJson(customers));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncSellers() {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (Account account : getAllAccounts()) {
            if (account instanceof Seller) sellers.add((Seller) account);
        }
        ContentValues cv = new ContentValues();
        cv.put("action", "syncSellers");
        cv.put("sellers", new Gson().toJson(sellers));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncAdministrators() {
        ArrayList<Administrator> administrators = new ArrayList<>();
        for (Account account : getAllAccounts()) {
            if (account instanceof Administrator) administrators.add((Administrator) account);
        }
        ContentValues cv = new ContentValues();
        cv.put("action", "syncAdministrators");
        cv.put("administrators", new Gson().toJson(administrators));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncCategories() {
        ContentValues cv = new ContentValues();
        cv.put("action", "syncCategories");
        cv.put("categories", new Gson().toJson(getAllCategories()));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncSales() {
        ContentValues cv = new ContentValues();
        cv.put("action", "syncSales");
        cv.put("sales", new Gson().toJson(getAllSales()));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncCoupons() {
        ContentValues cv = new ContentValues();
        cv.put("action", "syncCoupons");
        cv.put("coupons", new Gson().toJson(getAllCoupons()));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    // TODO: Cart should be get on login
    public void syncCartForUser() {
        if (getLoggedInAccount() instanceof Customer) {
            ContentValues cv = new ContentValues();
            cv.put("action", "syncCartForUser");
            cv.put("token", getToken());
            cv.put("cart", new Gson().toJson(((Customer) getLoggedInAccount()).getCart()));
            Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
            });
        }
    }

    public void syncLogs() {
        syncPurchaseLogs();
        syncSellLogs();
    }

    public void syncPurchaseLogs() {
        ArrayList<PurchaseLog> purchaseLogs = new ArrayList<>();
        for (Log log : getAllLogs()) {
            if (log instanceof PurchaseLog) purchaseLogs.add((PurchaseLog) log);
        }
        ContentValues cv = new ContentValues();
        cv.put("action", "syncPurchaseLogs");
        cv.put("logs", new Gson().toJson(purchaseLogs));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncSellLogs() {
        ArrayList<SellLog> sellLogs = new ArrayList<>();
        for (Log log : getAllLogs()) {
            if (log instanceof SellLog) sellLogs.add((SellLog) log);
        }
        ContentValues cv = new ContentValues();
        cv.put("action", "syncSellLogs");
        cv.put("logs", new Gson().toJson(sellLogs));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void syncTemporaryCart() {
        ContentValues cv = new ContentValues();
        cv.put("action", "syncTemporaryCart");
        cv.put("cart", new Gson().toJson(getTemporaryCart()));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public DataManager.AccountType login(String username, String password) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                loggedInAccount = account;
                ContentValues cv = new ContentValues();
                cv.put("action", "login");
                cv.put("username", username);
                cv.put("password", password);
                // TODO: Repeated username/password is checked where??
                Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
                    try {
                        JSONObject jo = new JSONObject(s);
                        setToken(jo.getString("token"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                if (account instanceof Customer) {
                    return DataManager.AccountType.CUSTOMER;
                } else if (account instanceof Administrator) {
                    return DataManager.AccountType.ADMINISTRATOR;
                } else if (account instanceof Seller) {
                    return DataManager.AccountType.SELLER;
                } else if (account instanceof Assistant) {
                    return DataManager.AccountType.ASSISTANT;
                }
                break;
            }
        }
        loggedInAccount = null;
        logout();
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
        ContentValues cv = new ContentValues();
        cv.put("action", "register");
        cv.put("username", account.getUsername());
        cv.put("password", account.getPassword());
        cv.put("email", account.getEmail());
        cv.put("phoneNumber", account.getPhoneNumber());
        cv.put("firstName", account.getFirstName());
        cv.put("lastName", account.getLastName());
        if (account instanceof Customer) {
            allCustomers.add((Customer) account);
            cv.put("type", "customer");
        } else if (account instanceof Seller) {
            allSellers.add((Seller) account);
            cv.put("type", "seller");
            cv.put("companyDetails", ((Seller) account).getCompanyDetails());
        } else if (account instanceof Administrator) {
            allAdministrators.add((Administrator) account);
            cv.put("type", "administrator");
        } else if (account instanceof Assistant) {
            allAssistants.add((Assistant) account);
            cv.put("type", "assistant");
        }
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
            try {
                JSONObject jo = new JSONObject(s);
                // TODO: Does the next line work correctly??
                setToken(jo.getString("token"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean givenUsernameHasGivenPassword(String username, String password) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return account.getPassword().equalsIgnoreCase(password);
            }
        }
        return false;
    }

    public void addLog(Log log) {
        ContentValues cv = new ContentValues();
        cv.put("action", "addLog");
        if (log instanceof SellLog) {
            sellLogs.add((SellLog) log);
            cv.put("log", new Gson().toJson((SellLog) log));
            cv.put("logType", "SellLog");
        } else if (log instanceof PurchaseLog) {
            purchaseLogs.add((PurchaseLog) log);
            cv.put("log", new Gson().toJson((PurchaseLog) log));
            cv.put("logType", "PurchaseLog");
        }
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void addSale(Sale sale) {
        allSales.add(sale);
        ContentValues cv = new ContentValues();
        cv.put("action", "addSale");
        cv.put("sale", new Gson().toJson(sale));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void addProduct(Product product) {
        allProducts.add(product);
        ContentValues cv = new ContentValues();
        cv.put("action", "addProduct");
        cv.put("product", new Gson().toJson(product));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void addCoupon(Coupon coupon) {
        allCoupons.add(coupon);
        ContentValues cv = new ContentValues();
        cv.put("action", "addCoupon");
        cv.put("coupon", new Gson().toJson(coupon));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void addCategory(Category category) {
        allCategories.add(category);
        ContentValues cv = new ContentValues();
        cv.put("action", "addCategory");
        cv.put("category", new Gson().toJson(category));
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
    }

    public void removeCoupon(Coupon coupon) {
        ContentValues cv = new ContentValues();
        cv.put("action", "removeCoupon");
        cv.put("id", coupon.getId());
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
        allCoupons.remove(coupon);
    }

    public void removeRequest(Request request) {
        ContentValues cv = new ContentValues();
        cv.put("action", "removeRequest");
        cv.put("id", request.getId());
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
        addProductBySellerRequests.remove(request);
        addSaleBySellerRequests.remove(request);
        editProductBySellerRequests.remove(request);
        editSaleBySellerRequests.remove(request);
        sellerRegistrationRequests.remove(request);
        adRequests.remove(request);
    }

    public void removeSale(Sale sale) {
        ContentValues cv = new ContentValues();
        cv.put("action", "removeSale");
        cv.put("id", sale.getOffId());
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
        allSales.remove(sale);
    }

    public void removeAccount(Account account) {
        ContentValues cv = new ContentValues();
        cv.put("action", "removeAccount");
        cv.put("username", account.getUsername());
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
        allCustomers.remove(account);
        allSellers.remove(account);
        allAdministrators.remove(account);
        allAssistants.remove(account);
    }

    public void removeCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put("action", "removeCategory");
        cv.put("id", category.getId());
        Gonnect.sendRequest(IP_SERVER, cv, (b, s) -> {
        });
        allCategories.removeIf(c -> c.getId().equals(category.getId()));
    }

    public Coupon getCouponWithId(String id) {
        return allCoupons.stream().filter(coupon -> coupon.getId().equals(id)).findFirst().orElse(null);
    }

    public Category getCategoryWithId(String id) {
        return allCategories.stream().filter(category -> category.getId().equals(id)).findFirst().orElse(null);
    }

    public Category getCategoryWithName(String name) {
        return allCategories.stream().filter(category -> category.getName().equals(name)).findFirst().orElse(null);
    }

    public Product getProductWithId(String id) {
        return allProducts.stream().filter(product -> product.getProductId().equals(id)).findFirst().orElse(null);
    }

    public Auction getAuctionWithId(String id) {
        return auctions.stream().filter(auction -> auction.getId().equals(id)).findFirst().orElse(null);
    }

    public Ad getAdWithId(String id) {
        return allAds.stream().filter(ad -> ad.getId().equals(id)).findFirst().orElse(null);
    }

    public Product getProductWithName(String name) {
        return allProducts.stream().filter(product -> product.getName().equals(name)).findFirst().orElse(null);
    }

    public Sale getSaleWithId(String id) {
        return allSales.stream().filter(sale -> sale.getOffId().equals(id)).findFirst().orElse(null);
    }

    public enum AccountType {
        CUSTOMER, ADMINISTRATOR, SELLER, ASSISTANT, NONE
    }

    public static LocalDateTime dateFromString(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu-HH-mm-ss");
        return LocalDateTime.parse(string, formatter);
    }

    public static String stringFromDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu-HH-mm-ss");
        return dateTime.format(formatter);
    }

}

class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu-HH-mm-ss");

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}

class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("d-MMM-uuuu-HH-mm-ss").withLocale(Locale.ENGLISH));
    }
}

class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDate.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("d-MMM-yyyy").withLocale(Locale.ENGLISH));
    }
}

class LocalDateSerializer implements JsonSerializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

    @Override
    public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDate));
    }
}