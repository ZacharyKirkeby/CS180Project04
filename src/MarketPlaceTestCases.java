package src;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MarketPlaceTestCases {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MarketPlaceTestCases.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayInputStream testIn;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayOutputStream testOut;

    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @SuppressWarnings("SameParameterValue")
    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }


    // Each of the correct outputs
    private static final String WELCOME_PROMPT = "Welcome to the Fruit Market!";
    private static final String LOGIN_PROMPT = "Would you like to Login or Register an Account? (Login / Register / " +
        "Exit)";
    private static final String sellerChoices = """
             1. Create Store\s
             2. Modify Store\s
             3. View Store Statistics\s
             4. Delete Store\s
             5. View Customer Reviews\s
             6. Manage Account\s
             7. Logout\s
            """;
    private static final String sellerModificationChoices = """
             1. Create Product\s
             2. Change Product Price\s
             3. Change Product Quantity\s
             4. Delete Product\s
             5. Add products to Store from CSV\s
             6. Start Sale\s
             7. Add Purchase Limit\s
             8. Back\s
            """;
    private static final String AccountChoices = """
             1. Change Username\s
             2. Change Password\s
             3. Change Role\s
             4. Delete Account\s
             5. Back\s
            """;
    private static final String sellerStatisticsChoices = """
             1. View Customer Purchases\s
             2. View Product Sales\s
             3. View Products in Shopping Cart\s
             4. View Products in Store as CSV file\s
             5. Back\s
            """;
    private static final String BUYERPROMPT = """
             1.  Search for a store\s
             2.  Search for a product\s
             3.  Search Product by Description\s
             4.  View All Products\s
             5.  Sort Products By Cheapest\s
             6.  Sort  Products By Most Expensive\s
             7.  Sort by Availability\s
             8.  Shopping Cart\s
             9.  Export Purchase History as file\s
             10. Leave Review\s
             11. View Product Reviews\s
             12. Manage Account\s
             13. Logout\s
            """;
    private static final String customerShoppingCartChoices = """
             1. Add product(s) to cart\s
             2. Change Quantity of Product in Cart\s
             3. Remove product(s) from cart\s
             4. Buy products in cart\s
             5. View shopping cart\s
             6. Back
            """;
    private static final String AVAILABILITY = """
            1. Sort By Highest Stock\s
            2. Sort By Low On Stock\s
            """;
    private static final String SEARCH_PROMPT = "Enter search term: ";


    @Test(timeout = 100000)
    public void testExpectedOne() {
        AccountTestCases.reset();
        // Set the input
        String input = "register\n" +
                "neweemail@gmail.com\n" +
                "username\n" +
                "password\n" +
                "seller\n" +
                "login\n" +
                "username\n" +
                "password\n" +
                "1\n" +
                "fruit stand\n" +
                "IN\n" +
                "2\n" +
                "1\n" +
                "fruit stand\n" +
                "apple\n" +
                "5\n" +
                "10\n" +
                "red fruit\n" +
                "7\n" +
            "exit";

        // Pair the input with the expected result
        String expected = "File cleared.\n" +
                WELCOME_PROMPT + "\n" +
                LOGIN_PROMPT + "\n" +
                "Enter an email: \n" +
                "Input Username: \n" +
                "Enter your password: \n" +
                "Enter your role (customer / seller)\n" +
                "Account Made Successfully\n" +
                "Welcome to the Fruit Market!\n" +
                "Would you like to Login or Register an Account? (Login / Register / Exit)\n" +
                "Input Username or Email: \n" +
                "Enter your password: \n" +
                "Login Successful!\n" +
                sellerChoices +
                "Enter a store name: \n" +
                "Enter a store location: \n" +
                "Successfully Created\n" +
                sellerChoices +
                sellerModificationChoices +
                "Enter Store Name: \n" +
                "Enter Product Name: \n" +
                "Enter Product Price: \n" +
                "Enter Product Quantity: \n" +
                "Enter Product Description: \n" +
                "Successfully Added Product\n" +
                sellerChoices +
                "Successfully Logged out\n" +
                "Welcome to the Fruit Market!\n" +
                "Would you like to Login or Register an Account? (Login / Register / Exit)\n" +
                "Thank You For Using Our Fruit Market!";

        // Runs the program with the input values
        receiveInput(input);
        MarketPlace.main(new String[0]);

        // Retreives the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct.
        stuOut = stuOut.replace("\r\n", "\n");
        assertEquals("Make sure marketplace is implemented correctly!",
                expected.trim(), stuOut.trim());
    }


}
