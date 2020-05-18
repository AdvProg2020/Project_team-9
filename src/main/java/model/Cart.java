package model;

import controller.DataManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cart {
    private HashMap<String, Integer> products;

    public Cart() {
        products = new HashMap<>();
    }

    public HashMap<Product, Integer> getProducts() {
        HashMap<Product, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> stringIntegerEntry : products.entrySet()) {
            Map.Entry pair = stringIntegerEntry;
            result.put(DataManager.shared().getProductWithId((String) pair.getKey()), (int) pair.getValue());
        }
        return result;
    }

    // TODO: Does this func work right??
    public void setProducts(HashMap<Product, Integer> products) {
        this.products = new HashMap<>();
        products.entrySet().forEach(productIntegerEntry -> this.products.put(((Product) (((Map.Entry) productIntegerEntry).getKey())).getProductId(), (int) ((Map.Entry) productIntegerEntry).getValue()));
        DataManager.saveData();
    }

    public void addProduct(Product product, int quantity) {
        products.put(product.getProductId(), products.getOrDefault(product.getProductId(), 0) + quantity);
        DataManager.saveData();
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void removeProduct(Product product, int quantity) {
        int finalQuantity = products.getOrDefault(product.getProductId(), 0) - quantity;
        if (finalQuantity <= 0) {
            products.remove(product.getProductId());
        } else {
            products.put(product.getProductId(), finalQuantity);
        }
        DataManager.saveData();
    }
    public void removeProduct(Product product) {
        removeProduct(product, 1);
    }

    public boolean containsProduct(Product product) {
        return products.containsKey(product.getProductId());
    }

    public long getPrice(){
        HashMap<Product,Integer> products = getProducts();
        long price = products.keySet().stream().mapToLong(Product::getPrice).sum();
        return price;
    }
}
