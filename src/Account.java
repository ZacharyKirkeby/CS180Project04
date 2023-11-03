package src;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * src.Account
 * <p>
 * Allows for creation of accounts, login, and deletion of accounts
 * Create emails.txt, usernames.txt, passwords.txt, and roles.txt before using
 *
 * @author Alexander Chen, 05
 * @version November 2, 2023
 */

public class Account {

    private static ArrayList<String> emails = new ArrayList<>(); // email arraylist
    private static ArrayList<String> usernames = new ArrayList<>(); // username arraylist
    private static ArrayList<String> passwords = new ArrayList<>(); // password arraylist
    private static ArrayList<String> roles = new ArrayList<>(); // roles arraylist

    /**
     * Creates new account if username and email are unique (case-insensitive) and stores
     * username, email, encrypted password, and role in same index in the arraylists
     *
     * @param username
     * @param password
     * @return boolean determining whether account creation was successful
     */
    public static boolean createAccount(String email, String username, String password, String role) {
        readFromFile();
        if (!emailValidator(email)) {
            return false;
        }
        if (username.contains("\\") || username.contains(" ") || username.contains("@") || username.contains(".")) {
            return false;
        }
        for (int i = 0; i < usernames.size(); i++) {
            if (username.equalsIgnoreCase(usernames.get(i))) {
                return false;
            }
        }
        emails.add(email);
        usernames.add(username);
        passwords.add(encrypt(password));
        roles.add(role);
        writeToFile();
        return true;
    }

