package manager;

import mod.User;
import file.FileManager;
import game.*;

import java.util.*;

public class BetManager {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Game> games;

    public BetManager() {
        games = List.of(new SlotMachine());
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

        FileManager.saveBetLog(user.getUsername(), selectedGame.getName(), amount, win);
    }

    public void showLogs() {
        List<String> logs = FileManager.readBetLogs();
        logs.forEach(System.out::println);
    }
    public void showUserBetLogs(String username) {
        List<String> logs = FileManager.readBetLogs();
        boolean found = false;

        System.out.println("=== Bet Logs for " + username + " ===");
        for (String log : logs) {
            if (log.startsWith(username + ",")) {
                System.out.println(log);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No logs found for user.");
        }
    }
}