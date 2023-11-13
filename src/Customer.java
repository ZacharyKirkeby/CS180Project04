package src;

import java.io.*;
import java.util.*;

/**
 * Class to support customer interaction with the stores in the marketplace.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 * Manages the shopping cart and purchase history
 * Creates ShoppingCartDatabase.txt and "PurchaseHistoryDatabase.txt"
 * Creates the Purchase History file based on the customer input filename
 *
 * @author Yi Lin Yang
 * @author Armaan Sayyad
 * @version November 9, 2023
 */
public abstract class Customer {
    private static ArrayList<String> emails = new ArrayList<>(); // emails arraylist
    private static ArrayList<String> usernames = new ArrayList<>(); // usernames arraylist
    private static ArrayList<String> storeNames = new ArrayList<>(); // storeNames arraylist
    private static ArrayList<String> productNames = new ArrayList<>(); // productNames arraylist
    private static ArrayList<Integer> quantities = new ArrayList<>(); // quantities arraylist
    private static String shoppingCartDatabaseFileName = "ShoppingCartDatabase.txt"; //shopping cart database
    private static String purchaseHistoryDatabaseFileName = "PurchaseHistoryDatabase.txt"; // purchase history
    private static boolean bool; //


    /**
     * @param storeName
     * @param productName
     * @return total number of a products in all the customer's carts
     */
    public static int getTotalInCart(String storeName, String productName) {
        readFromShoppingCartDatabaseFile();
        int totalQuantityOfProduct = 0;
        for (int i = 0; i < storeNames.size(); i++) {
            if (storeNames.get(i).equals(storeName) && productNames.get(i).equals(productName)) {
                totalQuantityOfProduct += quantities.get(i);
            }
        }
        return totalQuantityOfProduct;
    }

