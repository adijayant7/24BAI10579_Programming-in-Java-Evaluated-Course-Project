package util;

import java.util.Scanner;

public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);

    // ANSI colors
    public static final String RESET   = "\u001B[0m";
    public static final String BOLD    = "\u001B[1m";
    public static final String RED     = "\u001B[31m";
    public static final String GREEN   = "\u001B[32m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String BLUE    = "\u001B[34m";
    public static final String CYAN    = "\u001B[36m";
    public static final String WHITE   = "\u001B[37m";
    public static final String BG_BLUE = "\u001B[44m";

    public static void printHeader(String title) {
        int width = 70;
        String border = "=".repeat(width);
        System.out.println(BLUE + BOLD + border + RESET);
        int pad = (width - title.length()) / 2;
        System.out.println(BLUE + BOLD + " ".repeat(pad) + title + RESET);
        System.out.println(BLUE + BOLD + border + RESET);
    }

    public static void printSubHeader(String title) {
        System.out.println(CYAN + BOLD + "\n--- " + title + " ---" + RESET);
    }

    public static void printSuccess(String msg) {
        System.out.println(GREEN + BOLD + "✔ " + msg + RESET);
    }

    public static void printError(String msg) {
        System.out.println(RED + BOLD + "✘ " + msg + RESET);
    }

    public static void printInfo(String msg) {
        System.out.println(YELLOW + "ℹ " + msg + RESET);
    }

    public static void printLine() {
        System.out.println(BLUE + "-".repeat(70) + RESET);
    }

    public static void printMenuOption(int num, String label) {
        System.out.printf(CYAN + "  [%d]" + RESET + " %s%n", num, label);
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(YELLOW + prompt + RESET);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                printError("Please enter a valid integer.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(YELLOW + prompt + RESET);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                printError("Please enter a valid number.");
            }
        }
    }

    public static String readString(String prompt) {
        System.out.print(YELLOW + prompt + RESET);
        return scanner.nextLine().trim();
    }

    public static boolean readYesNo(String prompt) {
        System.out.print(YELLOW + prompt + " (y/n): " + RESET);
        String ans = scanner.nextLine().trim().toLowerCase();
        return ans.equals("y") || ans.equals("yes");
    }

    public static void pause() {
        System.out.print(WHITE + "\nPress ENTER to continue..." + RESET);
        scanner.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
