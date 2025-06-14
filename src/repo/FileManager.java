package repo;

import model.*;

import java.util.*;
import java.io.*;

public class FileManager{
    private static final String userFile = "users.txt";

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





}
