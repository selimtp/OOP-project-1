package file;

import mod.*;
import java.util.*;
import java.io.*;

public class FileManager {
    private static final String userFile = "users.txt";
    private static final String logFile = "logs.txt";

    public static List<User> loadUsers() {
        List<User> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0];
                    String password = parts[1];
                    double wallet = Double.parseDouble(parts[2]);
                    User newUser = new User(username,password,wallet);
                    if(parts[3] != null){
                        newUser.setAdmin(true);
                    }
                    list.add(newUser);
                }
            }
        } catch (IOException e) {
            System.out.println("User file not found. Creating new file.");
        }
        return list;
    }

    public static void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getWallet());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users.");
        }
    }

    public static void saveBetLog(String username, String game, double amount, boolean win) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            String w = "Win";
            if(!win){
                w = "Lose";
            }

            writer.write(username + "," + game + "," + amount + "," + w);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error writing log.");
        }
    }

    public static List<String> readBetLogs() {
        List<String> logs = new ArrayList<>();
        try (BufferedReader reader= new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading logs.");
        }
        return logs;
    }
}
