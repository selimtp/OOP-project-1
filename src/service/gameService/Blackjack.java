package service.gameService;

import model.User;

import java.util.*;

public class Blackjack extends Game {
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    public Blackjack() {
        super("Blackjack");
    }


    @Override
    public GameResult play(User user, double betAmount) {
        List<String> deck = createDeck();
        Collections.shuffle(deck);

        List<String> playerHand = new ArrayList<>();
        List<String> dealerHand = new ArrayList<>();

        playerHand.add(drawCard(deck));
        dealerHand.add(drawCard(deck));
        playerHand.add(drawCard(deck));
        dealerHand.add(drawCard(deck));

        System.out.println("Dealer shows: " + dealerHand.get(0));
        System.out.println("Your hand: " + String.join(", ", playerHand) + " (Total: " + handValue(playerHand) + ")");

        String gameName = "Blackjack";
        while (true) {
            System.out.print("Do you want to [H]it or [S]tand? ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("h")) {
                playerHand.add(drawCard(deck));
                int total = handValue(playerHand);
                System.out.println("You drew: " + playerHand.get(playerHand.size() - 1));
                System.out.println("Your hand: " + String.join(", ", playerHand) + " (Total: " + total + ")");
                if (total > 21) {
                    return new GameResult(gameName,false, -betAmount, "You busted! Dealer wins.");
                }
            } else if (input.equals("s")) {
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }

        // Dealer turn
        System.out.println("\nDealer's turn...");
        System.out.println("Dealer hand: " + String.join(", ", dealerHand) + " (Total: " + handValue(dealerHand) + ")");
        while (handValue(dealerHand) < 17) {
            String card = drawCard(deck);
            dealerHand.add(card);
            System.out.println("Dealer draws: " + card);
            System.out.println("Dealer hand: " + String.join(", ", dealerHand) + " (Total: " + handValue(dealerHand) + ")");
        }

        int playerTotal = handValue(playerHand);
        int dealerTotal = handValue(dealerHand);

        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            return new GameResult(gameName,true, betAmount, "You win! Your " + playerTotal + " vs Dealer's " + dealerTotal);
        } else if (dealerTotal == playerTotal) {
            return new GameResult(gameName,false, 0, "Push (tie). Both have " + playerTotal);
        } else {
            return new GameResult(gameName,false, -betAmount, "Dealer wins. Your " + playerTotal + " vs Dealer's " + dealerTotal);
        }
    }

    private List<String> createDeck() {
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        List<String> deck = new ArrayList<>();
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(rank + suit);
            }
        }
        return deck;
    }

    private String drawCard(List<String> deck) {
        return deck.remove(0);
    }

    private int handValue(List<String> hand) {
        int total = 0;
        int aceCount = 0;

        for (String card : hand) {
            String rank = card.substring(0, card.length() - 1);
            switch (rank) {
                case "J":
                case "Q":
                case "K":
                    total += 10;
                    break;
                case "A":
                    aceCount++;
                    total += 11;
                    break;
                default:
                    total += Integer.parseInt(rank);
                    break;
            }
        }

        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }
    @Override
    public String getRules() {
        return "Blackjack Rules:\n" +
                "- Get as close to 21 as possible without going over.\n" +
                "- Face cards = 10, Ace = 1 or 11.\n" +
                "- You can Hit (draw card) or Stand (stop).\n" +
                "- Dealer draws until reaching at least 17.\n" +
                "- Win if your hand is higher than dealer's or dealer busts.";
    }

}
