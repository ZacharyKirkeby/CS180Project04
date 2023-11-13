package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Project 04 -- Store.java
 * creates Store class
 * Handles routine store tasks
 *
 * @author Zachary Kirkeby, 05
 * @version November 10, 2023
 */
public class Store {
    private ArrayList<Product> productList; // list of products
    private String storeName; // store name
    private String storeLocation; // store location (thematic)
    private String sellerUsername; // second way of checking
    private int totalSales = 0; // total sales
    private double totalRevenue = 0; // total money earned

    /**
     * Constructor with Seller username
     *
     * @param name
     * @param storeLocation
     * @param sellerUsername
     * @param productList
     */
    public Store(String name, String storeLocation, String sellerUsername, ArrayList<Product> productList) {
        this.storeName = name;
        this.storeLocation = storeLocation;
        this.sellerUsername = sellerUsername;
        this.productList = productList;
    }

    /**
     * Creates a new Store object
     *
     * @param name
     * @param storeLocation
     * @param sellerUsername
     */
    public Store(String name, String storeLocation, String sellerUsername) {
        this.storeName = name;
        this.storeLocation = storeLocation;
        this.sellerUsername = sellerUsername;
        this.productList = new ArrayList<>();
    }

    /**
     * @return the total number of sales across all products
     */
    public int getTotalSales() {
        totalSales = 0;
        for (Product p : productList) {
            totalSales += p.getQuantitySold();
        }
        return totalSales;
    }

    /**
     * @return total revenue across all products
     */
    public double getTotalRevenue() {
        totalRevenue = 0;
        for (Product p : productList) {
            totalRevenue += p.getRevenue();
        }
        return totalRevenue;
    }

    /**
     * @return list of products
     */
    public ArrayList<Product> getProductList() {
        return productList;
    }

    /**
     * @param productList
     */
    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    /**
     * @return store name
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return store location
     */
    public String getStoreLocation() {
        return storeLocation;
    }

    /**
     * @param storeLocation
     */
    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    /**
     * @return username of the seller/store owner
     */
    public String getSellerUsername() {
        return sellerUsername;
    }

    /**
     * @param sellerUsername
     */
    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    /**
     * @return store name, store location, seller username
     */
    public String toString() {
        return storeName + "," + storeLocation + "," + sellerUsername;
    }

    /**
     * @return store name and the list of all products separated by a |
     */
    public String toStringProducts() {
        String products = "";
        for (Product p : productList) {
            products += p.getName() + " | ";
        }
        String result = storeName + ":" + products;
        return result.substring(0, result.length() - 3);
    }

    /**
     * @return sorted arraylist of products from cheapest to most expensive
     */
    public ArrayList<Product> getProductsSortedByCheapest() {
        return Product.sortByCheapest(productList);
    }

    /**
     * Iterates through purchase history file
     * if store name corresponds to this store name
     * adds line
     * returns end list of customers and their purchases
     *
     * @Author Zachary Kirkeby
     **/
    public String getCustomersAndPurchases() {
        String sentence = "Customer Email | Customer Username | Store Name | Product Name | Quantity Purchased \n";
        try (BufferedReader reader = new BufferedReader(new FileReader("PurchaseHistoryDatabase.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] subpart = line.split(";");
                if (subpart[2].equals(storeName)) {
                    sentence += line.replaceAll(";", " | ") + "\n";
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentence;
    }

    /**
     * Iterates through purchase history file
     * adds records by adding line
     * returns end list of customers and their purchases
     *
     * @Auther Zachary Kirkeby
     **/
    public static String getAllCustomersAndPurchases() {
        String sentence = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("PurchaseHistoryDatabase.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                sentence += line + "\n";
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentence;
    }

    /**
     * @return All customers and purchases sorted by the amount spend per customer
     */
    public String getSortedCustomersAndPurchases() {
        String result = "Customer Email | Customer Username | Store Name | Product Name | Quantity Purchased \n";
        ;
        String sentence = getAllCustomersAndPurchases();
        if (sentence == null || sentence.isEmpty()) {
            return "No Purchase History Found!";
        }
        String[] line = sentence.split("\n");
        ArrayList<String> lineList = new ArrayList<>();
        for (int a = 0; a < line.length; a++) {
            lineList.add(line[a]);
        }
        for (int i = 0; i < lineList.size(); i++) {
            for (int j = i + 1; j < lineList.size(); j++) {
                String[] attributes = lineList.get(i).split(";");
                String[] attributesTwo = lineList.get(j).split(";");
                double amountPurchased1 = Seller.getTotalPurchasePerCustomer(attributes[2], attributes[3],
                        Integer.parseInt(attributes[4]));
                double amountPurchased2 = Seller.getTotalPurchasePerCustomer(attributesTwo[2], attributesTwo[3],
                        Integer.parseInt(attributesTwo[4]));
                if (amountPurchased1 < amountPurchased2) {
                    Collections.swap(lineList, i, j);
                }
            }
        }
        for (int a = 0; a < lineList.size(); a++) {
            result += lineList.get(a) + "\n";
        }
        result = result.replace(";", " | ");
        return result;
    }

    /**
     * reads from purchase history file
     * checks for matches based off current store object
     * adds details if matches
     * includes some formatting
     *
     * @return
     * @Author Zachary Kirkeby
     */
    public String getCustomerInformationAndRevenue() {
        String sentence = "Customer Email | Customer Username | Store Name | Product Name | Quantity Purchased |"
                + " Revenue From Customer \n";
        try (BufferedReader reader = new BufferedReader(new FileReader("PurchaseHistoryDatabase.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] subpart = line.split(";");
                String productName = subpart[3];
                for (Product p : productList) {
                    if (p.getName().equals(productName) && (subpart[2].equals(storeName))) {
                        double revenue = Integer.parseInt(subpart[4]) * Double.parseDouble(subpart[5]); // only gets
                        // current
                        // price and doesn't know what the price at sale was
                        sentence += line.replaceAll(";", " | ") + " | " + revenue + "\n";
                    }
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentence;
    }

    /**
     * Finds product and calls respective product method
     * starts sale
     * returns same values as startSale method in product
     * handler for method in product
     *
     * @param productName
     * @param salePrice
     * @param saleCap
     * @return
     * @Author Zachary Kirkeby
     */
    public boolean triggerSale(String productName, double salePrice, int saleCap) {
        boolean result = false;
        for (Product p : productList) {
            if (p.getName().equals(productName)) {
                result = p.startSale(salePrice, saleCap);
            }
        }
        return result;
    }

    /**
     * @param productName of product you want to end sale for
     * @return true if product found and sale ended, false if not
     */
    public boolean triggerEndSale(String productName) {
        boolean result = false;
        for (Product p : productList) {
            if (p.getName().equals(productName)) {
                result = p.endSale();
            }
        }
        return result;
    }

    /**
     * sets the order capacity for each order
     * (NOT FOR CUSTOMER, customer can still place multiple order and bypass this)
     *
     * @param productName
     * @param cap
     * @return true if successful false if not
     * @Author Zachary Kirkeby
     */
    public boolean triggerOrderCap(String productName, int cap) {
        boolean result = false;
        for (Product p : productList) {
            if (p.getName().equals(productName)) {
                result = p.setCap(cap);
            }
        }
        return result;
    }
}
