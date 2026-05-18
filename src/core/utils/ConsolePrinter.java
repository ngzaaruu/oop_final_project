package core.utils;

import java.util.ArrayList;
import java.util.List;

public final class ConsolePrinter {

    private ConsolePrinter() {
    }

    public static void printHeader(String title) {
        String text = " " + title + " ";
        int width = Math.max(30, text.length() + 6);
        System.out.println("+" + repeat("=", width) + "+");
        System.out.println("|" + center(text, width) + "|");
        System.out.println("+" + repeat("=", width) + "+");
    }

    public static void printTable(List<String> headers, List<List<String>> rows) {
        if (headers == null || headers.isEmpty()) {
            printError("Table headers are empty");
            return;
        }

        List<Integer> widths = new ArrayList<>();
        for (String header : headers) {
            widths.add(safe(header).length());
        }

        if (rows != null) {
            for (List<String> row : rows) {
                for (int i = 0; i < headers.size(); i++) {
                    String value = i < row.size() ? row.get(i) : "";
                    widths.set(i, Math.max(widths.get(i), safe(value).length()));
                }
            }
        }

        printRow(headers, widths);
        printSeparator(widths);

        if (rows == null || rows.isEmpty()) {
            printInfo("Rows", "No data");
            return;
        }

        for (List<String> row : rows) {
            printRow(row, widths);
        }
    }

    public static void printSuccess(String message) {
        System.out.println("OK: " + message);
    }

    public static void printError(String message) {
        System.out.println("Error: " + message);
    }

    public static void printMenu(String title, String[] options) {
        printHeader(title);
        for (int i = 0; i < options.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + options[i]);
        }
        printSeparator();
    }

    public static void printSeparator() {
        System.out.println("-----------------------------");
    }

    public static void printInfo(String label, String value) {
        System.out.println(label + ": " + value);
    }

    private static void printRow(List<String> values, List<Integer> widths) {
        StringBuilder row = new StringBuilder("|");
        for (int i = 0; i < widths.size(); i++) {
            String value = i < values.size() ? values.get(i) : "";
            row.append(" ").append(padRight(value, widths.get(i))).append(" |");
        }
        System.out.println(row);
    }

    private static void printSeparator(List<Integer> widths) {
        StringBuilder line = new StringBuilder("+");
        for (int width : widths) {
            line.append(repeat("-", width + 2)).append("+");
        }
        System.out.println(line);
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    private static String repeat(String value, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(value);
        }
        return builder.toString();
    }

    private static String center(String value, int width) {
        int padding = Math.max(0, width - value.length());
        int left = padding / 2;
        int right = padding - left;
        return repeat(" ", left) + value + repeat(" ", right);
    }

    private static String padRight(String value, int width) {
        return safe(value) + repeat(" ", Math.max(0, width - safe(value).length()));
    }
}

