package service;

import model.User;
import service.gameService.*;
import java.util.*;

public class BetService {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Game> games;

    public BetService() {
        games = List.of(new SlotMachine(),new Roulette(),new Blackjack());
    }

    public void placeBet(User user,UserService userService) {
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
        boolean flag = true;
        while (flag){
            System.out.println();
            System.out.println("---Play Menu---");
            System.out.println("1. Play Game");
            System.out.println("2. Get rules");
            System.out.println("3. Exit to the previous page");
            System.out.println("Choose:");
            int choice1;
            try {
                choice1 = Integer.parseInt(scanner.nextLine()) ;
                if (choice1 < 1 || choice >= 4) {
                    System.out.println("Invalid choice.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                return;
            }
            if(choice1 == 1){
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
                GameResult gameResult = selectedGame.play(user, amount);
                System.out.println(gameResult.getMessage());
                userService.walletChange(user.getUsername(),gameResult);


            }else if(choice1 == 2){
                System.out.println(activeGames.get(choice).getRules());
            }else{
                flag = false;
            }
        }
    }

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

                if (choice < 1 || choice > games.size()+1) {
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