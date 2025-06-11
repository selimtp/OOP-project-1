package game;

import model.User;

import java.util.LinkedList;
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
    public boolean play(User user, double betAmount) {
        System.out.println("0 - Black\n1 - Red\n2 - Number");
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
                System.out.print("Choose number 4 number (0-36): ");
                try {
                    selectedNum = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }

        try {
            int result = spin();
            if (choice == 0 && result != 0 && result % 2 == 0) {
                user.setWallet(user.getWallet() + betAmount);
                return true;
            } else if (choice == 1 && result % 2 == 1) {
                user.setWallet(user.getWallet() + betAmount);
                return true;
            } else if (choice == 2 && result == selectedNum) {
                user.setWallet(user.getWallet() + (36* betAmount));
                return true;
            }
        } catch (InterruptedException e) {
            System.out.println("Spin error.");
        }
        user.setWallet(user.getWallet() - betAmount);
        return false;
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
        System.out.println("\n🎯 The ball lands on: " + getColor(result) + ANSI_BOLD + result + ANSI_RESET);

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

    // ANSI color codes
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BOLD = "\u001B[1m";
}
