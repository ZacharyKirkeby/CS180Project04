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
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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

        product1.buyProduct(10);

        assertEquals("Ensure that buyProduct method updates stock quantity!", 0, product1.getStockQuantity());

        assertEquals("Ensure that buyProduct method updates quantity sold!", 10, product1.getQuantitySold());

        assertEquals("Ensure that getSales method works correctly!", 15.0 * 10, product1.getSales(), 0.001);
    }

    // make sure to test getSales to see if a sale affects return value or not and also if anything changes if
    // the sale ends.

}