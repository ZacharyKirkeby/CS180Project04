package src;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class MarketPlace {
    private static final String WELCOMEPROMPT = "Login or Register an Account (Login / Register)";
    private static final String sellerChoices = " 1. Create Store \n 2. Modify Store " +
            "\n 3. View Store Statistics \n 4. Delete Store \n 5. Manage Account \n 6. Logout \n";
    private static final String sellerModificationChoices = " 1. Create Product \n 2. Change Product Price \n " +
            "3. Change Product Quantity \n 4. Delete Product \n 5. Add products to Store from CSV \n 6. Back \n";
    private static final String manageAccountChoices = " 1. Change Password \n 2. Change Role \n 3. Delete Account \n";
    private static final String sellerStatisticsChoices = " 1. View Customer Purchases \n 2. View Product Sales \n " +
            "3. View Products in Shopping Cart \n 4. View Products in Store as CSV file \n";
    private static final String BUYERPROMPT = " 1. Search for a store \n 2. Search for a product \n" +
            "3. View All Products \n 4. View Shopping Cart \n 5. Add Product To Cart \n 6. Remove Product From Cart \n 7. Buy Products In Cart \n 8. View Purchase History \n 9. Manage Account \n";
    private static final String SEARCHPROMPT = "Enter search term: ";
    static ArrayList<Store> stores;
    private static boolean isLoggedIn;

    public MarketPlace(ArrayList<Store> stores) {
        // some kind of logic tbd
        this.stores = stores;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(WELCOMEPROMPT);
        String input = scanner.nextLine().toLowerCase();
        switch (input) {
            case "login":
                System.out.println("Input Username or Email: ");
                String user = scanner.nextLine();
                System.out.println("Enter your password: ");
                String password = scanner.nextLine();
                Account.login(user, password);
                isLoggedIn = true;
                while (isLoggedIn) {

                    if (Account.getRole(user).equalsIgnoreCase("seller")) {
                        System.out.print(sellerChoices);
                        input = scanner.nextLine();
                        switch (input) {
                            case "1":
                                System.out.println("Enter a store name: ");
                                String storeName = scanner.nextLine();
                                System.out.println("Enter a store location: ");
                                String location = scanner.nextLine();
                                Seller.createStore(storeName, location, user);
                                break;
                            case "2":
                                System.out.print(sellerModificationChoices);
                                input = scanner.nextLine();
                                switch (input) {
                                    case "1":
                                        System.out.println("Enter Store Name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter Product Name: ");
                                        String productName = scanner.nextLine();
                                        System.out.println("Enter Product Price: ");
                                        double price = Double.parseDouble(scanner.nextLine());
                                        System.out.println("Enter Product Quantity: ");
                                        int quantity = Integer.parseInt(scanner.nextLine());
                                        Seller.createProduct(storeName, productName, price, quantity);
                                        break;
                                    case "2":
                                        System.out.println("Enter Store Name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter Product Name: ");
                                        productName = scanner.nextLine();
                                        System.out.println("Enter New Product Price: ");
                                        price = Double.parseDouble(scanner.nextLine());
                                        Seller.editProductPrice(storeName, productName, price);
                                        break;
                                    case "3":
                                        System.out.println("Enter Store Name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter Product Name: ");
                                        productName = scanner.nextLine();
                                        System.out.println("Enter New Quantity: ");
                                        quantity = Integer.parseInt(scanner.nextLine());
                                        Seller.editProductQuantity(storeName, productName, quantity);
                                        break;
                                    case "4":
                                        System.out.println("Enter Store Name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter Product Name: ");
                                        productName = scanner.nextLine();
                                        Seller.deleteProduct(storeName, productName);
                                        break;
                                    case "5":
                                        System.out.println("Enter Store Name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter file path to be written from (include .txt)");
                                        String filePath = scanner.nextLine();
                                        Seller.readProductsFromCSV(storeName, filePath);
                                    case "6":
                                        break;
                                    default:
                                        System.out.println("Invalid Option");
                                        break;
                                }
                                break;
                            case "3":
                                System.out.print(sellerStatisticsChoices);
                                input = scanner.nextLine();
                                switch (input) {
                                    case "1":
                                        System.out.println("Enter a store name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Input Username: ");
                                        user = scanner.nextLine();
                                        System.out.println("Do you want to Sort the Products? (Y/N)");
                                        String bool = scanner.nextLine();
                                        boolean sorted;
                                        if (bool.equalsIgnoreCase("y")) {
                                            sorted = true;
                                        } else if (bool.equalsIgnoreCase("n")) {
                                            sorted = false;
                                        } else {
                                            System.out.println("Invalid Input");
                                            break;
                                        }
                                        Seller.getCustomersAndPurchases(storeName, user, sorted);
                                        break;
                                    case "2":
                                        System.out.println("Enter a store name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Input Username: ");
                                        user = scanner.nextLine();
                                        System.out.println("Do you want to Sort the Products? (Y/N)");
                                        bool = scanner.nextLine();
                                        if (bool.equalsIgnoreCase("y")) {
                                            sorted = true;
                                        } else if (bool.equalsIgnoreCase("n")) {
                                            sorted = false;
                                        } else {
                                            System.out.println("Invalid Input");
                                            break;
                                        }
                                        Seller.getProductSales(storeName, user, sorted);
                                        break;
                                    case "3":
                                        System.out.println("Enter Username: ");
                                        user = scanner.nextLine();
                                        Seller.getShoppingCartProducts(user);
                                        break;
                                    case "4":
                                        System.out.println("Enter Store Name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter file path to be written to (include .txt)");
                                        String filePath = scanner.nextLine();
                                        boolean check = Seller.writeProductsToCSV(storeName, filePath);
                                        if (check) {
                                            System.out.println("Written to Successfully");
                                        } else if (!check) {
                                            System.out.println("Failed");
                                        }
                                        break;
                                    default:
                                        System.out.println("Invalid Input");
                                        break;
                                }
                                break;
                            case "4":
                                System.out.println("Enter a store name: ");
                                storeName = scanner.nextLine();
                                System.out.println("Input Username: ");
                                user = scanner.nextLine();
                                Seller.deleteStore(storeName, user);
                                break;
                            case "5":
                                System.out.print(manageAccountChoices);
                                input = scanner.nextLine();
                                switch (input) {
                                    case "1":
                                        System.out.println("Input Username or Email: ");
                                        user = scanner.nextLine();
                                        System.out.println("Enter Old Password: ");
                                        String oldPassword = scanner.nextLine();
                                        System.out.println("Enter New Password: ");
                                        String newPassword = scanner.nextLine();
                                        Account.changePassword(user, oldPassword, newPassword);
                                        break;
                                    case "2":
                                        System.out.println("Input Username or Email: ");
                                        user = scanner.nextLine();
                                        System.out.println("Enter Password: ");
                                        password = scanner.nextLine();
                                        System.out.println("Enter New Role: ");
                                        String newRole = scanner.nextLine();
                                        Account.changeRole(user, password, newRole);
                                        break;
                                    case "3":
                                        System.out.println("Input Username or Email: ");
                                        user = scanner.nextLine();
                                        System.out.println("Enter Password: ");
                                        password = scanner.nextLine();
                                        Account.deleteAccount(user, password);
                                        break;
                                    default:
                                        System.out.println("Invalid Input");
                                        break;
                                }
                            case "6":
                                isLoggedIn = false;
                                System.out.println("Sucessfully Logged out");
                                break;
                            default:
                                System.out.println("Invalid Input");
                                break;
                        }

                    } else if (Account.getRole(user).equals("customer")) {
                        System.out.println(BUYERPROMPT);
                        input = scanner.nextLine();
                        switch (input) {
                            case "1": // search for store
                                System.out.println(SEARCHPROMPT);
                                input = scanner.nextLine();
                                Store storeSearched = Customer.searchedStoreExists(input, stores);
                                if (storeSearched != null) {
                                    System.out.println(storeSearched);
                                }
                                break;
                            case "2": // search for product
                                System.out.println(SEARCHPROMPT);
                                input = scanner.nextLine();
                                Product productSearched = Customer.searchedProductExists(input, stores);
                                if (productSearched != null) {
                                    System.out.println(productSearched);
                                }
                                break;
                            case "3": // view all products
                                for (Store store : stores) {
                                    System.out.println(store.getProductList());
                                }
                                break;
                            case "4": // view shopping cart in a file
                                System.out.println("Enter file path to be written to (include .txt)");
                                String filePath = scanner.nextLine();
                                boolean check = Customer.getFile(filePath, Objects.requireNonNull(Customer.readShoppingCartFile()));
                                if (check) {
                                    System.out.println("Written to Successfully");
                                } else if (!check) {
                                    System.out.println("Failed");
                                }
                                break;
                            case "5": // Add Product To Cart
                                System.out.println("Enter the store name of the product you want to add to cart: ");
                                String storeName = scanner.nextLine();
                                boolean productAvailable;
                                for (Store s : stores) {
                                    if (s.getStoreName().equals(storeName)) {
                                        System.out.println("Enter the name of the product you want to add to cart: ");
                                        String productName = scanner.nextLine();
                                        for (Product product : s.getProductList()) {
                                            if (product.getName().equals(productName)) {
                                                System.out.printf("Enter the quantity of %s you want to buy%n", productName);
                                                int productQuantity = scanner.nextInt();
                                                scanner.nextLine();
                                                if (product.getStockQuantity() > productQuantity) {
                                                    Customer.addToCart(product, productQuantity);
                                                    Customer.writeShoppingCartFileAddProduct(product.getName(), productQuantity);
                                                    System.out.printf("%d %s added to cart%n", productQuantity, product.getName());
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            case "6":
                                //\n 6. Remove Product From Cart
                                System.out.println("Enter the store name of the product you want to remove from the cart: ");
                                storeName = scanner.nextLine();
                                for (Store s : stores) {
                                    if (s.getStoreName().equals(storeName)) {
                                        System.out.println("Enter the name of the product you want to remove from the cart: ");
                                        String productName = scanner.nextLine();
                                        for (Product product : s.getProductList()) {
                                            if (product.getName().equals(productName)) {
                                                System.out.printf("Enter the quantity of %s you want to remove from the cart: ", productName);
                                                int productQuantity = scanner.nextInt();
                                                scanner.nextLine();
                                                if (product.getStockQuantity() > productQuantity) {
                                                    Customer.removeFromToCart(product, productQuantity);
                                                    Customer.writeShoppingCartFileRemoveProduct(productName, productQuantity);
                                                    System.out.printf("%d %s removed from cart", productQuantity, product.getName());
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            case "7": // Buy Products In Cart
                                Customer.buyProductsInShoppingCart(10); // temporary max product quantity customer can buy threshold
                                break;
                            case "8": // view purchase history in a file
                                System.out.println("Enter file path to be written to (include .txt)");
                                filePath = scanner.nextLine();
                                check = Customer.getFile(filePath, Objects.requireNonNull(Customer.readPurchaseHistoryFile()));
                                if (check) {
                                    System.out.println("Written to Successfully");
                                } else if (!check) {
                                    System.out.println("Failed");
                                }
                                break;
                            case "9":
                                System.out.print(manageAccountChoices);
                                input = scanner.nextLine();
                                switch (input) {
                                    case "1":
                                        System.out.println("Input Username or Email: ");
                                        user = scanner.nextLine();
                                        System.out.println("Enter Old Password: ");
                                        String oldPassword = scanner.nextLine();
                                        System.out.println("Enter New Password: ");
                                        String newPassword = scanner.nextLine();
                                        Account.changePassword(user, oldPassword, newPassword);
                                        break;
                                    case "2":
                                        System.out.println("Input Username or Email: ");
                                        user = scanner.nextLine();
                                        System.out.println("Enter Password: ");
                                        password = scanner.nextLine();
                                        System.out.println("Enter New Role: ");
                                        String newRole = scanner.nextLine();
                                        Account.changeRole(user, password, newRole);
                                        break;
                                    case "3":
                                        System.out.println("Input Username or Email: ");
                                        user = scanner.nextLine();
                                        System.out.println("Enter Password: ");
                                        password = scanner.nextLine();
                                        Account.deleteAccount(user, password);
                                        break;
                                    default:
                                        System.out.println("Invalid Input");
                                        break;
                                }
                        }


                    }
                }
                break;
            case "Register":
                System.out.println("Enter an email: ");
                String email = scanner.nextLine();
                System.out.println("Input Username: ");
                user = scanner.nextLine();
                System.out.println("Enter your password: ");
                password = scanner.nextLine();
                System.out.println("Enter your role (customer / seller");
                String role = scanner.nextLine();
                Account.createAccount(email, user, password, role);
                break;
            default:
                System.out.println("Invalid Input");
        }


    }

}
