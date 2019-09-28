package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvilHangman {

    public static void main(String[] args) throws IOException {
        File dictFile = new File(args[0]);
        EvilHangmanGame theGame = new EvilHangmanGame(Integer.parseInt(args[2]), Integer.parseInt((args[1])));
        try {
            theGame.startGame(dictFile, Integer.parseInt(args[1]));
            while (theGame.numGuesses > 0) {
                System.out.println("You have " + theGame.numGuesses + " guesses left");
                System.out.print("Used Letters: ");
                for (char c : theGame.guessedLetters) {
                    System.out.print(c + " ");
                }
                System.out.print('\n');
                System.out.println("Word: " + theGame.currentWord);
                System.out.print("Enter guess: ");
                char nextGuess;
                Scanner getChar = new Scanner(System.in);
                String input = getChar.nextLine();
                input.toLowerCase();
                if (input.length() != 1) {
                    System.out.println("Invalid Input");
                    continue;
                }
                nextGuess = input.charAt(0);
                if (!Character.isAlphabetic(nextGuess)) {
                    System.out.println("Invalid Input");
                    continue;
                }
                try {
                    theGame.makeGuess(nextGuess);
                }
                catch (GuessAlreadyMadeException ex){
                    System.out.println("Guess already made");
                    continue;
                }
                if (theGame.foundNewLetter) {
                    System.out.println("Yes, there is " + theGame.numNewLetters + " " + nextGuess);
                    if (theGame.currentWord.indexOf('-') == -1) {
                        System.out.println("You Win!");
                        System.out.println("The word was: " + theGame.currentWord);
                        break;
                    }
                }
                else {
                    System.out.println("Sorry, there are no " + input + "'s");
                }
                System.out.print('\n');
                theGame.numGuesses--;
            }
            if (theGame.currentWord.indexOf('-') != -1) {
                System.out.println("You Lose!");
                String bsWord;
                bsWord = (String) theGame.possibleWords.toArray()[0];
                System.out.println("The word was: " + bsWord);
            }
        }
        catch (EmptyDictionaryException ex) {
            ex.printStackTrace();
        }
    }

}