    /**
     * @param storeName
     * @param stores
     * @return boolean of whether the store exists in the marketplace
     */
    public static boolean searchedStoreExists(String storeName, ArrayList<Store> stores) {
        for (Store store : Seller.getStores()) {
            if (store.getStoreName().equals(storeName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param productName
     * @param stores
     * @return boolean of whether the product exists in a store in the marketplace
     */
    public static boolean searchedProductExists(String productName, ArrayList<Store> stores) {
        for (Store store : Seller.getStores()) {
            for (Product product : store.getProductList()) {
                if (product.getName().equals(productName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a product to the shopping cart database
     *
     * @param email
     * @param username
     * @param store
     * @param product
     * @param quantity
     */
    public static boolean addToCart(String email, String username, String store, String product, int quantity) {
        if (alreadyInCartOfUser(store, product, username)) {
            System.out.println("This product is already in the Cart!");
            return false;
        } else {
            readFromShoppingCartDatabaseFile();
            for (int i = 0; i < Seller.getStores().size(); i++) {
                if (Seller.getStores().get(i).getStoreName().equals(store)) {
                    for (int j = 0; j < Seller.getStores().get(i).getProductList().size(); j++) {
                        if (Seller.getStores().get(i).getProductList().get(j).getName().equalsIgnoreCase(product)) {
                            if (Seller.getStores().get(i).getProductList().get(j).getStockQuantity() <= 0) {
                                System.out.println("Error out of Stock");
                                return false;
                            } else if (Seller.getStores().get(i).getProductList().get(j).getStockQuantity()
                                       < quantity) {
                                quantity = Seller.getStores().get(i).getProductList().get(j).getStockQuantity();
                                System.out.println("Quantity Exceeded Maximum in Stock, added as many as available");
                            }
                        }
                    }
                }
            }
            emails.add(email);
            usernames.add(username);
            storeNames.add(store);
            productNames.add(product);
            quantities.add(quantity);
            writeToShoppingCartDatabaseFile();
        }
        return true;
    }

    /**
     * Check if already in cart
     *
     * @param store
     * @param product
     * @return
     */
    public static boolean alreadyInCart(String store, String product) {
        readFromShoppingCartDatabaseFile();
        for (int i = 0; i < storeNames.size(); i++) {
            if (storeNames.get(i).equals(store) && productNames.get(i).equalsIgnoreCase(product)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if already in cart
     *
     * @param store
     * @param product
     * @param username
     * @return
     */
    public static boolean alreadyInCartOfUser(String store, String product, String username) {
        readFromShoppingCartDatabaseFile();
        for (int i = 0; i < storeNames.size(); i++) {
            if (storeNames.get(i).equals(store) && productNames.get(i).equalsIgnoreCase(product)
                    && usernames.get(i).equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds to cart
     *
     * @param storeName
     * @param productName
     * @param quantity
     * @return
     */
    public static boolean addToCartChangeCheckoutQuantity(String storeName, String productName, int quantity) {
        boolean success = true;
        if (alreadyInCart(storeName, productName)) {
            readFromShoppingCartDatabaseFile();
            for (int i = 0; i < storeNames.size(); i++) {
                if (storeNames.get(i).equals(storeName) && productNames.get(i).equalsIgnoreCase(productName)) {
                    quantity = quantities.get(i) + quantity;
                    quantities.set(i, quantity);
                    success = Seller.changeQuantity(storeNames.get(i), productNames.get(i), quantities.get(i));
                    if (success) {
                        writeToShoppingCartDatabaseFile();
                    } else if (!success) {
                        return success;
                    }
                }
            }
        }
        writeToShoppingCartDatabaseFile();
        return success;
    }

    /**
     * Removes a product from the purchase history database
     *
     * @param email
     * @param username
     * @param storeName
     * @param productName
     * @param quantity
     * @return
     */
    public static boolean removeFromCart(String email, String username, String storeName, String productName,
                                         int quantity) {
        boolean successfullyRemovedFromCart = false;
        readFromShoppingCartDatabaseFile();
        for (int i = 0; i < usernames.size(); i++) {
            if (emails.get(i).equals(email) && usernames.get(i).equals(username) && storeNames.get(i).equals(storeName)
                    && productNames.get(i).equals(productName) && quantities.get(i) == quantity) {
                successfullyRemovedFromCart = true;
                Seller.changeQuantity(storeName, productName, quantity);
                emails.remove(i);
                usernames.remove(i);
                storeNames.remove(i);
                productNames.remove(i);
                quantities.remove(i);
                break;
            } else if (emails.get(i).equals(email) && usernames.get(i).equals(username)
                       && storeNames.get(i).equals(storeName)
                    && productNames.get(i).equals(productName) && quantities.get(i) > quantity) {
                quantities.set(i, quantities.get(i) - quantity);
                Seller.changeQuantity(storeName, productName, quantity);
                successfullyRemovedFromCart = true;
            }

        }
        writeToShoppingCartDatabaseFile();
        return successfullyRemovedFromCart;
    }

    /**
     * Purchases the product by locating the product in the marketplace given the storeName and productName
     * Decreases the product quantity
     * Updates the shopping cart database
     * Updates the purchase history database
     *
     * @param username
     * @return boolean of whether the products in the shopping cart were purchased sucessfully
     */
    public static boolean buyProductsInShoppingCart(String username) {
        readFromShoppingCartDatabaseFile();
        boolean productsBoughtSuccessfully = false;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(username)) { // check if username matches'
                for (int j = 0; j < Seller.getStores().size(); j++) { // iterate through stores in marketplace
                    if (storeNames.get(i).equals(Seller.getStores().get(j).getStoreName())) {
                        // if storename matches
                        for (int k = 0; k < Seller.getStores().get(j).getProductList().size(); k++) {
                            // iterate through product list
                            if (Seller.getStores().get(j).getProductList().get(k).getName()
                                    .equals(productNames.get(i))) { // if product name matches
                                Seller.getStores().get(j).getProductList().get(k).buyProduct(quantities.get(i));
                                double unitprice =
                                        Seller.getStores().get(j).getProductList().get(k).getPurchasePrice();
                                writeToPurchaseHistoryDatabaseFile(emails.get(i), username, storeNames.get(i),
                                        productNames.get(i), quantities.get(i), unitprice);
                                removeFromCart(emails.get(i), usernames.get(i), storeNames.get(i),
                                        productNames.get(i), quantities.get(i));

                                productsBoughtSuccessfully = true;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return productsBoughtSuccessfully;
    }

    /**
     * Writes to the purchase history database file
     *
     * @param email
     * @param username
     * @param storeName
     * @param productName
     * @param quantity
     */
    public static void writeToPurchaseHistoryDatabaseFile(String email, String username, String storeName,
                                                          String productName, int quantity, double unitprice) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(purchaseHistoryDatabaseFileName, true))) {
            pw.println(String.format("%s;%s;%s;%s;%d;%.2f", email, username, storeName, productName, quantity,
                    unitprice));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes to the shopping cart database file by iterating through all the arrayList fields and appending them to
     * the shopping cart database file
     */
    public static void writeToShoppingCartDatabaseFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(shoppingCartDatabaseFileName))) {
            for (int i = 0; i < usernames.size(); i++) {
                pw.println(String.format("%s;%s;%s;%s;%d", emails.get(i), usernames.get(i), storeNames.get(i),
                        productNames.get(i), quantities.get(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads from the shopping cart database file
     */
    public static void readFromShoppingCartDatabaseFile() {
        emails.clear();
        usernames.clear();
        storeNames.clear();
        productNames.clear();
        quantities.clear();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(shoppingCartDatabaseFileName))) {
            line = br.readLine();
            while ((line != null) && (!line.isEmpty())) {
                String[] subpart = line.split(";");
                emails.add(subpart[0]);
                usernames.add(subpart[1]);
                storeNames.add(subpart[2]);
                productNames.add(subpart[3]);
                quantities.add(Integer.parseInt(subpart[4]));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads from the purchase history database file
     */
    public static void readFromPurchaseHistoryDatabaseFile() {
        emails.clear();
        usernames.clear();
        storeNames.clear();
        productNames.clear();
        quantities.clear();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(purchaseHistoryDatabaseFileName))) {
            line = br.readLine();
            while ((line != null) && (!line.isEmpty())) {
                String[] subpart = line.split(";");
                emails.add(subpart[0]);
                usernames.add(subpart[1]);
                storeNames.add(subpart[2]);
                productNames.add(subpart[3]);
                quantities.add(Integer.parseInt(subpart[4]));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param username
     * @return arraylist of the products currently in the shopping cart of the customer identified by the username
     */
    public static ArrayList<String> getShoppingCartofCustomer(String username) {
        readFromShoppingCartDatabaseFile();
        ArrayList<String> customerProducts = new ArrayList<>();
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(username)) { // check if username matches
                customerProducts.add(String.format("%s;%s;%s;%s;%d", emails.get(i), usernames.get(i),
                        storeNames.get(i), productNames.get(i), quantities.get(i)));
            }
        }
        return customerProducts;
    }

    /**
     * Generates purchase history file for the customer identified by the username
     *
     * @param username
     * @param fileName
     */
    public static boolean getPurchaseHistoryofCustomer(String username, String fileName) {
        boolean success = false;
        readFromPurchaseHistoryDatabaseFile();
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < usernames.size(); i++) {
                if (usernames.get(i).equals(username)) { // check if username and email match
                    pw.println(String.format("%s;%s;%s;%s;%d", emails.get(i), usernames.get(i), storeNames.get(i),
                            productNames.get(i), quantities.get(i)));
                }
            }
            success = true;
        } catch (IOException e) {
            success = false;
        }
        return success;
    }

    /**
     * @param storeName
     * @param productName
     * @param customerName
     * @param rating
     * @param description
     * @return whether or not the write process was successful
     */
    public static boolean leaveReview(String storeName, String productName, String customerName, int rating,
                                      String description) {
        if (!(1 <= rating && rating <= 5)) {
            System.out.println("Invalid Input");
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader("Reviews.txt"));
             PrintWriter pw = new PrintWriter(new FileWriter("Reviews.txt", true), true)) {
            String line = br.readLine();
            int count = 0;
            if (line == null) {
                pw.println(String.format("%s , %s , %s , %d , %s", storeName, productName, customerName, rating,
                        description));
            } else {
                while (line != null) {
                    line = br.readLine();
                    if (line == null) {
                        pw.println(String.format("%s , %s , %s , %d , %s", storeName, productName, customerName, rating,
                                description));
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param storeName
     * @param productName
     * @return the reviews of the specified product passed in
     */
    public static String viewReviews(String storeName, String productName) {
        String result = "Store Name | Product Name | Customer Name | Rating \n";
        try (BufferedReader br = new BufferedReader(new FileReader("Reviews.txt"))) {
            String line = br.readLine();
            while (line != null) {
                String[] subpart = line.split(",");
                if (storeName.equals("")) {
                    if (subpart[1].contains(productName)) {
                        result += line + "\n";
                    }
                } else {
                    if (subpart[0].contains(storeName) && subpart[1].contains(productName)) {
                        result += line + "\n";
                    }
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = result.replace(",", "|");
        return result;
    }
}
