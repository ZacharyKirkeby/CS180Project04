package src;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 04 -- MarketPlace.java
 * creates a Market to manage interactions and listings in a marketplace
 * Handles all MarketPlace related tasks
 * and functions.
 * Acts as the main interface of all the classes
 *
 * @author Armaan Sayyad, 05
 * @author Zachary Kirkeby, 05
 * @version November 10, 2023
 */
public class MarketPlace {
    private static String welcomePrompt = "Welcome to the Fruit Market!"; //
    private static String loginPrompt = "Would you like to Login or Register an Account? (Login / Register / " +
                                        "Exit)"; //
    private static String sellerChoices = " 1. Create Store \n" +
                                          " 2. Modify Store \n" +
                                          " 3. View Store Statistics \n" +
                                          " 4. Delete Store \n" +
                                          " 5. View Customer Reviews \n" +
                                          " 6. Manage Account \n" +
                                          " 7. Logout \n"; //
    private static String sellerModificationChoices = " 1. Create Product \n" +
                                                      " 2. Change Product Price \n" +
                                                      " 3. Change Product Quantity \n" +
                                                      " 4. Delete Product \n" +
                                                      " 5. Add products to Store from CSV \n" +
                                                      " 6. Start Sale \n" +
                                                      " 7. Add Purchase Limit \n" +
                                                      " 8. Back \n"; //
    private static String accountChoices = " 1. Change Username \n" +
                                           " 2. Change Password \n" +
                                           " 3. Change Role \n" +
                                           " 4. Delete Account \n" +
                                           " 5. Back \n"; //
    private static String sellerStatisticsChoices = " 1. View Customer Purchases \n" +
                                                    " 2. View Product Sales \n" +
                                                    " 3. View Products in Shopping Cart \n" +
                                                    " 4. View Products in Store as CSV file \n" +
                                                    " 5. Back \n"; //
    private static String buyerprompt = " 1.  Search for a store \n" +
                                        " 2.  Search for a product \n" +
                                        " 3.  Search Product by Description \n" +
                                        " 4.  View All Products \n" +
                                        " 5.  Sort Products By Cheapest \n" +
                                        " 6.  Sort  Products By Most Expensive \n" +
                                        " 7.  Sort by Availability \n" +
                                        " 8.  Shopping Cart \n" +
                                        " 9.  Export Purchase History as file \n" +
                                        " 10. Leave Review \n" +
                                        " 11. View Product Reviews \n" +
                                        " 12. Manage Account \n" +
                                        " 13. Logout \n"; //
    private static String customerShoppingCartChoices = " 1. Add product(s) to cart \n" +
                                                        " 2. Change Quantity of Product in Cart \n" +
                                                        " 3. Remove product(s) from cart \n" +
                                                        " 4. Buy products in cart \n" +
                                                        " 5. View shopping cart \n" +
                                                        " 6. Back\n"; //
    private static String availability = "1. Sort By Highest Stock \n" +
                                         "2. Sort By Low On Stock \n"; //
    private static String searchPrompt = "Enter search term: "; //
    private static ArrayList<Store> stores = new ArrayList<>(); //
    private static boolean isLoggedIn; //

    /**
     * Constructors for the MarketPlace
     *
     * @param stores (ArrayList<Stores></>)
     */
    public MarketPlace(ArrayList<Store> stores) {
        this.stores = stores;
        // some kind of logic tbd
    }

    /**
     * Getter for the stores in the MarketPlace
     *
     * @return stores
     */
    public static ArrayList<Store> getStores() {
        return stores;
    }

