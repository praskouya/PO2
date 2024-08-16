import java.util.Scanner;
import java.lang.Math; 

public class Main {
    public static void main(String args[]) {
        try {
            Scanner inputScanner = new Scanner(System.in);
            Scanner guessScanner = new Scanner(System.in);

            boolean playAgain = true;

            while (playAgain) {
                System.out.println("Enter the highest possible number to start the game (must be greater than 0): ");
                int NNN = inputScanner.nextInt();

                if (NNN < 1) {
                    System.out.println("Invalid input: The number must be greater than 0");
                    continue; 
                }

                int NN = (int)(Math.random() * NNN);

                System.out.println("Guess a number between 0 and " + NNN);

                int Guess = guessScanner.nextInt();
              int counter = 1;
                while (Guess != NN) {
                  counter++;
                    if (Guess < NN) {
                        System.out.println("The number is greater than your guess ");
                    } else if (Guess > NN) {
                        System.out.println("The number is smaller than your guess ");
                    }
                    System.out.println("Wrong guess! Try again: ");
                    Guess = guessScanner.nextInt();
                }
if (counter == 1) {
  System.out.println("Yaay! You won! The number was " + NN + ". You guessed it right away <3");
}
              else {
                System.out.println("Yaay! You won! The number was " + NN + ". You guessed it in " + counter + " tries");
              }
              System.out.println("Do you want to play again? (Y/N)");
                String playAgainResponse;
                boolean validResponse = false;
                while (!validResponse) {
                    playAgainResponse = inputScanner.next().toLowerCase();
                    if (playAgainResponse.equals("y") || playAgainResponse.equals("n")) {
                        validResponse = true;
                        playAgain = playAgainResponse.equals("y");
                    } else {
                        System.out.println("Invalid response. Please enter 'Y' or 'N'.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }
}
