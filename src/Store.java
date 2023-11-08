package src;
import java.util.ArrayList;
/**
 * Project 04 -- Store.java
 * creates Store class
 * WIP
 *
 * @author Zachary Kirkeby, 05
 *
 * @version November 3, 2023
 *
 */
public class Store {
    private ArrayList<Product> productList;
    private String storeName;
    private String storeLocation;
    private Seller storeOwner;
    private String sellerUsername;

    public Store(String name, String storeLocation, Seller seller,
                 ArrayList<Product> productList) {
        this.storeName = name;
        this.storeLocation = storeLocation;
        this.storeOwner = seller;
        this.productList = productList;
    }
    public Store(String name, String storeLocation, String sellerUsername,
                 ArrayList<Product> productList) {
        this.storeName = name;
        this.storeLocation = storeLocation;
        this.sellerUsername = sellerUsername;
        this.productList = productList;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public Seller getStoreOwner() {
        return storeOwner;
    }

    public void setStoreOwner(Seller storeOwner) {
        this.storeOwner = storeOwner;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public String toString() {
        return storeName + "," + storeLocation + "," + sellerUsername;
    }
}
