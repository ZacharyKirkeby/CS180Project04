package src;

import java.util.ArrayList;
import java.util.Scanner;

public class MarketPlace {
    private static final String WELCOMEPROMPT = "Login or Register an Account (Login / Register)";
    private static final String sellerChoices = "1. Create Store \n 2. Modify Store " +
            "\n 3. View Store Statistics \n 4. Delete Store ";
    private ArrayList<Store> stores;

    public MarketPlace() {

    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(WELCOMEPROMPT);
        String input = scanner.nextLine();
        switch (input) {
            case "Login":
                System.out.println("Input Username: ");
                String user = scanner.nextLine();
                System.out.println("Enter your password: ");
                String password = scanner.nextLine();
                Account.login(user, password);

                if (Account.getRole(user).equals("seller")) {
                    System.out.print(sellerChoices);
                    input = scanner.nextLine();
                    switch (input){
                        case "1":
                            System.out.println("Enter a store name: ");
                            String storeName = scanner.nextLine();
                            System.out.println("Enter a store location: ");
                            String location = scanner.nextLine();
                            Seller.createStore(storeName, location, user);
                            break;
                        case "2":

                            break;
                        case "3":
                            break;
                    }

                } else if (Account.getRole(user).equals("customer")) {

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
