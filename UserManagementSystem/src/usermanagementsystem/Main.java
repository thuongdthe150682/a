package usermanagementsystem;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
      ManageUser.deletefile();
        while (true) {
            // display menu
            ManageUser.displayMenu();
            // input choice
            int choice = ManageUser.inputChoice();
            switch (choice) {
                case 1:
                    // create a new account
                    ManageUser.createNewAccount();
                    break;
                case 2:
                    // login to a account
                    ManageUser.loginAccount();
                    break;
                case 3:
                    return;
            }
        }
    }
}
