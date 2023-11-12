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

import static org.junit.Assert.assertEquals;

/**
 * A set of product test cases.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author William Hyun
 * @version November 9, 2023
 */
public class StoreTestCases {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(StoreTestCases.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    @Test(timeout = 1000)
    public void createStoreTest() {

        ArrayList<Product> productList = new ArrayList<>();;

        Product strawberry = new Product("strawberry", "fruit", 15.0, 10);
        Product blueberry = new Product("blueberry", "fruit", 10.0, 10);
        Product raspberry = new Product("raspberry", "fruit", 5.0, 10);

        productList.add(strawberry);
        productList.add(blueberry);
        productList.add(raspberry);

        Store store = new Store("name", "storeLocation",  "seller", productList);
        
    }
}