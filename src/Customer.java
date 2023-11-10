package src;


import java.io.*;
import java.util.*;

/**
 * Customer class
 * Includes methods:
 */

public abstract class Customer {


    private static ArrayList<String> emails = new ArrayList<>(); // username arraylist
    private static ArrayList<String> usernames = new ArrayList<>(); // username arraylist
    private static ArrayList<String> storeNames = new ArrayList<>(); // storeNames arraylist
    private static ArrayList<String> productNames = new ArrayList<>(); // productNames arraylist
    private static ArrayList<Integer> quantities = new ArrayList<>(); // quantities arraylist
    private static String shoppingCartDatabaseFileName = "ShoppingCartDatabase.txt";
    private static String purchaseHistoryDatabaseFileName = "PurchaseHistoryDatabase.txt";

    public static int getTotalInCart(String storeName, String productName) {
        int totalQuantityOfProduct = 0;
        for (int i = 0; i < storeNames.size(); i++) {
            if (storeNames.get(i).equals(storeName) && productNames.get(i).equals(productName)) {
                totalQuantityOfProduct += quantities.get(i);
            }
        }
        return totalQuantityOfProduct;
    }

    public static Store searchedStoreExists(String storeName, ArrayList<Store> stores) {
        for (Store store : MarketPlace.getStores()) {
            if (store.getStoreName().equals(storeName)) {
                return store;
            }
        }
        return null;
    }

    public static Product searchedProductExists(String productName, ArrayList<Store> stores) {
        for (Store store : MarketPlace.getStores()) {
            for (Product product : store.getProductList()) {
                if (product.getName().equals(productName)) {
                    return product;
                }
            }
        }
        return null;
    }

    public static void addToCart(String email, String username, Store store, Product product, int quantity) {
        emails.add(email);
        usernames.add(username);
        storeNames.add(store.getStoreName());
        productNames.add(product.getName());
        quantities.add(quantity);
        writeToShoppingCartDatabaseFile();
    }

    public static void removeFromCart(String email, String username, Store store, Product product, int quantity) {
        readFromShoppingCartDatabaseFile();
        for (int i = 0; i < usernames.size(); i++) {
            if (emails.get(i).equals(email) && usernames.get(i).equals(username) && storeNames.get(i).equals(store.getStoreName()) && productNames.get(i).equals(product.getName()) && quantities.get(i) == quantity) {
                emails.remove(i);
                usernames.remove(i);
                storeNames.remove(i);
                productNames.remove(i);
                quantities.remove(i);
                break;
            }
        }
        writeToShoppingCartDatabaseFile();
    }

    // products from different stores

    public static boolean buyProductsInShoppingCart(String username) {
        readFromShoppingCartDatabaseFile();
        boolean productsBoughtSuccessfully = false;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(username)) { // check if username matches'
                for (int j = 0; j < MarketPlace.getStores().size(); j++) { // iterate through stores in marketplace
                    if (storeNames.get(i).equals(MarketPlace.getStores().get(j).getStoreName())) { // if storename matches
                        for (int k = 0; k < MarketPlace.getStores().get(j).getProductList().size(); k++) { // iterrate through product list
                            if (MarketPlace.getStores().get(j).getProductList().get(k).getName().equals(productNames.get(i))) { // if product name matches
                                MarketPlace.getStores().get(j).getProductList().get(k).buyProduct(quantities.get(i));
                                writeToPurchaseHistoryDatabaseFile(emails.get(i), username, storeNames.get(i), productNames.get(i), quantities.get(i));
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

    private static void writeToPurchaseHistoryDatabaseFile(String email, String username, String storeName, String productName, int quantity) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(purchaseHistoryDatabaseFileName, true))) {
            pw.println(String.format("%s,%s;%s;%s;%d", email, username, storeName, productName, quantity));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToShoppingCartDatabaseFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(shoppingCartDatabaseFileName))) {
            for (int i = 0; i < usernames.size(); i++) {
                pw.println(String.format("%s,%s;%s;%s;%d", emails.get(i), usernames.get(i), storeNames.get(i), productNames.get(i), quantities.get(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // format of shoppingCartDatabaseFile
    // email;username;storeName;productName;quantity
    private static void readFromShoppingCartDatabaseFile() {
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

    private static void readFromPurchaseHistoryDatabaseFile() {
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

    private static ArrayList<String> getShoppingCartofCustomer(String username) {
        readFromShoppingCartDatabaseFile();
        ArrayList<String> customerProducts = new ArrayList<>();
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(username)) { // check if username matches
                customerProducts.add(String.format("%s,%s;%s;%s;%d", emails.get(i), usernames.get(i), storeNames.get(i), productNames.get(i), quantities.get(i)));
            }
        }
        return customerProducts;
    }

    private static void getPurchaseHistoryofCustomer(String username, String fileName) {
        readFromPurchaseHistoryDatabaseFile();
        ArrayList<Integer> customerProducts = new ArrayList<>();
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(username)) { // check if username and email match
                customerProducts.add(i);
            }
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Integer cp : customerProducts) {
                for (int i = 0; i < usernames.size(); i++) {
                    pw.println(String.format("%s,%s;%s;%s;%d", emails.get(cp), usernames.get(cp), storeNames.get(cp), productNames.get(cp), quantities.get(cp)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
