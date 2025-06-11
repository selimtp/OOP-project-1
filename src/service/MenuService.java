package service;

import repo.FileManager;
import model.*;
import java.util.Scanner;

public class MenuService {
    private final Scanner scanner = new Scanner(System.in);

    public void run(UserService userService, BetService betService) {
        while (true) {

            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loginUser(userService, betService);
                    break;
                case "2":
                    registerUser(userService);
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void loginUser(UserService userService, BetService betService) {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        Credentials cred = new Credentials(username, password);

        if (userService.isAdmin(cred)) {
            adminMenu(userService, betService);
        } else {
            User user = userService.authenticate(cred);
            if (user != null) {
                userMenu(user, betService, userService);
            } else {
                System.out.println("Login failed.");
            }
        }
    }

    private void registerUser(UserService userService) {
        System.out.print("New Username: ");
        String username = scanner.nextLine().trim();

        String password;
        while (true) {
            System.out.print("New Password (min 5 chars): ");
            password = scanner.nextLine().trim();
            if (password.length() >= 5) {
                break;
            }
            System.out.println("Password too short.");
        }

        User user = new User(username, password, 100.0);
        userService.register(user);
    }

    private void userMenu(User user, BetService betService, UserService userService) {
        while (true) {
            System.out.println();
            System.out.println("User Menu - " + user.getUsername());
            System.out.println("1. Play Games");
            System.out.println("2. View Balance");
            System.out.println("3. Add Funds");
            System.out.println("4. Logout");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    betService.placeBet(user);
                    break;
                case "2":
                    System.out.println("Balance: $" + user.getWallet());
                    break;
                case "3":
                    System.out.print("Enter amount: ");
                    double amt = Double.parseDouble(scanner.nextLine());
                    userService.addFunds(user.getUsername(), amt);
                    System.out.print("Your new balance is: $" + user.getWallet() );
                    break;
                case "4":
                    System.out.println("Logging out...");
                    FileManager.logAction(user.getUsername(), "Logged out");
                    return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void adminMenu(UserService userService, BetService betService) {
        while (true) {
            System.out.println();
            System.out.println("Admin Panel");
            System.out.println("1. View Users");
            System.out.println("2. Add Funds");
            System.out.println("3. View All Logs");
            System.out.println("4. View User Details");
            System.out.println("5. View User Bet Logs");
            System.out.println("6. Modify Game Settings");
            System.out.println("7. Logout");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    userService.listUsers();
                    break;
                case "2":
                    System.out.print("Enter username: ");
                    String user0 = scanner.nextLine().trim();
                    System.out.print("Enter amount: ");
                    double amt = Double.parseDouble(scanner.nextLine());
                    userService.addFunds(user0, amt);
                    break;
                case "3":
                    betService.showLogs();
                    break;
                case "4":
                    System.out.print("Enter username: ");
                    String user1 = scanner.nextLine().trim();
                    userService.viewUserDetails(user1);
                    break;

                case "5":
                    System.out.print("Enter username: ");
                    String user2 = scanner.nextLine().trim();
                    betService.showUserActionLogs(user2);
                    break;
                case "6":
                    betService.modifyGameSettings();
                    break;
                case "7" :
                    System.out.println("Logging out...");
                    return;
                default : System.out.println("Invalid option.");
            }
        }
    }
}
