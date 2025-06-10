import manager.*;

public class GamblingSystem {
    public static void main(String[] args) {
        MenuManager menuManager = new MenuManager();
        UserManager userManager = new UserManager();
        BetManager betManager = new BetManager();

        menuManager.run(userManager, betManager);
    }
}