    /**
     * Returns boolean determining whether login was successful by comparing username / email and password
     *
     * @param usernameOrEmail
     * @param password
     * @return boolean determining whether login was successful
     */
    public static boolean login(String usernameOrEmail, String password) {
        if (usernameOrEmail.contains("\\") || usernameOrEmail.contains(" ")) {
            return false;
        }
        readFromFile();
        int index = -1;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernameOrEmail.equalsIgnoreCase(usernames.get(i))) {
                index = i;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                }
            }
            if (index == -1) {
                return false;
            } else {
                if (encrypt(password).equals(passwords.get(index))) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (encrypt(password).equals(passwords.get(index))) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Deletes account if username / email and password indices match and reformats
     * email, username, password, and role arraylists to remove null values
     *
     * @param usernameOrEmail
     * @param password
     * @return boolean determining whether account deletion was successful
     */
    public static boolean deleteAccount(String usernameOrEmail, String password) {
        if (usernameOrEmail.contains("\\") || usernameOrEmail.contains(" ")) {
            return false;
        }
        readFromFile();
        int index = -1;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernameOrEmail.equalsIgnoreCase(usernames.get(i))) {
                index = i;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                }
            }
            if (index == -1) {
                return false;
            } else {
                if (encrypt(password).equals(passwords.get(index))) {
                    emails.remove(index);
                    emails.removeAll(Collections.singleton(null));
                    usernames.remove(index);
                    usernames.removeAll(Collections.singleton(null));
                    passwords.remove(index);
                    passwords.removeAll(Collections.singleton(null));
                    roles.remove(index);
                    roles.removeAll(Collections.singleton(null));
                    writeToFile();
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (encrypt(password).equals(passwords.get(index))) {
                emails.remove(index);
                emails.removeAll(Collections.singleton(null));
                usernames.remove(index);
                usernames.removeAll(Collections.singleton(null));
                passwords.remove(index);
                passwords.removeAll(Collections.singleton(null));
                roles.remove(index);
                roles.removeAll(Collections.singleton(null));
                writeToFile();
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Takes a username / email and changes password if old password matches
     *
     * @param usernameOrEmail
     * @param oldPassword
     * @param newPassword
     * @return boolean determining whether password change was successful
     */
    public static boolean changePassword(String usernameOrEmail, String oldPassword, String newPassword) {
        if (usernameOrEmail.contains("\\") || usernameOrEmail.contains(" ")) {
            return false;
        }
        readFromFile();
        int index = -1;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernameOrEmail.equalsIgnoreCase(usernames.get(i))) {
                index = i;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                }
            }
            if (index == -1) {
                return false;
            }
        }
        if (encrypt(oldPassword).equals(passwords.get(index))) {
            passwords.set(index, encrypt(newPassword));
            writeToFile();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Takes a username / email and changes role if password is correct
     *
     * @param usernameOrEmail
     * @param password
     * @param newRole
     * @return boolean determining whether role change was successful
     */
    public static boolean changeRole(String usernameOrEmail, String password, String newRole) {
        if (usernameOrEmail.contains("\\") || usernameOrEmail.contains(" ")) {
            return false;
        }
        readFromFile();
        int index = -1;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernameOrEmail.equalsIgnoreCase(usernames.get(i))) {
                index = i;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                }
            }
            if (index == -1) {
                return false;
            }
        }
        if (encrypt(password).equals(passwords.get(index))) {
            roles.set(index, newRole);
            writeToFile();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the role of the associated username or email and returns null if not found
     *
     * @param usernameOrEmail
     * @return String value of role or null
     */
    public static String getRole(String usernameOrEmail) {
        if (usernameOrEmail.contains("\\") || usernameOrEmail.contains(" ")) {
            return null;
        }
        readFromFile();
        int index = -1;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernameOrEmail.equalsIgnoreCase(usernames.get(i))) {
                index = i;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                }
            }
            if (index == -1) {
                return null;
            }
        }
        return roles.get(index);
    }

    /**
     * Determines whether an email is valid
     *
     * @param email
     * @return boolean validating email
     */
    private static boolean emailValidator(String email) {
        if (email.contains("\\") || email.contains(" ")) {
            return false;
        }
        if (Pattern.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", email)) {
            for (int i = 0; i < emails.size(); i++) {
                if (emails.get(i).equalsIgnoreCase(email)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Encrypts password using SHA-256
     *
     * @param password
     * @return String of encrypted password
     */
    private static String encrypt(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = messageDigest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            stringBuilder.append(String.format("%02x", hash[i]));
        }
        return stringBuilder.toString();
    }

    /**
     * Writes information to files
     */
    private static void writeToFile() {
        try {
            PrintWriter emailPW = new PrintWriter(new FileWriter("emails.txt", false));
            for (int i = 0; i < emails.size(); i++) {
                emailPW.println(emails.get(i));
            }
            emailPW.close();
            PrintWriter usernamePW = new PrintWriter(new FileWriter("usernames.txt",false));
            for (int i = 0; i < usernames.size(); i++) {
                usernamePW.println(usernames.get(i));
            }
            usernamePW.close();
            PrintWriter passwordPW = new PrintWriter(new FileWriter("passwords.txt", false));
            for (int i = 0; i < passwords.size(); i++) {
                passwordPW.println(passwords.get(i));
            }
            passwordPW.close();
            PrintWriter rolePW = new PrintWriter(new FileWriter("roles.txt", false));
            for (int i = 0; i < roles.size(); i++) {
                rolePW.println(roles.get(i));
            }
            rolePW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads information from files
     */
    private static void readFromFile() {
        emails.clear();
        usernames.clear();
        passwords.clear();
        roles.clear();
        String line;
        try {
            BufferedReader emailBFR = new BufferedReader(new FileReader("emails.txt"));
            line = emailBFR.readLine();
            while ((line != null) && (!line.isEmpty())) {
                emails.add(line);
                line = emailBFR.readLine();
            }
            emailBFR.close();
            BufferedReader usernameBFR = new BufferedReader(new FileReader("usernames.txt"));
            line = usernameBFR.readLine();
            while ((line != null) && (!line.isEmpty())) {
                usernames.add(line);
                line = usernameBFR.readLine();
            }
            usernameBFR.close();
            BufferedReader passwordBFR = new BufferedReader(new FileReader("passwords.txt"));
            line = passwordBFR.readLine();
            while ((line != null) && (!line.isEmpty())) {
                passwords.add(line);
                line = passwordBFR.readLine();
            }
            passwordBFR.close();
            BufferedReader roleBFR = new BufferedReader(new FileReader("roles.txt"));
            line = roleBFR.readLine();
            while ((line != null) && (!line.isEmpty())) {
                roles.add(line);
                line = roleBFR.readLine();
            }
            roleBFR.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
