package spell;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SpellCorrector implements ISpellCorrector {
    Trie dictionary;
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File inFile;
        inFile = new File(dictionaryFileName);
        Scanner sc = new Scanner(inFile);
        dictionary = new Trie();
        while (sc.hasNext()) {
            dictionary.add(sc.next());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        Set<String> dist1;
        if (dictionary.find(inputWord) != null) {
            return inputWord;
        }
        else {
            dist1 = dictionary.editDist1(inputWord);
            Set<String> dist2 = new HashSet<String>(dist1);
            for (String s: dist1) {
                if (dictionary.find(s) == null) {
                    dist2.remove(s);
                }
            }
            if (dist2.isEmpty()) {
                for (String s: dist1) {
                    dist2.addAll(dictionary.editDist1(s));
                }
                Set<String> distCheck2 = new HashSet<String>(dist2);
                for (String s: dist2) {
                    if (dictionary.find(s) == null) {
                        distCheck2.remove(s);
                    }
                }
                if (distCheck2.isEmpty()) {
                    return null;
                }
                else if (distCheck2.size() == 1) {
                    StringBuilder whatevs = new StringBuilder(distCheck2.toString());
                    whatevs.deleteCharAt(whatevs.length() - 1);     //Deletes brackets around word
                    whatevs.deleteCharAt(0);

                    return whatevs.toString();
                }
                else {
                    int highestWordCount = 0;
                    Set<String> dist2Check = new HashSet<>();

                    for (String s : dist2) {
                        if (dictionary.getWordCount(s) == highestWordCount) {
                            dist2Check.add(s);
                        } else if (dictionary.getWordCount(s) > highestWordCount) {
                            dist2Check = new HashSet<String>();
                            dist2Check.add(s);
                            highestWordCount = dictionary.getWordCount(s);
                        }
                    }
                    dist2 = dist2Check;
                    if (dist2.size() == 1) {
                        StringBuilder whatevs = new StringBuilder(dist2.toString());
                        whatevs.deleteCharAt(whatevs.length() - 1);     //Deletes brackets around word
                        whatevs.deleteCharAt(0);

                        return whatevs.toString();
                    }
                    else {
                        String firstAlpha = "zzzzzzzzzzzzzzzzzz";       //Initialized as something reasonably expected to be last
                        for (String s : dist2) {
                            if (firstAlpha.compareTo(s) > 0) {
                                firstAlpha = s;
                            }
                        }
                        return firstAlpha;
                    }
                }
            }
            else if (dist2.size() == 1) {
                StringBuilder whatevs = new StringBuilder(dist2.toString());
                whatevs.deleteCharAt(whatevs.length() - 1);     //Deletes brackets around word
                whatevs.deleteCharAt(0);

                return whatevs.toString();
            }
            else {
                int highestWordCount = 0;
                Set<String> dist2Check = new HashSet<>();

                for (String s : dist2) {
                    if (dictionary.getWordCount(s) == highestWordCount) {
                        dist2Check.add(s);
                    } else if (dictionary.getWordCount(s) > highestWordCount) {
                        dist2Check = new HashSet<String>();
                        dist2Check.add(s);
                        highestWordCount = dictionary.getWordCount(s);
                    }
                }
                dist2 = dist2Check;
                if (dist2.size() == 1) {
                    StringBuilder whatevs = new StringBuilder(dist2.toString());
                    whatevs.deleteCharAt(whatevs.length() - 1);     //Deletes brackets around word
                    whatevs.deleteCharAt(0);

                    return whatevs.toString();
                }
                else {
                    String firstAlpha = "zzzzzzzzzzzzzzzzzz";       //Initialized as something reasonably expected to be last
                    for (String s : dist2) {
                        if (firstAlpha.compareTo(s) > 0) {
                            firstAlpha = s;
                        }
                    }
                    return firstAlpha;
                }
            }
        }
    }
}
