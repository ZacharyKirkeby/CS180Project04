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

        assertEquals("Ensure the addToCart method works!", "email;username;storename;" +
            "productname;10", list1.get(0));

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
                pw.println();
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
                pw.println();
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
