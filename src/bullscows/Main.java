package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input the length of the secret code: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error: \"abc 0 -7\" isn't a valid number.");
            return;
        }
        int length = scanner.nextInt();

        if (length <= 0) {
            System.out.println("Error: length of the secret code must be greater than 0.");
            return;
        }

        System.out.print("Input the number of possible symbols in the code: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error: \"abc 0 -7\" isn't a valid number.");
            return;
        }
        int symbols = scanner.nextInt();

        if (symbols < length) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + symbols + " unique symbols.");
            return;
        }

        if (symbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        String secretCode = generateSecretCode(length, symbols);
        System.out.println("The secret is prepared: " + "*".repeat(length) + " (0-9, a-" + (char) ('a' + symbols - 11) + ").");
        System.out.println("Okay, let's start a game!");

        int turn = 1;
        while (true) {
            System.out.println("Turn " + turn + ":");
            String guess = scanner.next();

            if (guess.length() != length) {
                System.out.println("Error: your guess must be " + length + " characters long.");
                return;
            }

            if (!isValidGuess(guess, symbols)) {
                System.out.println("Error: your guess contains invalid symbols.");
                return;
            }

            int[] result = gradeGuess(secretCode, guess);
            int bulls = result[0];
            int cows = result[1];

            if (bulls == length) {
                System.out.println("Grade: " + bulls + " bull(s)");
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else {
                System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s)");
            }
            turn++;
        }
    }

    private static boolean isValidGuess(String guess, int symbols) {
        String possibleSymbols = "0123456789abcdefghijklmnopqrstuvwxyz".substring(0, symbols);
        for (char c : guess.toCharArray()) {
            if (possibleSymbols.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    private static String generateSecretCode(int length, int symbols) {
        StringBuilder secretCode = new StringBuilder();
        Set<Character> usedDigits = new HashSet<>();
        Random random = new Random();

        String possibleSymbols = "0123456789abcdefghijklmnopqrstuvwxyz".substring(0, symbols);

        while (secretCode.length() < length) {
            char digitChar = possibleSymbols.charAt(random.nextInt(symbols));

            if (!usedDigits.contains(digitChar)) {
                secretCode.append(digitChar);
                usedDigits.add(digitChar);
            }
        }
        return secretCode.toString();
    }

    private static int[] gradeGuess(String secretCode, String guess) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < secretCode.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            } else if (secretCode.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }

        return new int[]{bulls, cows};
    }
}