package service;

import repo.FileManager;
import model.*;
import java.util.*;

public class UserService {
    private List<User> users;

    public UserService() {
        users = FileManager.loadUsers();
    }

    public boolean isAdmin(Credentials creds) {
        for (User user : users) {
            if (user.getUsername().equals(creds.getUsername()) && FileManager.decryption(user.getPassword()).equals(creds.getPassword()) && user.isAdmin()) {
                return true;
            }
        }
        return false;
    }

    public User authenticate(Credentials creds) {
        for (User user : users) {
            if (user.getUsername().equals(creds.getUsername()) && FileManager.decryption(user.getPassword()).equals(creds.getPassword())) {
                FileManager.logAction(user.getUsername(), "Logged in");
                return user;
            }
        }
        return null;
    }

    public void register(User user) {
        for (User user1 : users) {
            if (user1.getUsername().equals(user.getUsername())) {
                System.out.println("User already exists.");
                return;
            }
        }
        users.add(user);
        FileManager.saveUsers(users);
        System.out.println("User registered successfully.");
        FileManager.logAction(user.getUsername(), "Registered");

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
                FileManager.logAction(username, "Added $" + amount + " to wallet");
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
