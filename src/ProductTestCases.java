package src;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * A set of product test cases.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author William Hyun
 * @version November 9, 2023
 */
public class ProductTestCases {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ProductTestCases.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    @Test(timeout = 1000)
    public void createProductTest() {

        Product product1 = new Product("name", "description", 10.0, 5);

        assertEquals("Ensure that the constructor works!", 5, product1.getStockQuantity());

        product1.setStockQuantity(10);

        assertEquals("Ensure that setStockQuantity method works!", 10, product1.getStockQuantity());

        product1.setPurchasePrice(15.0);

        assertEquals("Ensure that setStockQuantity method works!", 15.0, product1.getPurchasePrice(), 0.001);

        product1.setName("productname");

        assertEquals("Ensure that setName method works!", "productname", product1.getName());

        product1.setDescription("product description");

        assertEquals("Ensure that setDescription method works!", "product description", product1.getDescription());

        assertFalse("Ensure that buyProduct checks for invalid inputs!", product1.buyProduct(20));

        assertTrue("Ensure that buyProduct works for valid inputs!", product1.buyProduct(10));

        assertEquals("Ensure that buyProduct method updates stock quantity!", 0, product1.getStockQuantity());

        assertEquals("Ensure that buyProduct method updates quantity sold!", 10, product1.getQuantitySold());

        assertEquals("Ensure that getRevenue method works correctly!", 15.0 * 10, product1.getRevenue(), 0.001);
    }

    // make sure to test getRevenue to see if a sale affects return value or not and also if anything changes if
    // the sale ends.
    @Test(timeout = 1000)
    public void productSaleTest() {

        Product product1 = new Product("name", "description", 10.0, 10);

        product1.buyProduct(5);

        assertEquals("Ensure that buyProduct method updates stock quantity!", 5, product1.getStockQuantity());

        assertEquals("Ensure that buyProduct method updates quantity sold!", 5, product1.getQuantitySold());

        assertEquals("Ensure that getSales method works correctly!", 10.0 * 5, product1.getRevenue(), 0.001);

        assertEquals("Ensure that startSale method handles invalid inputs", false,
                product1.startSale(0.0, 3));

        assertEquals("Ensure that startSale method handles invalid inputs", false,
                product1.startSale(8.0, 0));

        product1.startSale(8.0, 3);

        assertTrue("Ensure that buyProduct method works under sale", product1.buyProduct(1));

        assertEquals("Ensure that buyProduct method updates stock quantity even on sale!", 4,
                product1.getStockQuantity());

        assertEquals("Ensure that buyProduct method updates quantity sold even on sale!", 6,
                product1.getQuantitySold());

        assertEquals("Ensure that getRevenue method works correctly even on sale!", 10.0 * 5 + 8.0 * 1,
                product1.getRevenue(), 0.001);

        assertTrue("Ensure that getRevenue method works correctly even on sale!", product1.getOnSale());


        assertTrue("Ensure that buyProduct method works even on sale!", product1.buyProduct(2));

        assertEquals("Ensure that buyProduct method updates stock quantity even on sale!", 2,
                product1.getStockQuantity());

        assertEquals("Ensure that buyProduct method updates quantity sold even on sale!", 8,
                product1.getQuantitySold());

        assertEquals("Ensure that getRevenue method works correctly even on sale!", 10.0 * 5 + 8.0 * 3,
                product1.getRevenue(), 0.001);

        assertFalse("Ensure that buyProduct method ends sale when all sale items are sold!", product1.getOnSale());


        assertFalse("Ensure that buyProduct method works after ending sale", product1.buyProduct(3));

        assertTrue("Ensure that buyProduct method works after ending sale", product1.buyProduct(2));

        assertEquals("Ensure that buyProduct method updates stock quantity even after ending sale!", 0,
                product1.getStockQuantity());

        assertEquals("Ensure that buyProduct method updates quantity sold!", 10, product1.getQuantitySold());

        assertEquals("Ensure that getRevenue method works correctly!", 10.0 * 7 + 8.0 * 3, product1.getRevenue(),
                0.001);
    }

    @Test(timeout = 1000)
    public void sortAlphabeticallyTest() {
        Product strawberry = new Product("strawberry", "fruit", 15.0, 10);
        Product blueberry = new Product("blueberry", "fruit", 10.0, 10);
        Product raspberry = new Product("raspberry", "fruit", 5.0, 10);

        ArrayList<Product> arrayList = new ArrayList<>();

        arrayList.add(strawberry);
        arrayList.add(blueberry);
        arrayList.add(raspberry);

        ArrayList<Product> sortAlphabeticallyList;
        sortAlphabeticallyList = Product.sortAlphabetically(arrayList);

        // should be in order "blueberry, raspberry, strawberry"

        assertEquals("Ensure that sortAlphabetically method works!", blueberry, sortAlphabeticallyList.get(0));

        assertEquals("Ensure that sortAlphabetically method works!", raspberry, sortAlphabeticallyList.get(1));

        assertEquals("Ensure that sortAlphabetically method works!", strawberry, sortAlphabeticallyList.get(2));


        // test with more
        Product strawhat = new Product("strawhat", "hat", 15.0, 10);
        Product blue = new Product("blue", "color", 10.0, 10);
        Product raspy = new Product("raspy", "status", 5.0, 10);

        arrayList.add(strawhat);
        arrayList.add(blue);
        arrayList.add(raspy);

        sortAlphabeticallyList = Product.sortAlphabetically(arrayList);

        // should be in order "blue, blueberry, raspberry, raspy, strawberry, strawhat"

        assertEquals("Ensure that sortAlphabetically method works!", blue, sortAlphabeticallyList.get(0));

        assertEquals("Ensure that sortAlphabetically method works!", blueberry, sortAlphabeticallyList.get(1));

        assertEquals("Ensure that sortAlphabetically method works!", raspberry, sortAlphabeticallyList.get(2));

        assertEquals("Ensure that sortAlphabetically method works!", raspy, sortAlphabeticallyList.get(3));

        assertEquals("Ensure that sortAlphabetically method works!", strawberry, sortAlphabeticallyList.get(4));

        assertEquals("Ensure that sortAlphabetically method works!", strawhat, sortAlphabeticallyList.get(5));
    }

    @Test(timeout = 1000)
    public void sortByCheapestTest() {
        Product product1 = new Product("strawberry", "fruit", 15.0, 10);
        Product product2 = new Product("blueberry", "fruit", 10.0, 10);
        Product product3 = new Product("raspberry", "fruit", 5.0, 10);

        ArrayList<Product> arrayList = new ArrayList<>();

        arrayList.add(product1);
        arrayList.add(product2);
        arrayList.add(product3);

        ArrayList<Product> sortByCheapestList;
        sortByCheapestList = Product.sortByCheapest(arrayList);

        assertEquals("Ensure that sortByCheapest method works!", product3, sortByCheapestList.get(0));

        assertEquals("Ensure that sortByCheapest method works!", product2, sortByCheapestList.get(1));

        assertEquals("Ensure that sortByCheapest method works!", product1, sortByCheapestList.get(2));
    }
}