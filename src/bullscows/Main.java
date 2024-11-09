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

        if (length > 10) {
            System.out.println("Error: can't generate a secret number with a length of " + length + " because there aren't enough unique digits.");
        } else {
            String secretCode = generateSecretCode(length);
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

    private static String generateSecretCode(int length) {
        StringBuilder secretCode = new StringBuilder();
        Set<Character> usedDigits = new HashSet<>();
        Random random = new Random();

        while(secretCode.length() < length) {
            int digit = random.nextInt(10);
            char digitChar = (char) (digit + '0');

            if (secretCode.isEmpty() && digit == 0) {
                continue;
            }
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