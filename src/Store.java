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

    public Store(String name, String storeLocation, Seller seller,
                  ArrayList<Product> productList) {
        this.storeName = name;
        this.storeLocation = storeLocation;
        this.storeOwner = seller;
        this.productList = productList;
    }

    public Store() {
        this.storeName = "";
        this.storeLocation = "";
        this.storeOwner = new Seller();
        this.productList = new ArrayList<Product>();
    }




}
