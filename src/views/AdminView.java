package views;

import Model.enums.Language;
import Model.enums.UserType;
import Model.users.User;
import core.utils.ConsolePrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AdminView {

    private final Scanner scanner;

    public AdminView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showMenu() {
        ConsolePrinter.printMenu("ADMIN MENU", new String[]{
                "Add user",
                "Remove user",
                "Update user",
                "View users",
                "View system logs",
                "Save data",
                "Load data",
                "Logout"
        });
        return readInt("Choose: ");
    }

    public UserType readUserType() {
        ConsolePrinter.printMenu("USER TYPE", new String[]{
                "Student",
                "Graduate student",
                "Teacher",
                "Admin",
                "Manager",
                "Tech support specialist",
                "Researcher"
        });

        return switch (readInt("Choose: ")) {
            case 1 -> UserType.STUDENT;
            case 2 -> UserType.GRADUATE_STUDENT;
            case 3 -> UserType.TEACHER;
            case 4 -> UserType.ADMIN;
            case 5 -> UserType.MANAGER;
            case 6 -> UserType.TECH_SUPPORT_SPECIALIST;
            case 7 -> UserType.RESEARCHER;
            default -> throw new IllegalArgumentException("Unknown user type");
        };
    }

    public int readUserId() {
        return readInt("User id: ");
    }

    public String readFullName() {
        return readRequired("Full name: ");
    }

    public String readEmail() {
        return readRequired("Email: ");
    }

    public String readPassword() {
        return readRequired("Password: ");
    }

    public String readPhoneNumber() {
        return readRequired("Phone number: ");
    }

    public Language readLanguage() {
        ConsolePrinter.printMenu("LANGUAGE", new String[]{"English", "Kazakh", "Russian"});
        int choice = readInt("Choose: ");
        Language[] languages = Language.values();
        if (choice < 1 || choice > languages.length) {
            throw new IllegalArgumentException("Unknown language");
        }
        return languages[choice - 1];
    }

    public String readFilename() {
        return readRequired("Filename: ");
    }

    public void showUsers(List<User> users) {
        List<List<String>> rows = new ArrayList<>();
        for (User user : users) {
            rows.add(Arrays.asList(
                    String.valueOf(user.getId()),
                    user.getFullName(),
                    user.getEmail(),
                    user.getClass().getSimpleName()
            ));
        }
        ConsolePrinter.printTable(Arrays.asList("ID", "Name", "Email", "Role"), rows);
    }

    public void showLogs(List<String> logs) {
        if (logs.isEmpty()) {
            ConsolePrinter.printInfo("Logs", "No data");
            return;
        }
        for (String log : logs) {
            System.out.println(log);
        }
    }

    public void showSuccess(String message) {
        ConsolePrinter.printSuccess(message);
    }

    public void showError(String message) {
        ConsolePrinter.printError(message);
    }

    private String readRequired(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            ConsolePrinter.printError("Value cannot be empty");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                ConsolePrinter.printError("Enter a valid number");
            }
        }
    }
}
