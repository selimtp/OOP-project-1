package service;

import model.User;
import repo.FileManager;
import game.*;

import java.util.*;

public class BetService {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Game> games;

    public BetService() {
        games = List.of(new SlotMachine(),new Roulette());
    }

    public void placeBet(User user) {
        List<Game> activeGames = games.stream().filter(Game::isActive).toList();
        if (activeGames.isEmpty()) {
            System.out.println("No games are currently available.");
            return;
        }

        System.out.println("Choose game:");
        for (int i = 0; i < activeGames.size(); i++) {
            System.out.println((i + 1) + ". " + activeGames.get(i).getName());
        }

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice < 0 || choice >= activeGames.size()) {
                System.out.println("Invalid game choice.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        System.out.print("Enter bet amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount > user.getWallet()) {
            System.out.println("Insufficient balance.");
            return;
        }

        Game selectedGame = activeGames.get(choice);
        boolean win = selectedGame.play(user, amount);
        if(win){
            System.out.println("You won!");
        }else{
            System.out.println("You lost.");
        }
        System.out.println("New balance: $" + user.getWallet());
        FileManager.logAction(user.getUsername(), selectedGame.getName() + ",$" + amount + "," + win);

    }
    public void showLogs() {
        List<String> logs = FileManager.readUserActionLogs();
        System.out.println("=== User Action Logs ===");
        logs.forEach(System.out::println);
    }

    public void showUserActionLogs(String username) {
        List<String> logs = FileManager.readUserActionLogs();
        boolean found = false;

        System.out.println("=== Action Logs for " + username + " ===");
        for (String log : logs) {
            String[] parts = log.split(" \\| ");
            if (parts.length >= 2 && parts[1].trim().equalsIgnoreCase(username)) {
                System.out.println(log);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No action logs found for user.");
        }
    }

//    public void showLogs() {
//        List<String> logs = FileManager.readBetLogs();
//        logs.forEach(System.out::println);
//    }
//    public void showUserBetLogs(String username) {
//        List<String> logs = FileManager.readBetLogs();
//        boolean found = false;
//
//        System.out.println("=== Bet Logs for " + username + " ===");
//        for (String log : logs) {
//            if (log.startsWith(username + ",")) {
//                System.out.println(log);
//                found = true;
//            }
//        }
//
//        if (!found) {
//            System.out.println("No logs found for user.");
//        }
//
//    }

    public void modifyGameSettings() {
        while (true) {
            System.out.println();
            System.out.println("=== Game Settings ===");
            for (int i = 0; i < games.size(); i++) {
                Game g = games.get(i);
                System.out.println((i + 1) + ". " + g.getName() + " [" + (g.isActive() ? "ACTIVE" : "INACTIVE") + "]");
            }
            System.out.println((games.size() + 1) + ". Exit");
            String input;
            int choice;
            while (true){
                System.out.print("Select game to change settings (or " + (games.size() + 1) + " to exit): ");
                choice = scanner.nextInt();

                if (choice < 1 || choice > games.size()) {
                    System.out.println("Invalid game choice.");

                }else break;
            }
            if (choice == games.size() + 1) {
                System.out.println("Returning to admin menu...");
                break;
            }

            Game selectedGame = games.get(choice - 1);
            selectedGame.setActive(!selectedGame.isActive());
            System.out.println(selectedGame.getName() + " is now " + (selectedGame.isActive() ? "ACTIVE" : "INACTIVE") + ".");


        }
    }
}