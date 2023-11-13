package src;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SellerTestCases {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(SellerTestCases.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    @Test(timeout = 1000)
    public void sellerStoreEditTests() {
        CustomerTestCases.reset();

        assertEquals("Make sure editProductPrice method does invalid argument handling!", false,
                Seller.editProductPrice("invalidstorename", "invalidname", 10.0, "invalidusername"));

        assertEquals("Make sure editProductPrice method does invalid argument handling!", false,
                Seller.editProductQuantity("invalidstorename", "invalidname", 10, "invalidusername"));

        assertEquals("Make sure editProductPrice method does invalid argument handling!", false,
                Seller.editProductDescription("invalidstorename", "invalidname", "description", "invalidusername"));

        assertEquals("Make sure createStore method works with valid arguments!", true, Seller.createStore("store " +
                "name", "store location", "sellerUsername"));

        // check to see if stores.txt database has been updated
        ArrayList<String> list1 = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("stores.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list1.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        assertEquals("Ensure that createStore updates store.txt!", "store name,store location,sellerUsername;",
                list1.get(0));

        assertEquals("Make sure createStore method stops the creation of two of the same store names!", false,
                Seller.createStore("store name", "store location1", "username1"));


        assertEquals("Make sure createProduct method checks invalid arguments!", false, Seller.createProduct("invalid" +
                " store name", "product name", "product", 10, 10, "sellerUsername"));

        assertEquals("Make sure createProduct method checks invalid arguments!", false, Seller.createProduct("store " +
                "name", "product name", "product", -1, 10, "sellerUsername"));

        assertEquals("Make sure createProduct method checks invalid arguments!", false, Seller.createProduct("store " +
                "name", "product name", "product", 1, -10, "sellerUsername"));

        assertEquals("Make sure createProduct method checks invalid arguments!", false, Seller.createProduct("store " +
                "name", "product name", "product", 1, 10, "invalidUsername"));

        assertEquals("Make sure createProduct method works with valid arguments!", true, Seller.createProduct("store " +
                "name", "product name", "product", 10, 10, "sellerUsername"));

        // check to see if stores.txt database has been updated
        list1.clear();
        try (BufferedReader bfr = new BufferedReader(new FileReader("stores.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list1.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        assertEquals("Ensure that createStore updates store.txt!", "store name,store location,sellerUsername;product "
                + "name," + "product,10.0,10;", list1.get(0));


        assertEquals("Make sure editProductPrice method checks invalid arguments!", false, Seller.editProductPrice(
                "invalid store name", "product name", 8, "sellerUsername"));

        assertEquals("Make sure editProductPrice method checks invalid arguments!", false, Seller.editProductPrice(
                "store name", "invalid product name", 8, "sellerUsername"));

        assertEquals("Make sure editProductPrice method checks invalid arguments!", false, Seller.editProductPrice(
                "store name", "product name", -8, "sellerUsername"));

        assertEquals("Make sure editProductPrice method checks invalid arguments!", false, Seller.editProductPrice(
                "store name", "product name", -8, "invalid username"));

        assertEquals("Make sure editProductPrice method works with valid arguments!", true, Seller.editProductPrice(
                "store name", "product name", 8, "sellerUsername"));

        assertEquals("Make sure editProductDescription method checks invalid arguments!", false,
                Seller.editProductDescription("invalid store name", "product name", "new description",
                        "sellerUsername"));

        assertEquals("Make sure editProductDescription method checks invalid arguments!", false,
                Seller.editProductDescription("store name", "invalid product name", "new description",
                        "sellerUsername"));

        assertEquals("Make sure editProductDescription method checks invalid arguments!", false,
                Seller.editProductDescription("store name", "product name", "description", "invalid username"));

        assertEquals("Make sure editProductDescription method works with valid arguments!", true,
                Seller.editProductDescription("store name", "product name", "new description", "sellerUsername"));

        assertEquals("Make sure editProductQuantity method checks invalid arguments!", false,
                Seller.editProductQuantity("invalid store name", "product name", 20, "sellerUsername"));

        assertEquals("Make sure editProductQuantity method checks invalid arguments!", false,
                Seller.editProductQuantity("store name", "invalid product name", 20, "sellerUsername"));

        assertEquals("Make sure editProductQuantity method checks invalid arguments!", false,
                Seller.editProductQuantity("store name", "product name", -20, "sellerUsername"));

        assertEquals("Make sure editProductQuantity method checks invalid arguments!", false,
                Seller.editProductQuantity("store name", "product name", 20, "invalid username"));

        assertEquals("Make sure editProductQuantity method works with valid arguments!", true,
                Seller.editProductQuantity("store name", "product name", 20, "sellerUsername"));

        // check to see if stores.txt database has been updated
        list1.clear();
        try (BufferedReader bfr = new BufferedReader(new FileReader("stores.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list1.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        assertEquals("Ensure that createStore updates store.txt!", "store name,store location,sellerUsername;product "
                + "name," + "new description,8.0,20;", list1.get(0));

        assertEquals("Make sure createProduct method works with valid arguments!", true, Seller.createProduct("store " +
                "name", "second product", "product", 10, 10, "sellerUsername"));

        System.out.println("\nprintProductAndStores method test:");
        Seller.printProductAndStores();
        System.out.println("\nprintStores method test:");
        Seller.printStores();


        assertEquals("Make sure createProduct method works with valid arguments!", "product name, second product",
                Seller.printProducts("store name"));


        assertEquals(true, Customer.addToCart("email2", "username2", "store name", "product name", 5));
        assertEquals(true, Customer.addToCart("email1", "username1", "store name", "product name", 1));

        list1.clear();
        // clear and read from file again
        try (BufferedReader bfr = new BufferedReader(new FileReader("ShoppingCartDatabase.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list1.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        System.out.println(list1.toString());

        assertEquals("Ensure the buyProduct method works with valid input!", true,
                Customer.buyProductsInShoppingCart("username2"));

        assertEquals("Customer Email | Customer Username | Store Name | Product Name | Quantity Purchased | Revenue " + "From Customer \n" + "email2 | username2 | store name | product name | 5 | 8.00 | 40.0\n", Seller.salesByStore("store name", "sellerUsername"));

        // getProductSales is broken as of now
        System.out.println(Seller.getProductSales("store name", "sellerUsername", false));
        System.out.println(Seller.getProductSales("store name", "sellerUsername", true));

        assertEquals("store name - product name | in stock: 15 | in shopping cart: 1\n" + "store name - second " +
                "product | in stock: 10 | in shopping cart: 0\n", Seller.getShoppingCartProducts("sellerUsername"));


        assertEquals("Make sure editProductDescription method checks invalid arguments!", false,
                Seller.deleteProduct("invalid store name", "product name", "sellerUsername"));

        assertEquals("Make sure editProductDescription method checks invalid arguments!", false,
                Seller.deleteProduct("store name", "invalid product name", "sellerUsername"));

        assertEquals("Make sure editProductDescription method checks invalid arguments!", false,
                Seller.deleteProduct("store name", "product name", "invalid username"));

        assertEquals("Make sure editProductDescription method works!", true, Seller.deleteProduct("store name",
                "product name", "sellerUsername"));

        // check to see if stores.txt database has been updated
        list1.clear();
        try (BufferedReader bfr = new BufferedReader(new FileReader("stores.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list1.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        assertEquals("Ensure that createStore updates store.txt!",
                "store name,store location,sellerUsername;second " + "product,product,10.0,10;", list1.get(0));

        assertEquals(false, Seller.deleteStore("invalid store name", "sellerUsername"));
        assertEquals(false, Seller.deleteStore("store name", "invalid username"));
        assertEquals(true, Seller.deleteStore("store name", "sellerUsername"));

        list1.clear();
        // clear and read from file again
        try (BufferedReader bfr = new BufferedReader(new FileReader("stores.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list1.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        assertEquals("Make sure deleteStore method updates store.txt", 0, list1.size());
    }
}
