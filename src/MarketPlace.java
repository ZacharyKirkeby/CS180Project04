package src;
import java.util.ArrayList;
import java.util.Scanner;

public class MarketPlace {
    private static final String WELCOMEPROMPT = "Login or Register an Account (Login / Register)";
    private static final String sellerChoices = " 1. Create Store \n 2. Modify Store " +
            "\n 3. View Store Statistics \n 4. Delete Store \n 5. Manage Account \n 6. Logout \n";
    private static final String sellerModificationChoices = " 1. Create Product \n 2. Change Product Price \n " +
            "3. Change Product Quantity \n 4. Change Product Description \n 5. Delete Product \n 6. Add products to " +
            "Store from CSV \n 7. Back \n";
    private static final String AccountChoices = " 1. Change Password \n 2. Change Role \n 3. Delete Account \n 4. " +
            "Back \n";
    private static final String sellerStatisticsChoices = " 1. View Customer Purchases \n 2. View Product Sales \n " +
            "3. View Products in Shopping Cart \n 4. View Products in Store as CSV file \n 5. Back \n";
    private static final String BUYERPROMPT = " 1. Search for a store \n 2. Search for a product \n" +
            "3. Search by Description \n 4. View All Products \n 5. Manage Account \n 6. Logout \n";
    private static final String SEARCHPROMPT = "Enter search term: ";
    private static ArrayList<Store> stores;
    private static boolean isLoggedIn;

    public MarketPlace(ArrayList<Store> stores) {
        this.stores = stores;
        // some kind of logic tbd
    }

