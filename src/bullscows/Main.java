package bullscows;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the length of the secret code: ");
        int length = scanner.nextInt();

        if (length > 10) {
            System.out.println("Error: can't generate a secret number with a length of " + length + " because there aren't enough unique digits.");
        } else {
            String secretCode = generateSecretCode(length);
            System.out.println("The random secret number is " + secretCode + ".");
        }
    }

    private static String generateSecretCode(int length) {
        StringBuilder secretCode = new StringBuilder();
        Set<Character> usedDigits = new HashSet<>();

        while(secretCode.length() < length) {
            long pseudoRandomNumber = System.nanoTime();
            String pseudoRandomString = new StringBuilder(Long.toString(pseudoRandomNumber)).reverse().toString();
            for (char digit : pseudoRandomString.toCharArray()) {
                if (secretCode.isEmpty() && digit == '0') {
                    continue;
                }
                if(!usedDigits.contains(digit) && Character.isDigit(digit)) {
                    secretCode.append(digit);
                    usedDigits.add(digit);
                    if(secretCode.length() == length) {
                        break;
                    }
                }
            }
        }
        return secretCode.toString();
    }

    private static void gradeGuess(String secretCode, String guess) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < secretCode.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            } else if (secretCode.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }

        if (bulls == 0 && cows == 0) {
            System.out.println("Grade: None. The secret code is " + secretCode + ".");
        } else {
            System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s). The secret code is " + secretCode + ".");
        }
    }
}