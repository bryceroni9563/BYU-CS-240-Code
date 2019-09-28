package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {

    SortedSet<Character> guessedLetters;
    int numGuesses;
    Set<String> possibleWords = new TreeSet<String>();
    Map<String,Set<String>> patternSets;
    String currentWord;
    boolean foundNewLetter;
    int numNewLetters;

    public EvilHangmanGame() {
        guessedLetters = new TreeSet<>();
        patternSets = new HashMap<>();
        numGuesses = 0;

    }

    public EvilHangmanGame(int initGuesses, int wordSize) {
        guessedLetters = new TreeSet<>();
        patternSets = new HashMap<>();
        numGuesses = initGuesses;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordSize; i++) {
            sb.append('-');
        }
        currentWord = sb.toString();
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        Scanner sc = new Scanner(dictionary);
        possibleWords = new TreeSet<>();
        guessedLetters = new TreeSet<>();
        while (sc.hasNext()) {
            String nextWord = sc.next();
            if (nextWord.length() == wordLength) {
                possibleWords.add(nextWord);
            }
        }
        if (possibleWords.size() == 0) {
            throw new EmptyDictionaryException();
        }

    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        patternSets = new HashMap<>();
        foundNewLetter = false;
        numNewLetters = 0;
        guess = Character.toLowerCase(guess);
        if (guessedLetters.contains(guess)) {
            throw new GuessAlreadyMadeException();
        } else {
            guessedLetters.add(guess);
        }
        patternSets = new HashMap<>();
        for (String s : possibleWords) {
            StringBuilder sb = new StringBuilder();

            char[] chA = s.toCharArray();
            for (char c : chA) {
                if (c == guess) {
                    sb.append(c);
                } else if (guessedLetters.isEmpty()) {
                    sb.append('-');
                } else if (guessedLetters.contains(c)) {
                    sb.append(c);
                } else {
                    sb.append('-');
                }
            }
            if (patternSets.isEmpty()) {
                Set<String> newPattern = new TreeSet<String>();
                newPattern.add(s);
                patternSets.put(sb.toString(), newPattern);
            } else if (patternSets.containsKey(sb.toString())) {
                patternSets.get(sb.toString()).add(s);
            } else {
                Set<String> newPattern = new TreeSet<String>();
                newPattern.add(s);
                patternSets.put(sb.toString(), newPattern);
            }
        }

        possibleWords = new TreeSet<String>();
        String possWrdKey = "";
        for (Map.Entry entry : patternSets.entrySet()) {
            Set<String> toCheck = (Set<String>) entry.getValue();
            if (toCheck.size() > possibleWords.size()) {
                possibleWords = toCheck;
                possWrdKey = (String) entry.getKey();
            }
            else if (toCheck.size() == possibleWords.size()) {
                String thisKey = (String) entry.getKey();
                if (possWrdKey.indexOf(guess) == -1) {
                    //Do nothing, possibleWords does not contain the guess, and so should be the one to keep
                }
                else if (thisKey.indexOf(guess) == -1) {
                    possibleWords = toCheck;
                    possWrdKey = (String) entry.getKey();
                }
                else {
                    char[] charAPossWrd = possWrdKey.toCharArray();
                    char[] charAToChk = thisKey.toCharArray();
                    int possWrdBlanks = 0;
                    int toChkBlanks = 0;
                    for (int i = 0; i < charAPossWrd.length; i++) {
                        if (charAPossWrd[i] == '-') {
                            possWrdBlanks++;
                        }
                        if (charAToChk[i] == '-') {
                            toChkBlanks++;
                        }
                    }
                    if (toChkBlanks > possWrdBlanks) {
                        possibleWords = toCheck;
                        possWrdKey = (String) entry.getKey();
                    }
                    else if (toChkBlanks == possWrdBlanks) {
                        for (int i = charAPossWrd.length - 1; i >= 0; i--) {
                            if (charAPossWrd[i] == '-' && charAToChk[i] != '-') {
                                possibleWords = toCheck;
                                possWrdKey = (String) entry.getKey();
                                break;
                            }
                            else if (charAToChk[i] == '-' && charAPossWrd[i] != '-') {
                                break;
                            }
                        }
                    }
                }
            }
        }
        currentWord = possWrdKey;
        char[] wordSoFar = currentWord.toCharArray();
        for (char c : wordSoFar) {
            if (c == guess) {
                foundNewLetter = true;
                numNewLetters++;
            }
        }
        return possibleWords;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }
}
