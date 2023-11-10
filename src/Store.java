package src;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Project 04 -- Store.java
 * creates Store class
 * WIP
 *
 * @author Zachary Kirkeby, 05
 * @version November 3, 2023
 */
public class Store {
    private ArrayList<Product> productList;
    private String storeName;
    private String storeLocation;
    private Seller storeOwner;
    private String sellerUsername;
    private int totalSales = 0;
    private double totalRevenue = 0;

    public Store(String name, String storeLocation, Seller seller,
                 ArrayList<Product> productList) {
        this.storeName = name;
        this.storeLocation = storeLocation;
        this.storeOwner = seller;
        this.productList = productList;
    }

    public Store(String name, String storeLocation, String sellerUsername,
                 ArrayList<Product> productList) {
        this.storeName = name;
        this.storeLocation = storeLocation;
        this.sellerUsername = sellerUsername;
        this.productList = productList;
    }

    public int getTotalSales() {
        for(Product p:productList) {
            totalSales += p.getQuantitySold();
        }
        return totalSales;
    }

    public double getTotalRevenue() {
        for(Product p:productList) {
            totalRevenue += p.getSales();
        }
        return totalRevenue;
    }


    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public Seller getStoreOwner() {
        return storeOwner;
    }

    public void setStoreOwner(Seller storeOwner) {
        this.storeOwner = storeOwner;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public String toString() {
        return storeName + "," + storeLocation + "," + sellerUsername;
    }

    public String getSellserUsername () {
        return sellerUsername;
    }

    public void setSellserUsername (String sellserUsername){
        this.sellerUsername = sellserUsername;
    }

    public static String getSortedCustomersAndPurchases (ArrayList < Store > stores) {
        for (int i = 0; i < stores.size(); i++) {
            for (int j = i + 1; j < stores.size(); j++) {
                if (stores.get(i).getStoreName().compareTo(stores.get(j).getStoreName()) > 0) {
                    Collections.swap(stores, i, j);
                }
            }
        }
        return stores.toString();
    }
    public static String getCustomersAndPurchases (ArrayList < Store > stores) {
        return stores.toString();
    }
    public void Purchases () {
        double totalPurchases = 0;
        for (int i = 0; i < productList.size(); i++) {
            totalPurchases += productList.get(i).getSales();
        }
    }
}
