package file;

import mod.*;
import java.util.*;
import java.io.*;

public class FileManager {
    private static final String userFile = "users.txt";
    private static final String logFile = "logs.txt";

    public static List<User> loadUsers() {
        List<User> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0];
                    String password = parts[1];
                    double wallet = Double.parseDouble(parts[2]);
                    boolean isAdmin;
                    if(parts[3]==null){
                        isAdmin = false;
                    }else {
                        isAdmin = true;
                    }
                    User newUser = new User(username,password,wallet);
                    newUser.
                    list.add(new User(username, password, wallet));
                }
            }
        } catch (IOException e) {
            System.out.println("User file not found. Creating new list.");
        }
        return list;
    }

    public static void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(userFile))) {
            for (User u : users) {
                pw.println(u.getUsername() + "," + u.getPassword() + "," + u.getWallet() + "," + u.isAdmin());
            }
        } catch (IOException e) {
            System.out.println("Error saving users.");
        }
    }

    public static void saveBetLog(String username, String game, double amount, boolean win) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(logFile, true))) {
            pw.println(username + "," + game + "," + amount + "," + (win ? "Win" : "Lose"));
        } catch (IOException e) {
            System.out.println("Error writing log.");
        }
    }

    public static List<String> readBetLogs() {
        List<String> logs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading logs.");
        }
        return logs;
    }
}
