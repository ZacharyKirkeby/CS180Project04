package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Project 04 -- Store.java
 * creates Store class
 * Handles routine store tasks
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

    // Constructor for store based off a literal seller object
    public Store(String name, String storeLocation, Seller seller,
                 ArrayList<Product> productList) {
        this.storeName = name;
        this.storeLocation = storeLocation;
        this.storeOwner = seller;
        this.productList = productList;
    }
    // More generic Store constructor based off a named seller
    public Store(String name, String storeLocation, String sellerUsername,
                 ArrayList<Product> productList) {
        this.storeName = name;
        this.storeLocation = storeLocation;
        this.sellerUsername = sellerUsername;
        this.productList = productList;
    }
    // gets total number of products sold
    public int getTotalSales() {
        for(Product p:productList) {
            totalSales += p.getQuantitySold();
        }
        return totalSales;
    }
    // calculates total revenue by getting sales * price
    public double getTotalRevenue() {
        for(Product p:productList) {
            totalRevenue += p.getSales();
        }
        return totalRevenue;
    }

    // returns arraylist of products
    public ArrayList<Product> getProductList() {
        return productList;
    }
    // generic setter
    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
    // returns store name
    public String getStoreName() {
        return storeName;
    }
    // allows for renaming store
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    // generic getter
    public String getStoreLocation() {
        return storeLocation;
    }
    // generic setter
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

    /**
     * Iterates through purchase history file
     * if store name corosponds to this store name
     * adds line
     * returns end list of customers and their purchases
     **/
    public  String getSortedCustomersAndPurchases () {
        String sentence = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("PurchaseHistoryDatabase.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] subpart = line.split(",");
                if (subpart[3].equals(storeName)) {
                    sentence += line;
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentence;
    }
    public static String getCustomersAndPurchases () {
        String sentence = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("PurchaseHistoryDatabase.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                sentence += line;
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentence;
    }
    public void Purchases () {
        double totalPurchases = 0;
        for (int i = 0; i < productList.size(); i++) {
            totalPurchases += productList.get(i).getSales();
        }
    }
}
