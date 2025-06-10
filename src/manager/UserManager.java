package manager;

import file.FileManager;
import mod.*;
import java.util.*;

public class UserManager {
    private List<User> users;

    public UserManager() {
        users = FileManager.loadUsers();
    }

    public boolean isAdmin(Credentials creds) {
        for (User u : users) {
            if (u.getUsername().equals(creds.getUsername()) &&
                    u.getPassword().equals(creds.getPassword()) &&
                    u.isAdmin()) {
                return true;
            }
        }
        return false;
    }

    public User authenticate(Credentials creds) {
        for (User u : users) {
            if (u.getUsername().equals(creds.getUsername()) &&
                    u.getPassword().equals(creds.getPassword())) {
                return u;
            }
        }
        return null;
    }

    public void register(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                System.out.println("User already exists.");
                return;
            }
        }
        users.add(user);
        FileManager.saveUsers(users);
        System.out.println("User registered successfully.");
    }

    public void listUsers() {
        users.forEach(System.out::println);
    }

    public void addFunds(String username, double amount) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                u.setWallet(u.getWallet() + amount);
                FileManager.saveUsers(users);
                System.out.println("Funds added.");
                return;
            }
        }
        System.out.println("User not found.");
    }

    public void viewUserDetails(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println(u);
                return;
            }
        }
        System.out.println("User not found.");
    }
}
