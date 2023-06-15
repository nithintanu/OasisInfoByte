import java.util.Random;
import javax.swing.JOptionPane;

public class GuessTheNumberGame {
    public static void main(String[] args) {
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 5;
        int score = 0;
        
        JOptionPane.showMessageDialog(null, "Welcome to Guess the Number game!");
        
        while (true) {
            int randomNumber = generateRandomNumber(minRange, maxRange);
            int attempts = 0;
            boolean guessedCorrectly = false;
            
            JOptionPane.showMessageDialog(null, "I've generated a number between " + minRange + " and " + maxRange +
                    ". Try to guess it!");
            
            while (attempts < maxAttempts) {
                String guessStr = JOptionPane.showInputDialog("Enter your guess:");
                
                if (guessStr == null) {
                    JOptionPane.showMessageDialog(null, "Game aborted. Thank you for playing!");
                    System.exit(0);
                }
                
                int guess = Integer.parseInt(guessStr);
                
                if (guess == randomNumber) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number correctly!");
                    score += (maxAttempts - attempts) * 10; // Assigning points based on attempts
                    guessedCorrectly = true;
                    break;
                } else if (guess < randomNumber) {
                    JOptionPane.showMessageDialog(null, "Your guess is too low. Try again!");
                } else {
                    JOptionPane.showMessageDialog(null, "Your guess is too high. Try again!");
                }
                
                attempts++;
            }
            
            if (!guessedCorrectly) {
                JOptionPane.showMessageDialog(null, "Sorry, you ran out of attempts. The number was: " + randomNumber);
            }
            
            int playAgain = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again",
                    JOptionPane.YES_NO_OPTION);
            
            if (playAgain != JOptionPane.YES_OPTION) {
                break;
            }
        }
        
        JOptionPane.showMessageDialog(null, "Game over! Your score is: " + score);
    }
    
    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
