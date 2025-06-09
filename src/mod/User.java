package mod;

public class User {
    private String username;
    private String password;
    private double wallet;
    private boolean isAdmin;

    public User(String username, String password, double wallet) {
        this.username = username;
        this.password = password;
        this.wallet = wallet;
        this.isAdmin = false;

    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }
    @Override
    public String toString() {
        return "Username: " + username + ", Wallet: $" + wallet + ", Admin: " + isAdmin;
    }
}
