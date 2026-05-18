package views;

import Model.communication.Request;
import Model.enums.RequestStatus;
import core.utils.ConsolePrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TechSupportView {

    private final Scanner scanner;

    public TechSupportView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showMenu() {
        ConsolePrinter.printMenu("TECH SUPPORT MENU", new String[]{
                "View new requests",
                "Accept request",
                "Reject request",
                "Mark as done",
                "View requests by status",
                "Back"
        });
        return readInt("Choose: ");
    }

    public void showRequests(List<Request> requests) {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            rows.add(Arrays.asList(
                    String.valueOf(i + 1),
                    request.getSender().getFullName(),
                    request.getDescription(),
                    request.getStatus().name()
            ));
        }
        ConsolePrinter.printTable(Arrays.asList("#", "Sender", "Description", "Status"), rows);
    }

    public int readRequestIndex() {
        return readInt("Request number: ") - 1;
    }

    public String readReason() {
        System.out.print("Reason: ");
        return scanner.nextLine().trim();
    }

    public RequestStatus readStatus() {
        ConsolePrinter.printMenu("REQUEST STATUS", new String[]{"NEW", "VIEWED", "ACCEPTED", "REJECTED", "DONE"});
        int choice = readInt("Choose: ");
        return switch (choice) {
            case 1 -> RequestStatus.NEW;
            case 2 -> RequestStatus.VIEWED;
            case 3 -> RequestStatus.ACCEPTED;
            case 4 -> RequestStatus.REJECTED;
            case 5 -> RequestStatus.DONE;
            default -> throw new IllegalArgumentException("Unknown status");
        };
    }

    public void showSuccess(String message) {
        ConsolePrinter.printSuccess(message);
    }

    public void showError(String message) {
        ConsolePrinter.printError(message);
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

