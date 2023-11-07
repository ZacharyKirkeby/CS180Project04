package src;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
public class Product {
    private String name; //name of the product
    private int stockQuantity; //quantity of the product left
    private int quantitySold;
    private double purchasePrice;
    private Store stores;

    public Product(String name, int quantity, double purchasePrice, Store stores) {
        this.name = name;
        this.stockQuantity = quantity;
        this.purchasePrice = purchasePrice;
        this.stores = stores;
        this.quantitySold = 0;
    }
    public Product(String name, double purchasePrice, int quantity){
        this.name = name;
        this.stockQuantity = quantity;
        this.purchasePrice = purchasePrice;
        this.quantitySold = 0;
    }


    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
    public Store getStores() {
        return stores;
    }

    public void setStores(Store stores) {
        this.stores = stores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int quantity) {
        this.stockQuantity = quantity;
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
        return name + "," + purchasePrice + "," + stockQuantity;
    }
}
