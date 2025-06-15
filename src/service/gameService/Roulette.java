package service.gameService;

import model.User;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Roulette extends Game {
    private final LinkedList<Integer> wheel = new LinkedList<>();
    private final Scanner scanner = new Scanner(System.in);

    public Roulette() {
        super("Roulette");
        loadRoulette();
    }

    private void loadRoulette() {
        for (int i = 0; i < 37; i++) {
            wheel.add(i);
        }
    }

    @Override
    public GameResult play(User user, double betAmount) {
        System.out.println("0 - Black");
        System.out.println("1 - Red");
        System.out.println("2 - Number");
        int choice = -1;

        while (choice < 0 || choice > 2) {
            System.out.print("Place your bet on: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }

        int selectedNum = -1;
        if (choice == 2) {
            while (selectedNum < 0 || selectedNum > 36) {
                System.out.print("Choose number to bet on (0-36): ");
                try {
                    selectedNum = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }
        String gameName = "Roulette";
        try {
            int result = spin();
            boolean win = false;
            double amountChanged = -betAmount;
            String message;

            if (choice == 0 && getColor(result).equals(ANSI_BLACK)) {
                amountChanged = betAmount;
                win = true;
                message = "You won on Black! +" + betAmount;
            } else if (choice == 1 && getColor(result).equals(ANSI_RED)) {
                amountChanged = betAmount;
                win = true;
                message = "You won on Red! +" + betAmount;
            } else if (choice == 2 && result == selectedNum) {
                amountChanged = betAmount * 35;
                win = true;
                message = "You hit the exact number! +" + amountChanged;
            } else {

                message = "You lost. -" + betAmount;
            }
            
            return new GameResult(gameName,win, amountChanged, message);
        } catch (InterruptedException e) {
            return new GameResult(gameName,false, 0, "Spin interrupted. No bet processed.");
        }
    }

    private int spin() throws InterruptedException {
        Random rand = new Random();
        int spins = rand.nextInt(30) + 50;

        for (int i = 0; i < spins; i++) {
            clearConsole();
            System.out.println("+--------------------------v---------------------------+");
            System.out.print("| ");
            for (int j = 0; j < 9; j++) {
                int number = wheel.get(j);
                String colorCode = getColor(number);
                if (j == 4) {
                    System.out.print(ANSI_BOLD + colorCode + "[" + String.format("%2d", number) + "]" + ANSI_RESET + " | ");
                } else {
                    System.out.print(colorCode + " " + String.format("%2d", number) + " " + ANSI_RESET + "| ");
                }
            }
            System.out.println("\n+------------------------------------------------------+");

            wheel.addLast(wheel.removeFirst());
            Thread.sleep(50 + i * 4);
        }

        int result = wheel.get(3);
        System.out.println("\nThe ball lands on: " + getColor(result) + ANSI_BOLD + result + ANSI_RESET);
        return result;
    }

    private static String getColor(int number) {
        if (number == 0) return ANSI_GREEN;
        int[] reds = {
                1, 3, 5, 7, 9, 12, 14, 16, 18,
                19, 21, 23, 25, 27, 30, 32, 34, 36
        };
        for (int red : reds) {
            if (red == number) return ANSI_RED;
        }
        return ANSI_BLACK;
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    @Override
    public String getRules() {
        return "Roulette Rules:\n" +
                "- Choose to bet on Red (odd), Black (even), or a number (0–36).\n" +
                "- Win 1x for correct color, 36x for correct number.\n" +
                "- If you lose, bet amount is deducted.";
    }


    // ANSI color codes
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BOLD = "\u001B[1m";
}
