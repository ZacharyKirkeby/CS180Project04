package src;


import java.io.*;
import java.util.*;

/**
 * Customer class
 * Includes methods:
 */

public class Customer extends Account {
    private static String name;
    private static Map<Product, Integer> shoppingCart;
    private static Map<Product, Integer> purchaseHistory;
    private static String shoppingCartFileName = "Shopping Cart";
    private static String purchaseHistoryFileName = "Purchase History";


    public Customer(String name) {

        this.name = name;
        this.shoppingCart = new HashMap<>();
        this.purchaseHistory = new HashMap<>();
    }


//    public static Product[] getProductsInShoppingCart() {
//        ArrayList<Product> productsList = new ArrayList<>();
//        for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
//            Product product = entry.getKey();
//            productsList.add(product);
//        }
//        Product[] products = new Product[productsList.size()];
//        for (int i = 0; i < products.length; i++) {
//            products[i] = productsList.get(i);
//        }
//        return products;
//    }

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

    public static void addToCart(Product product, int quantity) {
        shoppingCart.put(product, quantity);
    }

    public static void removeFromToCart(Product product, int quantity) {
        shoppingCart.remove(product, quantity);
    }

    // products from different stores
    public static void buyProductsInShoppingCart(int sellerThreshold) {

        for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
            Product product = entry.getKey(); // Get the key
            int quantity = entry.getValue(); // Get the value

            boolean productBought = buyProduct(product.getStore().getStoreName(), product, quantity, sellerThreshold);

            if (productBought) {
                writePurchaseHistoryFile(product, quantity);
            }
        }
    }

    public static boolean buyProduct(String store, Product product, int quantity, int sellerThreshold) {
        int storeIndex = -1;
        for (int i = 0; i < MarketPlace.getStores().size(); i++) {
            if (MarketPlace.getStores().get(i).getStoreName().equalsIgnoreCase(store)) {

                storeIndex = i;
            }
        }
        if (storeIndex == -1) {
            return false;
        } else {
            Store storeToBuyFrom = MarketPlace.getStores().get(storeIndex);
            for (int i = 0; i < storeToBuyFrom.getProductList().size(); i++) {
                if (storeToBuyFrom.getProductList().get(i).getName().equalsIgnoreCase(product.getName()) && quantity < sellerThreshold) {

                    storeToBuyFrom.getProductList().get(i).buyProduct(quantity);
                    writePurchaseHistoryFile(product, quantity);
                }
            }
            return true;
        }
    }

    public static String[] readPurchaseHistoryFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(purchaseHistoryFileName))) {
            ArrayList<String> fileContents = new ArrayList<>();


            String s;
            while ((s = bufferedReader.readLine()) != null) {
                fileContents.add(s);
            }
            String[] fileContentsArray = new String[fileContents.size()];
            for (int i = 0; i < fileContents.size(); i++) {
                fileContentsArray[i] = fileContents.get(i);
            }
            return fileContentsArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writePurchaseHistoryFile(Product product, int quantity) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(purchaseHistoryFileName, true))) {
            pw.println(String.format("%s %d", product, quantity));
            pw.flush();
            purchaseHistory.put(product, quantity);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeShoppingCartFileAddProduct(String product, int quantity) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(shoppingCartFileName, true))) {
            pw.println(String.format("%s %d", product, quantity));
            pw.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeShoppingCartFileRemoveProduct(String product, int quantity) {
        String[] shoppingCartFileContents = readShoppingCartFile();
        try (PrintWriter pw = new PrintWriter(new FileWriter(shoppingCartFileName))) {
            for (int i = 0; i < shoppingCartFileContents.length; i++) {
                String[] productAndQuantity = shoppingCartFileContents[i].split(" ");
                if (productAndQuantity[0].equals(product)) {
                    productAndQuantity[1] = Integer.toString(Integer.parseInt(productAndQuantity[1]) - quantity);
                }
                pw.println(String.format("%s %d", productAndQuantity[0], Integer.parseInt(productAndQuantity[1])));
                pw.flush();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String[] readShoppingCartFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(shoppingCartFileName))) {
            ArrayList<String> fileContents = new ArrayList<>();
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                fileContents.add(s);
            }
            String[] arrayOfFileContents = new String[fileContents.size()];

            for (int i = 0; i < fileContents.size(); i++) {
                arrayOfFileContents[i] = fileContents.get(i);
            }
            return arrayOfFileContents;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean getFile(String fileName, String[] fileContents) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (String s : fileContents) {
                pw.println(s);
                pw.flush();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
