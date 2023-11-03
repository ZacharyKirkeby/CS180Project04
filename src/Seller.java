package src;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * src.Seller
 * <p>
 * Note: Class is UNTESTED
 * Manages all stores and allows for creation and deletion of stores
 * Also allows for creation, editing, and deletion of products in stores
 * Create stores.txt and products.txt before using
 *
 * @author Alexander Chen, 05
 * @version November 3, 2023
 */

public class Seller {

    private static ArrayList<Store> stores = new ArrayList<Store>(); // store arraylist

    /**
     * Prints all stores
     */
    public static void printStores() {
        readFromFile();
        for (int i = 0; i < stores.size(); i++) {
            System.out.println(stores.get(i).toString());
        }
    }

    /**
     * Prints products of a store given store name
     *
     * @param storeName
     */
    public static void printProducts(String storeName) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)) {
                index = i;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                System.out.println(stores.get(index).getProductList().get(i).toString());
            }
        }
    }

    /**
     * Prints all products and stores
     */
    public static void printProductAndStores() {
        readFromFile();
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                System.out.println(stores.get(i).getProductList().get(j).toString() +
                        " | " + stores.get(i).toString());
            }
        }
    }

    /**
     * Creates a new store given store name, store location, and username
     * Initializes store with an empty product arraylist
     * Username should be stored from user input and inputted automatically
     *
     * @param storeName
     * @param storeLocation
     * @param username
     * @return boolean indicating whether creation was successful
     */
    public static boolean createStore(String storeName, String storeLocation, String username) {
        readFromFile();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)) {
                return false;
            }
        }
        stores.add(new Store(storeName, storeLocation, username, new ArrayList<Product>()));
        writeToFile();
        return true;
    }

    /**
     * Deletes a store given store name and username
     * Only deletes if the current user's username is the same as the store owner's username
     * Username should be stored from user input and inputted automatically
     *
     * @param storeName
     * @return boolean indicating whether deletion was successful
     */
    public static boolean deleteStore(String storeName, String username) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)
                    && stores.get(i).getUsername.equalsIgnoreCase(username)) {
                index = i;
            }
        }
        if (index == -1) {
            return false;
        } else {
            stores.remove(index);
            writeToFile();
            return true;
        }
    }

    /**
     * Creates a product in a store given product name, product price, and product quantity
     * Store name should be stored from user input and inputted automatically
     * Product name should be unique (case-insensitive)
     *
     * @param storeName
     * @param name
     * @param price
     * @param quantity
     * @return boolean indicating whether creation was successful
     */
    public static boolean createProduct(String storeName, String name, double price, int quantity) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)) {
                index = i;
            }
        }
        if (index == -1) {
            return false;
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                if (stores.get(index).getProductList().get(i).getName().equalsIgnoreCase(name)) {
                    return false;
                }
            }
            stores.get(index).getProductList().add(new Product(name, price, quantity));
            return true;
        }
    }

    /**
     * Edits product price given store name, product name, and new product price
     * Store name should be stored from user input and inputted automatically
     *
     * @param storeName
     * @param name
     * @param price
     * @return boolean indicating whether edit was successful
     */
    public static boolean editProductPrice(String storeName, String name, double price) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)) {
                index = i;
            }
        }
        if (index == -1) {
            return false;
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                if (stores.get(index).getProductList().get(i).getName().equalsIgnoreCase(name)) {
                    (stores.get(index).getProductList().get(i).setPrice(price));
                    writeToFile();
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Edits product quantity given store name, product name, and new product quantity
     * Store name should be stored from user input and inputted automatically
     *
     * @param storeName
     * @param name
     * @param quantity
     * @return boolean indicating whether edit was successful
     */
    public static boolean editProductQuantity(String storeName, String name, int quantity) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)) {
                index = i;
            }
        }
        if (index == -1) {
            return false;
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                if (stores.get(index).getProductList().get(i).getName().equalsIgnoreCase(name)) {
                    (stores.get(index).getProductList().get(i).setQuantity(quantity));
                    writeToFile();
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Deletes a product from a store given store name and product name
     * Store name should be stored from user input and inputted automatically
     *
     * @param storeName
     * @param name
     * @return boolean indicating whether deletion was successful
     */
    public static boolean deleteProduct(String storeName, String name) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equalsIgnoreCase(storeName)) {
                index = i;
            }
        }
        if (index == -1) {
            return false;
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                if (stores.get(index).getProductList().get(i).getName().equalsIgnoreCase(name)) {
                    (stores.get(index).getProductList().get(i).remove());
                    writeToFile();
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Writes Store and Product information to stores.txt and products.txt
     */
    public static void writeToFile() {
        try {
            PrintWriter storePW = new PrintWriter(new FileWriter("stores.txt", false));
            for (int i = 0; i < stores.size(); i++) {
                storePW.println(stores.get(i).toString());
            }
            storePW.close();
            PrintWriter productPW = new PrintWriter(new FileWriter("products.txt", false));
            for (int i = 0; i < stores.size(); i++) {
                for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                    productPW.print(stores.get(i).getProductList().get(j).toString() + ";");
                }
                productPW.println();
            }
            productPW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads Store and Product information from stores.txt and products.txt
     * The toStrings for Store and Product should separate attributes with a ", "
     */
    public static void readFromFile() {
        stores.clear();
        String[] productSplit = null;
        String[] attributeSplit = null;
        String[] storeSplit = null;
        ArrayList<Product> productList = new ArrayList<Product>();
        String storeLine = null;
        String productLine = null;
        try {
            BufferedReader storeBFR = new BufferedReader(new FileReader("stores.txt"));
            BufferedReader productBFR = new BufferedReader(new FileReader("products.txt"));
            storeLine = storeBFR.readLine();
            productLine = productBFR.readLine();
            while ((storeLine != null) && (!storeLine.isEmpty())) {
                productSplit = productLine.split(";");
                for (int i = 0; i < productSplit.length; i++) {
                    attributeSplit = productSplit[i].split(", ");
                    productList.add(new Product(attributeSplit[0], attributeSplit[1], attributeSplit[2]));
                }
                storeSplit = storeLine.split(", ");
                stores.add(new Store(storeSplit[0], storeSplit[1], storeSplit[2], productList));
                storeLine = storeBFR.readLine();
                productLine = productBFR.readLine();
            }
            storeBFR.close();
            productBFR.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
