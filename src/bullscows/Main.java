package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please, enter the secret code's length: ");
        int length = scanner.nextInt();

        System.out.print("Input the number of possible symbols in the code: ");
        int symbols = scanner.nextInt();

        if (length > symbols || length > 36) {
            System.out.println("Error: can't generate a secret number with a length of " + length + " and " + symbols + " possible symbols.");
        } else {
            String secretCode = generateSecretCode(length, symbols);
            System.out.println("The secret is prepared: " + "*".repeat(length) + " (0-9, a-" + (char) ('a' + symbols - 11) + ").");
            System.out.println("Okay, let's start a game!");

            int turn = 1;
            while(true) {
                System.out.println("Turn " + turn + ":");
                String guess = scanner.next();

                int[] result = gradeGuess(secretCode, guess);
                int bulls = result[0];
                int cows = result[1];

                if( bulls == length) {
                    System.out.println("Grade: " + bulls + " bull(s)");
                    System.out.println("Congratulations! You guessed the secret code.");
                    break;
                } else {
                    System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s)");
                }
                turn++;
            }
        }
    }

    private static String generateSecretCode(int length, int symbols) {
        StringBuilder secretCode = new StringBuilder();
        Set<Character> usedDigits = new HashSet<>();
        Random random = new Random();

        String possibleSymbols = "0123456789abcdefghijklmnopqrstuvwxyz".substring(0, symbols);

        while(secretCode.length() < length) {
            char digitChar = possibleSymbols.charAt(random.nextInt(symbols));

            if(!usedDigits.contains(digitChar)) {
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