package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * Project 04 -- MarketPlace.java
 * creates a Market to manage interactions and listings in a marketplace
 * Handles all MarketPlace related tasks
 * and functions.
 * Acts as the main interface of all the classes
 * @author Armaan Sayyad, 05
 * @author Zachary Kirkeby, 05
 * @version November 10, 2023
 */
public class MarketPlace {
    private static final String WELCOME_PROMPT = "Login or Register an Account (Login / Register)";
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
             9.  Leave Review\s
             10. View Product Reviews\s
             11. Manage Account\s
             12. Logout\s
            """;
    private static final String customerShoppingCartChoices = """
             1. Add product(s) to cart\s
             2. Remove product(s) from cart\s
             3. Buy products in cart\s
             4. View shopping cart\s
            """;
    private static  final String AVAILABILITY = "1. Sort By Highest Stock \n 2. Sort By Low On Stock";
    private static final String SEARCH_PROMPT = "Enter search term: ";
    private static ArrayList<Store> stores;
    private static boolean isLoggedIn;

    /**
     *Constructors for the MarketPlace
     * @param stores (ArrayList<Stores></>)
     */
    public MarketPlace(ArrayList<Store> stores) {
        this.stores = stores;
        // some kind of logic tbd
    }
    /**
     *Getter for the Stores's in the MarketPlace
     * @return stores
     */
    public static ArrayList<Store> getStores() {
        return stores;
    }
    /**
     *Main method for the Marketplace handles all the interactions
     * and interfaces between the classes
     * @param args (String[])
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); //instantiates a scanner object to read terminal inputs
        do {
            System.out.println(WELCOME_PROMPT);
            String input = scanner.nextLine().toLowerCase();

            switch (input) { // handles user case of login or register and directs to the next relevant step
                // Authenticates User, begins use loop
                case "login":
                    System.out.println("Input Username or Email: ");
                    String user = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    String password = scanner.nextLine();
                    boolean bool = Account.login(user, password);
                    while(!bool){
                        System.out.println("Login Failed");
                        System.out.println("Try Again!");
                        System.out.println("Input Username or Email: ");
                        user = scanner.nextLine();
                        System.out.println("Enter your password: ");
                        password = scanner.nextLine();
                        bool = Account.login(user, password);
                    }
                    isLoggedIn = true;

                    // Switch Var declarations
                    String storeName;
                    String productName;
                    double price;
                    int quantity;
                    boolean sorted;
                    String isSorted;


                    // loops while definite user
                    while (isLoggedIn) {
                        //Seller Experience
                        if (Account.getRole(user).equalsIgnoreCase("seller")) {
                            System.out.print(sellerChoices);
                            input = scanner.nextLine();
                            switch (input) {
                                //Create Store
                                case "1":
                                    System.out.println("Enter a store name: ");
                                    storeName = scanner.nextLine();
                                    System.out.println("Enter a store location: ");
                                    String location = scanner.nextLine();
                                    bool = Seller.createStore(storeName, location, user);
                                    if (bool) {
                                        System.out.println("Suceesfully Created");
                                    } else {
                                        System.out.println("Creation Failed");
                                        System.out.println("Try Again!");
                                    }
                                    break;
                                // Modify Store
                                case "2":
                                    System.out.print(sellerModificationChoices);
                                    input = scanner.nextLine();
                                    switch (input) {
                                        // creates product
                                        case "1":
                                            System.out.println("Enter Store Name: ");
                                            storeName = scanner.nextLine();
                                            System.out.println("Enter Product Name: ");
                                            productName = scanner.nextLine();
                                            System.out.println("Enter Product Price: ");
                                            price = Double.parseDouble(scanner.nextLine());
                                            System.out.println("Enter Product Quantity: ");
                                            quantity = Integer.parseInt(scanner.nextLine());
                                            System.out.println("Enter Product Description: ");
                                            String description = scanner.nextLine();
                                            bool = Seller.createProduct(storeName, productName, description, price, quantity, user);
                                            if (bool) {
                                                System.out.println("Successfully Added Product");
                                            } else {
                                                System.out.println("Deletion Failed");
                                            }
                                            break;
                                        // Edit Product Price
                                        case "2":
                                            System.out.println("Enter Store Name: ");
                                            storeName = scanner.nextLine();
                                            System.out.println("Enter Product Name: ");
                                            productName = scanner.nextLine();
                                            System.out.println("Enter New Product Price: ");
                                            price = Double.parseDouble(scanner.nextLine());
                                            bool = Seller.editProductPrice(storeName, productName, price, user);
                                            if (bool) {
                                                System.out.println("Successfully edited");
                                            } else {
                                                System.out.println("Edit Failed");
                                            }
                                            break;
                                        // Edit Product Quantity
                                        case "3":
                                            System.out.println("Enter Store Name: ");
                                            storeName = scanner.nextLine();
                                            System.out.println("Enter Product Name: ");
                                            productName = scanner.nextLine();
                                            System.out.println("Enter New Quantity: ");
                                            quantity = Integer.parseInt(scanner.nextLine());
                                            bool = Seller.editProductQuantity(storeName, productName, quantity, user);
                                            if (bool) {
                                                System.out.println("Successfully Edited");
                                            } else {
                                                System.out.println("Failed");
                                            }
                                            break;
                                        // Delete product
                                        case "4":
                                            bool = false;
                                            while (!bool) {
                                                System.out.println("Enter Store Name: ");
                                                storeName = scanner.nextLine();
                                                System.out.println("Enter Product Name: ");
                                                productName = scanner.nextLine();
                                                bool = Seller.deleteProduct(storeName, productName, user);
                                                if (bool) {
                                                    System.out.println("Successfully Deleted");
                                                } else {
                                                    System.out.println("Deletion Failed");
                                                    System.out.println("Try Again!");
                                                }
                                            }
                                            break;
                                        // add from CSV
                                        case "5":
                                            System.out.println("Enter Store Name: ");
                                            storeName = scanner.nextLine();
                                            System.out.println("Enter file path to be written from (include .txt)");
                                            String filePath = scanner.nextLine();
                                            Seller.readProductsFromCSV(storeName, filePath);
                                            break;
                                        // Trigger Sale
                                        case "6":
                                            System.out.println("Enter Store Name: ");
                                            storeName = scanner.nextLine();
                                            Store store = Seller.whichStore(storeName);
                                            System.out.println("Enter Product to Put on Sale: ");
                                            input = scanner.nextLine();
                                            System.out.println("Enter Sale price: ");
                                            double salePrice = scanner.nextDouble();
                                            scanner.nextLine();
                                            System.out.println("Enter Quantity on sale: ");
                                            int numOnSale = scanner.nextInt();
                                            scanner.nextLine();
                                            if (store.triggerSale(input, salePrice, numOnSale)) {
                                                System.out.println("Sale Successfully Started!");
                                            } else {
                                                System.out.println("Failed to start Sale!");
                                            }
                                            break;
                                        // put a purchase cap on a product
                                        case "7":
                                            System.out.println("Enter Store Name: ");
                                            storeName = scanner.nextLine();
                                            store = Seller.whichStore(storeName);
                                            System.out.println("Enter a product to restrict: ");
                                            input = scanner.nextLine();
                                            System.out.println("Enter Sales cap ");
                                            int cap = scanner.nextInt();
                                            scanner.nextLine();
                                            if (store.triggerOrderCap(input, cap)) {
                                                System.out.println("Order Cap created successfully!");
                                            } else {
                                                System.out.println("There was a problem implementing an order cap");
                                            }
                                            break;
                                        // back
                                        case "8":
                                            break;
                                        // handles anything else
                                        default:
                                            System.out.println("Invalid Option");
                                            break;
                                    }
                                    break;
                                // Seller Stats menu
                                case "3":
                                    System.out.print(sellerStatisticsChoices);
                                    input = scanner.nextLine();
                                    switch (input) {
                                        //View Customer Purchases
                                        case "1":
                                            System.out.println("Enter a store name: ");
                                            storeName = scanner.nextLine();
                                            System.out.println("Input Username: ");
                                            user = scanner.nextLine();
                                            System.out.println("Do you want to Sort the Products? (Y/N)");
                                            isSorted = scanner.nextLine();
                                            if (isSorted.equalsIgnoreCase("y")) {
                                                sorted = true;
                                            } else if (isSorted.equalsIgnoreCase("n")) {
                                                sorted = false;
                                            } else {
                                                System.out.println("Invalid Input");
                                                break;
                                            }
                                            System.out.println(Seller.getCustomersAndPurchases(storeName, user, sorted));
                                            break;
                                        //View Product Sales
                                        case "2":
                                            System.out.println("Enter a store name: ");
                                            storeName = scanner.nextLine();
                                            System.out.println("Input Username: ");
                                            user = scanner.nextLine();
                                            System.out.println("Do you want to Sort the Products? (Y/N)");
                                            isSorted = scanner.nextLine();
                                            if (isSorted.equalsIgnoreCase("y")) {
                                                sorted = true;
                                            } else if (isSorted.equalsIgnoreCase("n")) {
                                                sorted = false;
                                            } else {
                                                System.out.println("Invalid Input");
                                                break;
                                            }
                                            System.out.println(Seller.getProductSales(storeName, user, sorted));
                                            break;
                                        //View Products in Shopping Cart
                                        case "3":
                                            System.out.println("Enter Username: ");
                                            user = scanner.nextLine();
                                            System.out.println(Seller.getShoppingCartProducts(user));
                                            break;
                                        //Get a CSV with all the Products in a Store
                                        case "4":
                                            System.out.println("Enter Store Name: ");
                                            storeName = scanner.nextLine();
                                            System.out.println("Enter file path to be written to (include .txt)");
                                            String filePath = scanner.nextLine();
                                            boolean check = Seller.writeProductsToCSV(storeName, filePath);
                                            if (check) {
                                                System.out.println("Written to Successfully");
                                            } else {
                                                System.out.println("Failed");
                                            }
                                            break;
                                        //Goes back to the Main menu
                                        case "5":
                                            break;
                                        default:
                                            System.out.println("Invalid Input");
                                            break;
                                    }
                                    break;
                                //Delete Store
                                case "4":
                                    bool = false;
                                    while (!bool) {
                                        System.out.println("Enter a store name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Input Username: ");
                                        user = scanner.nextLine();
                                        bool = Seller.deleteStore(storeName, user);
                                        if (bool) {
                                            System.out.println("Successfully Deleted");
                                        } else {
                                            System.out.println("Deletion Failed");
                                            System.out.println("Try Again!");
                                        }
                                    }
                                    break;
                                //View Customer Reviews
                                case "5":
                                    System.out.println("Enter Product Name (Leave empty if you want to view reviews " +
                                            "of all products)");
                                    productName = scanner.nextLine();
                                    System.out.println(Seller.viewCustomerReviews(productName, user));
                                    break;
                                //Manage the Seller's account
                                case "6":
                                    System.out.print(AccountChoices);
                                    input = scanner.nextLine();
                                    switch (input) {
                                        //Change Username
                                        case "1":
                                            bool = false;
                                            while(!bool) {
                                                System.out.println("Enter Old Username");
                                                String oldUsername = scanner.nextLine();
                                                System.out.println("Enter New Username");
                                                String newUsername = scanner.nextLine();
                                                System.out.println("Enter New Username Again");
                                                String newUsernameCheck = scanner.nextLine();
                                                while (!(newUsername.equals(newUsernameCheck))) {
                                                    System.out.println("Errors, new usernames do not match");
                                                    System.out.println("Enter New Username");
                                                    newUsername = scanner.nextLine();
                                                    System.out.println("Enter New Username Again");
                                                    newUsernameCheck = scanner.nextLine();
                                                }
                                                bool = Account.changeUsername(newUsername, oldUsername);
                                                if (bool) {
                                                    System.out.println("Successfully Changed Username");
                                                } else {
                                                    System.out.println("Change Failed");
                                                    System.out.println("Try Again!");
                                                }
                                            }
                                            break;
                                        //Change Password
                                        case "2":
                                            bool = false;
                                            while (!bool) {
                                                System.out.println("Input Username or Email: ");
                                                user = scanner.nextLine();
                                                System.out.println("Enter Old Password: ");
                                                String oldPassword = scanner.nextLine();
                                                System.out.println("Enter New Password: ");
                                                String newPassword = scanner.nextLine();
                                                System.out.println("Enter New Password Again");
                                                String newPasswordCheck = scanner.nextLine();
                                                while(!(newPassword.equals(newPasswordCheck))){
                                                    System.out.println("Error, new passwords do not match");
                                                    System.out.println("Enter New Password: ");
                                                    newPassword = scanner.nextLine();
                                                    System.out.println("Enter New Password Again");
                                                    newPasswordCheck = scanner.nextLine();
                                                }
                                                bool = Account.changePassword(user, oldPassword, newPassword);
                                                if (bool) {
                                                    System.out.println("Successfully Changed Password");
                                                } else {
                                                    System.out.println("Change Failed");
                                                    System.out.println("Try Again!");
                                                }
                                            }
                                            break;
                                        //Change Role
                                        case "3":
                                            bool = false;
                                            while (!bool) {
                                                System.out.println("Input Username or Email: ");
                                                user = scanner.nextLine();
                                                System.out.println("Enter Password: ");
                                                password = scanner.nextLine();
                                                System.out.println("Enter New Role: ");
                                                String newRole = scanner.nextLine();
                                                bool = Account.changeRole(user, password, newRole);
                                                if (bool) {
                                                    System.out.println("Successfully Changed Role");
                                                } else {
                                                    System.out.println("Change Failed");
                                                    System.out.println("Try Again!");
                                                }
                                            }
                                            break;
                                        //Delete Account
                                        case "4":
                                            bool = false;
                                            while (!bool) {
                                                System.out.println("Input Username or Email: ");
                                                user = scanner.nextLine();
                                                System.out.println("Enter Password: ");
                                                password = scanner.nextLine();
                                                bool = Account.deleteAccount(user, password);
                                                if (bool) {
                                                    System.out.println("Successfully Deleted");
                                                } else {
                                                    System.out.println("Deletion Failed");
                                                    System.out.println("Try Again!");
                                                }
                                            }
                                            break;
                                        case "5":
                                            break;
                                        default:
                                            System.out.println("Invalid Input");
                                            break;
                                    }
                                    break;
                                //Logout
                                case "7":
                                    isLoggedIn = false;
                                    System.out.println("Successfully Logged out");
                                    break;
                                default:
                                    System.out.println("Invalid Input");
                                    break;
                            }
                            // Customer Experience
                        } else if (Account.getRole(user).equalsIgnoreCase("customer")) {
                            System.out.println(BUYERPROMPT);
                            input = scanner.nextLine();
                            switch (input) {
                                //Search for a Store
                                case "1":
                                    System.out.println(SEARCH_PROMPT);
                                    input = scanner.nextLine();
                                    System.out.println(Seller.searchByStore(input));
                                    break;
                                //Search for a Product
                                case "2":
                                    System.out.println(SEARCH_PROMPT);
                                    input = scanner.nextLine();
                                    System.out.println(Seller.searchByProduct(input));
                                    break;
                                //Search for a Product by Description
                                case "3":
                                    System.out.println(SEARCH_PROMPT);
                                    input = scanner.nextLine();
                                    System.out.println(Seller.searchByDescription(input));
                                    break;
                                //View All Products
                                case "4":
                                    Seller.printProductAndStores();
                                    break;
                                //Sort Products by Cheapest
                                case "5":
                                    System.out.println(Seller.sortCheapest());
                                    break;
                                //Sort Products by Most Expensive
                                case "6":
                                    System.out.println(Seller.sortExpensive());
                                    break;
                                //Sort Products by Availability
                                case "7":
                                    System.out.println(AVAILABILITY);
                                    input = scanner.nextLine();
                                    switch (input) {
                                        //Sort Products by Most in Stock
                                        case "1":
                                            System.out.println(Seller.highestQuant());
                                            break;
                                        //Sort Products By Least in stock
                                        case "2":
                                            System.out.println(Seller.lowestQuant());
                                            break;
                                    }
                                    break;
                                // shopping cart
                                case "8":
                                    System.out.println(customerShoppingCartChoices);
                                    input = scanner.nextLine();
                                    switch (input) {
                                        case "1": // add product to cart
                                            System.out.println("Enter the store name of the product " +
                                                    "you want to add to cart: ");
                                            storeName = scanner.nextLine();
                                            if (Customer.searchedStoreExists(storeName, stores)) {
                                                System.out.println("Enter the name of the product " +
                                                        "you want to add to cart: ");
                                                productName = scanner.nextLine();
                                                if (Customer.searchedProductExists(productName, stores)) {
                                                    System.out.printf("Enter how many %s would you like to buy: "
                                                            , productName);
                                                    quantity = scanner.nextInt();
                                                    scanner.nextLine();
                                                    Customer.addToCart(Account.getEmail(user), Account.getUsername(user),
                                                            storeName, productName, quantity);
                                                    System.out.println("Product added to cart");
                                                }
                                            }
                                            break;
                                        case "2": // remove product from cart
                                            System.out.println("Enter the store name of the product you want to " +
                                                    "remove from cart: ");
                                            storeName = scanner.nextLine();
                                            if (Customer.searchedStoreExists(storeName, stores)) {
                                                System.out.println("Enter the name of the product you want to " +
                                                        "remove from cart: ");
                                                productName = scanner.nextLine();
                                                if (Customer.searchedProductExists(productName, stores)) {
                                                    System.out.printf("Enter how many %s would you like to " +
                                                            "remove: ", productName);
                                                    quantity = scanner.nextInt();
                                                    scanner.nextLine();
                                                    boolean productRemoved = Customer.removeFromCart(Account.getEmail(user),
                                                            Account.getUsername(user), storeName, productName, quantity);
                                                    if (productRemoved) {
                                                        System.out.println("Product removed from cart");
                                                    } else {
                                                        System.out.println("Product is not in cart");
                                                    }
                                                }
                                            }
                                            break;
                                        case "3": // buy products in cart
                                            Customer.buyProductsInShoppingCart(Account.getUsername(user));
                                            System.out.println("Products in cart purchased successfully!");
                                            break;
                                        case "4": // view shopping cart
                                            for (String s : Customer.getShoppingCartofCustomer(user)) {
                                                System.out.println(s);
                                            }
                                            break;
                                    }
                                    break;
                                //Leave Review
                                case "9":
                                    bool = false;
                                    int rating = 5;
                                    System.out.println("Name of Product reviewing: ");
                                    productName = scanner.nextLine();
                                    System.out.println("Name of Store Product is from: ");
                                    storeName = scanner.nextLine();
                                    System.out.println("Enter a rating between 1-5");
                                    while(!bool) {
                                        try {
                                            rating = Integer.parseInt(scanner.nextLine());
                                            bool = true;
                                        } catch (NumberFormatException e) {
                                            System.out.println("Error, Invalid Input");
                                            System.out.println("Enter a rating between 1-5");
                                        }
                                    }
                                    System.out.println("Review: ");
                                    String description = scanner.nextLine();
                                    boolean check = Customer.leaveReview(storeName, productName, user, rating,
                                            description);
                                    if(check){
                                        System.out.println("Review Left Successfully");
                                    } else if(!check){
                                        System.out.println("Failed");
                                    }
                                    break;
                                //View Reviews of Products
                                case "10":
                                    System.out.println("Enter Products Name to view Review");
                                    productName = scanner.nextLine();
                                    System.out.println("Enter Store Name (Leave Blank to view all stores selling " +
                                            "that product)");
                                    storeName = scanner.nextLine();
                                    System.out.println(Customer.viewReviews(storeName, productName));
                                    break;
                                //Manage Customer Account
                                case "11":
                                    System.out.print(AccountChoices);
                                    input = scanner.nextLine();
                                    switch (input) {
                                        //Change Username
                                        case "1":
                                            bool = false;
                                            while(!bool) {
                                                System.out.println("Enter Old Username");
                                                String oldUsername = scanner.nextLine();
                                                System.out.println("Enter New Username");
                                                String newUsername = scanner.nextLine();
                                                System.out.println("Enter New Username Again");
                                                String newUsernameCheck = scanner.nextLine();
                                                while (!(newUsername.equals(newUsernameCheck))) {
                                                    System.out.println("Errors, new usernames do not match");
                                                    System.out.println("Enter New Username");
                                                    newUsername = scanner.nextLine();
                                                    System.out.println("Enter New Username Again");
                                                    newUsernameCheck = scanner.nextLine();
                                                }
                                                bool = Account.changeUsername(newUsername, oldUsername);
                                                if (bool) {
                                                    System.out.println("Successfully Changed Username");
                                                } else {
                                                    System.out.println("Change Failed");
                                                    System.out.println("Try Again!");
                                                }
                                            }
                                            break;
                                        //Change Password
                                        case "2":
                                            bool = false;
                                            while (!bool) {
                                                System.out.println("Input Username or Email: ");
                                                user = scanner.nextLine();
                                                System.out.println("Enter Old Password: ");
                                                String oldPassword = scanner.nextLine();
                                                System.out.println("Enter New Password: ");
                                                String newPassword = scanner.nextLine();
                                                System.out.println("Enter New Password Again");
                                                String newPasswordCheck = scanner.nextLine();
                                                while(!(newPassword.equals(newPasswordCheck))){
                                                    System.out.println("Error, new passwords do not match");
                                                    System.out.println("Enter New Password: ");
                                                    newPassword = scanner.nextLine();
                                                    System.out.println("Enter New Password Again");
                                                    newPasswordCheck = scanner.nextLine();
                                                }
                                                bool = Account.changePassword(user, oldPassword, newPassword);
                                                if (bool) {
                                                    System.out.println("Successfully Changed Password");
                                                } else {
                                                    System.out.println("Change Failed");
                                                    System.out.println("Try Again!");
                                                }
                                            }
                                            break;
                                        //Change Role
                                        case "3":
                                            bool = false;
                                            while (!bool) {
                                                System.out.println("Input Username or Email: ");
                                                user = scanner.nextLine();
                                                System.out.println("Enter Password: ");
                                                password = scanner.nextLine();
                                                System.out.println("Enter New Role: ");
                                                String newRole = scanner.nextLine();
                                                bool = Account.changeRole(user, password, newRole);
                                                if (bool) {
                                                    System.out.println("Successfully Changed");
                                                } else {
                                                    System.out.println("Change Failed");
                                                    System.out.println("Try Again!");
                                                }
                                            }
                                            break;
                                        //Delete Account
                                        case "4":
                                            bool = false;
                                            while (!bool) {
                                                System.out.println("Input Username or Email: ");
                                                user = scanner.nextLine();
                                                System.out.println("Enter Password: ");
                                                password = scanner.nextLine();
                                                bool = Account.deleteAccount(user, password);
                                                if (bool) {
                                                    System.out.println("Successfully Deleted");
                                                } else {
                                                    System.out.println("Deletion Failed");
                                                    System.out.println("Try Again!");
                                                }
                                            }
                                            break;
                                        case "5":
                                            break;
                                        default:
                                            System.out.println("Invalid Input");
                                            break;
                                    }
                                    break;
                                //Logout
                                case "12":
                                    isLoggedIn = false;
                                    System.out.println("Sucessfully Logged out");
                                    break;
                                default:
                                    System.out.println("Invalid Input");
                                    break;
                            }
                        }
                    }
                    break;
                //Handles User Registration
                case "register":
                    System.out.println("Enter an email: ");
                    String email = scanner.nextLine();
                    System.out.println("Input Username: ");
                    user = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    password = scanner.nextLine();
                    System.out.println("Enter your role (customer / seller");
                    String role = scanner.nextLine();
                    while(!(role.equalsIgnoreCase("customer") ||
                            role.equalsIgnoreCase("seller"))) {
                        System.out.println("Enter your role (customer / seller");
                        role = scanner.nextLine();
                    }
                    Account.createAccount(email, user, password, role);
                    break;

                default:
                    System.out.println("Invalid Input");
            }
        } while (true);
    }


}
