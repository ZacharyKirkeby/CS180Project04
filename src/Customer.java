import java.io.*;
import java.util.*;

public class Customer extends Account {
    private static String name;
    private static Map<Product, Integer> shoppingCart;
    private static Map<Product, Integer> purchaseHistory;
    private static String shoppingCartFileName = "Shopping Cart";
    private static String purchaseHistoryFileName = "Purchase History";

    public Customer (String name) {
        this.name = name;
        this.shoppingCart = new HashMap<>();
        this.purchaseHistory = new HashMap<>();
    }

    public static void addToCart(Product product, int quantity) {
        shoppingCart.put(product, quantity);
    }
    public static Map<Product, Integer> viewCart(String Name){
        if (Name.equalsIgnoreCase(name)){
            return shoppingCart;
        }
        else return null;
    }
    // products from different stores
    public static void buyProductsInShoppingCart (ArrayList<Store> stores, int sellerThreshold) {
        for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
            Product product = entry.getKey(); // Get the key
            int quantity = entry.getValue(); // Get the value

            boolean productBought = buyProduct(stores, product.getStore().getStoreName(), product.getName(), quantity, sellerThreshold);

            if (productBought) {
                writePurchaseHistoryFile(product.getName(), quantity);
            }
        }
    }

    public static boolean buyProduct(ArrayList<Store> stores, String store, String product, int quantity, int sellerThreshold) {
        int storeIndex = -1;
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getStoreName().equalsIgnoreCase(store)) {
                storeIndex = i;
            }
        }
        if (storeIndex == -1) {
            return false;
        } else {
            Store storeToBuyFrom = stores.get(storeIndex);
            for (int i = 0; i < storeToBuyFrom.getProductList().size(); i++) {
                if (storeToBuyFrom.getProductList().get(i).getName().equalsIgnoreCase(product) && quantity < sellerThreshold) {
                    storeToBuyFrom.getProductList().get(i).buyProduct(quantity);
                    writePurchaseHistoryFile(product, quantity);
                }
            }
            return true;
        }
    }

    public static ArrayList<String> readPurchaseHistoryFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(purchaseHistoryFileName))) {
            ArrayList<String> fileContents = new ArrayList<>();
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                fileContents.add(s);
            }
            return fileContents;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writePurchaseHistoryFile(String product, int quantity) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(purchaseHistoryFileName, true))) {
            pw.println(String.format("%s %d", product, quantity));
            pw.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeShoppingCartFile(String product, int quantity) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(shoppingCartFileName, true))) {
            pw.println(String.format("%s %d", product, quantity));
            pw.flush();
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



    // get shopping cart
    // buy items in shopping cart. are you sure you want to buy? yes/no/cancel


}
