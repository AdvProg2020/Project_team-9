package model;

import controller.DataManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Log {
    private String id;
    // TODO: Date in Gson??
    private LocalDateTime date;
    private long price;
    private int discountAmount;
    private HashMap<String, Integer> products;
    // TODO: Enum in Gson??
    private DeliveryStatus deliveryStatus;

    public String getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public long getPrice() {
        return price;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public HashMap<Product, Integer> getProducts() {
        HashMap<Product, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> stringIntegerEntry : products.entrySet()) {
            Map.Entry pair = stringIntegerEntry;
            result.put(DataManager.shared().getProductWithId((String) pair.getKey()), (int) pair.getValue());
        }
        return result;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public Log(String id, LocalDateTime date, long price, int discountAmount, HashMap<Product, Integer> products, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.discountAmount = discountAmount;
        this.products = new HashMap<>();
        products.entrySet().forEach(productIntegerEntry -> this.products.put(((Product) (((Map.Entry) productIntegerEntry).getKey())).getProductId(), (int) ((Map.Entry) productIntegerEntry).getValue()));
        this.deliveryStatus = deliveryStatus;
    }
}
