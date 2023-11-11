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
 * @version November 10, 2023
 */

public abstract class Seller {

    private static ArrayList<Store> stores = new ArrayList<Store>(); // store arraylist

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
    public static void printProducts(String storeName) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)) {
                System.out.println("Store not found");
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                System.out.println(stores.get(index).getProductList().get(i).getName());
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
                System.out.println(stores.get(i).getProductList().get(j).getName() +
                        "Price: $" + stores.get(i).getProductList().get(j).getPurchasePrice() +
                        "Quantity: " + stores.get(i).getProductList().get(j).getStockQuantity() +
                        " | " + stores.get(i).getStoreName());
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
            if (stores.get(i).getStoreName().equalsIgnoreCase(storeName)) {
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
            PrintWriter pw = new PrintWriter(new FileWriter(path, false));
            for (int i = 0; i < stores.get(index).getProductList().size(); i++) {
                pw.println(stores.get(index).getProductList().get(i).toString());
            }
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
            for (int i = 0; i < stores.get(i).getProductList().size(); i++) {
                productSales.add(stores.get(index).getProductList().get(i).getName() + ": " +
                        stores.get(index).getProductList().get(i).getSales());
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
        String shoppingCartProducts = null;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getSellerUsername().equalsIgnoreCase(username)) {
                for (int j = 0; j < stores.get(i).getProductList().size(); j++) {
                    shoppingCartProducts += stores.get(i).getStoreName() + " - " +
                            stores.get(i).getProductList().get(j).getName() + ": " +
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
    public ArrayList<Store> getStores() {
        return stores;
    }

    /**
     * Writes Store and Product information to stores.txt and products.txt
     */
    private static void writeToFile() {
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
     * The toStrings for Store and Product should separate attributes with a ","
     */
    private static void readFromFile() {
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
                    attributeSplit = productSplit[i].split(",");
                    productList.add(new Product(attributeSplit[0], attributeSplit[1],
                            Double.parseDouble(attributeSplit[2]), Integer.parseInt(attributeSplit[3])));
                }
                storeSplit = storeLine.split(",");
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

    /**
     * Searches for store
     *
     * @param storeName
     * @return searched stores
     */
    public static String searchByStore(String storeName) {
        String searchedStore = null;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equals(storeName)) {
                searchedStore += stores.get(i).getStoreName() + ",";
            }
        }
        if (searchedStore == null) {
            searchedStore = "No Store Found ";
        }
        return (searchedStore.substring(0, (searchedStore.length() - 1)));
    }

    /**
     * Searches for product
     *
     * @param productName
     * @return searched products
     */
    public static String searchByProduct(String productName) {
        String searched = null;
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); i++) {
                if (stores.get(i).getProductList().get(j).getName().equalsIgnoreCase(productName)) {
                    searched += stores.get(i).getStoreName() + ",";
                }
            }
        }
        if (searched == null) {
            searched = "No locations found selling this product ";
        }
        return (searched.substring(0, (searched.length() - 1)));
    }

    /**
     * Searches from product by description
     *
     * @param productDescription
     * @return searched products
     */
    public static String searchByDescription(String productDescription) {
        String searchedProduct = null;
        String searchedStore = null;
        String searched = null;
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getProductList().size(); i++) {
                if (stores.get(i).getProductList().get(j).getDescription().contains(productDescription)) {
                    searchedStore += stores.get(i).getStoreName();
                    searchedProduct += stores.get(i).getProductList().get(j).getName();
                    searched +=
                            stores.get(i).getStoreName() + " | " +
                                    stores.get(i).getProductList().get(j).getName() + "|\n";
                }
            }
        }
        if (searchedProduct == null) {
            searched = "No Product found ";
        }
        return (searched.substring(0, (searched.length() - 1)));
    }

    /**
     * Sorts by price (ascending)
     *
     * @return sorted
     */
    public static String sortCheapest() {
        ArrayList<String> combined = new ArrayList<>();
        String result = "";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.size(); j++) {
                String element = stores.get(i).getStoreName() + ";"
                        + stores.get(i).getProductList().get(j).getName() + ";"
                        + stores.get(i).getProductList().get(j).getPurchasePrice()
                        + stores.get(i).getProductList().get(j).getStockQuantity();
                combined.add(element);
            }
        }
        for (int k = 0; k < combined.size(); k++) {
            double min = (double) Integer.MAX_VALUE;
            String[] subpart = combined.get(k).split(";");
            double purchasePrice = Double.parseDouble(subpart[2]);
            if (purchasePrice < min) {
                min = purchasePrice;
                String temp = combined.get(k);
                combined.remove(k);
                combined.add(0, temp);


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
        ArrayList<String> combined = new ArrayList<>();
        String result = "";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.size(); j++) {
                String element = stores.get(i).getStoreName() + ";"
                        + stores.get(i).getProductList().get(j).getName() + ";"
                        + stores.get(i).getProductList().get(j).getPurchasePrice()
                        + stores.get(i).getProductList().get(j).getStockQuantity();
                combined.add(element);
            }
        }
        for (int k = 0; k < combined.size(); k++) {
            double min = (double) Integer.MIN_VALUE;
            String[] subpart = combined.get(k).split(";");
            double purchasePrice = Double.parseDouble(subpart[2]);
            if (purchasePrice > min) {
                min = purchasePrice;
                String temp = combined.get(k);
                combined.remove(k);
                combined.add(0, temp);


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
        ArrayList<String> combined = new ArrayList<>();
        String result = "";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.size(); j++) {
                String element = stores.get(i).getStoreName() + ";"
                        + stores.get(i).getProductList().get(j).getName() + ";"
                        + stores.get(i).getProductList().get(j).getPurchasePrice()
                        + stores.get(i).getProductList().get(j).getStockQuantity();
                combined.add(element);
            }
        }
        for (int k = 0; k < combined.size(); k++) {
            double min = (double) Integer.MIN_VALUE;
            String[] subpart = combined.get(k).split(";");
            double quant = Double.parseDouble(subpart[3]);
            if (quant > min) {
                min = quant;
                String temp = combined.get(k);
                combined.remove(k);
                combined.add(0, temp);


            }
        }
        for(int a = 0; a < combined.size(); a++){
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
        ArrayList<String> combined = new ArrayList<>();
        String result = "";
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.size(); j++) {
                String element = stores.get(i).getStoreName() + ";"
                        + stores.get(i).getProductList().get(j).getName() + ";"
                        + stores.get(i).getProductList().get(j).getPurchasePrice()
                        + stores.get(i).getProductList().get(j).getStockQuantity();
                combined.add(element);
            }
        }
        for (int k = 0; k < combined.size(); k++) {
            double min = (double) Integer.MAX_VALUE;
            String[] subpart = combined.get(k).split(";");
            double quant = Double.parseDouble(subpart[3]);
            if (quant < min) {
                min = quant;
                String temp = combined.get(k);
                combined.remove(k);
                combined.add(0, temp);
            }
        }
        for(int a = 0; a < combined.size(); a++){
            result += combined.get(a) + "\n";
        }
        result = result.replace(";", " | ");
        return result;
    }
    public static String viewCustomerReviews(String productName, String user) {
        String result = "";
        if(!(productName.equals(""))) {
            for (int i = 0; i < stores.size(); i++) {
                if (stores.get(i).getSellerUsername().equalsIgnoreCase(user)) {
                    result += Customer.viewReviews(stores.get(i).getStoreName(), productName) + "\n";
                }
            }
        } else if(productName.equals("")){
            for(int i = 0; i < stores.size(); i++){
                if(stores.get(i).getSellerUsername().equalsIgnoreCase(user)) {
                    for (int j = 0; j < stores.get(i).getProductList().size(); i++) {
                        result += Customer.viewReviews(stores.get(i).getStoreName(),
                                stores.get(i).getProductList().get(j).getName() + "\n");
                    }
                }
            }
        }
        return result;
    }


}
