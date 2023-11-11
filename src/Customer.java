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
 * @version November 9, 2023
 */


public abstract class Customer {
    private static ArrayList<String> emails = new ArrayList<>(); // emails arraylist
    private static ArrayList<String> usernames = new ArrayList<>(); // usernames arraylist
    private static ArrayList<String> storeNames = new ArrayList<>(); // storeNames arraylist
    private static ArrayList<String> productNames = new ArrayList<>(); // productNames arraylist
    private static ArrayList<Integer> quantities = new ArrayList<>(); // quantities arraylist
    private static String shoppingCartDatabaseFileName = "ShoppingCartDatabase.txt";
    //shopping cart database for all customers
    private static String purchaseHistoryDatabaseFileName = "PurchaseHistoryDatabase.txt";
    // purchase history databse for all customers


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
        for (Store store : MarketPlace.getStores()) {
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
        for (Store store : MarketPlace.getStores()) {
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
     * @param email
     * @param username
     * @param store
     * @param product
     * @param quantity
     */

    public static void addToCart(String email, String username, String store, String product, int quantity) {
        readFromShoppingCartDatabaseFile();
        emails.add(email);
        usernames.add(username);
        storeNames.add(store);
        productNames.add(product);
        quantities.add(quantity);
        writeToShoppingCartDatabaseFile();
    }

    /**
     * Removes a product from the purchase history database
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
                emails.remove(i);
                usernames.remove(i);
                storeNames.remove(i);
                productNames.remove(i);
                quantities.remove(i);
                break;
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
     * @param username
     * @return boolean of whether the products in the shopping cart were purchased sucessfully
     */

    public static boolean buyProductsInShoppingCart(String username) {
        readFromShoppingCartDatabaseFile();
        boolean productsBoughtSuccessfully = false;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(username)) { // check if username matches'
                for (int j = 0; j < MarketPlace.getStores().size(); j++) { // iterate through stores in marketplace
                    if (storeNames.get(i).equals(MarketPlace.getStores().get(j).getStoreName())) {
                        // if storename matches
                        for (int k = 0; k < MarketPlace.getStores().get(j).getProductList().size(); k++) {
                            // iterrate through product list
                            if (MarketPlace.getStores().get(j).getProductList().get(k).getName()
                                    .equals(productNames.get(i))) { // if product name matches
                                MarketPlace.getStores().get(j).getProductList().get(k).buyProduct(quantities.get(i));
                                writeToPurchaseHistoryDatabaseFile(emails.get(i), username, storeNames.get(i),
                                        productNames.get(i), quantities.get(i));
                                emails.remove(i);
                                usernames.remove(i);
                                storeNames.remove(i);
                                productNames.remove(i);
                                quantities.remove(i);
                                writeToShoppingCartDatabaseFile();
                                productsBoughtSuccessfully = true;
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
     * @param email
     * @param username
     * @param storeName
     * @param productName
     * @param quantity
     */

    public static void writeToPurchaseHistoryDatabaseFile(String email, String username, String storeName,
                                                          String productName, int quantity) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(purchaseHistoryDatabaseFileName, true))) {
            pw.println(String.format("%s,%s;%s;%s;%d", email, username, storeName, productName, quantity));
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
                pw.println(String.format("%s,%s;%s;%s;%d", emails.get(i), usernames.get(i), storeNames.get(i),
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
                customerProducts.add(String.format("%s,%s;%s;%s;%d", emails.get(i), usernames.get(i),
                        storeNames.get(i), productNames.get(i), quantities.get(i)));
            }
        }
        return customerProducts;
    }

    /**
     * Generates purchase history file for the customer identified by the username
     * @param username
     * @param fileName
     */
    public static void getPurchaseHistoryofCustomer(String username, String fileName) {
        readFromPurchaseHistoryDatabaseFile();
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < usernames.size(); i++) {
                if (usernames.get(i).equals(username)) { // check if username and email match
                    pw.println(String.format("%s,%s;%s;%s;%d", emails.get(i), usernames.get(i), storeNames.get(i), productNames.get(i), quantities.get(i)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Optional Method
    public static boolean leaveReview(String storeName, String productName, String customerName, int rating,
                                    String description ){
        boolean bool = false;
        while(!bool){
            if( !(1 <= rating && rating <= 5)){
                System.out.println("Invalid Input");
                System.out.println("Try Again");
                System.out.println("What is your Rating 1-5 ");
            }
            else {bool = true;}
        }
        try(BufferedReader br = new BufferedReader(new FileReader("Reviews.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("Reviews.txt"), true)){
            String line = br.readLine();
            int count = 0;
            while(line != null) {
                line = br.readLine();
                if(line == null){
                    pw.write(String.format("%s | %s | %s | %d | %s", storeName, productName, customerName, rating,
                            description));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return false;
    }
    public static String viewReviews(String storeName, String productName){
        String result = "";
        try(BufferedReader br = new BufferedReader(new FileReader("Reviews.txt"))){
            String line = br.readLine();
            while(line != null){
                String[] subpart = line.split("\\|");
                if(!(storeName.equals(""))) {
                    if (subpart[0].equalsIgnoreCase(storeName) && subpart[1].equalsIgnoreCase(productName)) {
                        result += line + "\n";
                    }
                } else if(storeName.equals("")){
                    if (subpart[1].equalsIgnoreCase(productName)) {
                        result += line + "\n";
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();;
        }
        return result;
    }
}
