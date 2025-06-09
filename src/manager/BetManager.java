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

        System.out.println(win ? "You won!" : "You lost.");
        System.out.println("New balance: $" + user.getWallet());

        FileManager.saveBetLog(user.getUsername(), selectedGame.getName(), amount, win);
    }

    public void showLogs() {
        List<String> logs = FileManager.readBetLogs();
        logs.forEach(System.out::println);
    }
}