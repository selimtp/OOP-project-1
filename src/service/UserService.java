package service;

import util.PasswordManage;
import repo.FileManager;
import model.*;
import repo.LogManager;
import service.gameService.GameResult;

import java.util.*;

public class UserService{
    private List<User> users;

    public UserService() {
        users = FileManager.loadUsers();
    }

    public boolean isAdmin(Credentials creds){
        for (User user : users) {
            if (user.getUsername().equals(creds.getUsername()) && PasswordManage.decryption(user.getPassword()).equals(creds.getPassword()) && user.isAdmin()) {
                return true;
            }
        }
        return false;
    }

    public User authenticate(Credentials creds){
        for (User user : users){
            if (user.getUsername().equals(creds.getUsername()) && PasswordManage.decryption(user.getPassword()).equals(creds.getPassword())) {
                LogManager.logAction(user.getUsername(), "Logged in");
                return user;
            }
        }
        return null;
    }

    public void register(User user) {
        for (User user1 : users) {
            if (user1.getUsername().equals(user.getUsername())){
                System.out.println("User already exists.");
                return;
            }
        }
        users.add(user);
        FileManager.saveUsers(users);
        System.out.println("User registered successfully.");
        LogManager.logAction(user.getUsername(), "Registered");

    }


    public void listUsers(){
        users.forEach(System.out::println);
    }
    public void walletChange(String username, GameResult gameResult){
        for(User u : users){
            if(u.getUsername().equals(username)){
                u.setWallet(u.getWallet() + gameResult.getAmount());
                FileManager.saveUsers(users);
                System.out.println("New balance: $" + u.getWallet());
                String win;
                if(gameResult.isWin()){
                    win = "win";
                }else{
                    if(gameResult.getAmount() == 0){
                        win = "draw";
                    }else{
                        win = "lose";
                    }
                }
                LogManager.logAction(u.getUsername(), gameResult.getGameName() + ",$" + gameResult.getAmount() + "," + win);
            }
        }
    }

    public void addFunds(String username, double amount){
        for (User u : users){
            if (u.getUsername().equals(username)) {
                u.setWallet(u.getWallet() + amount);
                FileManager.saveUsers(users);
                System.out.println("Funds added.");
                LogManager.logAction(username, "Added $" + amount + " to wallet");
                return;
            }
        }
        System.out.println("User not found.");
    }

    public void viewUserDetails(String username){
        for (User u : users) {
            if (u.getUsername().equals(username)){
                System.out.println(u);
                return;
            }
        }
        System.out.println("User not found.");
    }
}
