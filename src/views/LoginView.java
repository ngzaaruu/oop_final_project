package views;

import core.utils.ConsolePrinter;

import java.util.Scanner;

public class LoginView {

    private final Scanner scanner;

    public LoginView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readEmail() {
        System.out.print("Email: ");
        return scanner.nextLine().trim();
    }

    public String readPassword() {
        System.out.print("Password: ");
        return scanner.nextLine().trim();
    }

    public void showLoginSuccess(String role) {
        ConsolePrinter.printSuccess("Logged in as " + role);
    }

    public void showError(String message) {
        ConsolePrinter.printError(message);
    }
}
