package src;

import java.io.*;
import java.util.*;

/**
 * src.Seller
 * <p>
 * Note: Class is UNTESTED
 * Manages all stores and allows for creation and deletion of stores
 * Also allows for creation, editing, and deletion of products in stores
 * Create stores.txt and products.txt before using
 *
 * @author Alexander Chen, 05
 * @author Armaan Sayyad, 05
 * @version November 10, 2023
 */
public abstract class Seller {

    private static final ArrayList<Store> stores = new ArrayList<Store>(); // store arraylist

    /**
     * Prints all stores
     */
    public static void printStores() {
        readFromFile();
        for (int i = 0; i < stores.size(); i++) {
            System.out.println(stores.get(i).getStoreName());
        }
    }

    /**
     * Prints products of a store given store name
     *
     * @param storeName
     */
    public static String printProducts(String storeName) {
        readFromFile();
        String result = "";
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)) {
                index = i;
            }
        }
        if (index == -1) {
            result += "Store not found";
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                result += stores.get(index).getProductList().get(i).getName() + ", ";
            }
        }
        return result.substring(0, result.length() - 2);
    }

    /**
     * Prints all products and stores
     */
    public static void printProductAndStores() {
        readFromFile();
        System.out.println("Store Name | Product Name | Product Price | Qty Left in Stock");
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                if (stores.get(i).getProductList().get(j).getStockQuantity() <= 0) {
                    System.out.println(stores.get(i).getStoreName() + " | " +
                            stores.get(i).getProductList().get(j).getName() + " | " +
                            stores.get(i).getProductList().get(j).getPurchasePrice() + " | " +
                            "Out of Stock");
                } else {
                    System.out.println(stores.get(i).getStoreName() + " | " +
                            stores.get(i).getProductList().get(j).getName() + " | " +
                            stores.get(i).getProductList().get(j).getPurchasePrice() + " | " +
                            stores.get(i).getProductList().get(j).getStockQuantity());
                }
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
        if (storeName == null || storeName.isEmpty() || storeLocation == null || storeLocation.isEmpty()) {
            return false;
        }
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)) {
                return false;
            }
        }
        stores.add(new Store(storeName, storeLocation, username));
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
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)
                    && stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                index = i;
                break;
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
    public static boolean createProduct(String storeName, String name, String description, double price, int quantity,
                                        String username) {
        if (price <= 0 || quantity <= 0) {
            return false;
        }
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName) &&
                    stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                index = i;
                break;
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
            stores.get(index).getProductList().add(new Product(name, description, price, quantity));
            writeToFile();
            return true;
        }
    }

    /**
     * Edits product description given store name, product name, and new product description
     * Store name should be stored from user input and inputted automatically
     *
     * @param storeName
     * @param name
     * @param description
     * @return boolean indicating whether edit was successful
     */
    public static boolean editProductDescription(String storeName, String name, String description, String username) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName) &&
                    stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                if (stores.get(index).getProductList().get(i).getName().equalsIgnoreCase(name)) {
                    stores.get(index).getProductList().get(i).setDescription(description);
                    writeToFile();
                    return true;
                }
            }
            return false;
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
    public static boolean editProductPrice(String storeName, String name, double price, String username) {
        if (price <= 0) {
            return false;
        }
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName) &&
                    stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                if (stores.get(index).getProductList().get(i).getName().equalsIgnoreCase(name)) {
                    stores.get(index).getProductList().get(i).setPurchasePrice(price);
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
    public static boolean editProductQuantity(String storeName, String name, int quantity, String username) {
        if (quantity < 0) {
            return false;
        }
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName) &&
                    stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                if (stores.get(index).getProductList().get(i).getName().equalsIgnoreCase(name)) {
                    stores.get(index).getProductList().get(i).setStockQuantity(quantity);
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
    public static boolean deleteProduct(String storeName, String name, String username) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName) &&
                    stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                if (stores.get(index).getProductList().get(i).getName().equalsIgnoreCase(name)) {
                    stores.get(index).getProductList().remove(i);
                    writeToFile();
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Writes products to a csv file given store name and path
     *
     * @param storeName
     * @param path
     * @return boolean indicating successful write
     */
    public static boolean writeProductsToCSV(String storeName, String path) {
        readFromFile();
        int index = -1;
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, false))) {
            for (int i = 0; i < stores.size(); i++) {
                if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                return false;
            }
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                pw.write(stores.get(index).getProductList().get(i).toString() + "\n");
                pw.flush();
            }
            pw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Adds products to store from csv given store name and path
     *
     * @param storeName
     * @param path
     * @return boolean indicating successful read
     */
    public static boolean readProductsFromCSV(String storeName, String path) {
        int index = -1;
        String line = null;
        String[] split = null;
        readFromFile();
        try {
            for (int i = 0; i < stores.size(); i++) {
                if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                return false;
            }
            BufferedReader bfr = new BufferedReader(new FileReader(path));
            line = bfr.readLine();
            while ((line != null) && (!line.isEmpty())) {
                split = line.split(",");
                createProductInternal(storeName, split[0], split[1], Double.parseDouble(split[2]),
                        Integer.parseInt(split[3]));
                line = bfr.readLine();
            }
            writeToFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Internal product creation without username for CSV
     *
     * @param storeName
     * @param name
     * @param description
     * @param price
     * @param quantity
     * @return boolean indicating successful product creation
     */
    private static boolean createProductInternal(String storeName, String name, String description, double price,
                                                 int quantity) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)) {
                index = i;
                break;
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
            stores.get(index).getProductList().add(new Product(name, description, price, quantity));
            writeToFile();
            return true;
        }

    }

    /**
     * Returns a String of customers and their purchases for a particular store given store name and username
     * Sellers may only view their own stores
     * Username should be stored from user input and inputted automatically
     *
     * @param storeName
     * @param username
     * @param sorted
     * @return String of product sales, can be sorted
     */
    public static String getCustomersAndPurchases(String storeName, String username, boolean sorted) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)
                    && stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return "Error: Invalid parameters";
        } else {
            if (sorted) {
                return stores.get(index).getSortedCustomersAndPurchases();
            }
            return stores.get(index).getCustomersAndPurchases();
        }
    }

    /**
     * Gets store list by sales for a user
     * Username should be inputted automatically from user input
     *
     * @param username
     * @return String of stores by sales (descending)
     */
    public static String storesBySales(String username) {
        readFromFile();
        ArrayList<Store> sales = new ArrayList<>();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                sales.add(stores.get(i));
            }
        }
        if (sales.isEmpty()) {
            return "Error: Invalid parameters";
        }
        Collections.sort(sales, (s1, s2) -> s2.getTotalSales() - s1.getTotalSales());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sales.size(); i++) {
            sb.append(sales.get(i).getStoreName() + ": " + sales.get(i).getTotalSales() + "\n");
        }
        return sb.toString();
    }

    /**
     * Returns details of the sales by store
     * Should be an option given after using storesBySales()
     * Username should be inputted automatically from user input
     *
     * @param storeName
     * @param username
     * @return Store information and revenue
     */
    public static String salesByStore(String storeName, String username) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)
                    && stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return "Error: Invalid parameters";
        } else {
            return stores.get(index).getCustomerInformationAndRevenue();
        }

    }

    /**
     * Returns a String of product sales given a store name and username
     * Sellers may only view their own stores
     * Username should be stored from user input and inputted automatically
     *
     * @param storeName
     * @param username
     * @param sorted
     * @return String of product sales, can be sorted
     */
    public static String getProductSales(String storeName, String username, boolean sorted) {
        readFromFile();
        int index = -1;
        ArrayList<String> productSales = new ArrayList<>();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)
                    && stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return "Error: Invalid parameters";
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                productSales.add(stores.get(index).getProductList().get(i).getName() + ": " +
                        stores.get(index).getProductList().get(i).getRevenue());
            }
        }
        if (sorted) {
            productSales.sort(Comparator.comparing(s -> s.substring(s.indexOf(":") + 2),
                    Comparator.reverseOrder()));
        }
        return String.join("\n", productSales);
    }

    /**
     * Returns a String of all products and quantities in customer shopping carts for a given seller's products
     * Username should be stored from user input and inputted automatically
     *
     * @param username
     * @return String of products and quantities
     */
    public static String getShoppingCartProducts(String username) {
        readFromFile();
        String shoppingCartProducts = "";
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                    shoppingCartProducts += stores.get(i).getStoreName() + " - " +
                            stores.get(i).getProductList().get(j).getName() + " | in stock: "
                            + stores.get(i).getProductList().get(j).getStockQuantity() + " | in shopping cart: " +
                            Customer.getTotalInCart(stores.get(i).getStoreName(),
                                    stores.get(i).getProductList().get(j).getName()) + "\n";
                }
            }
        }
        return shoppingCartProducts;
    }

    /**
     * Changes store usernames given new username and old username
     * Should only be called by Account.changeUsername()
     *
     * @param newUsername
     * @param oldUsername
     */
    public static void changeStoreUsernames(String newUsername, String oldUsername) {
        readFromFile();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getSellerUsername().equals(oldUsername)) {
                stores.get(i).setSellerUsername(newUsername);
            }
        }
        writeToFile();
    }

    /**
     * Gets arraylist of stores
     *
     * @return ArrayList of stores
     */
    public static ArrayList<Store> getStores() {
        readFromFile();
        return stores;
    }

    /**
     * Writes Store and Product information to stores.txt and products.txt
     */
    private static void writeToFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("stores.txt", false));
            for (int i = 0; i < stores.size(); i++) {
                pw.print(stores.get(i).toString() + ";");
                for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                    pw.print(stores.get(i).getProductList().get(j).toString() + ";");
                }
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads Store and Product information from stores.txt and products.txt
     * The toStrings for Store and Product should separate attributes with a ","
     */
    private static void readFromFile() {
        stores.clear();
        int index = 0;
        try (BufferedReader bfr = new BufferedReader(new FileReader("stores.txt"))) {
            String line = bfr.readLine();
            while ((line != null) && !line.isEmpty()) {
                String[] split = line.split(";");
                String[] storeSplit = split[0].split(",");
                stores.add(new Store(storeSplit[0], storeSplit[1], storeSplit[2]));
                for (int i = 1; i < split.length; i++) {
                    String[] attributeSplit = split[i].split(",");
                    stores.get(index).getProductList().add(new Product(attributeSplit[0],
                            attributeSplit[1], Double.parseDouble(attributeSplit[2]),
                            Integer.parseInt(attributeSplit[3])));
                }
                index++;
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for store
     *
     * @param storeName
     * @return searched stores
     */
    public static String searchByStore(String storeName) {
        readFromFile();
        String searchedStore = "Store Name | Product Name \n";
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equals(storeName)) {
                searchedStore += stores.get(i).getStoreName() + " | " +
                        Seller.printProducts(stores.get(i).getStoreName()) + "\n";
            }
        }
        if (searchedStore.equals("Store Name | Product Name \n")) {
            searchedStore = "No Store Found ";
        }
        return (searchedStore);
    }

    /**
     * Searches for product
     *
     * @param productName
     * @return searched products
     */
    public static String searchByProduct(String productName) {
        readFromFile();
        String searched = "Store Name | Product Name | Quantity Available | Description\n";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                if (stores.get(i).getProductList().get(j).getName().equalsIgnoreCase(productName)) {
                    searched += stores.get(i).getStoreName() + " | " + stores.get(i).getProductList().get(j).getName()
                            + " | " + stores.get(i).getProductList().get(j).getStockQuantity()
                            + " | " + stores.get(i).getProductList().get(j).getDescription();
                }
            }
        }
        if (searched.equals("Store Name | Product Name \n")) {
            searched = "No locations found selling this product ";
        }
        return (searched);
    }

    /**
     * Searches from product by description
     *
     * @param productDescription
     * @return searched products
     */
    public static String searchByDescription(String productDescription) {
        readFromFile();
        String searchedProduct = null;
        String searchedStore = null;
        String searched = "Store Name | Product Name | Quantity Available | Description\n";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                if (stores.get(i).getProductList().get(j).getDescription().contains(productDescription)) {
                    searchedStore += stores.get(i).getStoreName();
                    searchedProduct += stores.get(i).getProductList().get(j).getName();
                    searched +=
                            stores.get(i).getStoreName() + " | " +
                                    stores.get(i).getProductList().get(j).getName() + " | " +
                                    stores.get(i).getProductList().get(j).getStockQuantity() + " | " +
                                    stores.get(i).getProductList().get(j).getDescription() + "\n";
                }
            }
        }
        if (searchedProduct == null) {
            searched = "No Product found ";
        }
        if (searched.equals("Store Name | Product Name \n")) {
            searched = "No product with that description found";
        }
        return (searched.substring(0, (searched.length() - 1)));
    }

    /**
     * Sorts by price (ascending)
     *
     * @return sorted
     */
    public static String sortCheapest() {
        readFromFile();
        ArrayList<String> combined = new ArrayList<>();
        String result = "";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                String element = stores.get(i).getStoreName() + ";"
                        + stores.get(i).getProductList().get(j).getName() + ";"
                        + stores.get(i).getProductList().get(j).getPurchasePrice() + ";"
                        + stores.get(i).getProductList().get(j).getStockQuantity();
                combined.add(element);
            }
        }

        for (int k = 0; k < combined.size(); k++) {
            for (int c = k + 1; c < combined.size(); c++) {
                String[] subpartK = combined.get(k).split(";");
                String[] subpartC = combined.get(c).split(";");
                double purchasePriceK = Double.parseDouble(subpartK[2]);
                double purchasePriceC = Double.parseDouble(subpartC[2]);
                if (purchasePriceK > purchasePriceC) {
                    Collections.swap(combined, k, c);
                }
            }
        }
        for (int a = 0; a < combined.size(); a++) {
            result += combined.get(a) + "\n";
        }
        result = result.replace(";", " | ");
        return result;
    }

    /**
     * Sorts by price (descending)
     *
     * @return sorted
     */
    public static String sortExpensive() {
        readFromFile();
        ArrayList<String> combined = new ArrayList<>();
        String result = "";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                String element = stores.get(i).getStoreName() + ";"
                        + stores.get(i).getProductList().get(j).getName() + ";"
                        + stores.get(i).getProductList().get(j).getPurchasePrice() + ";"
                        + stores.get(i).getProductList().get(j).getStockQuantity();
                combined.add(element);
            }
        }
        for (int k = 0; k < combined.size(); k++) {
            for (int c = k + 1; c < combined.size(); c++) {
                String[] subpartK = combined.get(k).split(";");
                String[] subpartC = combined.get(c).split(";");
                double purchasePriceK = Double.parseDouble(subpartK[2]);
                double purchasePriceC = Double.parseDouble(subpartC[2]);
                if (purchasePriceK < purchasePriceC) {
                    Collections.swap(combined, k, c);
                }
            }
        }
        for (int a = 0; a < combined.size(); a++) {
            result += combined.get(a) + "\n";
        }
        result = result.replace(";", " | ");
        return result;

    }

    /**
     * Gets highest quantity
     *
     * @return highest
     */
    public static String highestQuant() {
        readFromFile();
        ArrayList<String> combined = new ArrayList<>();
        String result = "";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                String element = stores.get(i).getStoreName() + ";"
                        + stores.get(i).getProductList().get(j).getName() + ";"
                        + stores.get(i).getProductList().get(j).getPurchasePrice() + ";"
                        + stores.get(i).getProductList().get(j).getStockQuantity();
                combined.add(element);
            }
        }
        for (int k = 0; k < combined.size(); k++) {
            for (int c = k + 1; c < combined.size(); c++) {
                String[] subpartK = combined.get(k).split(";");
                String[] subpartC = combined.get(c).split(";");
                double purchasePriceK = Double.parseDouble(subpartK[3]);
                double purchasePriceC = Double.parseDouble(subpartC[3]);
                if (purchasePriceK < purchasePriceC) {
                    Collections.swap(combined, k, c);
                }
            }
        }
        for (int a = 0; a < combined.size(); a++) {
            result += combined.get(a) + "\n";
        }
        result = result.replace(";", " | ");
        return result;
    }

    /**
     * Returns lowest quantity
     *
     * @return lowest
     */
    public static String lowestQuant() {
        readFromFile();
        ArrayList<String> combined = new ArrayList<>();
        String result = "";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                String element = stores.get(i).getStoreName() + ";"
                        + stores.get(i).getProductList().get(j).getName() + ";"
                        + stores.get(i).getProductList().get(j).getPurchasePrice() + ";"
                        + stores.get(i).getProductList().get(j).getStockQuantity();
                combined.add(element);
            }
        }

        for (int k = 0; k < combined.size(); k++) {
            for (int c = k + 1; c < combined.size(); c++) {
                String[] subpartK = combined.get(k).split(";");
                String[] subpartC = combined.get(c).split(";");
                double purchasePriceK = Double.parseDouble(subpartK[3]);
                double purchasePriceC = Double.parseDouble(subpartC[3]);
                if (purchasePriceK > purchasePriceC) {
                    Collections.swap(combined, k, c);
                }
            }
        }
        for (int a = 0; a < combined.size(); a++) {
            result += combined.get(a) + "\n";
        }
        result = result.replace(";", " | ");
        return result;
    }

    /**
     * @param productName
     * @param user
     * @return String of all reviews of the product
     */
    public static String viewCustomerReviews(String productName, String user) {
        readFromFile();
        String result = "";
        if (!(productName.equals(""))) {
            for (int i = 0; i < stores.size(); i++) {
                if (stores.get(i).getSellerUsername().equalsIgnoreCase(user)) {
                    result += Customer.viewReviews(stores.get(i).getStoreName(), productName) + "\n";
                } else {
                    result += "No store found";
                }
            }
        } else if (productName.equals("")) {
            for (int i = 0; i < stores.size(); i++) {
                if (stores.get(i).getSellerUsername().equalsIgnoreCase(user)) {
                    for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                        result += Customer.viewReviews(stores.get(i).getStoreName(),
                                stores.get(i).getProductList().get(j).getName()) + "\n";
                    }
                } else {
                    result += "No store found";
                }
            }
        }
        return result;
    }

    /**
     * @param storeName
     * @return
     */
    public static Store whichStore(String storeName) {
        for (Store s : stores) {
            if (s.equals(storeName)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Changes the quantity of the product passed in
     *
     * @param storeName
     * @param productName
     * @param quantity
     * @return true if success false if not
     */
    public static boolean changeQuantity(String storeName, String productName, int quantity) {
        readFromFile();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equals(storeName)) {
                for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                    if (stores.get(i).getProductList().get(j).getName().equalsIgnoreCase(productName)) {
                        if (quantity > stores.get(i).getProductList().get(j).getStockQuantity() || quantity < 0) {
                            return false;
                        }
                        stores.get(i).getProductList().get(j).setStockQuantity(
                                (stores.get(i).getProductList().get(j).getStockQuantity() - quantity)
                        );

                    }
                }
            }
        }
        writeToFile();
        return true;
    }

    /**
     * Returns the total spending per customer for a certain product
     *
     * @param storeName
     * @param productName
     * @param quantity
     * @return
     */
    public static double getTotalPurchasePerCustomer(String storeName, String productName, int quantity) {
        readFromFile();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equals(storeName)) {
                for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                    if (stores.get(i).getProductList().get(j).getName().equalsIgnoreCase(productName)) {
                        double purchase = stores.get(i).getProductList().get(j).getPurchasePrice() * quantity;
                        return purchase;
                    }
                }
            }
        }
        return 0.0;
    }
}
