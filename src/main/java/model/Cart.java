package model;

import java.util.HashMap;

public class Cart {
    HashMap<Product, Integer> products;

    public Cart() {
        products = new HashMap<>();
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    public void addProduct(Product product, int quantity) {
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }
    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void removeProduct(Product product, int quantity) {
        int finalQuantity = products.getOrDefault(product, 0) - quantity;
        if (finalQuantity <= 0) {
            products.remove(product);
        } else {
            products.put(product, finalQuantity);
        }
    }
    public void removeProduct(Product product) {
        removeProduct(product, 1);
    }

    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public boolean containsProduct(Product product) {
        return products.containsKey(product);
    }
}
