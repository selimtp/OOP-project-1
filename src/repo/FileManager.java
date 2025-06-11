package repo;

import model.*;

import java.time.LocalDateTime;
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
                    if(Boolean.parseBoolean(parts[3])){
                        newUser.setAdmin(true);
                    }
                    list.add(newUser);
                }
            }
        } catch (IOException e) {
            System.out.println("User repo not found. Creating new repo.");
        }
        return list;
    }

    public static void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getWallet()+","+user.isAdmin());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users.");
        }
    }
    public static List<String> readUserActionLogs() {
        List<String> logs = new ArrayList<>();
        try (Scanner scanner = new Scanner(new java.io.File(logFile))) {
            while (scanner.hasNextLine()) {
                logs.add(scanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading user action logs.");
        }
        return logs;
    }
    public static void logAction(String username, String action) {
        String timestamp = LocalDateTime.now().toString();
        String logEntry = timestamp + " | " + username + " | " + action;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing action log.");
        }
    }
    public static String encryption(String str){
        Random random = new Random();
        int rnd = random.nextInt(10,50);
        String result = String.valueOf(rnd);

        for(int i = 0; i < str.length(); i++){
            result +=  (char) (str.charAt(i) + rnd);
        }
        return result;

    }
    public static String decryption(String enStr){
        String result = "";
        String strKey = "";
        strKey += enStr.charAt(0);
        strKey += enStr.charAt(1);
        int key = Integer.parseInt(strKey);

        for(int i = 2; i < enStr.length(); i++){
            result += (char)(enStr.charAt(i) - key);
        }
        return result;

    }


//    public static void saveBetLog(String username, String service.gameService, double amount, boolean win) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
//            String w = "Win";
//            if(!win){
//                w = "Lose";
//            }
//
//            writer.write(username + "," + service.gameService + "," + amount + "," + w);
//            writer.newLine();
//
//        } catch (IOException e) {
//            System.out.println("Error writing log.");
//        }
//    }
//
//    public static List<String> readBetLogs() {
//        List<String> logs = new ArrayList<>();
//        try (BufferedReader reader= new BufferedReader(new FileReader(logFile))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                logs.add(line);
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading logs.");
//        }
//        return logs;
//    }
}
