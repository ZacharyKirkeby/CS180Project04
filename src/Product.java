package src;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
public class Product {
    private String name; //name of the product

    private String description;
    private int stockQuantity; //quantity of the product left
    private int quantitySold;
    private double purchasePrice;
    private Store store;

    public Product(String name, String description, int quantity, double purchasePrice, Store stores) {
        this.name = name;
        this.description = description;
        this.stockQuantity = quantity;
        this.purchasePrice = purchasePrice;
        this.store = stores;
        this.quantitySold = 0;
    }
    public Product(String name, double purchasePrice, int quantity){
        this.name = name;
        this.stockQuantity = quantity;
        this.purchasePrice = purchasePrice;
        this.quantitySold = 0;
    }

    public Product() {
        this.name = "";
        this.stockQuantity = 0;
        this.purchasePrice = 0;
        this.quantitySold = 0;
        this.store = new Store("", "", new Seller("", "", ""),
                new ArrayList<>(0));
    }
    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
    public Store getStore() {
        return store;
    }

    public void setStores(Store stores) {
        this.store = stores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int quantity) {
        this.stockQuantity = quantity;
    }

    public void buyProduct(int quantity) {
        this.stockQuantity -= quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    //returns alphabetically sorted Array List of products by name
    public static ArrayList<Product> sortAlphabetically(ArrayList<Product> products){
        for(int i = 0; i< products.size(); i++)
        {
            for (int j = i+1; j< products.size(); j++)
            {
                if(products.get(i).getName().compareTo(products.get(j).getName())>0)
                {
                    Collections.swap(products, i, j);
                }
            }
        }
        return products;
    }
    //returns sorted Array List of products by cheapest product
    public static ArrayList<Product> sortByCheapest(ArrayList<Product> products) {
        for(int i = 0; i< products.size(); i++)
        {
            for (int j = i+1; j< products.size(); j++)
            {
                if(products.get(i).getPurchasePrice() > products.get(j).getPurchasePrice())
                {
                    Collections.swap(products, i, j);
                }
            }
        }
        return products;
    }
    public double getSales(){
        return (purchasePrice * quantitySold);
    }
    public static void readProductFile(String fileName){
        ArrayList<String> products = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line = br.readLine();
            while(line != null){
                products.add(line + ";");
                line = br.readLine();
            }
            products.remove(products.size()-1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                "description='" + description + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", purchasePrice=" + purchasePrice +
                ", store=" + store +
                '}';
    }
}
