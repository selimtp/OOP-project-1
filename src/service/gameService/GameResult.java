package service.gameService;

public class GameResult {
    private final boolean win;
    private final double netAmount;
    private final String message;
    private String gameName;
    public GameResult(String gameName ,boolean win, double amount, String message) {
        this.gameName = gameName;
        this.win = win;
        this.netAmount = amount;
        this.message = message;
    }

    public String getGameName() {
        return gameName;
    }

    public boolean isWin() {
        return win;
    }

    public double getAmount() {
        return netAmount;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return (win ? "WIN" : "LOSS") + " | Amount: $" + netAmount + " | " + message;
    }
}
