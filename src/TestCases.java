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
public class TestCases {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCases.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }


    @Test(timeout = 1000)
    public void createAccountTest() {
        reset();
        assertEquals("Ensure that invalid email format is being checked!", false,
                Account.createAccount("InvalidEmailFormat", "username", "password", "customer"));

        assertEquals("Ensure that invalid email format is being checked!", false,
                Account.createAccount("InvalidEmail Format", "username", "password", "customer"));

        assertEquals("Ensure that invalid email format is being checked!", false,
                Account.createAccount("InvalidEmail @Format", "username", "password", "customer"));

        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("ValidEmailFormat@gmail.com", "username", "password", "customer"));

        assertEquals("Ensure that repeat emails are being checked!", false,
                Account.createAccount("ValidEmailFormat@gmail.com", "username1", "password", "customer"));

        // should have account added to database (persistent file) now
        assertEquals("Ensure that invalid username format is being checked!", false,
                Account.createAccount("ValidEmailFormat@gmail.com", "invaliduse rname", "password", "customer"));

        assertEquals("Ensure that invalid username format is being checked!", false,
                Account.createAccount("ValidEmailFormat@gmail.com", "invaliduse@rname", "password", "customer"));

        assertEquals("Ensure that invalid username format is being checked!", false,
                Account.createAccount("ValidEmailFormat@gmail.com", "invaliduse.rname", "password", "customer"));

        assertEquals("Ensure that invalid username format is being checked!", false,
                Account.createAccount("ValidEmailFormat@gmail.com", "invaliduse\\rname", "password", "customer"));

        assertEquals("Ensure that repeat usernames are being checked!", false,
                Account.createAccount("ValidEmailFormat1@gmail.com", "username", "password", "customer"));
    }

    @Test(timeout = 1000)
    public void loginTest() {
        reset();

        // create a test account to log into
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("ValidEmailFormat@gmail.com", "username", "password", "customer"));

        // test
        assertEquals("Ensure that invalid usernames are being checked!", false,
                Account.login("invalidUsername", "password"));

        assertEquals("Ensure that invalid emails are being checked!", false,
                Account.login("invalidEmail@gmail.com", "password"));

        assertEquals("Ensure that valid usernames are being passed!", true,
                Account.login("username", "password"));

        assertEquals("Ensure that valid emails are being passed!", true,
                Account.login("ValidEmailFormat@gmail.com", "password"));

        assertEquals("Ensure that invalid passwords are being checked with email!", false,
                Account.login("ValidEmailFormat@gmail.com", "invalidPassword"));

        assertEquals("Ensure that invalid passwords are being checked with username!", false,
                Account.login("username", "invalidPassword"));
    }

    @Test(timeout = 1000)
    public void deleteAccountTest() {
        reset();

        // create a test account to delete
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("ValidEmailFormat@gmail.com", "username1", "password", "customer"));

        // create a second test account to delete
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("testing@gmail.com", "username2", "password", "customer"));

        //test
        assertEquals("Ensure that invalid usernames are being checked!", false,
                Account.deleteAccount("invalidUsername", "password"));

        assertEquals("Ensure that invalid emails are being checked!", false,
                Account.deleteAccount("invalidEmail@gmail.com", "password"));

        assertEquals("Ensure that invalid passwords are being checked with email!", false,
                Account.deleteAccount("ValidEmailFormat@gmail.com", "invalidPassword"));

        assertEquals("Ensure that invalid passwords are being checked with username!", false,
                Account.deleteAccount("username", "invalidPassword"));

        assertEquals("Ensure that valid usernames are being passed!", true,
                Account.deleteAccount("username1", "password"));

        // account1 should have been deleted by now so check file to see if it has been updated
        ArrayList<String> list1 = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("AccountData.txt"))) {
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

        assertEquals("Ensure the AccountData.txt file is updated after deleting account!", 1, list1.size());

        assertEquals("Ensure that valid emails are being passed!",
                Account.deleteAccount("testing@gmail.com", "password"),
                true);

        // account2 should have been deleted by now so check file to see if its empty
        ArrayList<String> list2 = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("AccountData.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list2.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }
        assertEquals("Ensure the AccountData.txt file is updated after deleting account!", 0, list2.size());
    }

    @Test(timeout = 1000)
    public void changeUsernameTest() {
        reset();

        // create a test account1
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("email1@gmail.com", "username1", "password", "customer"));

        // create a second test account2
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("email2@gmail.com", "username2", "password", "seller"));

        assertEquals("Ensure that the old username exists!", false,
                Account.changeUsername("newusername", "oldusername"));

        assertEquals("Ensure that the old username exists!", true,
                Account.changeUsername("newusername", "username1"));

        ArrayList<String> list1 = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("AccountData.txt"))) {
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

        assertEquals("Ensure the AccountData.txt file is updated after!", "email1@gmail.com;newusername;" +
                "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8;customer", list1.get(0));

        assertEquals("Ensure that nothing else in the AccountData.txt file is updated after!",
                "email2@gmail.com;username2;5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8;" +
                        "seller", list1.get(1));


        assertEquals("Ensure that the method also works for seller!", true,
                Account.changeUsername("newusername2", "username2"));


        // check file contents
        ArrayList<String> list2 = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("AccountData.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list2.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        assertEquals("Ensure that nothing else in the AccountData.txt file is updated after!",
                "email1@gmail.com;newusername;5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8;" +
                        "customer", list2.get(0));

        assertEquals("Ensure the AccountData.txt file is updated after!", "email2@gmail.com;newusername2;" +
                "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8;seller", list2.get(1));
    }

    @Test(timeout = 1000)
    public void changePasswordTest() {
        reset();

        // create a test account
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("email1@gmail.com", "username1", "password", "customer"));

        // create a second test account
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("email2@gmail.com", "username2", "password", "seller"));

        assertEquals("Ensure that the username is valid!", false,
                Account.changePassword("username", "password", "newpassword"));

        assertEquals("Ensure that the email is valid!", false,
                Account.changePassword("fakeemail@gmail.com", "password", "newpassword"));

        assertEquals("Ensure that the old password is valid!", false,
                Account.changePassword("username1", "incorrectpassword", "newpassword"));

        assertEquals("Ensure that the new password is valid!", false,
                Account.changePassword("username1", "password", ""));

        assertEquals("Ensure that the new password is valid!", false,
                Account.changePassword("username1", "password", " "));

        assertEquals("Ensure that the new password is valid!", false,
                Account.changePassword("username1", "password", "    "));

        assertEquals("Ensure that the arguments are valid!", true,
                Account.changePassword("username1", "password", "newpassword"));

        ArrayList<String> list1 = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("AccountData.txt"))) {
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

        assertEquals("Ensure the AccountData.txt file is updated after!", "email1@gmail.com;username1;" +
                "089542505d659cecbb988bb5ccff5bccf85be2dfa8c221359079aee2531298bb;customer", list1.get(0));

        assertEquals("Ensure that nothing else in the AccountData.txt file is updated after!",
                "email2@gmail.com;username2;5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8;" +
                        "seller", list1.get(1));


        assertEquals("Ensure that the method also works with an email!", true,
                Account.changePassword("email2@gmail.com", "password", "newpassword"));


        // check file contents
        ArrayList<String> list2 = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("AccountData.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list2.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        assertEquals("Ensure that nothing else in the AccountData.txt file is updated after!",
                "email1@gmail.com;username1;089542505d659cecbb988bb5ccff5bccf85be2dfa8c221359079aee2531298bb;" +
                        "customer", list2.get(0));

        assertEquals("Ensure the AccountData.txt file is updated after!", "email2@gmail.com;username2;" +
                "089542505d659cecbb988bb5ccff5bccf85be2dfa8c221359079aee2531298bb;seller", list2.get(1));
    }

    @Test(timeout = 1000)
    public void changeRoleTest() {
        reset();

        // create a test account
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("email1@gmail.com", "username1", "password", "customer"));

        // create a second test account
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("email2@gmail.com", "username2", "password", "seller"));

        assertEquals("Ensure that the username is valid!", false,
                Account.changeRole("username", "password", "seller"));

        assertEquals("Ensure that the email is valid!", false,
                Account.changeRole("fakeemail@gmail.com", "password", "seller"));

        assertEquals("Ensure that the password is valid!", false,
                Account.changeRole("username1", "incorrectpassword", "seller"));

        assertEquals("Ensure that the arguments are valid!", true,
                Account.changeRole("username1", "password", "seller"));

        ArrayList<String> list1 = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("AccountData.txt"))) {
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

        assertEquals("Ensure the AccountData.txt file is updated after!", "email1@gmail.com;username1;" +
                "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8;seller", list1.get(0));

        assertEquals("Ensure that nothing else in the AccountData.txt file is updated after!",
                "email2@gmail.com;username2;5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8;" +
                        "seller", list1.get(1));


        assertEquals("Ensure that the method also works with an email!", true,
                Account.changeRole("email2@gmail.com", "password", "customer"));


        // check file contents
        ArrayList<String> list2 = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("AccountData.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                list2.add(line);
                line = bfr.readLine();
            }
        } catch (FileNotFoundException e) { // this is a subclass of IOException so catch it first
            e.printStackTrace();
        } catch (IOException e) { // dont forget to catch IOException as well (more general exceptions)
            e.printStackTrace();
        }

        assertEquals("Ensure that nothing else in the AccountData.txt file is updated after!",
                "email1@gmail.com;username1;5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8;" +
                        "seller", list2.get(0));

        assertEquals("Ensure the AccountData.txt file is updated after!", "email2@gmail.com;username2;" +
                "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8;customer", list2.get(1));
    }

    @Test(timeout = 1000)
    public void getRoleTest() {
        reset();

        // create a test account
        assertEquals("Ensure that valid format is being passed!", true,
                Account.createAccount("email1@gmail.com", "username1", "password", "customer"));

        assertEquals("Ensure that the username is valid!", null,
                Account.getRole("username"));

        assertEquals("Ensure that the email is valid!", null,
                Account.getRole("fakeemail@gmail.com"));

        assertEquals("Ensure that the argument is valid!", "customer",
                Account.getRole("email1@gmail.com"));

        assertEquals("Ensure that the argument is valid!", "customer",
                Account.getRole("username1"));
    }

    public static void reset() {
        // create AccountData.txt if not already there or clear its contents if its already there
        try {
            File myObj = new File("AccountData.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                FileOutputStream fos = new FileOutputStream("AccountData.txt", false);
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
