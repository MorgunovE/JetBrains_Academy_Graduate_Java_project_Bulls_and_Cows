package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String secretCode = "9305";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your guess: ");
        String guess = scanner.nextLine();

        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < 4; i++) {
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