    /**
     * Main method for the Marketplace handles all the interactions
     * and interfaces between the classes
     *
     * @param args (String[])
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); //instantiates a scanner object to read terminal inputs
        do {
            boolean logOrRegistration = false;
            while (!logOrRegistration) {
                System.out.println(welcomePrompt);
                System.out.println(loginPrompt);
                String input = scanner.nextLine().toLowerCase();
                isLoggedIn = false;
                switch (input) { // handles user case of login or register and directs to the next relevant step
                    // Authenticates User, begins use loop
                    case "login":
                        System.out.println("Input Username or Email: ");
                        String user = scanner.nextLine();
                        System.out.println("Enter your password: ");
                        String password = scanner.nextLine();
                        boolean bool = Account.login(user, password);
                        logOrRegistration = Account.login(user, password);
                        if (!logOrRegistration) {
                            System.out.println("Login Failed");
                            System.out.println("Try Again!");
                        } else if (logOrRegistration) {
                            isLoggedIn = true;
                            System.out.println("Login Successful!");
                        }

                        // Switch Var declarations
                        String storeName;
                        String productName;
                        double price;
                        int quantity;
                        boolean sorted;
                        String isSorted;
                        boolean isDouble = false;
                        boolean isInt = false;

                        // loops while definite user
                        while (isLoggedIn) {
                            //Seller Experience
                            if (Account.getRole(user) != null
                                && Account.getRole(user).equalsIgnoreCase("seller")) {
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
                                            System.out.println("Successfully Created");
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

                                                price = -0.1;
                                                isDouble = false;
                                                while (!isDouble) {
                                                    try {
                                                        price = Double.parseDouble(scanner.nextLine());
                                                        isDouble = true;
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Invalid Price!");
                                                        System.out.println("Enter Product Price: ");
                                                    }
                                                }

                                                System.out.println("Enter Product Quantity: ");
                                                quantity = -1;
                                                isInt = false;
                                                while (!isInt) {
                                                    try {
                                                        quantity = Integer.parseInt(scanner.nextLine());
                                                        if (quantity > 0) {
                                                            isInt = true;
                                                        } else {
                                                            System.out.println("Invalid Quantity!");
                                                            System.out.println("Enter Product Quantity: ");
                                                        }
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Invalid Quantity!");
                                                        System.out.println("Enter Product Quantity: ");
                                                    }

                                                }
                                                System.out.println("Enter Product Description: ");
                                                String description = scanner.nextLine();
                                                bool = Seller.createProduct(storeName, productName, description,
                                                        price, quantity, user);
                                                if (bool) {
                                                    System.out.println("Successfully Added Product");
                                                } else {
                                                    System.out.println("Addition Failed");
                                                }
                                                break;
                                            // Edit Product Price
                                            case "2":
                                                System.out.println("Enter Store Name: ");
                                                storeName = scanner.nextLine();
                                                System.out.println("Enter Product Name: ");
                                                productName = scanner.nextLine();
                                                System.out.println("Enter New Product Price: ");
                                                price = -0.1;
                                                isDouble = false;
                                                while (!isDouble) {
                                                    try {
                                                        price = Double.parseDouble(scanner.nextLine());
                                                        isDouble = true;
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Invalid Price!");
                                                        System.out.println("Enter Product Price: ");
                                                    }
                                                }
                                                bool = Seller.editProductPrice(storeName,
                                                        productName, price, user);
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
                                                quantity = -1;
                                                isInt = false;
                                                while (!isInt) {
                                                    try {
                                                        quantity = Integer.parseInt(scanner.nextLine());
                                                        isInt = true;
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Error, Invalid Input");
                                                        System.out.println("Enter New Quantity: ");
                                                    }
                                                }
                                                bool = Seller.editProductQuantity(storeName, productName,
                                                        quantity, user);
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
                                                System.out.println("Enter file path to be written from " +
                                                                   "(include .csv)");
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
                                                isDouble = false;
                                                double salePrice = -0.1;
                                                while (!isDouble) {
                                                    try {
                                                        salePrice = Double.parseDouble(scanner.nextLine());
                                                        isDouble = true;
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Invalid Sale price!");
                                                        System.out.println("Enter Sale price: ");
                                                    }
                                                }
                                                System.out.println("Enter Quantity on sale: ");
                                                int numOnSale = -1;
                                                isInt = false;
                                                while (!isInt) {
                                                    try {
                                                        numOnSale = Integer.parseInt(scanner.nextLine());
                                                        isInt = true;
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Error, Invalid Input");
                                                        System.out.println("Enter Quantity on sale: ");
                                                    }
                                                }
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
                                                System.out.println("Enter Sales cap: ");
                                                int cap = -1;
                                                isInt = false;
                                                while (!isInt) {
                                                    try {
                                                        cap = Integer.parseInt(scanner.nextLine());
                                                        isInt = true;
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Error, Invalid Input");
                                                        System.out.println("Enter Sales cap: ");
                                                    }
                                                }
                                                scanner.nextLine();
                                                if (store.triggerOrderCap(input, cap)) {
                                                    System.out.println("Order Cap created successfully!");
                                                } else {
                                                    System.out.println("There was a problem " +
                                                                       "implementing an order cap");
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
                                                System.out.println(Seller.getCustomersAndPurchases(storeName,
                                                        user, sorted));
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
                                                if (Seller.getProductSales(storeName, user, sorted).equals(
                                                        "Customer " +
                                                        "Email | Customer Username | " +
                                                        "Store Name | Product Name | " +
                                                        "Quantity Purchased ")) {
                                                    System.out.println("No Product Sales Found!");
                                                } else {
                                                    System.out.println(Seller.getProductSales(storeName,
                                                            user, sorted));
                                                }

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
                                                System.out.println("Enter file path to be written to (include .csv)");
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
                                        System.out.println("Enter Product Name (Leave empty if you want to " +
                                                           "view reviews of all products)");
                                        productName = scanner.nextLine();
                                        if (Seller.viewCustomerReviews(productName, user).equals("Store Name | " +
                                                                                                 "Product Name | " +
                                                                                                 "Customer Name | " +
                                                                                                 "Rating ")) {
                                            System.out.println("Could not find any reviews!");
                                        } else {
                                            System.out.print(Seller.viewCustomerReviews(productName, user));
                                        }
                                        break;
                                    //Manage the Seller's account
                                    case "6":
                                        System.out.print(accountChoices);
                                        input = scanner.nextLine();
                                        switch (input) {
                                            //Change Username
                                            case "1":
                                                bool = false;
                                                while (!bool) {
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
                                                    while (!(newPassword.equals(newPasswordCheck))) {
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
                                                        isLoggedIn = false;
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
                            } else if (Account.getRole(user) != null
                                       && Account.getRole(user).equalsIgnoreCase("customer")) {
                                System.out.print(buyerprompt);
                                input = scanner.nextLine();
                                switch (input) {
                                    //Search for a Store
                                    case "1":
                                        System.out.println(searchPrompt);
                                        input = scanner.nextLine();
                                        System.out.println(Seller.searchByStore(input));
                                        break;
                                    //Search for a Product
                                    case "2":
                                        System.out.println(searchPrompt);
                                        input = scanner.nextLine();
                                        System.out.println(Seller.searchByProduct(input));
                                        break;
                                    //Search for a Product by Description
                                    case "3":
                                        System.out.println(searchPrompt);
                                        input = scanner.nextLine();
                                        System.out.println(Seller.searchByDescription(input));
                                        break;
                                    //View All Products
                                    case "4":
                                        Seller.printProductAndStores();
                                        System.out.println("Which Product would you like to view?");
                                        input = scanner.nextLine();
                                        String result = Seller.searchByProduct(input);
                                        if (result.isEmpty() || result == null) {
                                            System.out.println("No Products Available for Listing");
                                        } else {
                                            System.out.println(result);
                                        }
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
                                        System.out.println(availability);
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
                                        System.out.print(customerShoppingCartChoices);
                                        input = scanner.nextLine();
                                        switch (input) {
                                            case "1": // add product to cart
                                                System.out.println("Enter the store name of the product " +
                                                                   "you want to add to cart: ");
                                                storeName = scanner.nextLine();
                                                if (Customer.searchedStoreExists(storeName, stores)) {
                                                    bool = false;
                                                    System.out.println("Enter the name of the product " +
                                                                       "you want to add to cart: ");
                                                    productName = scanner.nextLine();
                                                    if (Customer.searchedProductExists(productName, stores)) {
                                                        quantity = -1;
                                                        System.out.printf("Enter how many %s would you " +
                                                                          "like to buy: \n"
                                                                , productName);
                                                        isInt = false;
                                                        while (!isInt) {
                                                            try {
                                                                quantity = Integer.parseInt(scanner.nextLine());
                                                                isInt = true;
                                                            } catch (NumberFormatException e) {
                                                                System.out.println("Error, Invalid Input");
                                                                System.out.printf("Enter how many %s would " +
                                                                                  "you like to buy: \n", productName);
                                                            }
                                                        }
                                                        bool = Customer.addToCart(Account.getEmail(user),
                                                                Account.getUsername(user),
                                                                storeName, productName, quantity);
                                                    }
                                                    if (bool) {
                                                        System.out.println("Product added to cart!");
                                                    } else if (!bool) {
                                                        System.out.println("Error, could not find Product!");
                                                    }
                                                } else {
                                                    System.out.println("Error, could not find store!");
                                                }

                                                break;
                                            case "2":
                                                System.out.println("Enter store of the product whose checkout " +
                                                                   "quantity you want to change");
                                                storeName = scanner.nextLine();
                                                System.out.println("Enter name of the product whose " +
                                                                   "quantity you want to change");
                                                productName = scanner.nextLine();
                                                System.out.println("Enter amount you want to change by (include " +
                                                                   "- if you want to reduce)");
                                                quantity = Integer.parseInt(scanner.nextLine());
                                                bool = Customer.addToCartChangeCheckoutQuantity(
                                                        storeName, productName, quantity);
                                                if (bool) {
                                                    System.out.println("Change Successful");
                                                } else if (!bool) {
                                                    System.out.println("Change Failed, Invalid Checkout Quantity!");
                                                }
                                                break;
                                            case "3": // remove product from cart
                                                System.out.println("Enter the store name of " +
                                                                   "the product you want to " +
                                                                   "remove from cart: ");
                                                storeName = scanner.nextLine();
                                                if (Customer.searchedStoreExists(storeName, stores)) {
                                                    System.out.println("Enter the name of the product " +
                                                                       "you want to " +
                                                                       "remove from cart: ");
                                                    productName = scanner.nextLine();
                                                    if (Customer.searchedProductExists(productName, stores)) {
                                                        System.out.printf("Enter how many %s would " +
                                                                          "you like to " +
                                                                          "remove: \n", productName);
                                                        quantity = -1;
                                                        isInt = false;
                                                        while (!isInt) {
                                                            try {
                                                                quantity = Integer.parseInt(scanner.nextLine());
                                                                isInt = true;
                                                            } catch (NumberFormatException e) {
                                                                System.out.println("Error, Invalid Input");
                                                                System.out.printf("Enter how many %s " +
                                                                                  "would you like " +
                                                                                  "to remove: \n"
                                                                        , productName);
                                                            }
                                                        }
                                                        boolean productRemoved = Customer.removeFromCart(
                                                                Account.getEmail(user),
                                                                Account.getUsername(user),
                                                                storeName, productName, quantity);
                                                        if (productRemoved) {
                                                            System.out.println("Product removed from cart");
                                                        } else {
                                                            System.out.println("Product is not in cart");
                                                        }
                                                    }
                                                }
                                                break;
                                            case "4": // buy products in cart
                                                bool = Customer.buyProductsInShoppingCart(Account.getUsername(user));
                                                ArrayList<String> check = Customer.getShoppingCartofCustomer(user);
                                                while (!check.isEmpty()) {
                                                    bool = Customer.buyProductsInShoppingCart(
                                                            Account.getUsername(user));
                                                    check = Customer.getShoppingCartofCustomer(user);
                                                }
                                                if (bool) {
                                                    System.out.println("Products in cart purchased successfully!");
                                                } else if (!bool) {
                                                    System.out.println("Checkout failed");
                                                }
                                                break;
                                            case "5": // view shopping cart
                                                for (String s : Customer.getShoppingCartofCustomer(user)) {
                                                    String[] view = s.split(";");
                                                    System.out.println("Customer Name | Store Name " +
                                                                       "| Product Name | Qty");
                                                    String output =
                                                            view[1] + " | " + view[2] +
                                                            " | " + view[3] + " | " + view[4];
                                                    System.out.println(output);
                                                }
                                                break;
                                            case "6":
                                                break;
                                            default:
                                                break;
                                        }
                                        break;
                                    case "9":
                                        System.out.println("Enter Filename to be exported to (include .txt)");
                                        String filename = scanner.nextLine();
                                        bool = Customer.getPurchaseHistoryofCustomer(user, filename);
                                        if (bool) {
                                            System.out.println("Exported Successfully!");
                                        } else if (!bool) {
                                            System.out.println("Export Failed");
                                        }
                                        break;
                                    //Leave Review
                                    case "10":
                                        bool = false;
                                        int rating = 5;
                                        System.out.println("Name of Product reviewing: ");
                                        productName = scanner.nextLine();
                                        System.out.println("Name of Store Product is from: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter a rating between 1-5");
                                        isInt = false;
                                        while (!isInt) {
                                            try {
                                                rating = Integer.parseInt(scanner.nextLine());
                                                isInt = true;
                                            } catch (NumberFormatException e) {
                                                System.out.println("Error, Invalid Input");
                                                System.out.println("Enter a rating between 1-5");
                                            }
                                        }
                                        System.out.println("Review: ");
                                        String description = scanner.nextLine();
                                        boolean check = Customer.leaveReview(storeName,
                                                productName, user, rating, description);
                                        if (check) {
                                            System.out.println("Review Left Successfully");
                                        } else if (!check) {
                                            System.out.println("Failed");
                                        }
                                        break;
                                    //View Reviews of Products
                                    case "11":
                                        System.out.println("Enter Products Name to view Review");
                                        productName = scanner.nextLine();
                                        System.out.println("Enter Store Name (Leave Blank " +
                                                           "to view all stores selling " +
                                                           "that product)");
                                        storeName = scanner.nextLine();
                                        System.out.print(Customer.viewReviews(storeName, productName));
                                        break;
                                    //Manage Customer Account
                                    case "12":
                                        System.out.print(accountChoices);
                                        input = scanner.nextLine();
                                        switch (input) {
                                            //Change Username
                                            case "1":
                                                bool = false;
                                                while (!bool) {
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
                                                    while (!(newPassword.equals(newPasswordCheck))) {
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
                                                        isLoggedIn = false;
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
                                    case "13":
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
                        System.out.println("Enter your role (customer / seller)");
                        String role = scanner.nextLine();
                        while (!(role.equalsIgnoreCase("customer") ||
                                 role.equalsIgnoreCase("seller"))) {
                            System.out.println("Invalid Input!");
                            System.out.println("Enter your role (customer / seller)");
                            role = scanner.nextLine();
                        }
                        logOrRegistration = Account.createAccount(email, user, password, role);
                        if (logOrRegistration) {
                            System.out.println("Account Made Successfully");
                        } else if (!logOrRegistration) {
                            System.out.println("Account Creation failed. Invalid Credentials");
                        }
                        break;
                    case ("exit"):
                        System.out.println("Thank You For Using Our Fruit Market!");
                        return;

                    default:
                        System.out.println("Invalid Input");
                }
            }
        } while (true);
    }


}
