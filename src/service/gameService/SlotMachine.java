package service.gameService;

import model.User;

import java.util.Random;

public class SlotMachine extends Game {
    private static final String[] SYMBOLS = {"🍒", "🍋", "🔔", "⭐", "💎"};
    private final Random random = new Random();

    public SlotMachine() {
        super("Slot Machine");
    }

    @Override
    public GameResult play(User user, double betAmount) {
        System.out.println("\nSpinning the reels... ");

        String[] result = new String[3];
        for (int i = 0; i < 3; i++) {
            result[i] = SYMBOLS[random.nextInt(SYMBOLS.length)];
        }

        System.out.println("+-------+-------+-------+");
        System.out.printf("|  %s  |  %s  |  %s  |\n", result[0], result[1], result[2]);
        System.out.println("+-------+-------+-------+");

        boolean win = result[0].equals(result[1]) && result[1].equals(result[2]);

        String gameName = "Slot Machine";
        if (win) {
            double amountChanged = betAmount * 4;
            return new GameResult(gameName, true, amountChanged, "JACKPOT! You won $" + amountChanged);
        } else {
            return new GameResult(gameName,false, -betAmount, "You lost $" + betAmount);
        }

    }

    @Override
    public String getRules() {
        return "Match 3 symbols to win!\n" +
                "- Bet an amount, and spin.\n" +
                "- If all 3 symbols match, you win 5x your bet.\n" +
                "- Otherwise, you lose the bet amount.";
    }

}
