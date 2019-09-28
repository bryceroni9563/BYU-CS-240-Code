package spell;

import java.util.HashSet;
import java.util.Set;

public class Trie implements ITrie {
    Node root;
    int wordCount;
    int nodeCount;
    public Trie() {
        root = new Node();
        wordCount = 0;
        nodeCount = 1;
    }

    @Override
    public void add(String word) {
        word = word.toLowerCase();
        int numNewNodes = 0;
        numNewNodes = root.addWord(word, 0, numNewNodes);
        if (root.find(word,0).wordCount == 1) {
            wordCount++;
        }
        nodeCount = nodeCount + numNewNodes;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        String wordSoFar = "";

        root.recursiveToString(wordSoFar, toReturn);
        return toReturn.toString();
    }

    public int hashCode() {
        int toReturn = 0;
        toReturn = root.recursiveHash(toReturn);
        return toReturn;
    }

    @Override
    public INode find(String word) {
        word = word.toLowerCase();
        if (word.length() < 1) {
            return null;
        }
        return root.find(word, 0);
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    public boolean equals(Object otherTrie) {
        if (this == otherTrie) {
            return true;
        }
        else if (otherTrie == null || !(otherTrie instanceof Trie)) {
            return false;
        }
        boolean isEqualTrie = root.recursiveEquals(((Trie) otherTrie).root,true);
        return isEqualTrie;
    }

    public Set<String> editDist1(String inputWord) {
        Set<String> dist1 = new HashSet<>();
        for (int i = 0; i < inputWord.length(); i++) { // Deletion
            StringBuilder goober = new StringBuilder(inputWord);
            String newString = goober.deleteCharAt(i).toString();
            dist1.add(newString);
        }
        for (int i = 0; i < inputWord.length() - 1; i++) { // Transposition
            char ch[] = inputWord.toCharArray();
            char temp = ch[i];
            ch[i] = ch[i + 1];
            ch[i + 1] = temp;
            StringBuilder sb = new StringBuilder();
            for (char c: ch) {
                sb.append(c);
            }
            String newString = sb.toString();
            dist1.add(newString);
        }
        for (int i = 0; i < inputWord.length(); i++) { // Alteration
            char ch[] = inputWord.toCharArray();
            for (int j = 0; j < 26; j++) {
                if ((char)('a' + j) == ch[i]) {

                }
                else {
                    ch[i] = (char)('a' + j);
                    StringBuilder sb = new StringBuilder();
                    for (char c: ch) {
                        sb.append(c);
                    }
                    String newString = sb.toString();
                    dist1.add(newString);
                }
            }
        }
        for (int i = 0; i <= inputWord.length(); i++) { // Insertion
            for (int k = 0; k < 26; k++) {
                StringBuilder sb = new StringBuilder(inputWord);
                sb.insert(i,(char)('a' + k));
                String newString = sb.toString();
                dist1.add(newString);
            }
        }
        return dist1;
    }

    public int getWordCount(String word) {
        return root.getWordCount(word, 0);
    }
}
