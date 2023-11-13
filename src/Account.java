package src;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * src.Account
 * <p>
 * Allows for creation of accounts, login, and deletion of accounts
 * Create AccountData.txt before using
 *
 * @author Alexander Chen, 05
 * @version November 10, 2023
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
                break;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                return false;
            } else {
                return encrypt(password).equals(passwords.get(index));
            }
        } else {
            return encrypt(password).equals(passwords.get(index));
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
                break;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                return false;
            } else {
                if (encrypt(password).equals(passwords.get(index))) {
                    emails.remove(index);
                    usernames.remove(index);
                    passwords.remove(index);
                    roles.remove(index);
                    writeToFile();
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (encrypt(password).equals(passwords.get(index))) {
                emails.remove(index);
                usernames.remove(index);
                passwords.remove(index);
                roles.remove(index);
                writeToFile();
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Changes username of a user and all their store usernames
     * Old username should be inputted automatically from user input
     *
     * @param newUsername
     * @param oldUsername
     * @return boolean indicating whether username change was successful
     */
    public static boolean changeUsername(String newUsername, String oldUsername) {
        readFromFile();
        int index = -1;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(newUsername)) {
                return false;
            }
            if (usernames.get(i).equals(oldUsername)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false; // should not happen
        }
        usernames.set(index, newUsername);
        writeToFile();
        return true;
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
        if (newPassword.isEmpty() || newPassword.isBlank()) {
            return false;
        }
        if (usernameOrEmail.contains("\\") || usernameOrEmail.contains(" ")) {
            return false;
        }
        readFromFile();
        int index = -1;
        for (int i = 0; i < usernames.size(); i++) {
            if (usernameOrEmail.equalsIgnoreCase(usernames.get(i))) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                    break;
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
                break;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                    break;
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
     * Returns username
     *
     * @param usernameOrEmail
     * @return String username
     */
    public static String getUsername(String usernameOrEmail) {
        int index = -1;
        if (usernameOrEmail.contains("@")) {
            for (int i = 0; i < emails.size(); i++) {
                if (emails.get(i).equalsIgnoreCase(usernameOrEmail)) {
                    index = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < usernames.size(); i++) {
                if (usernames.get(i).equalsIgnoreCase(usernameOrEmail)) {
                    index = i;
                    break;
                }
            }
        }
        return usernames.get(index);
    }

    /**
     * Returns email
     *
     * @param usernameOrEmail
     * @return String email
     */
    public static String getEmail(String usernameOrEmail) {
        int index = -1;
        if (usernameOrEmail.contains("@")) {
            for (int i = 0; i < emails.size(); i++) {
                if (emails.get(i).equalsIgnoreCase(usernameOrEmail)) {
                    index = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < usernames.size(); i++) {
                if (usernames.get(i).equalsIgnoreCase(usernameOrEmail)) {
                    index = i;
                    break;
                }
            }
        }
        return emails.get(index);
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
                break;
            }
        }
        if (index == -1) {
            for (int i = 0; i < emails.size(); i++) {
                if (usernameOrEmail.equalsIgnoreCase(emails.get(i))) {
                    index = i;
                    break;
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
        try (PrintWriter pw = new PrintWriter(new FileWriter("AccountData.txt"))) {
            for (int i = 0; i < emails.size(); i++) {
                pw.println(emails.get(i) + ";" + usernames.get(i) + ";" + passwords.get(i) + ";" + roles.get(i));
            }
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
        try (BufferedReader br = new BufferedReader(new FileReader("AccountData.txt"))) {
            line = br.readLine();
            while ((line != null) && (!line.isEmpty())) {
                String[] subpart = line.split(";");
                emails.add(subpart[0]);
                usernames.add(subpart[1]);
                passwords.add(subpart[2]);
                roles.add(subpart[3]);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
