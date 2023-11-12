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
     * Constructor w Seller username
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
        for (Product p : productList) {
            totalRevenue += p.getSales();
        }
        return totalRevenue;
    }

    /**
     * @return list of products
     */
    public ArrayList<Product> getProductList() {
        return productList;
    }

    // generic setter
    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    /**
     * @return store name
     */
    public String getStoreName() {
        return storeName;
    }

    // allows for renaming store
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return store location
     */
    public String getStoreLocation() {
        return storeLocation;
    }

    // generic setter
    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    /**
     * @return username of the seller/store owner
     */
    public String getSellerUsername() {
        return sellerUsername;
    }

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
        return storeName + "," + products;
    }

    /**
     * @return sorted arraylist of products from cheapest to most expensive
     */
    public ArrayList<Product> cheapestProduct(ArrayList<Product> products) {
        return Product.sortByCheapest(products);
    }

    /**
     * Iterates through purchase history file
     * if store name corresponds to this store name
     * adds line
     * returns end list of customers and their purchases
     *
     * @Author Zachary Kirkeby
     **/
    public String getSortedCustomersAndPurchases() {
        String sentence = "Customer Email | Customer Username | Store Name | Product Name | Quantity Purchased \n";
        try (BufferedReader reader = new BufferedReader(new FileReader("PurchaseHistoryDatabase.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] subpart = line.split(";");
                if (subpart[2].equals(storeName)) {
                    sentence += line + "\n";
                    sentence.replace(";", " | ");
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
    public static String getCustomersAndPurchases() {
        String sentence = "Customer Email | Customer Username | Store Name | Product Name | Quantity Purchased \n";
        try (BufferedReader reader = new BufferedReader(new FileReader("PurchaseHistoryDatabase.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                sentence += line + "\n";
                sentence.replace(";", " | ");
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentence;
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
        String sentence = "Customer Email | Customer Username | Store Name | Product Name | Quantity Purchased |" + " Revenue From Customer \n";
        try (BufferedReader reader = new BufferedReader(new FileReader("PurchaseHistoryDatabase.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] subpart = line.split(";");
                String productName = subpart[3];
                for (Product p : productList) {
                    if (p.getName().equals(productName) && (subpart[2].equals(storeName))) {
                        double revenue = Integer.parseInt(subpart[4]) * p.getPurchasePrice();
                        sentence += line + " | " + revenue + "\n";
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
    public String triggerSale(String productName, double salePrice, int saleCap) {
        String output = "";
        for (Product p : productList) {
            if (p.getName().equals(productName)) {
                output = p.startSale(salePrice, saleCap);
            }
        }
        return output;
    }

    /**
     * Finds product and calls respective product method
     * starts sale
     * returns failure or not
     * handler for method in product
     *
     * @param productName
     * @param cap
     * @return
     * @Author Zachary Kirkeby
     */
    public String triggerCap(String productName, int cap) {
        String output = "";
        for (Product p : productList) {
            if (p.getName().equals(productName)) {
                output = p.setCap(cap);
            }
        }
        return output;
    }
}
