package src;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
/**
 * Project 04 -- Product.java
 * creates Product class
 * Handles all Product related tasks
 * and functions.
 * @author Armaan Sayyad, 05
 * @version November 10, 2023
 */
public class Product {
    private String name; // name of the product
    private String description; // description of product
    private int stockQuantity; // quantity of the product left
    private int quantitySold; // quantity of product sold
    private double purchasePrice; // price of product

    // Optional Features Parameters
    private boolean onSale; // Sale status
    private int saleCap; // Amount of product on sale
    private int saleSold;
    private double salePrice; // Sale price

    private int orderCap; // Max number a customer can order

    /**
     *Constructors for the Product
     * @param name, description, quantity, purchasePrice,
     */
    public Product(String name, double purchasePrice, int quantity) {
        this.name = name;
        this.stockQuantity = quantity;
        this.purchasePrice = purchasePrice;
        this.quantitySold = 0;
    }

    public Product(String name, String description, double purchasePrice, int quantity) {
        this.name = name;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.stockQuantity = quantity;
        this.quantitySold = 0;

    }
    /**
     *Getter for the Product's quantity sold
     * @return quantitySold
     */
    public int getQuantitySold() {
        return quantitySold;
    }
    /**
     *Setter for the Product's quantity sold
     * @param quantitySold
     */
    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    /**
     *Getter for the Product's Name
     * @return Name
     */
    public String getName() {
        return name;
    }
    /**
     *Setter for the Product's Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *Getter for the Product's description
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     *Setter for the Product's description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     *Getter for the Product's stock quantity
     * @return stockQuantity
     */
    public int getStockQuantity() {
        return stockQuantity;
    }
    /**
     *Setter for the Product's stockQuantity
     * @param stockQuantity
     */
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void buyProduct(int quantity) {
        if (this.saleCap > 0 && quantity < this.saleCap) {
            this.stockQuantity -= quantity;
        }
        if (this.onSale && this.saleCap != 0) {
            this.saleCap--;
            this.saleSold++;
            if (saleCap == 0) {
                onSale = false;
            }
        }
    }
    /**
     *Getter for the Product's purchase price
     * @return purchasePrice
     */
    public double getPurchasePrice() {
        if (onSale) {
            return salePrice;
        } else {
            return purchasePrice;
        }
    }
    /**
     *Setter for the Product's purchase price
     * @param purchasePrice
     */
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     *Sorting Algorithm that sorts Products Alphabetically
     * @param products (ArrayList<Product></>)
     * @return ArrayList<Product></>
     */
    public static ArrayList<Product> sortAlphabetically(ArrayList<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size(); j++) {
                if (products.get(i).getName().compareTo(products.get(j).getName()) > 0) {
                    Collections.swap(products, i, j);
                }
            }
        }
        return products;
    }

    /**
     *Sorting Algorithm that sorts Products from Cheapest to most Expensive
     * @param products (ArrayList<Product></>)
     * @return ArrayList<Product> </>
     */
    public static ArrayList<Product> sortByCheapest(ArrayList<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size(); j++) {
                if (products.get(i).getPurchasePrice() > products.get(j).getPurchasePrice()) {
                    Collections.swap(products, i, j);
                }
            }
        }
        return products;
    }

    /**
     * get sales
     * checks if a sale is ongoing
     * if so, calculates sales attributed to the current sale
     * as well as the rest of products sold
     * @return
     */
    public double getSales() {
        if (onSale) {
            int temp = quantitySold - saleSold;
            return salePrice * saleSold + (purchasePrice * temp);
        }
        return (purchasePrice * quantitySold);
    }

    /**
     * Reads the products from a file and adds them to an ArrayList
     * @param fileName (String)
     */
    public static void readProductFile(String fileName) {
        ArrayList<String> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {
                products.add(line + ";");
                line = br.readLine();
            }
            products.remove(products.size() - 1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Optional Feature
     * creates a sale
     * sale cannot exceed stock available
     * price cannot be negative
     * @param salePrice
     * @param saleCap
     *
     */
    public String startSale(double salePrice, int saleCap) {
        onSale = true;
        if (saleCap > this.stockQuantity) {
            saleCap = stockQuantity;
        } else {
            this.saleCap = saleCap;
        }
        if (salePrice <= 0) {
            return "Unable to Start Sale";
        } else {
            this.salePrice = salePrice;
        }
        return "Sale Successfully Started";
    }
    public int getSaleCap() {
        return saleCap;
    }
    public double getSalePrice() {
        return salePrice;
    }


    /**
     * Sets order cap
     * makes sure it is greater than 0
     * @param cap
     */
    public String setCap(int cap) {
        if (cap > 0) {
            this.orderCap = cap;
        } else {
            return "There was a problem implementing an order cap";
        }
        return "Order Cap created successfully!";
    }
    /**
     * Gets order cap
     * makes sure it is greater than 0
     * @return orderCap
     */
    public int getOrderCap() {
        return orderCap;
    }



    @Override
    public String toString() {
        return name + "," + description + "," + purchasePrice + "," + stockQuantity;
    }
}
