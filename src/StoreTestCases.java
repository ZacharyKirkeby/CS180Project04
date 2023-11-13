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

        ArrayList<Product> productList = new ArrayList<>();

        Product strawberry = new Product("strawberry", "fruit", 15.0, 10);
        Product blueberry = new Product("blueberry", "fruit", 10.0, 10);
        Product raspberry = new Product("raspberry", "fruit", 5.0, 10);

        productList.add(strawberry);
        productList.add(blueberry);
        productList.add(raspberry);

        Store store = new Store("name", "storeLocation", "seller", productList);

        assertEquals("Ensure getStoreName is working correctly!", "name", store.getStoreName());

        assertEquals("Ensure getStoreLocation is working correctly!", "storeLocation", store.getStoreLocation());

        assertEquals("Ensure getProductList is working correctly!", productList, store.getProductList());

        assertEquals("Ensure getSellerUsername is working correctly!", "seller", store.getSellerUsername());

        assertEquals("Ensure getTotalSales is working correctly!", 0, store.getTotalSales());

        assertEquals("Ensure getTotalRevenue is working correctly!", 0.0, store.getTotalRevenue(), 0.001);

        store.getProductsSortedByCheapest();
        // this method sorts the productlist in store and also return the sorted productlist

        assertEquals("Ensure that getProductsSortedByCheapest method works!", raspberry, store.getProductList().get(0));

        assertEquals("Ensure that getProductsSortedByCheapest method works!", blueberry, store.getProductList().get(1));

        assertEquals("Ensure that getProductsSortedByCheapest method works!", strawberry,
                store.getProductList().get(2));


        assertEquals("Ensure that toString method works!", "name,storeLocation,seller", store.toString());

        assertEquals("Ensure that toString method works!", "name:raspberry | blueberry | strawberry",
                store.toStringProducts());

        // buy 5 raspberries at 5.0 each
        store.getProductList().get(0).buyProduct(5);

        assertEquals("Ensure getTotalSales is working correctly!", 5, store.getTotalSales());

        assertEquals("Ensure getTotalRevenue is working correctly!", 5.0 * 5, store.getTotalRevenue(), 0.001);

        // buy 5 blueberries at 10.0 each
        store.getProductList().get(1).buyProduct(5);

        assertEquals("Ensure getTotalSales is working correctly!", 10, store.getTotalSales());

        assertEquals("Ensure getTotalRevenue is working correctly!", 5.0 * 5 + 10.0 * 5, store.getTotalRevenue(),
                0.001);

        // buy 5 strawberries at 15.0 each
        store.getProductList().get(2).buyProduct(5);

        assertEquals("Ensure getTotalSales is working correctly!", 15, store.getTotalSales());

        assertEquals("Ensure getTotalRevenue is working correctly!", 5.0 * 5 + 10.0 * 5 + 15.0 * 5,
                store.getTotalRevenue(), 0.001);

        store.triggerSale("blueberry", 8.0, 10);

        // buy 2 blueberries at 8.0 each
        store.getProductList().get(1).buyProduct(2);

        assertEquals("Ensure getTotalSales is working correctly during a sale!", 17, store.getTotalSales());

        assertEquals("Ensure getTotalRevenue is working correctly during a sale!",
                5.0 * 5 + 10.0 * 5 + 15.0 * 5 + 8.0 * 2,
                store.getTotalRevenue(), 0.001);

        // start sale for raspberry and strawberry
        assertTrue(store.triggerSale("raspberry", 3.0, 10));
        assertTrue(store.triggerSale("strawberry", 12.0, 10));
        assertFalse(store.triggerSale("fakename", 8.0, 10));
        assertFalse(store.triggerSale("raspberry", 8.0, -10));
        assertFalse(store.triggerSale("raspberry", -8.0, 10));

        // end sale for blueberry
        assertTrue(store.triggerEndSale("blueberry"));
        assertFalse(store.triggerEndSale("fakename"));

        // buy 2 blueberries at regular price (10.0)
        store.getProductList().get(1).buyProduct(2);

        assertEquals("Ensure getTotalSales is working correctly!", 19, store.getTotalSales());

        assertEquals("Ensure getTotalRevenue is working correctly!",
                5.0 * 5 + 10.0 * 5 + 15.0 * 5 + 8.0 * 2 + 10.0 * 2,
                store.getTotalRevenue(), 0.001);

        // buy 5 raspberries at sale price of 3.0 each
        store.getProductList().get(0).buyProduct(5);

        assertEquals("Ensure getTotalSales is working correctly!", 24, store.getTotalSales());

        assertEquals("Ensure getTotalRevenue is working correctly!",
                5.0 * 5 + 10.0 * 5 + 15.0 * 5 + 8.0 * 2 + 10.0 * 2 + 3.0 * 5, store.getTotalRevenue(),
                0.001);

        assertTrue("Ensure triggerOrderCap method works!", store.triggerOrderCap("strawberry", 3));

        // try to buy 4 strawberries at sale price 12.0 each
        assertFalse("Ensure triggerOrderCap method works!", store.getProductList().get(2).buyProduct(4));

        assertTrue("Ensure triggerOrderCap method works!", store.getProductList().get(2).buyProduct(3));

        assertEquals("Ensure getTotalSales is working correctly!", 27, store.getTotalSales());

        assertEquals("Ensure getTotalRevenue is working correctly!",
                5.0 * 5 + 10.0 * 5 + 15.0 * 5 + 8.0 * 2 + 10.0 * 2 + 3.0 * 5 + 12.0 * 3,
                store.getTotalRevenue(), 0.001);

        store.setStoreLocation("internet");

        store.setStoreName("storename");

        store.setSellerUsername("sellerusername");

        assertEquals("Ensure that the set methods work!", "storename,internet,sellerusername", store.toString());

        productList.remove(strawberry);

        store.setProductList(productList);

        assertEquals("Ensure that setProductList method works!", "storename:raspberry | blueberry",
                store.toStringProducts());


        System.out.println(store.getCustomerInformationAndRevenue());
        System.out.println(Store.getAllCustomersAndPurchases());
        System.out.println(store.getCustomersAndPurchases());

    }
}