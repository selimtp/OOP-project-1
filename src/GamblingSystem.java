import service.*;

public class GamblingSystem {
    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        UserService userService = new UserService();
        BetService betService = new BetService();

        menuService.run(userService, betService);
    }
}