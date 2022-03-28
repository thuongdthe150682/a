package usermanagementsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManageUser {

    static void displayMenu() {
        System.out.println("========USER MANAGEMENT SYSTEM=====");
        System.out.println("1.Create a new account");
        System.out.println("2.Login system");
        System.out.println("3.Exit");

    }

    // input choice
    // return choice
    static int inputChoice() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("> Choose: ");
            try {
                // input choice
                int choice = Integer.parseInt(sc.nextLine().trim());
                // check if choice out of range[1,3]
                if (choice < 1 || choice > 3) {
                    System.out.println("Please enter choice in range [1,3]");
                } else {
                    return choice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter integer");
            }
        }
    }

    static void deletefile() {
        File file = new File("user.dat");
        if (file.exists()) {
            file.delete();
        }
    }

    // load dât from file
    // parametter is the file
    // return list of account
    static List<Account> readDataFromFile(File file) throws IOException {
        List<Account> list = new ArrayList<>();
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(in);
            // read list from file
            list = (List<Account>) inputStream.readObject();
            in.close();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Can not read list from data.");
        }
        return list;
    }

    // write the list of account to file
    // parametter is the file and the list
    static void writeToFile(File file, List<Account> list) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            // write list to file
            outputStream.writeObject(list);
            out.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Can not write list to data.");
        }
    }

    // input new account , check valid of username 
    // write new account to file
    static void createNewAccount() throws IOException {
        List<Account> list = new ArrayList<>();
        File file = new File("user.dat");
        // check if file does not exist
        if (!file.exists()) {
            // create new file
            file.createNewFile();
        } else {
            // read list from file
            list = readDataFromFile(file);
        }
        String username;
        while (true) {
            // input username
            username = inputAccount("Enter Username: ", 5);
            // check if user name valid
            boolean check = true;
            // traverse the list
            for (Account account : list) {
                // check if username is duplicated
                if (account.Username.equals(username)) {
                    System.out.println("Username is duplicated.");
                    check = false;
                    break;
                }
            }
            if (check == true) {
                break;
            }
        }
        // input password
        String password = inputAccount("Enter Password: ", 6);
        // create a new acoount
        Account newAccount = new Account(username, password);
        // add new account to the list
        list.add(newAccount);
        // write new list to file
        writeToFile(file, list);

    }

    // input a string (input username or password)
    // parametter is message before enter ans number of least length of input
    // return string input
    public static String inputAccount(String message, int number) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(message);
            // input string
            String input = sc.nextLine().trim();
            // check if input contain space or length less than number
            if (input.contains(" ") || input.length() < number) {
                System.out.println("You must enter least at " + number + " character, and no space!");
            } else {
                return input;
            }
        }
    }

    // input username and password
    // check if accout exist
    static void loginAccount() throws IOException {
        File file = new File("user.dat");
        if (!file.exists()) {
            System.out.println("Data not exist.");
            return;
        }
        // read data from file
        List<Account> list = readDataFromFile(file);
        // input username
        String username = inputAccount("Enter Username: ", 5);
        // input password
        String password = inputAccount("Enter Password: ", 6);
        // check if account can be login
        boolean check = false;
        // traverse the list of account
        for (Account account : list) {
            // check if username and password valid 
            if (username.equals(account.Username) && password.equals(account.Password)) {
                System.out.println("Login successful!");
                check = true;
                break;
            }
        }
        // check if account not exist in list
        if (check == false) {
            System.out.println("Invalid username or password!");
        }

    }

}