    public static ArrayList<Store> getStores() {
        return stores;
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
                        boolean bool = false;
                        System.out.print(sellerChoices);
                        input = scanner.nextLine();
                        switch (input) {
                            case "1":
                                System.out.println("Enter a store name: ");
                                String storeName = scanner.nextLine();
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
                            case "2":
                                System.out.print(sellerModificationChoices);
                                input = scanner.nextLine();
                                switch (input){
                                    case "1":
                                        System.out.println("Enter Store Name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter Product Name: ");
                                        String productName = scanner.nextLine();
                                        System.out.println("Enter Product Description");
                                        String description = scanner.nextLine();
                                        System.out.println("Enter Product Price: ");
                                        double price = Double.parseDouble(scanner.nextLine());
                                        System.out.println("Enter Product Quantity: ");
                                        int quantity = Integer.parseInt(scanner.nextLine());
                                        bool = Seller.createProduct(storeName, productName, description, price,
                                                quantity, user);
                                        if (bool) {
                                            System.out.println("Successfully Deleted");
                                        } else {
                                            System.out.println("Deletion Failed");
                                        }
                                        break;
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
                                            System.out.println("Deletion Failed");
                                        }
                                        break;
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
                                    case "4":
                                        bool = false;
                                        while(!bool) {
                                            System.out.println("Enter Store Name: ");
                                            storeName = scanner.nextLine();
                                            System.out.println("Enter Product Name: ");
                                            productName = scanner.nextLine();
                                            System.out.println("Enter New Product Description: ");
                                            String newDescription = scanner.nextLine();
                                            bool = Seller.editProductDescription(storeName, productName,
                                                    newDescription, user);
                                            if (bool) {
                                                System.out.println("Successfully Deleted");
                                            } else {
                                                System.out.println("Deletion Failed");
                                                System.out.println("Try Again!");
                                            }
                                        }
                                        break;
                                    case "5":
                                        bool = false;
                                        while(!bool) {
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
                                    case "6":
                                        System.out.println("Enter Store Name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter file path to be written from (include .txt)");
                                        String filePath = scanner.nextLine();
                                        Seller.readProductsFromCSV(storeName, filePath);
                                    case "7":
                                        break;
                                    default:
                                        System.out.println("Invalid Option");
                                        break;
                                }
                                break;
                            case "3":
                                System.out.print(sellerStatisticsChoices);
                                input = scanner.nextLine();
                                switch (input){
                                    case "1":
                                        System.out.println("Enter a store name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Input Username: ");
                                        user = scanner.nextLine();
                                        System.out.println("Do you want to Sort the Products? (Y/N)");
                                        String isSorted = scanner.nextLine();
                                        boolean sorted;
                                        if(isSorted.equalsIgnoreCase("y")){
                                            sorted = true;
                                        } else if (isSorted.equalsIgnoreCase("n")){
                                            sorted = false;
                                        } else{
                                            System.out.println("Invalid Input");
                                            break;
                                        }
                                        System.out.println(Seller.getCustomersAndPurchases(storeName,user, sorted));
                                        break;
                                    case "2":
                                        System.out.println("Enter a store name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Input Username: ");
                                        user = scanner.nextLine();
                                        System.out.println("Do you want to Sort the Products? (Y/N)");
                                        isSorted = scanner.nextLine();
                                        if(isSorted.equalsIgnoreCase("y")){
                                            sorted = true;
                                        } else if (isSorted.equalsIgnoreCase("n")){
                                            sorted = false;
                                        } else{
                                            System.out.println("Invalid Input");
                                            break;
                                        }
                                        System.out.println(Seller.getProductSales(storeName,user, sorted));
                                        break;
                                    case "3":
                                        System.out.println("Enter Username: ");
                                        user = scanner.nextLine();
                                        System.out.println(Seller.getShoppingCartProducts(user));
                                        break;
                                    case "4":
                                        System.out.println("Enter Store Name: ");
                                        storeName = scanner.nextLine();
                                        System.out.println("Enter file path to be written to (include .txt)");
                                        String filePath = scanner.nextLine();
                                        boolean check = Seller.writeProductsToCSV(storeName, filePath);
                                        if(check){
                                            System.out.println("Written to Successfully");
                                        } else if (!check){
                                            System.out.println("Failed");
                                        }
                                        break;
                                    case "5" :
                                        break;
                                    default:
                                        System.out.println("Invalid Input");
                                        break;
                                }
                                break;
                            case "4":
                                bool = false;
                                while(!bool) {
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
                            case "5":
                                System.out.print(AccountChoices);
                                input = scanner.nextLine();
                                switch (input){
                                    case "1":
                                        bool = false;
                                        while(!bool) {
                                            System.out.println("Input Username or Email: ");
                                            user = scanner.nextLine();
                                            System.out.println("Enter Old Password: ");
                                            String oldPassword = scanner.nextLine();
                                            System.out.println("Enter New Password: ");
                                            String newPassword = scanner.nextLine();
                                            bool = Account.changePassword(user, oldPassword, newPassword);
                                            if (bool) {
                                                System.out.println("Successfully Changed Password");
                                            } else {
                                                System.out.println("Change Failed");
                                                System.out.println("Try Again!");
                                            }
                                        }
                                        break;
                                    case "2":
                                        bool = false;
                                        while(!bool) {
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
                                    case "3":
                                        bool = false;
                                        while(!bool) {
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
                                    default:
                                        System.out.println("Invalid Input");
                                        break;
                                }
                            case "6":
                                isLoggedIn = false;
                                System.out.println("Successfully Logged out");
                                break;
                            default:
                                System.out.println("Invalid Input");
                                break;
                        }

                    } else if (Account.getRole(user).equals("customer")) {
                        System.out.println(BUYERPROMPT);
                        input = scanner.nextLine();
                        switch (input){
                            case "1":
                                System.out.println(SEARCHPROMPT);
                                input = scanner.nextLine();
                                System.out.println(Seller.searchByStore(input));
                                break;
                            case "2":
                                System.out.println(SEARCHPROMPT);
                                input = scanner.nextLine();
                                System.out.println(Seller.searchByProduct(input));
                                //search logic
                                break;
                            case "3":
                                System.out.println(SEARCHPROMPT);
                                input = scanner.nextLine();
                                System.out.println("Store  |  Product ");
                                System.out.println(Seller.searchByDescription(input));
                                break;
                            case "4":
                                Seller.printProductAndStores();
                                break;
                            case "5":
                                System.out.print(AccountChoices);
                                input = scanner.nextLine();
                                switch (input){
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
                                        String newRole= scanner.nextLine();
                                        Account.changeRole(user, password, newRole);
                                        break;
                                    case "3":
                                        System.out.println("Input Username or Email: ");
                                        user = scanner.nextLine();
                                        System.out.println("Enter Password: ");
                                        password = scanner.nextLine();
                                        Account.deleteAccount(user, password);
                                        break;
                                    case "4" :
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
