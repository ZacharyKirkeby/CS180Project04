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
 * A set of test cases.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author William Hyun
 * @version November 9, 2023
 */
public class CustomerTestCases {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(CustomerTestCases.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }


    @Test(timeout = 1000)
    public void buyProductsTest() {
        reset();
        assertEquals(false, Customer.buyProductsInShoppingCart("username"));

        Customer.addToCart("email", "username", "storename", "productname", 10);

        // check to see if shoppingcart database has been updated
        ArrayList<String> list1 = new ArrayList<>();
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

        assertEquals("Ensure the addToCart method works!", "email;username;storename;" + "productname;10",
                list1.get(0));

        Customer.removeFromCart("email", "username", "storename", "productname", 10);

        list1.remove(0);
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

        assertEquals("Ensure the removeFromCart method works!", 0, list1.size());

        Seller.createStore("storename", "storeLocation", "username");
        Seller.createProduct("storename", "strawberry", "fruit", 20.0, 20, "username");
        Seller.createProduct("storename", "blueberry", "fruit", 15.0, 20, "username");

        assertEquals("Ensure the addtoCart method works!", true, Customer.addToCart("email", "username1", "storename"
                , "strawberry", 10));
        assertEquals("Ensure the addtoCart method works!", true, Customer.addToCart("email", "username1", "storename"
                , "blueberry", 12));


        assertEquals("Ensure the getTotalInCart method works!", 10, Customer.getTotalInCart("storename", "strawberry"));
        assertEquals("Ensure the getTotalInCart method works!", 12, Customer.getTotalInCart("storename", "blueberry"));

        assertEquals("Ensure the addtoCart method does not stop different customers from adding multiple of the same "
                + "product!", true, Customer.addToCart("email2", "username2", "storename", "strawberry", 5));


        assertEquals("Ensure the getTotalInCart method works with multiple users' shopping carts!", 15,
                Customer.getTotalInCart("storename", "strawberry"));
        assertEquals("Ensure the getTotalInCart method works with multiple users' shopping carts!", 12,
                Customer.getTotalInCart("storename", "blueberry"));

        // test buyproduct
        assertEquals("Ensure the buyProduct method works with valid input!", true,
                Customer.buyProductsInShoppingCart("username1"));

        list1.clear();
        // clear and read from file again
        try (BufferedReader bfr = new BufferedReader(new FileReader("PurchaseHistoryDatabase.txt"))) {
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

        assertEquals("email;username1;storename;strawberry;10;20.00", list1.get(0));

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

        assertEquals("email;username1;storename;blueberry;12", list1.get(0));
        assertEquals("email2;username2;storename;strawberry;5", list1.get(1));

        assertEquals("[email;username1;storename;blueberry;12]",
                Customer.getShoppingCartofCustomer("username1").toString());

        assertEquals("[email2;username2;storename;strawberry;5]",
                Customer.getShoppingCartofCustomer("username2").toString());


        Customer.getPurchaseHistoryofCustomer("username1", "Customer1PurchaseHistory.txt");

        list1.clear();
        // clear and read from file again
        try (BufferedReader bfr = new BufferedReader(new FileReader("Customer1PurchaseHistory.txt"))) {
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

        assertEquals("email;username1;storename;strawberry;10", list1.get(0));

        Customer.getPurchaseHistoryofCustomer("username2", "Customer2PurchaseHistory.txt");

        list1.clear();
        // clear and read from file again
        try (BufferedReader bfr = new BufferedReader(new FileReader("Customer2PurchaseHistory.txt"))) {
            String line = bfr.readLine();
            while (line != null && !line.isEmpty()) {
                list1.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        assertEquals(0, list1.size());
        assertEquals("Ensure the addtoCart method works with adding maximum available quantity!", true,
                Customer.addToCart("email", "username1", "storename", "strawberry", 20));

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

        // should have added max
        assertEquals("email;username1;storename;strawberry;10", list1.get(2));

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

        //
        assertEquals("Make sure buyProduct is updating stores.txt!",
                "storename,storeLocation,username;strawberry," + "fruit,20.0,10;blueberry,fruit,15.0,20;",
                list1.get(0));

    }

    @Test(timeout = 1000)
    public void reviewTests() {
        reset();
        assertEquals(false, Customer.leaveReview("storename", "productName", "customerName", 6, "description"));

        assertEquals(false, Customer.leaveReview("storename", "productName", "customerName", 0, "description"));

        assertEquals(true, Customer.leaveReview("storename", "productName", "customerName", 3, "description"));

        assertEquals("Store Name | Product Name | Customer Name | Rating \n" + "storename | productName | " +
                "customerName | 3 | description\n", Customer.viewReviews("storename", "productName"));
    }

    @Test(timeout = 1000)
    public void searchTests() {

    }

    public static void reset() {
        // create ShoppingCartDatabase.txt if not already there or clear its contents if its already there
        try {
            File myObj = new File("ShoppingCartDatabase.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                FileOutputStream fos = new FileOutputStream("ShoppingCartDatabase.txt", false);
                PrintWriter pw = new PrintWriter(fos);
                pw.print("");
                if (pw != null) {
                    pw.close();
                }
                System.out.println("File cleared.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // create ShoppingCartDatabase.txt if not already there or clear its contents if its already there
        try {
            File myObj = new File("PurchaseHistoryDatabase.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                FileOutputStream fos = new FileOutputStream("PurchaseHistoryDatabase.txt", false);
                PrintWriter pw = new PrintWriter(fos);
                pw.print("");
                if (pw != null) {
                    pw.close();
                }
                System.out.println("File cleared.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("Reviews.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                FileOutputStream fos = new FileOutputStream("Reviews.txt", false);
                PrintWriter pw = new PrintWriter(fos);
                pw.print("");
                if (pw != null) {
                    pw.close();
                }
                System.out.println("File cleared.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("stores.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                FileOutputStream fos = new FileOutputStream("stores.txt", false);
                PrintWriter pw = new PrintWriter(fos);
                pw.print("");
                if (pw != null) {
                    pw.close();
                }
                System.out.println("File cleared.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
