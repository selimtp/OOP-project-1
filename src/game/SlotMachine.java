package game;

import mod.User;
import java.util.Random;

public class SlotMachine extends Game {
    public SlotMachine() {
        super("Slot Machine");
    }

    @Override
    public boolean play(User user, double betAmount) {
        Random rand = new Random();
        boolean win = rand.nextDouble() > 0.5;

        if (win) {
            user.setWallet(user.getWallet() + betAmount); // kazandıysa 2x
        } else {
            user.setWallet(user.getWallet() - betAmount);
        }

        return win;
    }
}