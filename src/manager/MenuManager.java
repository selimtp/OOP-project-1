//package manager;
//
//import mod.*;
//import java.util.Scanner;
//
//public class MenuManager {
//    private final Scanner scanner = new Scanner(System.in);
//
//    public void run(UserManager userManager, BetManager betManager) {
//        while (true) {
//            System.out.println("1. Login\n2. Register\n3. Exit");
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1" -> handleLogin(userManager, betManager);
//                case "2" -> handleRegister(userManager);
//                case "3" -> {
//                    System.out.println("Exiting...");
//                    return;
//                }
//                default -> System.out.println("Invalid option.");
//            }
//        }
//    }
//
//    private void handleLogin(UserManager userManager, BetManager betManager) {
//        System.out.print("Username: ");
//        String username = scanner.nextLine().trim();
//        System.out.print("Password: ");
//        String password = scanner.nextLine().trim();
//
//        Credentials creds = new Credentials(username, password);
//
//        if (userManager.isAdmin(creds)) {
//            adminMenu(userManager, betManager);
//        } else {
//            User user = userManager.authenticate(creds);
//            if (user != null) {
//                userMenu(user, betManager);
//            } else {
//                System.out.println("Login failed.");
//            }
//        }
//    }
//
//    private void handleRegister(UserManager userManager) {
//        System.out.print("New Username: ");
//        String username = scanner.nextLine().trim();
//
//        String password;
//        while (true) {
//            System.out.print("New Password (min 5 chars): ");
//            password = scanner.nextLine().trim();
//            if (password.length() >= 5) break;
//            System.out.println("Password too short.");
//        }
//
//        User user = new User(username, password, 100.0);
//        userManager.register(user);
//    }
//
//    private void userMenu(User user, BetManager betManager) {
//        while (true) {
//            System.out.println("\nUser Menu - " + user.getUsername());
//            System.out.println("1. Place Bet\n2. View Balance\n3. Logout");
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1" -> betManager.placeBet(user);
//                case "2" -> System.out.println("Balance: $" + user.getWallet());
//                case "3" -> {
//                    System.out.println("Logging out...");
//                    return;
//                }
//                default -> System.out.println("Invalid option.");
//            }
//        }
//    }
//
//    private void adminMenu(UserManager userManager, BetManager betManager) {
//        while (true) {
//            System.out.println("\nAdmin Panel");
//            System.out.println("1. View Users\n2. Add Funds\n3. View All Logs");
//            System.out.println("4. View User Details\n5. View User Bet Logs\n6. Modify Game Settings (Not implemented)\n7. Logout");
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1" -> userManager.listUsers();
//                case "2" -> {
//                    System.out.print("Enter username: ");
//                    String u = scanner.nextLine().trim();
//                    System.out.print("Enter amount: ");
//                    double amt = Double.parseDouble(scanner.nextLine());
//                    userManager.addFunds(u, amt);
//                }
//                case "3" -> betManager.showLogs();
//                case "4" -> {
//                    System.out.print("Enter username: ");
//                    String u = scanner.nextLine().trim();
//                    userManager.viewUserDetails(u);
//                }
//                case "5" -> {
//                    System.out.print("Enter username: ");
//                    String u = scanner.nextLine().trim();
//                    betManager.showLogsForUser(u);
//                }
//                case "6" -> {
//                    System.out.println("Game settings modification is not implemented yet.");
//                }
//                case "7" -> {
//                    System.out.println("Logging out...");
//                    return;
//                }
//                default -> System.out.println("Invalid option.");
//            }
//        }
//    }
//}
