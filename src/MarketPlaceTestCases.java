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


    @Test(timeout = 1000)
    public void testSellerCreateStore() {
        AccountTestCases.reset();
        CustomerTestCases.reset();
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
            "File cleared.\n" +
            "File cleared.\n" +
            "File cleared.\n" +
            "File cleared.\n" +
            "Welcome to the Fruit Market!\n" +
            "Would you like to Login or Register an Account? (Login / Register / Exit)\n" +
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
            " 1. Create Store \n" +
            " 2. Modify Store \n" +
            " 3. View Store Statistics \n" +
            " 4. Delete Store \n" +
            " 5. View Customer Reviews \n" +
            " 6. Manage Account \n" +
            " 7. Logout \n" +
            "Enter a store name: \n" +
            "Enter a store location: \n" +
            "Successfully Created\n" +
            " 1. Create Store \n" +
            " 2. Modify Store \n" +
            " 3. View Store Statistics \n" +
            " 4. Delete Store \n" +
            " 5. View Customer Reviews \n" +
            " 6. Manage Account \n" +
            " 7. Logout \n" +
            " 1. Create Product \n" +
            " 2. Change Product Price \n" +
            " 3. Change Product Quantity \n" +
            " 4. Delete Product \n" +
            " 5. Add products to Store from CSV \n" +
            " 6. Start Sale \n" +
            " 7. Add Purchase Limit \n" +
            " 8. Back \n" +
            "Enter Store Name: \n" +
            "Enter Product Name: \n" +
            "Enter Product Price: \n" +
            "Enter Product Quantity: \n" +
            "Enter Product Description: \n" +
            "Successfully Added Product\n" +
            " 1. Create Store \n" +
            " 2. Modify Store \n" +
            " 3. View Store Statistics \n" +
            " 4. Delete Store \n" +
            " 5. View Customer Reviews \n" +
            " 6. Manage Account \n" +
            " 7. Logout \n" +
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

    @Test(timeout = 1000)
    public void testSellerCreateStoreInvalidInputs() {
        AccountTestCases.reset();
        CustomerTestCases.reset();
        String input = "\n" +
            "7\n" +
            "register\n" +
            "email@gmail.com\n" +
            "username\n" +
            "password\n" +
            "seller\n" +
            "login\n" +
            "username\n" +
            "password\n" +
            "8\n" +
            "1\n" +
            "\n" +
            "\n" +
            "1\n" +
            "validname\n" +
            "\n" +
            "7\n" +
            "exit";

        // Pair the input with the expected result
        String expected = "File cleared.\n" +
            "File cleared.\n" +
            "File cleared.\n" +
            "File cleared.\n" +
            "File cleared.\n" +
            WELCOME_PROMPT + "\n" +
            LOGIN_PROMPT + "\n" +
            "Invalid Input\n" +
            WELCOME_PROMPT + "\n" +
            LOGIN_PROMPT + "\n" +
            "Invalid Input\n" +
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
            "Invalid Input\n" +
            sellerChoices +
            "Enter a store name: \n" +
            "Enter a store location: \n" +
            "Creation Failed\n" +
            "Try Again!\n" +
            sellerChoices +
            "Enter a store name: \n" +
            "Enter a store location: \n" +
            "Creation Failed\n" +
            "Try Again!\n" +
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

    @Test(timeout = 1000)
    public void testSellerEditAndDelete() {
        AccountTestCases.reset();
        CustomerTestCases.reset();
        // Set the input
        String input = "register\n" +
            "email@gmail.com\n" +
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
            "2\n" +
            "1\n" +
            "fruit stand\n" +
            "blueberry\n" +
            "3\n" +
            "20\n" +
            "blue fruit\n" +
            "2\n" +
            "2\n" +
            "fruit stand\n" +
            "apple\n" +
            "7\n" +
            "2\n" +
            "3\n" +
            "fruit stand\n" +
            "blueberry\n" +
            "15\n" +
            "7\n" +
            "exit";

        // Pair the input with the expected result
        String expected = "File cleared.\n" +
            "File cleared.\n" +
            "File cleared.\n" +
            "File cleared.\n" +
            "File cleared.\n" +
            "Welcome to the Fruit Market!\n" +
            "Would you like to Login or Register an Account? (Login / Register / Exit)\n" +
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
            " 1. Create Store \n" +
            " 2. Modify Store \n" +
            " 3. View Store Statistics \n" +
            " 4. Delete Store \n" +
            " 5. View Customer Reviews \n" +
            " 6. Manage Account \n" +
            " 7. Logout \n" +
            "Enter a store name: \n" +
            "Enter a store location: \n" +
            "Successfully Created\n" +
            " 1. Create Store \n" +
            " 2. Modify Store \n" +
            " 3. View Store Statistics \n" +
            " 4. Delete Store \n" +
            " 5. View Customer Reviews \n" +
            " 6. Manage Account \n" +
            " 7. Logout \n" +
            " 1. Create Product \n" +
            " 2. Change Product Price \n" +
            " 3. Change Product Quantity \n" +
            " 4. Delete Product \n" +
            " 5. Add products to Store from CSV \n" +
            " 6. Start Sale \n" +
            " 7. Add Purchase Limit \n" +
            " 8. Back \n" +
            "Enter Store Name: \n" +
            "Enter Product Name: \n" +
            "Enter Product Price: \n" +
            "Enter Product Quantity: \n" +
            "Enter Product Description: \n" +
            "Successfully Added Product\n" +
            " 1. Create Store \n" +
            " 2. Modify Store \n" +
            " 3. View Store Statistics \n" +
            " 4. Delete Store \n" +
            " 5. View Customer Reviews \n" +
            " 6. Manage Account \n" +
            " 7. Logout \n" +
            " 1. Create Product \n" +
            " 2. Change Product Price \n" +
            " 3. Change Product Quantity \n" +
            " 4. Delete Product \n" +
            " 5. Add products to Store from CSV \n" +
            " 6. Start Sale \n" +
            " 7. Add Purchase Limit \n" +
            " 8. Back \n" +
            "Enter Store Name: \n" +
            "Enter Product Name: \n" +
            "Enter Product Price: \n" +
            "Enter Product Quantity: \n" +
            "Enter Product Description: \n" +
            "Successfully Added Product\n" +
            " 1. Create Store \n" +
            " 2. Modify Store \n" +
            " 3. View Store Statistics \n" +
            " 4. Delete Store \n" +
            " 5. View Customer Reviews \n" +
            " 6. Manage Account \n" +
            " 7. Logout \n" +
            " 1. Create Product \n" +
            " 2. Change Product Price \n" +
            " 3. Change Product Quantity \n" +
            " 4. Delete Product \n" +
            " 5. Add products to Store from CSV \n" +
            " 6. Start Sale \n" +
            " 7. Add Purchase Limit \n" +
            " 8. Back \n" +
            "Enter Store Name: \n" +
            "Enter Product Name: \n" +
            "Enter New Product Price: \n" +
            "Successfully edited\n" +
            " 1. Create Store \n" +
            " 2. Modify Store \n" +
            " 3. View Store Statistics \n" +
            " 4. Delete Store \n" +
            " 5. View Customer Reviews \n" +
            " 6. Manage Account \n" +
            " 7. Logout \n" +
            " 1. Create Product \n" +
            " 2. Change Product Price \n" +
            " 3. Change Product Quantity \n" +
            " 4. Delete Product \n" +
            " 5. Add products to Store from CSV \n" +
            " 6. Start Sale \n" +
            " 7. Add Purchase Limit \n" +
            " 8. Back \n" +
            "Enter Store Name: \n" +
            "Enter Product Name: \n" +
            "Enter New Quantity: \n" +
            "Successfully Edited\n" +
            " 1. Create Store \n" +
            " 2. Modify Store \n" +
            " 3. View Store Statistics \n" +
            " 4. Delete Store \n" +
            " 5. View Customer Reviews \n" +
            " 6. Manage Account \n" +
            " 7. Logout \n" +
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
