package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * The Main class implements the Bulls and Cows game.
 * The game involves two players: one who sets a secret code and another who tries to guess it.
 * The code consists of unique digits, and the guess is graded based on the number of bulls and cows.
 */
public class Main {
    /**
     * The main method is the entry point of the program.
     * It handles user input for the length of the secret code and the number of possible symbols,
     * generates the secret code, and manages the game loop where the user guesses the code.
     *
     * @param args Command line arguments (not used).
     */
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

    /**
     * Checks if the guess contains only valid symbols.
     *
     * @param guess   The guessed code.
     * @param symbols The number of possible symbols.
     * @return true if the guess is valid, false otherwise.
     */
    private static boolean isValidGuess(String guess, int symbols) {
        String possibleSymbols = "0123456789abcdefghijklmnopqrstuvwxyz".substring(0, symbols);
        for (char c : guess.toCharArray()) {
            if (possibleSymbols.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a secret code of the specified length using the specified number of symbols.
     *
     * @param length  The length of the secret code.
     * @param symbols The number of possible symbols.
     * @return The generated secret code.
     */
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

    /**
     * Grades the guess by comparing it to the secret code.
     *
     * @param secretCode The secret code.
     * @param guess      The guessed code.
     * @return An array where the first element is the number of bulls and the second element is the number of cows.
     */
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