package repo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogManager{
    private static final String logFile = "logs.txt";

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
    public static void showLogs() {
        List<String> logs = readUserActionLogs();
        System.out.println("=== User Action Logs ===");
        logs.forEach(System.out::println);
    }

    public static void showUserActionLogs(String username) {
        List<String> logs = readUserActionLogs();
        boolean found = false;

        System.out.println("=== Action Logs for " + username + " ===");
        for (String log : logs) {
            String[] parts = log.split(" \\| ");
            if (parts.length >= 2 && parts[1].trim().equalsIgnoreCase(username)) {
                System.out.println(log);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No action logs found for user.");
        }
    }
}
